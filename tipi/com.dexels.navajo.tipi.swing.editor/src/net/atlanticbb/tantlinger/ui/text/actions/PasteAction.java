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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JEditorPane;
import javax.swing.KeyStroke;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import net.atlanticbb.tantlinger.ui.UIUtils;
import net.atlanticbb.tantlinger.ui.text.CompoundUndoManager;
import net.atlanticbb.tantlinger.ui.text.HTMLUtils;

import org.bushe.swing.action.ActionManager;
import org.bushe.swing.action.ShouldBeEnabledDelegate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
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
			    DataFlavor html = DataFlavor.selectBestTextFlavor(content.getTransferDataFlavors());
			    if (html.getMimeType().startsWith("text/html")) {
	                // read html-code from stream
	                Object transferData = content.getTransferData(html);
	                BufferedReader reader = new BufferedReader((InputStreamReader) transferData);
	                StringBuffer str = new StringBuffer();
	                char[] buf = new char[512];
	                while (reader.read(buf) > 0) {
	                    str.append(buf);
	                }

	                htmlcontent =  str.toString();
	            } else {
	                // Another try at this
	                htmlcontent = content.getTransferData(new DataFlavor("text/html;class=java.lang.String;charset=UTF-8")).toString();
	            }
			      
			} catch (UnsupportedFlavorException ex) {
				// fallback to text below
			}
			if (htmlcontent == null || "".equals(htmlcontent.trim())) {
                txt = content.getTransferData(new DataFlavor(String.class, "String")).toString();
            } 
        
			if (htmlcontent != null) {
				Whitelist list =  Whitelist.basic();
				list.addTags("table", "tr", "td" , "h1", "h2", "h3", "h4", "h5", "h6");
				list.addAttributes("table", "width", "border", "align", "cellspacing", "bgcolor", "cellpadding");
				htmlcontent = htmlcontent.substring(0,  htmlcontent.indexOf("</html>")); // ignore everything after html closing tag
				String clean = Jsoup.clean(htmlcontent, list);
				clean = optimizeHtmlPaste(clean);
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

    /* Performs tweaks to give the best result */
    private String optimizeHtmlPaste(String sourceTxt) {
        // Replace <p> inside <li> with <div>
        // This prevents a newline for every item
        // Needed for OpenOffice 
        org.jsoup.nodes.Document doc = Jsoup.parse(sourceTxt);
        doc.outputSettings().prettyPrint(true);
        for (Element ul : doc.body().select("ul")) {
            Iterator<Element> iterator = ul.children().iterator();
            while (iterator.hasNext()) {
                Element child = iterator.next();
                if (child.tagName().equals("li") && iterator.hasNext()) {
                    // Check if next child is a <p>. If so take text from <p> and put it in <li>
                    Element nextChild = iterator.next();
                    if (nextChild.tagName().equals("p")) {
                        child.html(nextChild.html());
                        nextChild.remove();
                    }
                }
                
            }
            
           
        }
        return doc.body().html();
    }    
}
