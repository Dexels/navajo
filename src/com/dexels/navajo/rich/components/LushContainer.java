package com.dexels.navajo.rich.components;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class LushContainer extends JPanel{
	private float opacity = 0.5f;
	private int arc = 20;
	private Color borderColor = Color.white;
	private float borderWidth = 2.0f;
	
	public LushContainer(){
		setOpaque(false);
    setBackground(new Color(145,183,219));
	}
	
	public void setOpacity(double opacity){
		this.opacity = (float)opacity;
	}
	
	public void setArcWidth(int arc){
		this.arc = arc;
	}
	
	public void setBorderWidth(double width){
		borderWidth = (float)width;
	}
	
	public void paint(Graphics g){
		Graphics2D graf = (Graphics2D)g;
		Rectangle bounds = getBounds();

		
		int blocksize = 20;
		
    GraphicsConfiguration	gc = graf.getDeviceConfiguration();		
		BufferedImage buffer = gc.createCompatibleImage(bounds.width, bounds.height, Transparency.TRANSLUCENT);
		Graphics2D bg = buffer.createGraphics();
		bg.setComposite(AlphaComposite.Clear);
		bg.fillRect(0, 0, bounds.width, bounds.height);
		
		bg.setComposite(AlphaComposite.Src.derive(opacity));
		bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		bg.setColor(getBackground());
	
		RoundRectangle2D clip = new RoundRectangle2D.Double(0,0, bounds.width, bounds.height, arc, arc);
		bg.fill(clip);
		
		BufferedImage gradient = gc.createCompatibleImage(bounds.width, bounds.height, Transparency.TRANSLUCENT);
		Graphics2D gg = gradient.createGraphics();				
		
		bg.setComposite(AlphaComposite.SrcAtop);
		bg.setColor(getBackground());
		bg.fillRoundRect(0, 1, bounds.width-1, bounds.height-2, arc, arc);
		bg.setColor(borderColor);
		bg.setStroke(new BasicStroke(borderWidth));
		bg.drawRoundRect((int)borderWidth/2, (int)borderWidth/2, (int)(bounds.width-borderWidth), (int)(bounds.height-borderWidth), arc, arc);
		
		// glo top		
		GradientPaint gp = new GradientPaint(0,0, new Color(1.0f, 1.0f, 1.0f, 0.5f), 0, blocksize, new Color(1.0f, 1.0f, 1.0f, 0.0f));
		gg.setPaint(gp);
		gg.fillRect(blocksize, 0, bounds.width-2*blocksize, bounds.height-1);
		
		
		BufferedImage cornerGradient = gc.createCompatibleImage(blocksize, 2*blocksize, Transparency.TRANSLUCENT); 
		Graphics2D cg = cornerGradient.createGraphics();		
		RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(0.0, 2*blocksize), 2*blocksize, new float[]{0.5f, 1.0f}, new Color[]{new Color(1.0f,1.0f,1.0f,0.0f), new Color(1.0f,1.0f,1.0f,0.5f)} );
		cg.setPaint(rgp);
		cg.fillRect(0, 0, 2*blocksize, 2*blocksize);
		
		BufferedImage cornerGradientLeft = gc.createCompatibleImage(blocksize, 2*blocksize, Transparency.TRANSLUCENT);
		Graphics2D cgl = cornerGradientLeft.createGraphics();		
		RadialGradientPaint rgpl = new RadialGradientPaint(new Point2D.Double(blocksize, 2*blocksize), 2*blocksize, new float[]{0.5f, 1.0f}, new Color[]{new Color(1.0f,1.0f,1.0f,0.0f), new Color(1.0f,1.0f,1.0f,0.5f)} );
		cgl.setPaint(rgpl);
		cgl.fillRect(0, 0, 2*blocksize, 2*blocksize);
		
		gg.drawImage(cornerGradient, bounds.width-blocksize, 0, blocksize, 2*blocksize, null);
		gg.drawImage(cornerGradientLeft, 0, 0, blocksize, 2*blocksize, null);
		
		// glo bottom, black.. a little too much, let's not use it
		
//		GradientPaint gpb = new GradientPaint(0,bounds.height-blocksize, new Color(0.0f, 0.0f, 0.0f, 0.0f), 0, bounds.height, new Color(0.0f, 0.0f, 0.0f, 0.5f));
//		gg.setPaint(gpb);
//		gg.fillRect(blocksize, bounds.height-blocksize, bounds.width-2*blocksize, bounds.height);
//		
//		
//		BufferedImage cornerGradientRB = new BufferedImage(blocksize, 2*blocksize, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D cgrt = cornerGradientRB.createGraphics();		
//		RadialGradientPaint rgprt = new RadialGradientPaint(new Point2D.Double(0.0, 0.0), 2*blocksize, new float[]{0.5f, 1.0f}, new Color[]{new Color(0.0f,0.0f,0.0f,0.0f), new Color(0.0f,0.0f,0.0f,0.5f)} );
//		cgrt.setPaint(rgprt);
//		cgrt.fillRect(0, 0, 2*blocksize, 2*blocksize);
//		
//		BufferedImage cornerGradientLB = new BufferedImage(blocksize, 2*blocksize, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D cglb = cornerGradientLB.createGraphics();		
//		RadialGradientPaint rgplb = new RadialGradientPaint(new Point2D.Double(blocksize, 0.0), 2*blocksize, new float[]{0.5f, 1.0f}, new Color[]{new Color(0.0f,0.0f,0.0f,0.0f), new Color(0.0f,0.0f,0.0f,0.5f)} );
//		cglb.setPaint(rgplb);
//		cglb.fillRect(0, 0, 2*blocksize, 2*blocksize);
//		
//		gg.drawImage(cornerGradientRB, bounds.width-blocksize, bounds.height-2*blocksize, blocksize, 2*blocksize, null);
//		gg.drawImage(cornerGradientLB, 0, bounds.height-2*blocksize, blocksize, 2*blocksize, null);
//		
		
		
		bg.setComposite(AlphaComposite.SrcAtop.derive(1.0f));
		bg.drawImage(gradient, 0, 0, bounds.width-1, bounds.height, null);
		bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		
		graf.drawImage(buffer, 0, 0, bounds.width, bounds.height, null);	
		
		bg.dispose();		
		// Now draw the children and other stuff
		paintChildren(g);
		paintBorder(g);
	}
};