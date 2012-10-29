package com.dexels.navajo.functions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.Expression;
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
  private final int width = 150;
  private final int height = 30;
  private String str;
//  Component observer = new Label();
  Random rnd = new Random();
  
private final static Logger logger = LoggerFactory
		.getLogger(ToSecureImage.class);
  
  public ToSecureImage() {
  }

  public Object evaluate() throws TMLExpressionException {
    Object o = getOperand(0);

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
    return "ToSecureImage(Object value)";
  }

  private Binary generateImage(){
    try{
      BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = (Graphics2D)img.getGraphics();

      char[] characters = str.toCharArray();
      int xpos = 0;
      int ypos = 0;

      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g.setColor(Color.white);
      g.fillRect(0,0,width, height);
      g.setColor(Color.blue);

      for(int i=0;i<characters.length;i++){
        String s = new String(new char[]{characters[i]});
        Font f = new Font("Serif", Font.BOLD, 10 + rnd.nextInt(15));
        double bi_width = f.getStringBounds(s, g.getFontRenderContext()).getWidth();
        double bi_height = f.getStringBounds(s, g.getFontRenderContext()).getHeight()/1.6;

        BufferedImage bi = new BufferedImage(2*(int)Math.ceil(bi_width),2*(int)Math.ceil(bi_height), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        g2.fillRect(0,0,2*(int)Math.ceil(bi_width)-1,2*(int)Math.ceil(bi_height)-1);
        g2.setColor(new Color(rnd.nextInt(255), rnd.nextInt(180), rnd.nextInt(180)));

        boolean pos = rnd.nextBoolean();
        int sign = pos? 1: -1;
        AffineTransform rotate = AffineTransform.getRotateInstance(sign*rnd.nextDouble(), bi.getWidth()/2, bi.getHeight()/2);
        g2.transform(rotate);
        g2.setFont(f);
        g2.drawString(s, (int)Math.floor(bi_width/2), (int)Math.ceil(bi_height*1.5));

        g2.setColor(Color.gray);
        g2.drawRect(0,0,2*(int)Math.ceil(bi_width)-1,2*(int)Math.ceil(bi_height)-1);
        g.drawImage(bi, xpos, ypos, null);
        xpos += f.getStringBounds(s, g.getFontRenderContext()).getWidth() + 4;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
      }

//      ImageIcon imc = new ImageIcon(getClass().getResource("overlay.gif"));
//      Composite old = g.getComposite();
//      AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
//      g.setComposite(ac);
//      g.drawImage(imc.getImage(), 0, 0, observer);
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
//      g.setComposite(old);

      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      ImageIO.write(img, "png", bout);
      byte[] data = bout.toByteArray();
      bout.flush();
      bout.close();
      Binary b = new Binary(data);

      return b;
    }catch(Exception e){
    	logger.error("Error: ", e);
    }
    return null;
  }

  public static void main(String[] args){
    try{
      java.util.Locale.setDefault(new java.util.Locale("nl", "NL"));
      // Tests.
      ToSecureImage tm = new ToSecureImage();
      tm.reset();
      tm.insertOperand(new String("Alles is doch quatch"));
      Binary b = (Binary) tm.evaluate();

      // Using expressions.
      String expression = "ToSecureImage('NOTENAEP')";
      Operand o = Expression.evaluate(expression, null);
      System.out.println("o = " + o.value);
      System.out.println("type = " + o.type);

      FileOutputStream fos = new FileOutputStream(new java.io.File("/Users/arjenschoneveld/aepenoot.png"));
      fos.write(b.getData());
      fos.flush();
      fos.close();

      FileOutputStream fs = new FileOutputStream(new java.io.File("/Users/arjenschoneveld/noteaep.png"));
      Binary c = (Binary)o.value;
      fs.write(c.getData());
      fs.flush();
      fs.close();


     }catch(Exception e){
    	 logger.error("Error: ", e);
     }

  }


}
