package com.dexels.navajo.geo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import javax.imageio.ImageIO;

import com.dexels.navajo.geo.color.BlueRedGeoColorizer;

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
		NumberFormat perc = NumberFormat.getPercentInstance();
		n.setMaximumFractionDigits(1);
		// bandSize in pixels:
		System.err.println("DiffsizE: "+diffSize);
		double bandSize = diffSize / steps;
		System.err.println("BandSize: "+bandSize);
		double range = max - min;
		
		double stepSize = range / steps;

		for (double j = min; (max - j) > 0.01; j+= stepSize) {
			double percentage = (j-min) / range;
			System.err.println("J-min: "+percentage+ " to go: "+(max - j));
			int i = (int)(startOffset + percentage * diffSize);
			double val = (double)(i)/diffSize;
//			double valEnd = (double)(i+bandSize)/(double)diffSize;
//			if(val<min) {
//				val = min;
//			}
//			if(valEnd>max) {
//				valEnd = max;
//			}
			System.err.println("BandSize: "+bandSize+" j: "+j+" i: "+i);
//			System.err.println("Val: "+val);
			Color ccc = colorizer.createGeoColor(val, 0, 1);
			g2d.setColor(ccc);
			g2d.fillRoundRect(barInset, i, size.width-(barInset*2), (int)bandSize-10, 8, 8);
			g2d.setColor(Color.white);
			
			g2d.drawString(n.format(j)+ " - "+n.format(j+stepSize)+" ("+perc.format(percentage)+" - "+perc.format(((j+stepSize)-min) / range) +")", barInset+inset+20,(int)(i+(bandSize/2)));
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
		
		return ff;
	}

	public static void main(String[] args) throws IOException {
		LegendCreator lc = new LegendCreator();
		lc.createLegendImagePath("Title", 5, 22,34, new Dimension(200,400), new BlueRedGeoColorizer());
	}
	
}
