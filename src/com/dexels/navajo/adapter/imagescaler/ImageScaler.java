/*
 * ImageScaler.java - Copyright (c) 2004 Torsten R�er - dode@luniks.net
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.dexels.navajo.adapter.imagescaler;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.plugins.jpeg.*;
import javax.imageio.spi.*;
import javax.imageio.stream.*;

/**
 * Class to demonstrate a way to scale an image without the
 * requirement of a graphical environment and without enabling
 * the headless support available since Java 1.4
 * @author Torsten R�er
 * @author www.luniks.net
 */
public class ImageScaler {
  
  /**
   * Reads an image of format GIF, JPEG or PNG, scales and saves it
   * as a JPEG image where no graphical environment is available
   * without enabling headless support.
   * Works thanks to the class ImageGenerator from j3d.org
   * @param infile the image file to be used as input
   * @param outfile write the scaled image to this file
   * @param width the width to scale to
   * @param height the height to scale to
   * @param keepAspect if the aspect should be kept or not
   * @param quality the compression quality
 * @throws IOException 
   * @see org.j3d.util.ImageGenerator
   */
  public static void scale(ImageInputStream infile,
                          ImageOutputStream outfile,
                           int width,
                           int height,
                           boolean keepAspect,
                           float quality) throws IOException {

    BufferedImage original = ImageIO.read(infile);
    if(original == null) {
      throw new IOException("Unsupported file format!");
    }
    
    int originalWidth = original.getWidth();
    int originalHeight = original.getHeight();
    float factorX = (float)originalWidth / width;
    float factorY = (float)originalHeight / height;
    if(keepAspect) {
      factorX = Math.max(factorX, factorY);
      factorY = factorX;
    }
    
    // The scaling will be nice smooth with this filter
    AreaAveragingScaleFilter scaleFilter =
      new AreaAveragingScaleFilter(Math.round(originalWidth / factorX),
                                   Math.round(originalHeight / factorY));
    ImageProducer producer = new FilteredImageSource(original.getSource(),
                                                     scaleFilter);
    ImageGenerator generator = new ImageGenerator();
    producer.startProduction(generator);
    BufferedImage scaled = generator.getImage();
    
    // Write the scaled image to a file
    // ImageIO.write(scaled, "jpg", outfile);

    // Alternatively set the compression quality and then write
    // the image to a file
    JPEGImageWriteParam param = new JPEGImageWriteParam(null);
    param.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
    param.setCompressionQuality(quality);
    java.util.Iterator it = ImageIO.getImageWritersBySuffix("jpg");
    ImageWriter writer = (ImageWriter)it.next();
    writer.setOutput(outfile);
    writer.write(null, new IIOImage(scaled, null, null), param);
    writer.dispose();
  }
  
  /**
   * Converts a java.awt.Image to a java.awt.image.BufferedImage.
   * Requires a graphics context. Not used in this class.
   * @param image the Image to convert to a BufferedImage
   * @return the BufferedImage the Image has been converted to
   */
  public static BufferedImage convert(Image image) {
    BufferedImage bi = new BufferedImage(image.getWidth(null),
                                         image.getHeight(null),
                                         BufferedImage.TYPE_INT_RGB);
    Graphics g = bi.getGraphics();
    g.drawImage(image, 0, 0, null);
    g.dispose();
    return bi;
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
//      e.printStackTrace();
//    }
  }
}

