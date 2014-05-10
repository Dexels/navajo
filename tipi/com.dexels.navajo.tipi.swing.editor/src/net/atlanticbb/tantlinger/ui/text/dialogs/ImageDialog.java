/*
 * Created on Jan 14, 2006
 *
 */
package net.atlanticbb.tantlinger.ui.text.dialogs;

import java.awt.Dialog;
import java.awt.Frame;
import java.util.Iterator;
import java.util.Map;

import javax.swing.Icon;

import net.atlanticbb.tantlinger.i18n.I18n;
import net.atlanticbb.tantlinger.ui.UIUtils;


public class ImageDialog extends HTMLOptionDialog
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final I18n i18n = I18n.getInstance("net.atlanticbb.tantlinger.ui.text.dialogs");
    
    private static Icon icon = UIUtils.getIcon(UIUtils.X48, "image.png"); //$NON-NLS-1$
    private static String title = i18n.str("image"); //$NON-NLS-1$
    private static String desc = i18n.str("image_desc"); //$NON-NLS-1$

    private ImagePanel imagePanel;
    
    public ImageDialog(Frame parent)
    {
        super(parent, title, desc, icon);
        init();
    }

    public ImageDialog(Dialog parent)
    {
        super(parent, title, desc, icon);   
        init();
    }
    
    private void init()
    {
        imagePanel = new ImagePanel();
        setContentPane(imagePanel);
        setSize(300, 345);
        setResizable(false);
    }
    
    public void setImageAttributes(Map attr)
    {
        imagePanel.setAttributes(attr);
    }  
    
    public Map getImageAttributes()
    {
        return imagePanel.getAttributes();
    }

    private String createImgAttributes(Map ht)
    {
        String html = ""; //$NON-NLS-1$
        for(Iterator e = ht.keySet().iterator(); e.hasNext();)
        {
            Object k = e.next();
            if(k.toString().equals("a") || k.toString().equals("name")) //$NON-NLS-1$ //$NON-NLS-2$
                continue;
            html += " " + k + "=" + "\"" + ht.get(k) + "\""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        }
        
        return html;
    }
    
    public String getHTML()
    {
        Map imgAttr = imagePanel.getAttributes();
        boolean hasLink = imgAttr.containsKey("a"); //$NON-NLS-1$
        String html = ""; //$NON-NLS-1$
        if(hasLink)
        {
            html = "<a " + imgAttr.get("a") + ">";             //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        html += "<img" + createImgAttributes(imgAttr) + ">"; //$NON-NLS-1$ //$NON-NLS-2$
        
        if(hasLink)
            html += "</a>"; //$NON-NLS-1$
        
        return html;
    }
    
}
