/*
 * Created on Jan 24, 2006
 *
 */
package net.atlanticbb.tantlinger.ui.text.actions;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JEditorPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import net.atlanticbb.tantlinger.ui.text.dialogs.TextFinderDialog;


public class FindReplaceAction extends BasicEditAction
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private boolean isReplaceTab;    
    private TextFinderDialog dialog;
        
    
    public FindReplaceAction(boolean isReplace)
    {
        super(null);
        if(isReplace)
        {           
            putValue(NAME, i18n.str("replace_")); //$NON-NLS-1$
            putValue(MNEMONIC_KEY, new Integer(i18n.mnem("replace_")));
        }
        else
        {           
            putValue(NAME, i18n.str("find_")); //$NON-NLS-1$
            putValue(MNEMONIC_KEY, new Integer(i18n.mnem("find_")));
            putValue(ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK));
        }
                
        isReplaceTab = isReplace;
    }

    /* (non-Javadoc)
     * @see net.atlanticbb.tantlinger.ui.text.actions.BasicEditAction#doEdit(java.awt.event.ActionEvent, javax.swing.JEditorPane)
     */
    protected void doEdit(ActionEvent e, JEditorPane textComponent)
    {
        Component c = SwingUtilities.getWindowAncestor(textComponent);  
        if(dialog == null)
        {
            if(c instanceof Frame)
            {           
                if(isReplaceTab)
                dialog = new TextFinderDialog((Frame)c, textComponent, TextFinderDialog.REPLACE);
                else
                dialog = new TextFinderDialog((Frame)c, textComponent, TextFinderDialog.FIND);
            }
            else if(c instanceof Dialog)
            {           
                if(isReplaceTab)
                dialog = new TextFinderDialog((Dialog)c, textComponent, TextFinderDialog.REPLACE);
                else
                dialog = new TextFinderDialog((Dialog)c, textComponent, TextFinderDialog.FIND);
            }
            else 
                return;
        }
        
        //if(textComponent.getSelectionStart() != textComponent.getSelectionEnd())
        //  dialog.setSearchText(textComponent.getSelectedText());
        
        if(!dialog.isVisible())
        {
            dialog.show((isReplaceTab) ? TextFinderDialog.REPLACE : TextFinderDialog.FIND);
        }
    }
    
    protected void updateContextState(JEditorPane editor)
    {
        if(dialog != null)
        {
            dialog.setJTextComponent(editor);
        }
    }
    
}
