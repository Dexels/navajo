/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Nov 2, 2007
 */
package net.atlanticbb.tantlinger.ui.text.actions;

import java.awt.event.ActionEvent;

import javax.swing.JEditorPane;


/**
 * Action suitable for when wysiwyg or source context does not matter.
 * 
 * @author Bob Tantlinger
 *
 */
public abstract class BasicEditAction extends HTMLTextEditAction
{

    /**
     * @param name
     */
    public BasicEditAction(String name)
    {
        super(name);        
    }

    /* (non-Javadoc)
     * @see net.atlanticbb.tantlinger.ui.text.actions.HTMLTextEditAction#sourceEditPerformed(java.awt.event.ActionEvent, javax.swing.JEditorPane)
     */
    protected final void sourceEditPerformed(ActionEvent e, JEditorPane editor)
    {        
        doEdit(e, editor);
    }

    /* (non-Javadoc)
     * @see net.atlanticbb.tantlinger.ui.text.actions.HTMLTextEditAction#wysiwygEditPerformed(java.awt.event.ActionEvent, javax.swing.JEditorPane)
     */
    protected final void wysiwygEditPerformed(ActionEvent e, JEditorPane editor)
    {        
        doEdit(e, editor);
    }
    
    protected abstract void doEdit(ActionEvent e, JEditorPane editor);
    
    protected void updateContextState(JEditorPane editor)
    {
        
    }
    
    protected final void updateWysiwygContextState(JEditorPane wysEditor)
    {
        updateContextState(wysEditor);
    }
    
    protected final void updateSourceContextState(JEditorPane srcEditor)
    {
        updateContextState(srcEditor);
    }    
}
