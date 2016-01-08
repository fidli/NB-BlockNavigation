/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.fidli.blockNavigation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

@ActionID(
	category = "Edit",
	id = "eu.fidli.blockNavigation.Downwards"
)
@ActionRegistration(
	displayName = "#CTL_Downwards"
)
@ActionReferences({
    @ActionReference(path = "Menu/GoTo", position = 1176),
    @ActionReference(path = "Shortcuts", name = "D-DOWN")
})
@Messages("CTL_Downwards=Downward jump to another code chunk")
public final class Downwards extends Jump{
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
		    int lastNewline = cursorPos;
			while(cursorPos < textLen-1){
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
						    cursorPos = lastNewline;
						    break;
						}
						else{
						    wantChars = true;
						}
					}
					lastNewline = cursorPos;
				}
				keptWhitespace = keptWhitespace && isWhitespace;
				if(!isWhitespace){
				    keptWhitespace = false;
				    seenChars = true;
				    if(wantChars){
					break;
				    }
				}
			    
			    cursorPos++;
			}
				
			 textComponent.getCaret().setDot(cursorPos);


		} catch (BadLocationException ex) {
		    Exceptions.printStackTrace(ex);
		}
	}

}
