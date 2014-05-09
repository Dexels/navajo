/*
 * Created on Jan 10, 2006
 *
 */

package net.atlanticbb.tantlinger.ui;

import java.awt.*;
import javax.swing.*;



public class OptionDialog extends StandardDialog
{    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel internalContentPane;
    private Container contentPane;
    
    public OptionDialog(Frame parent, String headerTitle, String desc, Icon icon)
    {
        super(parent, headerTitle, BUTTONS_RIGHT);
        init(headerTitle, desc, icon);        
    }

    public OptionDialog(Dialog parent, String headerTitle, String desc, Icon icon)
    {
        super(parent, headerTitle, BUTTONS_RIGHT);
        init(headerTitle, desc, icon);
    }
    
    private void init(String title, String desc, Icon icon)
    {
        internalContentPane = new JPanel(new BorderLayout());
        HeaderPanel hp = new HeaderPanel();
        hp.setTitle(title);
        hp.setDescription(desc);
        hp.setIcon(icon);
        //hp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        internalContentPane.add(hp, BorderLayout.NORTH);
        
        super.setContentPane(internalContentPane);
    }
    
    public Container getContentPane()
    {
        return contentPane;
    }
    
    public void setContentPane(Container c)
    {
        //internalContentPane.remove(contentPane);
        contentPane = c;
        internalContentPane.add(c, BorderLayout.CENTER);
        
    }
}
