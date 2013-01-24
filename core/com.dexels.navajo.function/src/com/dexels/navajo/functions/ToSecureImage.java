package com.dexels.navajo.functions;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * <p>Title: ToSecureImage.java</p>
 * <p>Description: Transforms a string to a security verification image</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public class ToSecureImage extends FunctionInterface{
  private int width = 110;
  private int height = 30;
  private String str;
  Component observer = null;
  Random rnd = new Random();

  public ToSecureImage() {
  }

  public Object evaluate() throws TMLExpressionException {
    Object o = getOperand(0);

    try{
	    Object w = getOperand(1);
	    Object h = getOperand(2);
	    
	    if(w != null && h != null){
	    	width = (Integer)w;
	    	height = (Integer)h;
	    }
    } catch(Exception e){
    	// Continue without setting width and height
    }
   if (o == null) {
     return null;
   }else{
     str = ""+o.toString();
     return generateImage();
   }
  }

  public String remarks() {
    return "Creates a Binary containing a PNG image representation of specified objects string representation\n max. inputlength is 6 chars";
  }

  public String usage() {
    return "ToSecureImage(Object value, <int width>, <int height>)";
  }

  private Binary generateImage(){
    try{
      BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = (Graphics2D)temp.getGraphics();

      char[] characters = str.toCharArray();
      int xpos = 0;
      int ypos = 0;

      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setColor(Color.white);
      g.fillRect(0,0,width, height);

      for(int i=0;i<characters.length;i++){
        String s = new String(new char[]{characters[i]});
//        Font f = new Font("Serif", Font.BOLD, 14 + rnd.nextInt(12));
        Font f = new Font("Serif", Font.BOLD, 20);
        double bi_width = f.getStringBounds(s, g.getFontRenderContext()).getWidth();
        double bi_height = f.getStringBounds(s, g.getFontRenderContext()).getHeight()/1.6;

        //final_height = 30;
        
        BufferedImage bi = new BufferedImage(2*(int)Math.ceil(bi_width),2*(int)Math.ceil(bi_height), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2.setColor(new Color(rnd.nextInt(255), rnd.nextInt(180), rnd.nextInt(180)));
        g2.setColor(Color.black);
        
        boolean pos = rnd.nextBoolean();
        int sign = pos? 1: -1;
        AffineTransform rotate = AffineTransform.getRotateInstance(sign*rnd.nextDouble()/2d, bi.getWidth()/2, bi.getHeight()/2);
        g2.transform(rotate);
        g2.setFont(f);
        g2.drawString(s, (int)Math.floor(bi_width/2), (int)Math.ceil(bi_height*1.5));

        g.drawImage(bi, xpos, ypos, observer);
        double xbounds = f.getStringBounds(s, g.getFontRenderContext()).getWidth() + 4;
        xpos += xbounds;
       
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
      }
     
      // Add some lines over the text
      
      for(int i=0;i<3;i++){
    	  g.setColor(new Color(rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat(), rnd.nextFloat()/1.2f));
    	  g.drawLine(0, rnd.nextInt(500), width, -1* rnd.nextInt(500));
    	  g.drawLine(width, -1*rnd.nextInt(50), 0,  rnd.nextInt(50));
      }
      
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

      BufferedImage finalImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D fg = (Graphics2D)finalImg.getGraphics();
      fg.drawImage(temp, 0, 0,  null);
      
      
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      ImageIO.write(finalImg, "png", bout);
      byte[] data = bout.toByteArray();
      bout.flush();
      bout.close();
      Binary b = new Binary(data);

      return b;
    }catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args){
    try{
      java.util.Locale.setDefault(new java.util.Locale("nl", "NL"));
      // Tests.
      ToSecureImage tm = new ToSecureImage();
      
      for(int i=0;i<100;i++){
      
	      tm.reset();
	      RandomString rs = new RandomString();
	      rs.reset();
	      rs.insertOperand(6);
	      String random = (String)rs.evaluate();
	      System.err.println("String["+i+"]: " + random);
	      // lala
	      
	      tm.insertOperand(new String(random));
	      tm.insertOperand(110);
	      tm.insertOperand(30);
	      Binary b = (Binary) tm.evaluate();
	
	      FileOutputStream fos = new FileOutputStream(new java.io.File("/Users/arnoud/secureimg/IMG_" + i + ".png"));
	      fos.write(b.getData());
	      fos.flush();
	      fos.close();
      }

     }catch(Exception e){
      e.printStackTrace();
    }

  }


}
