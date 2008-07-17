package com.dexels.navajo.geo;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.text.*;

import javax.imageio.*;

public class LegendCreator {

//	   <ScreenOverlay id="khScreenOverlay756">
//	      <name>Simple crosshairs</name>
//	      <description> WONKA</description>
//	    <!--    <color>ffff0000</color>                 kml:color -->
//	      <drawOrder>0</drawOrder>                 <!-- int -->
//	      <Icon>aap.png</Icon>
//	      <screenXY x="100" y="140" xunits="pixels" yunits="insetPixels" />
//	      <rotation>0</rotation>
//	      <size x="180" y="250" xunits="pixels" yunits="pixels" />
//	   </ScreenOverlay>

	public BufferedImage createLegendImage(String title, int steps, double min, double max, Dimension size, GeoColorizer colorizer) {
		BufferedImage bi = new BufferedImage(size.width,size.height, BufferedImage.TYPE_INT_ARGB);

		int inset = 2;
		int barInset = 5;
		Graphics2D g2d =  (Graphics2D) bi.getGraphics();
		g2d.setColor(new Color(30,30,70,180));
//		g2d.setFont(Font.getFont("Arial"));
		g2d.fillRoundRect(inset,inset, size.width - 2 * inset, size.height - 2*inset, 15, 15);
		
		g2d.setColor(Color.white);
		g2d.drawString(title, 12, 20);

		
		int startOffset = 40;
		int diffSize = size.height - startOffset;
		NumberFormat n = NumberFormat.getNumberInstance();
		n.setMaximumFractionDigits(1);
		double bandSize = diffSize / steps;
		for (int i = startOffset; i < size.height; i+=bandSize) {
			double val = (i-bandSize)/diffSize;
			double valEnd = (double)(i)/(double)diffSize;
			if(val<min) {
				val = min;
			}
			if(valEnd>max) {
				valEnd = max;
			}
			Color ccc = colorizer.createGeoColor(val, 0, 1);
			g2d.setColor(ccc);
			g2d.fillRoundRect(barInset, i, size.width-(barInset*2), (int)bandSize-10, 8, 8);
			g2d.setColor(Color.white);
			
			g2d.drawString(n.format(val*diffSize)+ " - "+n.format(valEnd*diffSize), barInset+inset+20,(int)(i+(bandSize/2)));
		}
		
		return bi;
	}

	public void dumpImage(BufferedImage bi, File f) throws IOException {
		ImageIO.write(bi, "png",f);
	}
	
	public File createLegendImagePath(String title, int steps, double start, double end, Dimension size, GeoColorizer gi) throws IOException {
		BufferedImage bi = createLegendImage(title, steps, start, end, size, gi);
		File ff = File.createTempFile("legend", ".png");
		ImageIO.write(bi, "png",ff);
		try {
		ImageIO.write(bi, "png", new File("c:/aap.png"));
		} catch(Throwable t) {
			t.printStackTrace();
		}
		return ff;
	}

}
