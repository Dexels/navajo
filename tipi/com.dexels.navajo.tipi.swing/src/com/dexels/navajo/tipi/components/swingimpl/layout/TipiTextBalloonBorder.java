package com.dexels.navajo.tipi.components.swingimpl.layout;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author Erik Versteeg
 */
public class TipiTextBalloonBorder extends AbstractBorder {
	private static final long serialVersionUID = -3132786337435345898L;
	private Color linecolor = Color.BLACK;
	private int linesize = 1;
	Insets insets = new Insets(20, 12, 10, 10);

    public TipiTextBalloonBorder(Color linecolor, int linesize) {
    	this.linecolor = linecolor;
    	this.linesize = linesize;
    }

    @Override
    public Insets getBorderInsets(Component cmpnt) {
        return insets;
    }

    @Override
    public Insets getBorderInsets(Component cmpnt, Insets insets) {
        insets.left = this.insets.left;
        insets.right = this.insets.right;
        insets.top = this.insets.top;
        insets.bottom = this.insets.bottom;
        return insets;
    }

    @Override
    public void paintBorder(Component cmpnt, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;

        // turn Anti-aliasing on to make the corners smooth
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(this.linesize));
        g.setColor(this.linecolor);

        // create the callout "tip"
        int tipCenter = x + (width / 3) ;
        GeneralPath p = new GeneralPath();
        p.moveTo(tipCenter - (insets.top / 2), (y + (insets.top / 2)));
        p.lineTo(tipCenter, 0);
        p.lineTo(tipCenter + (insets.top / 2), (y + (insets.top / 2)));
        p.closePath();

        // create the shape
        RoundRectangle2D rect = new RoundRectangle2D.Double(x, (y + (insets.top / 2)), (width - 1), ((height - 1) - (insets.top / 2)), 20, 20);
        Area area = new Area(rect);
        area.add( new Area(p) );
        
        g2d.draw(area); // draw the shape
    }
}
