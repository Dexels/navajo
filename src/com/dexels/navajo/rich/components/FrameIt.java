package com.dexels.navajo.rich.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class FrameIt {
	
	public static void main(String[] args){
		try{
			int framesize = 10;
			int angle = 25;
			Color frameColor = Color.white;
			BufferedImage test = ImageIO.read(new File("/home/aphilip/Desktop/ai-scaled.jpg"));
			BufferedImage framed = new BufferedImage(test.getWidth() + 2*framesize, test.getHeight() + 2*framesize, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = framed.createGraphics();
			g.setColor(frameColor);
			g.fillRect(0, 0, framed.getWidth(), framed.getHeight());
			g.drawImage(test, framesize, framesize, test.getWidth(), test.getHeight(),null);
			PerspectiveTransform.setQ(1);
			framed = PerspectiveTransform.transform(framed, angle, true, PerspectiveTransform.FLIP_RIGHT);
			ImageIO.write(framed, "png", new File("/home/aphilip/Desktop/framed.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
