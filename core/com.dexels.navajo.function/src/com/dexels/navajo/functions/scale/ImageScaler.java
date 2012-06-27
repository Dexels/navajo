package com.dexels.navajo.functions.scale;

//import java.awt.Graphics;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.dexels.navajo.document.types.Binary;

/** Class which scales images and keeping transparency
 * @author Erik Versteeg
 */
public class ImageScaler {
    private static Binary scale(Binary b, int width, int height, boolean keepAspect, boolean alsoScaleUp, boolean clipToCenter, boolean cropToCenter) throws IOException {
    	if (b==null || b.getLength()<=0) {
            return null;
        }
        InputStream is = null;
        ImageInputStream iis = null;
        OutputStream os = null;
        ImageOutputStream ios = null;
        try {
        	Binary c = new Binary();
        	is = b.getDataAsStream();
        	iis = ImageIO.createImageInputStream(is);
        	os = c.getOutputStream();
        	ios = ImageIO.createImageOutputStream(os);
        	os.flush();
            ImageScaler.scale(iis, ios, width, height, keepAspect, alsoScaleUp, clipToCenter, cropToCenter);
        	ios.flush();
        	ios.close();
        	ios = null;
        	return c;
        } catch (IOException e) {
        	throw new IOException(e.getMessage());
        } finally {
        	if ( is != null ) {
        		try {
        			is.close();
        		} catch (IOException e) {
        			
        		}
        	}
        	if ( iis != null ) {
        		try {
        			iis.close();
        		} catch (IOException e) {
        			
        		}
        	}
        	if ( os != null ) {
        		try {
        			os.close();
        		} catch (IOException e) {
        			
        		}
        	}
        	if ( ios != null ) {
        		try {
        			ios.close();
        		} catch (Exception e) {
        			// too bad, ios can throw a NullPointer too
        		}
        	}
        }
    }

	public static Binary scaleToMax(Binary b, int width, int height, double compressionQuality) throws IOException {
		if (width > height) {
			height = width;
		}
		if (height > width) {
			width = height;
		}
		return scale(b, width, height, true, false, false, false);
	}
	
	public static Binary scaleToMin(Binary b, int width, int height, double compressionQuality) throws IOException {
		return scale(b, width, height, true, true, false, false);
	}
	
	public static Binary scaleFree(Binary b, int width, int height, double compressionQuality) throws IOException {
		return scale(b, width, height, false, true, false, false);
	}
	
	public static Binary scaleClipped(Binary b, int width, int height) throws IOException {
		return scale(b, width, height, false, false, true, false);
	}
	
	public static Binary scaleCropped(Binary b, int width, int height) throws IOException {
		return scale(b, width, height, false, false, true, true);
	}

	/**
	 * Convenience method to determine which actual method needs to be called
	 * @param infile
	 * @param outfile
	 * @param width
	 * @param height
	 * @param keepAspect
	 * @param alsoScaleUp
	 * @param clipToCenter
	 * @param cropToCenter
	 * @throws IOException
	 */
	private static void scale(ImageInputStream infile,
							  ImageOutputStream outfile, 
							  int width,
							  int height,
							  boolean keepAspect,
							  boolean alsoScaleUp,
							  boolean clipToCenter,
							  boolean cropToCenter) throws IOException {
		
		if (clipToCenter) {
			scaleClip(infile, outfile, width, height);
		} else if (cropToCenter) {
			scaleCrop(infile, outfile, width, height);
		} else {
			scale(infile, outfile, width, height, keepAspect, alsoScaleUp);
		}
	}
	

	/**
    * Reads an image of format GIF, JPEG or PNG, scales and saves it
    * If necessary the image will be cropped to fit the given size
    * @param infile the image file to be used as input
    * @param outfile write the scaled image to this file
    * @param width the width to scale to
    * @param height the height to scale to
    * @throws IOException 
    */
	private static void scaleCrop(ImageInputStream infile,
							      ImageOutputStream outfile, 
							      int width,
							      int height) throws IOException {
		
		BufferedImage original = ImageIO.read(infile);
		if (original == null) {
			throw new IOException("Unsupported file format!");
		}
		
		float factorX = (float)original.getWidth() / width;
		float factorY = (float)original.getHeight() / height;
		factorX = Math.max(factorX, factorY);
		factorY = factorX;
//		System.out.println("factorX: " + factorX + " - factorY: " + factorY);
		Dimension dim = getNewCroppedDimension(width, height, original.getWidth(), original.getHeight(), factorX, factorY);
		original = ImageScaler.getScaledBufferedImage(original, (int)dim.getWidth(), (int)dim.getHeight());

		// Scale the img and then clip and center
		// determine the x and y
		// if 1 of them is generating an error because of the size (which is out of bounds),
		// then create a new img with the requested dimension
		boolean createNewImg = false;
		int x = ((original.getWidth() - width) / 2);
		int y = ((original.getHeight() - height) / 2);
		if ((x + width) > original.getWidth() || (y + height) > original.getHeight()) {
			createNewImg = true;
		}
		
		if (createNewImg) {
			// determine the dimensions
			int w = width > original.getWidth() ? original.getWidth() : width;
			int h = height > original.getHeight() ? original.getHeight() : height;
			x = x < 0 ? 0 : x;
			y = y < 0 ? 0 : y;
			BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = newImg.createGraphics();
			g.setColor(Color.decode("#ff0000"));
			g.fillRect(0, 0, width, height);
			g.drawImage(original.getSubimage(x, y, w, h), ((width - w) / 2), ((height - h) / 2), null);
			original = newImg;
		} else {
			original = original.getSubimage(x, y, width, height);
		}
		
//		System.out.println("x: " + x + " - y: " + y + " - originalWidth: " + original.getWidth() + " - originalHeight: " + original.getHeight());

		scale(original, outfile, width, height, 1, 1);
	}
	
	/**
	 * Calculates the new width and height taking all situations in consideration
	 * The largest possible image will be scaled and the aspect ratio will be maintained
	 * After that the image will be, if necessary, cropped to the given size
	 * @param width
	 * @param height
	 * @param originalWidth
	 * @param originalHeight
	 * @param factorX
	 * @param factorY
	 * @return Dimension
	 */
	private static Dimension getNewCroppedDimension(int width, int height, int originalWidth, int originalHeight, float factorX, float factorY) {
		Dimension dim = new Dimension();
		int newWidth = width;
		int newHeight = height;
		
		if ((width < originalWidth) && (height < originalHeight)) {
			if (width > height) {
				float newFactor = ((float)originalWidth / (float)width);
				if ((originalHeight / newFactor) > height) {
					newFactor = ((float)originalHeight / (float)height);
					newWidth = (int)(originalWidth / newFactor);
				} else {
					newHeight = (int)(originalHeight / newFactor);
				}
			} else {
				float newFactor = ((float)originalHeight / (float)height);
				if ((originalWidth / newFactor) > width) {
					newFactor = ((float)originalWidth / (float)width);
					newHeight = (int)(originalHeight / newFactor);
				} else {
					newWidth = (int)(originalWidth / newFactor);
				}
			}
		} else if ((width >= originalWidth) && (height >= originalHeight)) {
			newWidth = originalWidth;
			newHeight = originalHeight;
		} else if (width >= originalWidth) {
			newWidth = originalWidth;
			// check if the new value will be correct
			if ((height * factorY) > originalHeight) {
				newWidth = (int)(originalWidth / factorX);
			} else {
				newWidth = (int)(originalWidth / factorX);
			}
		} else if (height >= originalHeight) {
			newHeight = originalHeight;
			// check if the new value will be correct
			if ((width * factorX) > originalWidth) {
				newHeight = (int)(originalHeight / factorY);
			} else {
				newHeight = (int)(originalHeight / factorY);
			}
		}

		dim.setSize(newWidth, newHeight);
		return dim;
	}
	
	/**
    * Reads an image of format GIF, JPEG or PNG, scales and saves it
    * The image will be scaled and fit the given size as large as possible
    * @param infile the image file to be used as input
    * @param outfile write the scaled image to this file
    * @param width the width to scale to
    * @param height the height to scale to
    * @throws IOException 
    */
	private static void scaleClip(ImageInputStream infile,
			 				      ImageOutputStream outfile, 
							      int width,
							      int height) throws IOException {

		BufferedImage original = ImageIO.read(infile);
		if (original == null) {
			throw new IOException("Unsupported file format!");
		}
		
		float factorX = (float)original.getWidth() / width;
		float factorY = (float)original.getHeight() / height;
		factorX = Math.max(factorX, factorY);
		factorY = factorX;
		Dimension dim = getNewClippedDimension(width, height, original.getWidth(), original.getHeight(), factorX, factorY);
		original = ImageScaler.getScaledBufferedImage(original, (int)dim.getWidth(), (int)dim.getHeight());

		// Scale the img and then clip and center
		// determine the x and y
		// if 1 of them is generating an error because of the size (which is out of bounds),
		// then create a new img with the requested dimension
		boolean createNewImg = false;
		int x = ((original.getWidth() - width) / 2);
		int y = ((original.getHeight() - height) / 2);
		if ((x + width) > original.getWidth() || (y + height) > original.getHeight()) {
			createNewImg = true;
		}
		
		if (createNewImg) {
			// determine the dimensions
			int w = width > original.getWidth() ? original.getWidth() : width;
			int h = height > original.getHeight() ? original.getHeight() : height;
			x = x < 0 ? 0 : x;
			y = y < 0 ? 0 : y;
			BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = newImg.createGraphics();
//			g.setColor(Color.decode("#ff0000"));
//			g.fillRect(0, 0, width, height);
			g.drawImage(original.getSubimage(x, y, w, h), ((width - w) / 2), ((height - h) / 2), null);
			original = newImg;
		} else {
			original = original.getSubimage(x, y, width, height);
		}

		scale(original, outfile, width, height, 1, 1);
	}
	
	/**
	 * Calculates the new width and height taking all situations in consideration
	 * The largest possible image will be scaled and the aspect ratio will be maintained
	 * @param width
	 * @param height
	 * @param originalWidth
	 * @param originalHeight
	 * @param factorX
	 * @param factorY
	 * @return Dimension
	 */
	private static Dimension getNewClippedDimension(int width, int height, int originalWidth, int originalHeight, float factorX, float factorY) {
		Dimension dim = new Dimension();
		int newWidth = width;
		int newHeight = height;
		
		if ((width < originalWidth) && (height < originalHeight)) {
			if (width > height) {
				float newFactor = ((float)originalWidth / (float)width);
				if ((originalHeight / newFactor) > height) {
					newFactor = ((float)originalHeight / (float)height);
					newWidth = (int)(originalWidth / newFactor);
				} else {
					newHeight = (int)(originalHeight / newFactor);
				}
			} else {
				float newFactor = ((float)originalHeight / (float)height);
				if ((originalWidth / newFactor) > width) {
					newFactor = ((float)originalWidth / (float)width);
					newHeight = (int)(originalHeight / newFactor);
				} else {
					newWidth = (int)(originalWidth / newFactor);
				}
			}
		} else if ((width >= originalWidth) && (height >= originalHeight)) {
			newWidth = originalWidth;
			newHeight = originalHeight;
		} else if (width >= originalWidth) {
			newWidth = originalWidth;
			// check if the new value will be correct
			if ((height * factorY) > originalHeight) {
				newWidth = (int)(originalWidth / factorX);
			} else {
				newWidth = (int)(originalWidth / factorX);
			}
		} else if (height >= originalHeight) {
			newHeight = originalHeight;
			// check if the new value will be correct
			if ((width * factorX) > originalWidth) {
				newHeight = (int)(originalHeight / factorY);
			} else {
				newHeight = (int)(originalHeight / factorY);
			}
		}

		dim.setSize(newWidth, newHeight);
		return dim;
	}

	/**
	 * Reads an image of format GIF, JPEG or PNG, scales and saves it
	 * @param infile the image file to be used as input
	 * @param outfile write the scaled image to this file
	 * @param width the width to scale to
	 * @param height the height to scale to
	 * @param keepAspect if the aspect should be kept or not
	 * @param alsoScaleUp if allowed to scale up or not
	 * @throws IOException
	 */
	private static void scale(ImageInputStream infile,
							  ImageOutputStream outfile, 
							  int width, 
							  int height,
							  boolean keepAspect, 
							  boolean alsoScaleUp) throws IOException {

		BufferedImage original = ImageIO.read(infile);
		if (original == null) {
			throw new IOException("Unsupported file format!");
		}

		if (!alsoScaleUp) {
			if (width > original.getWidth()) {
				width = original.getWidth();
			}
			if (height > original.getHeight()) {
				height = original.getHeight();
			}
		}

		float factorX = (float) original.getWidth() / width;
		float factorY = (float) original.getHeight() / height;
		if (keepAspect) {
			factorX = Math.max(factorX, factorY);
			factorY = factorX;
		}

		scale(original, outfile, width, height, factorX, factorY);
	}

	/**
	 * Method that actually creates the new image
	 * @param original
	 * @param outfile
	 * @param width
	 * @param height
	 * @param factorX
	 * @param factorY
	 * @throws IOException
	 */
	private static void scale(BufferedImage original,
							  ImageOutputStream outfile,
							  int width,
							  int height,
							  float factorX,
 							  float factorY) throws IOException {
		
		BufferedImage out = ImageScaler.getScaledBufferedImage(original, Math.round(original.getWidth() / factorX), Math.round(original.getHeight() / factorY));
		ImageIO.write(out, "png", outfile);
	}
	
	/**
	 * Creates a new BufferedImage object based on the given dimensions
	 * @param original
	 * @param width
	 * @param height
	 * @param factorX
	 * @param factorY
	 * @return BufferedImage
	 */
	private static BufferedImage getScaledBufferedImage(BufferedImage original,
												        int width,
												        int height) {
		
		GraphicsConfiguration gc = original.createGraphics().getDeviceConfiguration();
		BufferedImage out = gc.createCompatibleImage(width, height, Transparency.BITMASK);
		Graphics2D g2d = out.createGraphics();
		g2d.setComposite(AlphaComposite.Src);
		g2d.drawImage(original.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH), 0, 0, width, height, null);
		g2d.dispose();
		return out;
	}

  /**
   * Just for testing...
   */
  public static void main(String[] args) {
//    if(args.length < 6) {
//      System.out.println("Usage: ImageScaler <infile> <outfile> " +
//                         "<width> <height> <keep aspect> <quality>");
//      return;
//    }
//    try {
//      scale(new File(args[0]),
//            new File(args[1]),
//            Integer.parseInt(args[2]),
//            Integer.parseInt(args[3]),
//            Boolean.valueOf(args[4]).booleanValue(),
//            Float.parseFloat(args[5]));
//    }
//    catch(Exception e) {
//    }
	  Binary b = null;
	try {
		b = new Binary(new File("C:/Users/Erik/Desktop/logo.gif"));
//		b = new Binary(new File("C:/Users/Erik/Desktop/DSC_0009.jpg"));
//		b = new Binary(new File("C:/Users/Erik/Desktop/logo1.png"));
	} catch (IOException e1) {
		e1.printStackTrace();
	}
      Integer width = 400;
      Integer height = 400;

      try {
//          Binary res = ImageScaler.scaleToMax(b, width.intValue(), height.intValue(), 1);
//          Binary res = ImageScaler.scaleFree(b, width.intValue(), height.intValue(), 1);
//          Binary res = ImageScaler.scaleClipped(b, width.intValue(), height.intValue());
          Binary res = ImageScaler.scaleCropped(b, width.intValue(), height.intValue());
          res.write(res.getOutputStream());
          System.out.println("Filename: " + res.getFile().getAbsolutePath() + res.getFile().getName());
      } catch (IOException e) {
      }
	  
  }
}

