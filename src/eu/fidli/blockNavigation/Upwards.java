/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.fidli.blockNavigation;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditorCookie;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@ActionID(
	category = "Edit",
	id = "eu.fidli.blockNavigation.Upwards"
)
@ActionRegistration(
	displayName = "#CTL_Upwards"
)
@ActionReferences({
    @ActionReference(path = "Menu/GoTo", position = 1175),
    @ActionReference(path = "Shortcuts", name = "D-UP")
})
@Messages("CTL_Upwards=Upward jump to another code chunk")
public final class Upwards extends Jump {

 @Override
	protected void doEditorAction(final JTextComponent textComponent){
	    try {
		    int cursorPos = textComponent.getCaret().getDot();
		    int textLen = textComponent.getDocument().getLength();
		    String text = textComponent.getDocument().getText(0, textLen);
		    
		    boolean wantChars = false;
		    boolean wasEmptyLine = false;
		    boolean keptWhitespace = false;
		    boolean firstLineEnd = false;
		    boolean seenChars = false;
		    int lastCharacterPos = cursorPos;
			while(cursorPos > 0){
				cursorPos--;
				char currentCharacter = text.charAt(cursorPos);
				boolean isWhitespace = (currentCharacter == '\r' || currentCharacter == '\t' || currentCharacter == ' ' || currentCharacter == '\n');
				boolean isNewline = currentCharacter == '\n';
				if(isNewline){
					if(!firstLineEnd){
					    firstLineEnd = true;
					}else{
					    if(keptWhitespace){
						wasEmptyLine = true;
					    }
					}
					keptWhitespace = true;
					if(wasEmptyLine){
						if(seenChars){
						    cursorPos = lastCharacterPos;
						    break;
						}
						else{
						    wantChars = true;
						}
					}
				}
				keptWhitespace = keptWhitespace && isWhitespace;
				if(!isWhitespace){
				    keptWhitespace = false;
				    seenChars = true;
				    lastCharacterPos = cursorPos;
				    if(wantChars){
					cursorPos++;
					break;
				    }
				}
			    
			}
				
			 textComponent.getCaret().setDot(cursorPos);


		} catch (BadLocationException ex) {
		    Exceptions.printStackTrace(ex);
		}
	}
}
