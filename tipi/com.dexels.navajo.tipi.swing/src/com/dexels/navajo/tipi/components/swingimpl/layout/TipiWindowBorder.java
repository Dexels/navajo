package com.dexels.navajo.tipi.components.swingimpl.layout;


import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.border.AbstractBorder;

/**
 *
 * @author Erik Versteeg
 */
public class TipiWindowBorder extends AbstractBorder {
	private static final long serialVersionUID = -6885526357572424206L;
	private Color startGradientColor = Color.GRAY;
	private Color endGradientColor = Color.WHITE;
	private String gradientLayout = "vertical";
    Insets insets = new Insets(0, 0, 0, 0);

	/** Create a few constructors */
    public TipiWindowBorder(Color startGradientColor, Color endGradientColor) {
    	this.startGradientColor = startGradientColor;
    	this.endGradientColor = endGradientColor;
    }

    public TipiWindowBorder(Color startGradientColor, Color endGradientColor, String gradientLayout) {
    	this.startGradientColor = startGradientColor;
    	this.endGradientColor = endGradientColor;
    	this.gradientLayout = gradientLayout;
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
        
        // create the shape
        Rectangle2D rect = new Rectangle2D.Double(x, (y + (insets.top / 2)), width, (height - (insets.top / 2)));
    	GradientPaint gradient = null;
    	if (this.gradientLayout.equalsIgnoreCase("horizontal")) {
        	gradient = new GradientPaint(0, 0, this.startGradientColor, (width - 0), 0, this.endGradientColor);
        } else {
        	gradient = new GradientPaint(0, 0, this.startGradientColor, 0, (height - 0), this.endGradientColor);
        }
        g2d.setPaint(gradient);
        g2d.fill (rect);
        
        
        Area area = new Area(rect);
        
        g2d.draw(area); // draw the shape
    }
}
