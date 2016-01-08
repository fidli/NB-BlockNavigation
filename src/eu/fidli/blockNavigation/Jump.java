/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.fidli.blockNavigation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JEditorPane;
import javax.swing.text.JTextComponent;
import org.openide.cookies.EditorCookie;
import org.openide.windows.TopComponent;

/**
 *
 * @author Fidli
 */
abstract public class Jump implements ActionListener{
    
    @Override
    public void actionPerformed(ActionEvent e) {
	TopComponent active = TopComponent.getRegistry().getActivated();
	String className = active.getClass().getName();
	//we are editor
	if(className.equals("org.netbeans.core.multiview.MultiViewCloneableTopComponent")){
                 EditorCookie edit = (active.getActivatedNodes()[0]).getCookie(EditorCookie.class);
		 JEditorPane[] panes = edit.getOpenedPanes();
                 if (panes != null) {
                     for (int i = 0; i < panes.length; i++) {
                         if (active.isAncestorOf(panes[i])) {
			     doEditorAction(panes[i]);
                             break;
                         }
                     }
                 }
	}
    }
    
    abstract protected void doEditorAction(final JTextComponent textComponent);
    
}
