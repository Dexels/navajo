/*
 * Created on Jan 17, 2006
 *
 */
package net.atlanticbb.tantlinger.ui.text.dialogs;

import java.awt.Dialog;
import java.awt.Frame;
import java.util.Map;

import javax.swing.Icon;

import net.atlanticbb.tantlinger.i18n.I18n;
import net.atlanticbb.tantlinger.ui.OptionDialog;
import net.atlanticbb.tantlinger.ui.UIUtils;



public class ElementStyleDialog extends OptionDialog
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text.dialogs");
    
    private static Icon icon = UIUtils.getIcon(UIUtils.X48, "pencil.png"); //$NON-NLS-1$
    private  String title = i18n.str("element_style"); //$NON-NLS-1$
    private  String desc = i18n.str("element_style_desc"); //$NON-NLS-1$
    
    private StyleAttributesPanel stylePanel;

    public ElementStyleDialog(Frame parent)
    {
        super(parent, i18n.str("element_style"), i18n.str("element_style_desc"), icon);   
        init();
    }

    public ElementStyleDialog(Dialog parent)
    {
        super(parent, i18n.str("element_style"), i18n.str("element_style_desc"), icon);
        init();
    }
    
    private void init()
    {
        stylePanel = new StyleAttributesPanel();
        setContentPane(stylePanel);        
        pack();
        setSize(300, getHeight());
        setResizable(false);
    }
    
    public void setStyleAttributes(Map attr)
    {
        stylePanel.setAttributes(attr);
    }
    
    public Map getStyleAttributes()
    {
        return stylePanel.getAttributes();
    }
}
