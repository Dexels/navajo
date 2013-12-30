package com.dexels.navajo.tipi.components.swingimpl.layout;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;

/**
 *
 * @author Erik Versteeg
 */
public class TipiRoundedBorder extends AbstractBorder {
	private static final long serialVersionUID = -6885526357572424206L;
	private Color linecolor = Color.BLACK;
	private int linesize = 1;
	private boolean useGradient = false;
	private boolean useBorderlineAroundGradient = false;
	private Color startGradientColor = Color.GRAY;
	private Color endGradientColor = Color.WHITE;
	private String gradientLayout = "vertical";
	private boolean useShadow = false;
	private Color shadowColor = Color.DARK_GRAY;
    protected Dimension arcs = new Dimension(20, 20);
    protected int shadowGap = 5;
    protected int shadowOffset = 4;
    protected int shadowAlpha = 150;

    Insets insets = new Insets(0, 10, 10, 10);

	/** Create a few constructors */
    public TipiRoundedBorder(Color linecolor, int linesize) {
    	this.linecolor = linecolor;
    	this.linesize = linesize;
    }

    public TipiRoundedBorder(Color startGradientColor, Color endGradientColor, String gradientLayout, boolean useBorderlineAroundGradient) {
    	this.useGradient = true;
    	this.useBorderlineAroundGradient = useBorderlineAroundGradient;
    	this.startGradientColor = startGradientColor;
    	this.endGradientColor = endGradientColor;
    	this.gradientLayout = gradientLayout;
    }

    public TipiRoundedBorder(Color linecolor, int linesize, Color startGradientColor, Color endGradientColor, String gradientLayout, boolean useBorderlineAroundGradient) {
    	this.linecolor = linecolor;
    	this.linesize = linesize;
    	this.useGradient = true;
    	this.useBorderlineAroundGradient = useBorderlineAroundGradient;
    	this.startGradientColor = startGradientColor;
    	this.endGradientColor = endGradientColor;
    	this.gradientLayout = gradientLayout;
    }

    public TipiRoundedBorder(Color shadowColor) {
    	this.useShadow = true;
    	this.shadowColor = shadowColor;
    }

    public TipiRoundedBorder(Color linecolor, int linesize, Color shadowColor) {
    	this.linecolor = linecolor;
    	this.linesize = linesize;
    	this.useShadow = true;
    	this.shadowColor = shadowColor;
    }

    public TipiRoundedBorder(Color startGradientColor, Color endGradientColor, String gradientLayout, boolean useBorderlineAroundGradient, Color shadowColor) {
    	this.useGradient = true;
    	this.useBorderlineAroundGradient = useBorderlineAroundGradient;
    	this.startGradientColor = startGradientColor;
    	this.endGradientColor = endGradientColor;
    	this.gradientLayout = gradientLayout;
    	this.useShadow = true;
    	this.shadowColor = shadowColor;
    }

    public TipiRoundedBorder(Color linecolor, int linesize, Color startGradientColor, Color endGradientColor, String gradientLayout, boolean useBorderlineAroundGradient, Color shadowColor) {
    	this.linecolor = linecolor;
    	this.linesize = linesize;
    	this.useGradient = true;
    	this.useBorderlineAroundGradient = useBorderlineAroundGradient;
    	this.startGradientColor = startGradientColor;
    	this.endGradientColor = endGradientColor;
    	this.gradientLayout = gradientLayout;
    	this.useShadow = true;
    	this.shadowColor = shadowColor;
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

        
        // create shadow?
        if (this.useShadow) {
            Color shadowColorA = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
            g2d.setColor(shadowColorA);
            g2d.fillRoundRect(shadowOffset, shadowOffset, (width - linesize - shadowOffset), (height - linesize - shadowOffset), arcs.width, arcs.height);
        } else {
        	this.shadowGap = 1;
        }
        
        // create the shape
        RoundRectangle2D rect = new RoundRectangle2D.Double(x, (y + (insets.top / 2)), (width - this.shadowGap), ((height - this.shadowGap) - (insets.top / 2)), arcs.width, arcs.height);
        // create gradient if desired
        if (this.useGradient) {
        	GradientPaint gradient = null;
        	if (this.gradientLayout.equalsIgnoreCase("horizontal")) {
            	gradient = new GradientPaint(0, 0, this.startGradientColor, (width - linesize), 0, this.endGradientColor);
            } else {
            	gradient = new GradientPaint(0, 0, this.startGradientColor, 0, (height - linesize), this.endGradientColor);
            }
            g2d.setPaint(gradient);
            g2d.fill (rect);
            // use the border??
            if (this.useBorderlineAroundGradient) {
                g2d.setColor(this.linecolor);;
            }
        }
        
        
        Area area = new Area(rect);
        
        g2d.draw(area); // draw the shape
    }
}
