package com.dexels.navajo.functions.scale;

import java.awt.AlphaComposite;
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

	public static Binary scaleToMax(Binary b, int width, int height) throws IOException {
		if (width > height) {
			height = width;
		}
		if (height > width) {
			width = height;
		}
		return scale(b, width, height, true, false, false, false);
	}
	
	public static Binary scaleToMin(Binary b, int width, int height) throws IOException {
		return scale(b, width, height, true, true, false, false);
	}
	
	public static Binary scaleFree(Binary b, int width, int height) throws IOException {
		return scale(b, width, height, false, true, false, false);
	}
	
	public static Binary scaleCentered(Binary b, int width, int height) throws IOException {
		return scale(b, width, height, false, false, true, false);
	}
	
	public static Binary scaleCropped(Binary b, int width, int height) throws IOException{
		return scale(b, width, height, false, false, false, true);
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
			scaleCenter(infile, outfile, width, height);
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
		
		Dimension dim = ImageScaler.getNewCroppedDimension(width, height, original.getWidth(), original.getHeight());
//		System.out.println("width: " + width + "\nheight: " + height + "\nnewWidth: " + dim.getWidth() + "\nnewHeight: " + dim.getHeight() + "\noriginalWidth: " + original.getWidth() + "\noriginalHeight: " + original.getHeight());
		
		original = ImageScaler.getSubScaledBufferedImage(original, width, height, (int)dim.getWidth(), (int)dim.getHeight());

		scale(original, outfile, width, height, 1, 1);
	}
	
	/**
	 * Calculates the new width and height while possibly cropping the image to make it fit
	 * @param desiredWidth
	 * @param desiredHeight
	 * @param originalWidth
	 * @param originalHeight
	 * @return Dimension
	 */
	private static Dimension getNewCroppedDimension(int desiredWidth, int desiredHeight, int originalWidth, int originalHeight) {
		Dimension dim = new Dimension();
		boolean isLandscape = ImageScaler.isLandscape(originalWidth, originalHeight);
		int newWidth = desiredWidth;
		int newHeight = desiredHeight;
		float factor = 0;
		
		// First do a basic scaling operation and then check if it's within the allowed dimension
		if (isLandscape) {
			factor = ImageScaler.getAspectRatioFactor(originalWidth, desiredWidth);
			newHeight = (int) (originalHeight / factor);
		} else {
			factor = ImageScaler.getAspectRatioFactor(originalHeight, desiredHeight);
			newWidth = (int) (originalWidth / factor);
		}
		
		// So now check and correct if necessary
		if ((newHeight > originalHeight) && (newWidth > originalWidth)) {
			newWidth = originalWidth;
			newHeight = originalHeight;
		
		} else if (isLandscape && ((newHeight > originalHeight) ||
								   ((newHeight < desiredHeight) && (desiredHeight <= originalHeight)) ||
								   ((newHeight > desiredHeight) && (desiredHeight <= originalHeight))
								   )) {
			newHeight = desiredHeight > originalHeight ? originalHeight : desiredHeight;
			newWidth = (int)(originalWidth / ImageScaler.getAspectRatioFactor(originalHeight, desiredHeight));
		
		} else if (!isLandscape && ((newWidth > originalWidth) ||
								    ((newWidth < desiredWidth) && (desiredWidth <= originalWidth)) ||
								    ((newWidth > desiredWidth) && (desiredWidth <= originalWidth))
								    )) {
			newWidth = desiredWidth > originalWidth ? originalWidth : desiredWidth;
			newHeight = (int)(originalHeight / ImageScaler.getAspectRatioFactor(originalWidth, desiredWidth));
			
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
	private static void scaleCenter(ImageInputStream infile,
			 				        ImageOutputStream outfile, 
							        int width,
							        int height) throws IOException {

		BufferedImage original = ImageIO.read(infile);
		if (original == null) {
			throw new IOException("Unsupported file format!");
		}
		
		Dimension dim = ImageScaler.getNewCenteredDimension(width, height, original.getWidth(), original.getHeight());
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
	 * @param desiredWidth
	 * @param desiredHeight
	 * @param originalWidth
	 * @param originalHeight
	 * @return Dimension
	 */
	private static Dimension getNewCenteredDimension(int desiredWidth, int desiredHeight, int originalWidth, int originalHeight) {
		Dimension dim = new Dimension();
		int newWidth = desiredWidth;
		int newHeight = desiredHeight;
		float factorX = ImageScaler.getAspectRatioFactor(originalWidth, desiredWidth);
		float factorY = ImageScaler.getAspectRatioFactor(originalHeight, desiredHeight);
		factorX = Math.max(factorX, factorY);
		factorY = factorX; // looks silly, but it is needed in some cases
		
		if ((desiredWidth < originalWidth) && (desiredHeight < originalHeight)) {
			if (desiredWidth > desiredHeight) {
				float newFactor = ((float)originalWidth / (float)desiredWidth);
				if ((originalHeight / newFactor) > desiredHeight) {
					newFactor = ((float)originalHeight / (float)desiredHeight);
					newWidth = (int)(originalWidth / newFactor);
				} else {
					newHeight = (int)(originalHeight / newFactor);
				}
			} else {
				float newFactor = ((float)originalHeight / (float)desiredHeight);
				if ((originalWidth / newFactor) > desiredWidth) {
					newFactor = ((float)originalWidth / (float)desiredWidth);
					newHeight = (int)(originalHeight / newFactor);
				} else {
					newWidth = (int)(originalWidth / newFactor);
				}
			}
		} else if ((desiredWidth >= originalWidth) && (desiredHeight >= originalHeight)) {
			newWidth = originalWidth;
			newHeight = originalHeight;
		} else if (desiredWidth >= originalWidth) {
			newWidth = originalWidth;
			// check if the new value will be correct
			if ((desiredHeight * factorY) > originalHeight) {
				newWidth = (int)(originalWidth / factorX);
			} else {
				newWidth = (int)(originalWidth / factorX);
			}
		} else if (desiredHeight >= originalHeight) {
			newHeight = originalHeight;
			// check if the new value will be correct
			if ((desiredWidth * factorX) > originalWidth) {
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

		float factorX = ImageScaler.getAspectRatioFactor(original.getWidth(), width);
		float factorY = ImageScaler.getAspectRatioFactor(original.getHeight(), height);
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
	 * @return BufferedImage
	 */
	private static BufferedImage getScaledBufferedImage(BufferedImage original,
												        int width,
												        int height) {
		
		GraphicsConfiguration gc = original.createGraphics().getDeviceConfiguration();
		BufferedImage out = gc.createCompatibleImage(width, height, Transparency.BITMASK);
		Graphics2D g2d = out.createGraphics();
//		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setComposite(AlphaComposite.Src);
        if (original.getTransparency() == Transparency.BITMASK) {
    		g2d.drawImage(original.getScaledInstance(width, height, BufferedImage.SCALE_REPLICATE), 0, 0, width, height, null);
        } else {
    		g2d.drawImage(original.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH), 0, 0, width, height, null);
        }
		g2d.dispose();
		return out;
	}
	
	/**
	 * Scales the image and then crops (and centers) it if necessary
	 * @param original
	 * @param totalWidth
	 * @param totalHeight
	 * @param imgWidth
	 * @param imgHeight
	 * @return BufferedImage
	 */
	private static BufferedImage getSubScaledBufferedImage(BufferedImage original,
												           int totalWidth,
												           int totalHeight,
												           int imgWidth,
												           int imgHeight) {
		
		BufferedImage image = ImageScaler.getScaledBufferedImage(original, imgWidth, imgHeight);
		int x = (imgWidth < totalWidth) ? 0 : ((totalWidth - imgWidth) / 2);
		int y = (imgHeight < totalHeight) ? 0 : ((totalHeight - imgHeight) / 2);
		image = image.getSubimage(Math.abs(x), Math.abs(y), (totalWidth <= image.getWidth() ? totalWidth : image.getWidth()), (totalHeight <= image.getHeight() ? totalHeight : image.getHeight()));
		
		BufferedImage newImg = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newImg.createGraphics();
//		g.setColor(Color.decode("#ff0000"));
//		g.fillRect(0, 0, totalWidth, totalHeight);
		// calculate the correct x and y
		x = ((totalWidth - image.getWidth()) / 2);
		y = ((totalHeight - image.getHeight()) / 2);
		g.drawImage(image, Math.abs(x), Math.abs(y), null);
		return newImg;
	}
	
	/**
	 * Determines if the image is landscape or portrait
	 * If landscape, then value is true otherwise....you'll know
	 * @param width
	 * @param height
	 * @return boolean
	 */
	private static boolean isLandscape(int width, int height) {
		return (width >= height);
	}
	
	/**
	 * Computes the ratio factor
	 * @param originalSize
	 * @param size
	 * @return float
	 */
	private static float getAspectRatioFactor(int originalSize, int size) {
		return (float) originalSize / size;
	}
    
    
  /**
   * Just for testing...
   */
  public static void main(String[] args) {
	Binary b = null;
	try {
//		b = new Binary(new File("C:/Users/Erik/Desktop/logo.gif"));
//		b = new Binary(new File("C:/Users/Erik/Desktop/Naamloos.png"));
//		b = new Binary(new File("C:/_GedeeldeMap/DSC_0001.jpg"));
		b = new Binary(new File("C:/Users/Erik/Desktop/logo1.png"));
	} catch (IOException e1) {
		e1.printStackTrace();
	}
      Integer width = 600;
      Integer height = 800;

      try {
//          Binary res = ImageScaler.scaleToMax(b, width.intValue(), height.intValue(), 1);
//          Binary res = ImageScaler.scaleFree(b, width.intValue(), height.intValue(), 1);
          Binary res = ImageScaler.scaleCentered(b, width.intValue(), height.intValue());
//          Binary res = ImageScaler.scaleCropped(b, width.intValue(), height.intValue());
          res.write(res.getOutputStream());
          System.out.println("Filename: " + res.getFile().getAbsolutePath() + res.getFile().getName());
      } catch (IOException e) {
      }
	  
  }
}

