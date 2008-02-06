package com.dexels.navajo.rich.components;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class LushContainer extends JPanel{
	private float opacity = 0.5f;
	private int arc = 50;
	private Color borderColor = Color.white;
	private float borderWidth = 5.0f;
	
	public LushContainer(){
		setOpaque(false);
    setBackground(new Color(145,183,219));
	}
	
	public void setOpacity(float opacity){
		this.opacity = opacity;
	}
	
	public void paint(Graphics g){
		Graphics2D graf = (Graphics2D)g;
		Graphics2D g2 = (Graphics2D)graf.create();
		
		Rectangle bounds = getBounds();

		int blocksize = 20;
		
		
		BufferedImage buffer = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D bg = buffer.createGraphics();
		bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		BufferedImage gradient = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gg = gradient.createGraphics();				
		RoundRectangle2D clip = new RoundRectangle2D.Double(0,0, bounds.width, bounds.height, arc, arc);
		gg.setClip(clip);
		
		
		bg.setComposite(AlphaComposite.SrcOver.derive(opacity));
		bg.setColor(getBackground());
		bg.fillRoundRect(0, 1, bounds.width-1, bounds.height-2, arc, arc);
		bg.setColor(borderColor);
		bg.setStroke(new BasicStroke(borderWidth));
		bg.drawRoundRect((int)borderWidth/2, (int)borderWidth/2, (int)(bounds.width-borderWidth), (int)(bounds.height-borderWidth), arc, arc);
		
		// glo top		
		GradientPaint gp = new GradientPaint(0,0, new Color(1.0f, 1.0f, 1.0f, 0.5f), 0, blocksize, new Color(1.0f, 1.0f, 1.0f, 0.0f));
		gg.setPaint(gp);
		gg.fillRect(blocksize, 0, bounds.width-2*blocksize, bounds.height-1);
		
		
		BufferedImage cornerGradient = new BufferedImage(blocksize, 2*blocksize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D cg = cornerGradient.createGraphics();		
		RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(0.0, 2*blocksize), 2*blocksize, new float[]{0.5f, 1.0f}, new Color[]{new Color(1.0f,1.0f,1.0f,0.0f), new Color(1.0f,1.0f,1.0f,0.5f)} );
		cg.setPaint(rgp);
		cg.fillRect(0, 0, 2*blocksize, 2*blocksize);
		
		BufferedImage cornerGradientLeft = new BufferedImage(blocksize, 2*blocksize, BufferedImage.TYPE_INT_ARGB);
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
		
		bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		
		
		g2.drawImage(buffer, 0, 0, bounds.width, bounds.height, null);	
		g2.drawImage(gradient, 0, 0, bounds.width-1, bounds.height, null);
		bg.dispose();
		g2.dispose();
		
		// Now draw the children and other stuff
		paintChildren(g);
		paintBorder(g);
	}
};