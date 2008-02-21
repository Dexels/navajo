package com.dexels.navajo.rich.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class MacBar extends JPanel {
	private boolean showBar = true;
	private String orientation = "bottom";

	public MacBar() {
		setLayout(new GridBagLayout());
	}

	public void add(JComponent link) {
		this.add(link, new GridBagConstraints(getComponentCount(), 0, 1, 1, 0.0, 1.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(0, 2, 0, 2), 0, 0));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Rectangle bounds = getBounds();
		// Color current = g2.getColor();
		// g2.setColor(getBackground());
		// g2.fill(bounds);
		// g2.setColor(current);
		if (showBar) {
			BufferedImage plank = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gPlank = plank.createGraphics();

			int[] xPoints = new int[4];
			int[] yPoints = new int[4];

			xPoints[0] = 10;
			xPoints[1] = getWidth() - 10;
			xPoints[2] = getWidth();
			xPoints[3] = 0;

			yPoints[0] = getHeight() - 22;
			yPoints[1] = getHeight() - 22;
			yPoints[2] = getHeight();
			yPoints[3] = getHeight();

			gPlank.setComposite(AlphaComposite.SrcOver.derive(0.5f));
			GradientPaint gradient = new GradientPaint(0, yPoints[0], Color.gray, 0, yPoints[2], Color.white);
			gPlank.setPaint(gradient);
			gPlank.fillPolygon(xPoints, yPoints, xPoints.length);

			gPlank.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			gPlank.setColor(Color.black);
			gPlank.drawPolygon(xPoints, yPoints, xPoints.length);
			gPlank.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

			g2.drawImage(plank, 0, 0, null);
		}

	}

}
