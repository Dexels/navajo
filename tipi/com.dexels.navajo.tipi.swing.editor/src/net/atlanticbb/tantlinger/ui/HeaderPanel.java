/*
 * Created on Jan 10, 2006
 *
 */
package net.atlanticbb.tantlinger.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HeaderPanel extends JPanel
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JLabel titleLabel = null;
    private JLabel msgLabel = null;
    private JLabel iconLabel = null;
    
    /**
     * This is the default constructor
     */
    public HeaderPanel()
    {
        super();
        initialize();
    }
    
    public HeaderPanel(String title, String desc, Icon ico)
    {
        super();
        initialize();
        setTitle(title);
        setDescription(desc);
        setIcon(ico);
        
    }
    
    public void setTitle(String title)
    {
        titleLabel.setText(title);
    }
    
    public void setDescription(String desc)
    {
        msgLabel.setText(desc);
    }
    
    public void setIcon(Icon icon)
    {
        iconLabel.setIcon(icon);
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
        gridBagConstraints3.gridx = 1;
        gridBagConstraints3.gridheight = 2;
        gridBagConstraints3.insets = new java.awt.Insets(0,5,0,10);
        gridBagConstraints3.gridy = 0;
        iconLabel = new JLabel();
        //iconLabel.setText("");
        //iconLabel.setIcon(new ImageIcon(getClass().getResource("/com/bob/ui/text/post.png")));
        iconLabel.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.insets = new java.awt.Insets(2,25,0,0);
        gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints2.weightx = 1.0;
        gridBagConstraints2.weighty = 1.0;
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints2.gridy = 1;
        msgLabel = new JLabel();
        msgLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        //msgLabel.setText("The description goes here");
        msgLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.insets = new java.awt.Insets(6,10,0,0);
        gridBagConstraints.weighty = 0.0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridy = 0;
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        titleLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        //titleLabel.setText("This is a title");
        this.setLayout(new GridBagLayout());
        this.setSize(360, 56);
        this.setPreferredSize(new java.awt.Dimension(360,56));
        this.add(titleLabel, gridBagConstraints);
        this.add(msgLabel, gridBagConstraints2);
        this.add(iconLabel, gridBagConstraints3);
        
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
     
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                       RenderingHints.VALUE_ANTIALIAS_ON);
        Rectangle bounds = getBounds();
     
        // Set Paint for filling Shape
        Color blue = new Color(153, 204, 255);
        
        Paint gradientPaint = new GradientPaint(bounds.width * 0.5f, 
            bounds.y, Color.white, bounds.width, 0f, blue);
        g2.setPaint(gradientPaint);
        g2.fillRect(0, 0, bounds.width, bounds.height);     

      }

}  //  @jve:decl-index=0:visual-constraint="10,10"
