package com.dexels.navajo.functions;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * <p>
 * Title: ToSecureImage.java
 * </p>
 * <p>
 * Description: Transforms a string to a security verification image
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class CreatePlacemarkIcon extends FunctionInterface {
	private int width = 25;
	private int height = 30;
	private String label;
	private Color bgColor = Color.decode("#FFDD66");
	private Color fgColor = Color.black;

	private final static Logger logger = LoggerFactory.getLogger(CreatePlacemarkIcon.class);

	public CreatePlacemarkIcon() {
		System.err.println("TOE");
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		Object l = getOperand(0);
		try {
			
			
			Object w = getOperand(1);
			Object h = getOperand(2);
			Object bgc = getOperand(3);
			Object fgc = getOperand(4);
			
			if (w != null && h != null) {
				width = (Integer) w;
				height = (Integer) h;
			}
			
			if(bgc != null){
				bgColor = Color.decode(bgc.toString());
			}
			if(fgc != null){
				fgColor = Color.decode(fgc.toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// Continue without setting width and height
		}
		label = (String) l;
		return generateImage();
	}

	@Override
	public String remarks() {
		return "Creates a Binary containing a PNG image representation of placemark icon with selected label";
	}

	@Override
	public String usage() {
		return "CreatePlacemarkIcon(String label, <int width>, <int height>, <String backgroundColor>, <String foregroundColor>)";
	}

	private Binary generateImage() {
		try {
			Font f = new Font("Arial", Font.BOLD, 12);
			
			BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) temp.getGraphics();
			FontMetrics metrics = g.getFontMetrics(f);
			g.setFont(f);
			
			Rectangle2D fm = metrics.getStringBounds(label, g);

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(bgColor);
			
			GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 7);
			polygon.moveTo(0,0);
			polygon.lineTo(width, 0);
			polygon.lineTo(width, height - 5);
			polygon.lineTo((width/2) + 3, height -5);
			polygon.lineTo((width/2), height);
			polygon.lineTo((width/2) - 3, height -5);
			polygon.lineTo(0, height-5);
			
			
			g.fill(polygon);
			
			g.setColor(fgColor);
			g.draw(polygon);
			g.drawLine(0, 0, 0, height -5);
			g.drawLine(width-1, 0, width-1, height -5);
			g.drawString(label, (int)((width/2) - fm.getCenterX()) + 1, height - 12);
//			g.setColor(Color.red);
//			g.drawRect((int)((width/2) - fm.getCenterX()), height - 12, (int)fm.getWidth(), (int)fm.getWidth());

		    // Draw it
			
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_OFF);

			BufferedImage finalImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D fg = (Graphics2D) finalImg.getGraphics();
			fg.drawImage(temp, 0, 0, null);
			

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			//FileOutputStream bout = new FileOutputStream(new java.io.File(System.getProperty("user.home") + "/markers/marker_" + label + ".png"));
			ImageIO.write(finalImg, "png", bout);
			byte[] data = bout.toByteArray();
			bout.flush();
			bout.close();
			Binary b = new Binary(data);
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: ", e);
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			// Tests.
			CreatePlacemarkIcon cpm = new CreatePlacemarkIcon();
			cpm.reset();
			String label = "101";
			cpm.insertOperand(new String(label));
			Binary b = (Binary)cpm.evaluate();
			
			FileOutputStream fos = new FileOutputStream(new java.io.File(System.getProperty("user.home") + "/markers/marker_" + label + ".png"));
		      fos.write(b.getData());
		      fos.flush();
		      fos.close();
		} catch (Exception e) {
			System.err.println("aaap");
			e.printStackTrace();
			logger.error("Error: ", e);
		}

	}

}
