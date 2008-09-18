package com.dexels.navajo.rich.components;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import com.dexels.navajo.document.types.*;

public class CreateTextImage {
//	private static final int max_fontsize = 40;
	private static int reflectionSize = 18; 
	public static int height = 60;
	public static int width = 265;
	
	
		
	public static Binary createTextImage(String text){
		Binary result = new Binary();
		
		BufferedImage target = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D targetGraphics = target.createGraphics();
		
		int fontsize = 8;
		
		int string_width = 0;
		Font f = null;
		Rectangle2D stringbounds = new Rectangle2D.Double(0.0,0.0,0.0,0.0);
		JComponent c = new JLabel();
		while(string_width < width - 10 && stringbounds.getHeight() < (0.66*height)){
			fontsize++;
			f = new Font("Dialog", Font.BOLD, fontsize);	
			FontMetrics fm = c.getFontMetrics(f);
			string_width = fm.stringWidth(text);	
			stringbounds = fm.getStringBounds(text, targetGraphics);
		}
		
		System.err.println("fonsize: " + fontsize + ", bounds: " + stringbounds);
		
		BufferedImage text_target = new BufferedImage(width, (int)stringbounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D text_targetGraphics = text_target.createGraphics();
		text_targetGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		text_targetGraphics.setFont(f);
		text_targetGraphics.setColor(Color.white);
		text_targetGraphics.drawString(text, width/2 - (int)(stringbounds.getWidth()/2), (int)(-stringbounds.getY()+1));
		
		BufferedImage reflection = createReflection(text_target);
		
		OutputStream out = result.getOutputStream();		
		try{
			ImageIO.write(reflection, "png", out);
		}catch(Exception e){}
		return result;
	}
	
	private static BufferedImage createReflection(BufferedImage img) {
		int height = img.getHeight();
		BufferedImage result = new BufferedImage(img.getWidth(), (height + reflectionSize), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = result.createGraphics();

		g2.drawImage(img, 0, 0, null);
		g2.scale(1.0, -1.0);
		g2.drawImage(img, 0, -height - height, null);
		g2.scale(1.0, -1.0);
		g2.translate(0, height);

		GradientPaint mask = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.5f), 0, reflectionSize, new Color(1.0f, 1.0f, 1.0f, 0.0f));
		g2.setPaint(mask);
		g2.setComposite(AlphaComposite.DstIn);
		g2.fillRect(0, 0, img.getWidth(), reflectionSize);
		g2.dispose();
		return result;
	}
	
	public static void main(String[] args){
		CreateTextImage.createTextImage("MHC PUNiCA");
	}
}