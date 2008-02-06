package com.dexels.navajo.rich.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class PerspectiveTransform {
	
	public static BufferedImage transform(BufferedImage input, double degrees, boolean keepcenter, boolean to_left){
		long st = System.currentTimeMillis();
		int width = input.getWidth();
		int height = input.getHeight();
		double rad_a = degrees * (Math.PI/180.0);
		int new_width = (int) ((width * Math.sin(rad_a) * Math.cos(rad_a)) / (Math.tan(rad_a)));
		float alpha_overlay = (float)degrees * 1.0f / 80.0f;

		BufferedImage result = new BufferedImage(new_width, input.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D buffer = result.createGraphics();
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		BufferedImage new_width_input = new BufferedImage(new_width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D nwg = new_width_input.createGraphics();		
		nwg.drawImage(input, 0, 0, new_width, height, null);			
		nwg.dispose();
		
		int dw = 5;
		float alpha_factor = alpha_overlay / new_width;
		
		for(int x = 0;x<new_width;x+=dw){
			double ddh = 0.25*x*Math.tan(rad_a);
			if(to_left) {
				ddh = 0.25*(new_width-x)*Math.tan(rad_a);
			}
			int h = (int)(height - 2*ddh);
			
			if(h < 1){
				h=1;
				ddh = height/2;
			}
			BufferedImage target = new BufferedImage(dw, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D tg = target.createGraphics();
		  tg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			
			
			tg.setColor(Color.darkGray);
			
			if(to_left){
				float val = alpha_overlay - x*alpha_factor;
				if(val > 1){
					val = 1.0f;
				}else if(val < 0){
					val = 0.0f;
				}
				tg.setComposite(AlphaComposite.SrcOver.derive(val));
			}else{
				float val = x*alpha_factor;
				if(val > 1){
					val = 1.0f;
				}else if(val < 0){
					val = 0.0f;
				}
				tg.setComposite(AlphaComposite.SrcOver.derive(val));
			}
			
			tg.fillRect(0,0, dw, h);
			tg.setComposite(AlphaComposite.SrcOut.derive(1.0f));
			tg.drawImage(new_width_input, -x, 0, new_width, h, null);
			
			buffer.drawImage(target, x, (int)ddh, dw, h, null);
			tg.dispose();
		}
		
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		buffer.dispose();
		
		if(keepcenter){
			BufferedImage displaced = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB); 
			displaced.createGraphics().drawImage(result, (input.getWidth() - result.getWidth()) / 2, 0, result.getWidth(), result.getHeight(), null);
			return displaced;
		}	
		return result;
	}
	
	
	public static void main(String[] args){
		try{
			
			BufferedImage img = ImageIO.read(new FileInputStream("/home/aphilip/Desktop/screenshot.png"));
			int angle = 45;
		//	for (int angle = 5;angle<86;angle+=5){
			
			  //BufferedImage lowres = new BufferedImage(img.getWidth()/2, img.getHeight()/2, BufferedImage.TYPE_INT_ARGB);
			  //Graphics2D lrg = lowres.createGraphics();
			  //lrg.drawImage(img, 0, 0, lowres.getWidth(), lowres.getHeight(), null);
				long st = System.currentTimeMillis();
				BufferedImage result = transform(img, angle, true, false);
				
				//BufferedImage target = new BufferedImage(result.getWidth()*2, result.getHeight()*2, BufferedImage.TYPE_INT_ARGB);
				//target.createGraphics().drawImage(result, 0, 0, target.getWidth(), target.getHeight(), null);
				
				ImageIO.write(result, "png", new File("/home/aphilip/Desktop/output" + angle +".png"));
				System.err.println("Done..: " + (System.currentTimeMillis()-st) + " ms");
			//}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	

}
