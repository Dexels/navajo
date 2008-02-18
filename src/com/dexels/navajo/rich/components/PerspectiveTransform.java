package com.dexels.navajo.rich.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class PerspectiveTransform {
	public static final int FLIP_LEFT = 0;
	public static final int FLIP_RIGHT = 1;
	public static final int FLIP_UP = 2;
	public static final int FLIP_DOWN = 3;
	
	public static ConvolveOp getBlurFilter(int horizontalRadius, int verticalRadius) {
		int width = horizontalRadius * 2 + 1;
		int height = verticalRadius * 2 + 1;

		float weight = 1.0f / (width * height);
		float[] data = new float[width * height];
  
		for (int i = 0; i < data.length; i++) {
      data[i] = weight;
		}
  
		Kernel kernel = new Kernel(width, height, data);
		return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
	}
	
	public static BufferedImage transform(BufferedImage input, double degrees, boolean keepcenter, int direction){
		switch(direction){
			case FLIP_LEFT:
				return transformYaxis(input, degrees, keepcenter, true);
			case FLIP_RIGHT:
				return transformYaxis(input, degrees, keepcenter, false);
			case FLIP_UP:
				return transformXaxis(input, degrees, keepcenter, true);
			case FLIP_DOWN:
				return transformXaxis(input, degrees, keepcenter, false);
			default:
				return transformYaxis(input, degrees, keepcenter, false);
		}
	}
	
	private static BufferedImage transformXaxis(BufferedImage input, double degrees, boolean keepcenter, boolean to_left){
		long st = System.currentTimeMillis();
		int width = input.getWidth();
		int height = input.getHeight();
		float scalar = 1.0f - ((float)degrees * (0.5f/85.0f));
		double rad_a = degrees * (Math.PI/180.0);
		
		int new_height = (int) (scalar*(height * Math.sin(rad_a) * Math.cos(rad_a)) / (Math.tan(rad_a)));
		width = (int)(scalar*width);
		float alpha_overlay = (float)degrees * 1.0f / 80.0f;
		
		
		BufferedImage result = new BufferedImage(width, new_height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D buffer = result.createGraphics();
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		BufferedImage new_width_input = new BufferedImage(width, new_height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D nwg = new_width_input.createGraphics();		
		nwg.drawImage(input, 0, 0, width, new_height, null);			
		nwg.dispose();
		
		int dh = 2;
		float alpha_factor = alpha_overlay / new_height;
		
		for(int y = 0;y<new_height;y+=dh){
			double ddw = 0.25*y*Math.tan(rad_a);
			if(to_left) {
				ddw = 0.25*(new_height-y)*Math.tan(rad_a);
			}
			int w = (int)(width - 2*ddw);
			
			if(w < 1){
				w=1;
				ddw = height/2;
			}
			
			BufferedImage target = new BufferedImage(w, dh, BufferedImage.TYPE_INT_ARGB);
			Graphics2D tg = target.createGraphics();
		  tg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		  
			if(to_left){
				float val = alpha_overlay - y*alpha_factor;
				if(val > 1){
					val = 1.0f;
				}else if(val < 0){
					val = 0.0f;
				}
				tg.setComposite(AlphaComposite.SrcOver.derive(val));
			}else{
				float val = y*alpha_factor;
				if(val > 1){
					val = 1.0f;
				}else if(val < 0){
					val = 0.0f;
				}
				tg.setComposite(AlphaComposite.SrcOver.derive(val));
			}
			
			tg.fillRect(0,0, w, dh);
			tg.setComposite(AlphaComposite.SrcOut.derive(1.0f));
			tg.drawImage(new_width_input, 0, -y, w, new_height, null);
			
			/* Possible Blurring
			int radius = 1;
			
			target = getBlurFilter(radius, 0).filter(target, null);
			target = getBlurFilter(0, radius).filter(target, null);
			
			*/
			
			buffer.drawImage(target, (int)ddw, y, w, dh, null);
			tg.dispose();
		}
		
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		buffer.dispose();

		if(keepcenter){
			BufferedImage displaced = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB); 
			displaced.createGraphics().drawImage(result, (input.getWidth() - result.getWidth()) / 2, (input.getHeight() - result.getHeight()) / 2, result.getWidth(), result.getHeight(), null);
			return displaced;
		}
		
		return result;
	}
	
	private static BufferedImage transformYaxis(BufferedImage input, double degrees, boolean keepcenter, boolean to_left){
		long st = System.currentTimeMillis();
		int width = input.getWidth();
		int height = input.getHeight();
		double rad_a = degrees * (Math.PI/180.0);
		int new_width = (int) ((width * Math.sin(rad_a) * Math.cos(rad_a)) / (Math.tan(rad_a)));
		
//	 zooming for speed, though performance is the worst when zoom is near original image, so it does not matter much, but it looks nice
		float scalar = 1.0f - ((float)degrees * (0.5f/85.0f)); 
		//========
		
		new_width = (int)(scalar*new_width); 
		height = (int)(scalar* height);
		float alpha_overlay = (float)degrees * 1.0f / 80.0f;

		BufferedImage result = new BufferedImage(new_width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D buffer = result.createGraphics();
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		BufferedImage new_width_input = new BufferedImage(new_width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D nwg = new_width_input.createGraphics();		
		nwg.drawImage(input, 0, 0, new_width, height, null);			
		nwg.dispose();
		
		int dw = 2;
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
		  tg.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);		
			
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
			displaced.createGraphics().drawImage(result, (input.getWidth() - result.getWidth()) / 2, (input.getHeight() - result.getHeight()) / 2, result.getWidth(), result.getHeight(), null);
			return displaced;
		}		
		
		return result;
	}	
	
	public static void main(String[] args){
		try{
			
			BufferedImage img = ImageIO.read(new FileInputStream("/home/aphilip/Desktop/test.png"));
//			int angle = 45;
			for (int angle = 25;angle<50;angle+=5){
			
			  //BufferedImage lowres = new BufferedImage(img.getWidth()/2, img.getHeight()/2, BufferedImage.TYPE_INT_ARGB);
			  //Graphics2D lrg = lowres.createGraphics();
			  //lrg.drawImage(img, 0, 0, lowres.getWidth(), lowres.getHeight(), null);
				long st = System.currentTimeMillis();
				BufferedImage result = transformYaxis(img, angle, true, true);
				System.err.println("Done.slow: " + (System.currentTimeMillis()-st) + " ms");
				
				ImageIO.write(result, "png", new File("/home/aphilip/Desktop/test-slow" + angle +".png"));
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	

}
