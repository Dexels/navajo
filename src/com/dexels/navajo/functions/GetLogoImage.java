package com.dexels.navajo.functions;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public final class GetLogoImage extends FunctionInterface {
	
	private int reflectionSize = 18;
	private int height = 60;
	private int width = 265;

	public String remarks() {
		return "Get the specified string a a nice mirroed logo image";
	}

	public String usage() {
		return "Get the string as an image";
	}

	public final Object evaluate() throws TMLExpressionException {
		// input (ArrayList, Object).
		if (this.getOperands().size() != 1)
			throw new TMLExpressionException("getLogoImage(String) expected");
		Object a = this.getOperands().get(0);
		if (a == null) {
			return null;
		}
		if (!(a instanceof String))
			throw new TMLExpressionException("getLogoImage(String) expected");

		return createTextImage((String)a);
	}

	private final Binary createTextImage(String text) {
		Binary result = new Binary();

		int fontsize = 8;

		int string_width = 0;
		Font f = null;
		Rectangle2D stringbounds = new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
		JComponent c = new JLabel();
		while (string_width < width - 10 && stringbounds.getHeight() < (0.66 * height)) {
			fontsize++;
			f = new Font("Dialog", Font.BOLD, fontsize);
			FontMetrics fm = c.getFontMetrics(f);
			string_width = fm.stringWidth(text);
			stringbounds = fm.getStringBounds(text, null);
		}

		BufferedImage text_target = new BufferedImage(width, (int) stringbounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D text_targetGraphics = text_target.createGraphics();

		text_targetGraphics.setFont(f);
		text_targetGraphics.setColor(Color.black);
		text_targetGraphics.drawString(text, width / 2 - (int) (stringbounds.getWidth() / 2), (int) (-stringbounds.getY() + 1));

		text_target = getBlurFilter(2, 0).filter(text_target, null);
		text_target = getBlurFilter(0, 2).filter(text_target, null);

		BufferedImage target = new BufferedImage(width, (int) stringbounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D targetGraphics = target.createGraphics();

		targetGraphics.setColor(Color.white);
		targetGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		targetGraphics.setFont(f);
		targetGraphics.drawImage(text_target, 0, 0, text_target.getWidth(), text_target.getHeight(), null);
		targetGraphics.drawString(text, width / 2 - (int) (stringbounds.getWidth() / 2), (int) (-stringbounds.getY() + 1));

		BufferedImage reflection = createReflection(target);

		OutputStream out = result.getOutputStream();
		try {
			ImageIO.write(reflection, "png", out);
			out.close();
		} catch (Exception e) {
			
		}
		result.setMimeType(result.guessContentType());
		return result;
	}

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

	private final BufferedImage createReflection(BufferedImage img) {
		int height = img.getHeight();
		BufferedImage result = new BufferedImage(img.getWidth(), (int) (height + reflectionSize), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = result.createGraphics();

		g2.drawImage(img, 0, 0, null);
		g2.scale(1.0, -1.0);
		g2.drawImage(img, 0, -height - height, null);
		g2.scale(1.0, -1.0);
		g2.translate(0, height);

		GradientPaint mask = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.5f), 0, reflectionSize, new Color(1.0f, 1.0f, 1.0f, 0.0f));
		g2.setPaint(mask);
		g2.setComposite(AlphaComposite.DstIn);
		g2.fillRect(0, 0, img.getWidth(), reflectionSize);
		g2.dispose();
		return result;
	}
}
