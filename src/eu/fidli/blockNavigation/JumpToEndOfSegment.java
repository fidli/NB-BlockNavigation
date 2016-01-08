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

@ActionID(
	category = "Edit",
	id = "eu.fidli.blockNavigation.JumpToEndOfSegment"
)
@ActionRegistration(
	displayName = "#CTL_JumpToEndOfSegment"
)
@ActionReferences({
    @ActionReference(path = "Menu/GoTo", position = 1188),
    @ActionReference(path = "Shortcuts", name = "O-RIGHT")
})
@Messages("CTL_JumpToEndOfSegment=Jump to end of segment")
public final class JumpToEndOfSegment extends Jump {

    @Override
	protected void doEditorAction(final JTextComponent textComponent){
	    try {
		    int cursorPos = textComponent.getCaret().getDot();
		    int textLen = textComponent.getDocument().getLength();
		    String text = textComponent.getDocument().getText(0, textLen);
		    
			boolean seenChars = false;
			boolean wasWhitespace = false;
			while(cursorPos < textLen-1){
				char currentCharacter = text.charAt(cursorPos);
				boolean isWhitespace = (currentCharacter == '\r' || currentCharacter == '\t' || currentCharacter == ' ' || currentCharacter == '\n');
				if(isWhitespace){
					wasWhitespace = true;
					if(seenChars){
					    break;
					}
				}else{
				    if(wasWhitespace){
					break;
				    }
				    seenChars = true;
				}
			    
			    cursorPos++;
			}
				
			 textComponent.getCaret().setDot(cursorPos);


		} catch (BadLocationException ex) {
		    Exceptions.printStackTrace(ex);
		}
	}
}
