package com.dexels.navajo.rich.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;

public class GradientDesktop extends JDesktopPane {
  BufferedImage gradientImage = null;
	TipiGradientPaint myPaint = new TipiGradientPaint("north", new Color(120, 160, 205), Color.black);
	
	BufferedImage bg;
	
	public GradientDesktop(){
		try{
//			bg = ImageIO.read(new File("/home/aphilip/Desktop/ai-scaled.jpg"));
		}catch(Exception e){}
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g);
    
    if(gradientImage == null || gradientImage.getHeight() != getHeight()){
    	myPaint.setBounds(this.getBounds());
    	Paint p = myPaint.getPaint();
    	gradientImage = new BufferedImage(1, getHeight(), BufferedImage.TYPE_INT_RGB);
    	Graphics2D gg = (Graphics2D)gradientImage.getGraphics();
    	gg.setPaint(p);
    	gg.fillRect(0,0,1,getHeight());
    	gg.dispose();    	
    }	
    
    g2.drawImage(gradientImage, 0, 0, getWidth(), getHeight(), null);
    if(bg != null){
    	g2.drawImage(bg, 0, 0, bg.getWidth(), bg.getHeight(), null);
    }

	}
	
	
	public static void main(String[] args){
		double cap = 0.047e-6;
		int res = 100000;
//		double cap = 0.0033e-6;
//		int res = 22000;
		System.err.println("Starting..");
		double freq0 = 1 / (2*Math.PI * cap * res);
		double fmax = 1 / (2*Math.PI * cap * 500);
		
		
		BufferedImage apenoot = new BufferedImage(res/10, (int)(fmax-freq0)/100, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = apenoot.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, apenoot.getWidth(), apenoot.getHeight());
		
		Point previous = new Point(res/10, apenoot.getHeight());;
		g.setColor(Color.blue);
		
		g.drawLine(0, apenoot.getHeight() - 48, apenoot.getWidth(), apenoot.getHeight()-48);
		g.drawLine(0, apenoot.getHeight() - 150, apenoot.getWidth(), apenoot.getHeight()-150);
		for(int pot = res;pot>500;pot-=100){
			double freq = 1 / (2*Math.PI * cap * pot);
			Point current = new Point(pot/10, apenoot.getHeight()-(int)freq/100);
			g.setColor(Color.black);
			g.drawLine(previous.x, previous.y, current.x, current.y);
			if(pot%1000 == 0){
				g.setColor(Color.red);
				g.drawLine(current.x, apenoot.getHeight(), current.x, current.y);
			}
			previous = current;
			System.err.println("At r=" +pot + ", cutoff[f]=" + freq);
		}
		try{
			ImageIO.write(apenoot, "jpg", new File("/home/aphilip/Desktop/apenoot.jpg"));
		}catch(Exception e){}
		
		
	}
	
}
