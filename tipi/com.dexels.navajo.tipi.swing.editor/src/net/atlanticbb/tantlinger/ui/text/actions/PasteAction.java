/*
 * Created on Jun 19, 2005
 *
 */
package net.atlanticbb.tantlinger.ui.text.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.StringReader;

import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.KeyStroke;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import net.atlanticbb.tantlinger.ui.UIUtils;
import net.atlanticbb.tantlinger.ui.text.CompoundUndoManager;
import net.atlanticbb.tantlinger.ui.text.HTMLUtils;

import org.bushe.swing.action.ActionManager;
import org.bushe.swing.action.ShouldBeEnabledDelegate;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


public class PasteAction extends HTMLTextEditAction
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
        
    public PasteAction()
    {
        super(i18n.str("paste"));
        putValue(MNEMONIC_KEY, new Integer(i18n.mnem("paste")));
        putValue(SMALL_ICON, UIUtils.getIcon(UIUtils.X16, "paste.png"));
        putValue(ActionManager.LARGE_ICON, UIUtils.getIcon(UIUtils.X24, "paste.png"));
		putValue(ACCELERATOR_KEY,
			KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        addShouldBeEnabledDelegate(new ShouldBeEnabledDelegate()
        {
            public boolean shouldBeEnabled(Action a)
            {                          
                //return getCurrentEditor() != null &&
                //    Toolkit.getDefaultToolkit().getSystemClipboard().getContents(PasteAction.this) != null;
                return true;
            }
        });
        
        putValue(Action.SHORT_DESCRIPTION, getValue(Action.NAME));
    }
    
    protected void updateWysiwygContextState(JEditorPane wysEditor)
    {
        this.updateEnabledState();
    }
    
    protected void updateSourceContextState(JEditorPane srcEditor)
    {
        this.updateEnabledState();
    }

    /* (non-Javadoc)
     * @see net.atlanticbb.tantlinger.ui.text.actions.HTMLTextEditAction#sourceEditPerformed(java.awt.event.ActionEvent, javax.swing.JEditorPane)
     */
    protected void sourceEditPerformed(ActionEvent e, JEditorPane editor)
    {
        editor.paste();        
    }

    /* (non-Javadoc)
     * @see net.atlanticbb.tantlinger.ui.text.actions.HTMLTextEditAction#wysiwygEditPerformed(java.awt.event.ActionEvent, javax.swing.JEditorPane)
     */
    protected void wysiwygEditPerformed(ActionEvent e, JEditorPane editor)
    {        
        HTMLEditorKit ekit = (HTMLEditorKit)editor.getEditorKit();
        HTMLDocument document = (HTMLDocument)editor.getDocument();        
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();

        
        try {
            CompoundUndoManager.beginCompoundEdit(document);
            Transferable content = clip.getContents(this);                           
            String htmlcontent= null;
            String txt = null;
			try {
				htmlcontent = content.getTransferData(
						new DataFlavor("text/html;class=java.lang.String;charset=UTF-8")).toString();
			} catch (UnsupportedFlavorException ex) {
				txt = content.getTransferData(new DataFlavor(String.class, "String")).toString();
			}
        
			if (htmlcontent != null) {
				Whitelist list =  Whitelist.basic();
				list.addTags("table", "tr", "td" , "h1", "h2", "h3", "h4", "h5", "h6");
				list.addAttributes("table", "width", "border", "align", "cellspacing", "bgcolor", "cellpadding");
				String clean = Jsoup.clean(htmlcontent, list);
				StringReader reader = new StringReader(HTMLUtils.jEditorPaneizeHTML(clean));
	            // remove existing selection if applicable
				if (editor.getSelectionEnd() > editor.getSelectionStart()) {
					document.replace(editor.getSelectionStart(),
							editor.getSelectionEnd() - editor.getSelectionStart(), "",
							ekit.getInputAttributes());
				}
				ekit.read(reader, document, editor.getSelectionStart());
				
	            
			} else {
				document.replace(editor.getSelectionStart(),
                editor.getSelectionEnd() - editor.getSelectionStart(),
                txt, ekit.getInputAttributes());
			}
        } 
        catch(Exception ex) 
        {
            ex.printStackTrace();
        }
        finally
        {
            CompoundUndoManager.endCompoundEdit(document);
        }
    }    
}
