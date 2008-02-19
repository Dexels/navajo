package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.net.*;

import javax.swing.*;

import com.dexels.navajo.tipi.components.swingimpl.parsers.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingDesktop extends JDesktopPane {
	private static final String ALIGNMENT = "alignment";
	private static final String PAINT = "paint";
	private Image myImage = null;

	private final String NORTH = "north";
	private final String EAST = "east";
	private final String SOUTH = "south";
	private final String WEST = "west";
	private final String CENTER = "center";
	private final String NORTHEAST = "northeast";
	private final String SOUTHEAST = "southeast";
	private final String NORTHWEST = "northwest";
	private final String SOUTHWEST = "southwest";

	private String alignment = CENTER;
	private TipiGradientPaint paint = null;

	public TipiSwingDesktop() {
	}

	public void setLogoUrl(URL u) {
		myImage = new ImageIcon(u).getImage();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (paint != null) {
			paint.setBounds(this.getBounds());
			Paint p = paint.getPaint();
			Graphics2D g2 = (Graphics2D) g;
			Paint oldPaint = g2.getPaint();
			g2.setPaint(p);
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.setPaint(oldPaint);
		}

		if (myImage != null) {
			if (NORTHWEST.equals(alignment)) {
				g.drawImage(myImage, 0, 0, this);
			}
			if (NORTH.equals(alignment)) {
				g.drawImage(myImage, ((getWidth() / 2) - (myImage.getWidth(this) / 2)), 0, this);
			}
			if (NORTHEAST.equals(alignment)) {
				g.drawImage(myImage, getWidth() - myImage.getWidth(this), 0, this);
			}
			if (WEST.equals(alignment)) {
				g.drawImage(myImage, 0, ((getHeight() / 2) - (myImage.getHeight(this) / 2)), this);
			}
			if (CENTER.equals(alignment)) {
				g.drawImage(myImage, ((getWidth() / 2) - (myImage.getWidth(this) / 2)),
						((getHeight() / 2) - (myImage.getHeight(this) / 2)), this);
			}
			if (EAST.equals(alignment)) {
				g.drawImage(myImage, getWidth() - myImage.getWidth(this), ((getHeight() / 2) - (myImage.getHeight(this) / 2)), this);
			}
			if (SOUTHWEST.equals(alignment)) {
				g.drawImage(myImage, 0, getHeight() - myImage.getHeight(this), this);
			}
			if (SOUTH.equals(alignment)) {
				g.drawImage(myImage, ((getWidth() / 2) - (myImage.getWidth(this) / 2)), getHeight() - myImage.getHeight(this), this);
			}
			if (SOUTHEAST.equals(alignment)) {
				g.drawImage(myImage, getWidth() - myImage.getWidth(this), getHeight() - myImage.getHeight(this), this);
			}
		}
		Color old = g.getColor();

		g.setColor(old);
	}

	public void setAlignment(String al) {
		String old = this.alignment;
		alignment = al;
		repaint();
		firePropertyChange(ALIGNMENT, old, al);
	}

	public TipiGradientPaint getPaint() {
		return paint;
	}

	public void setPaint(TipiGradientPaint paint) {
		TipiGradientPaint old = this.paint;
		this.paint = paint;
		firePropertyChange(PAINT, old, paint);
		repaint();
	}

}
