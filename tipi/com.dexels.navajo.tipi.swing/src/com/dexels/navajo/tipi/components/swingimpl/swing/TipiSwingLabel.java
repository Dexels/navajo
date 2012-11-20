package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndCapable;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndManager;

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
public class TipiSwingLabel extends JLabel implements TipiDndCapable {
	private static final long serialVersionUID = -6493113702683929580L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingLabel.class);
	
	final TipiDndManager myDndManager;

	public TipiSwingLabel(TipiComponent tc) {
		myDndManager = new TipiDndManager(this, tc);
	}

	public Dimension getMinumumSize() {
		return getPreferredSize();
	}

	public void setIconUrl(Object u) {
		setIcon(getIcon(u));
	}

	protected ImageIcon getIcon(Object u) {
		if (u == null) {
			return null;
		}
		if (u instanceof URL) {
			return new ImageIcon((URL) u);
		}
		if (u instanceof Binary) {
			Image i;
			try {
				i = ImageIO.read(((Binary) u).getDataAsStream());
				ImageIcon ii = new ImageIcon(i);
				return ii;
			} catch (IOException e) {
				logger.error("Error detected",e);
			}
		}
		return null;
	}

	private boolean isVertical = false;

	public boolean isVertical() {
		return isVertical;
	}

	public void setVertical(boolean isVertical) {
		this.isVertical = isVertical;
		if (isVertical) {
			createVerticalImage(super.getText(), true);
		}
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		if (isVertical) {
			createVerticalImage(super.getText(), true);
		}
	}

	public TipiDndManager getDndManager() {
		return myDndManager;
	}

	public void setTipiHorizontalAlignment(String horizontal) {
		logger.debug("Setting horizontal: " + horizontal);
		if (horizontal.equals("left")) {
			setHorizontalAlignment(SwingConstants.LEFT);
			return;
		}
		if (horizontal.equals("right")) {
			setHorizontalAlignment(SwingConstants.RIGHT);
			return;
		}
		if (horizontal.equals("leading")) {
			setHorizontalAlignment(SwingConstants.LEADING);
			return;
		}
		if (horizontal.equals("center")) {
			setHorizontalAlignment(SwingConstants.CENTER);
			return;
		}
		if (horizontal.equals("trailing")) {
			setHorizontalAlignment(SwingConstants.TRAILING);
			return;
		}
		throw new IllegalArgumentException(
				"Error setting horizontal alignment. Value: " + horizontal
						+ " is not valid");

	}

	public void createVerticalImage(String caption, boolean clockwise) {
		logger.debug("Creating vertical image");
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int captionHeight = fm.getHeight();
		int captionWidth = fm.stringWidth(caption);
		BufferedImage bi = new BufferedImage(captionHeight + 4,
				captionWidth + 4, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();

		g.setColor(new Color(0, 0, 0, 0)); // transparent
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());

		g.setColor(getForeground());
		g.setFont(f);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (clockwise) {
			g.rotate(Math.PI / 2);
		} else {
			g.rotate(-Math.PI / 2);
			g.translate(-bi.getHeight(), bi.getWidth());
		}
		g.drawString(caption, 2, -6);

		Icon icon = new ImageIcon(bi);
		setIcon(icon);
		// super.setText("");
	}

	public String getText() {
		if (isVertical) {
			return "";
		} else {
			return super.getText();
		}
	}

	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (isVertical) {
			createVerticalImage(super.getText(), true);
		}
	}

	public void setTipiVerticalAlignment(String vertical) {
		logger.debug("Setting vertical: " + vertical);
		if (vertical.equals("top")) {
			setVerticalAlignment(SwingConstants.TOP);
			return;
		}
		if (vertical.equals("center")) {
			setVerticalAlignment(SwingConstants.CENTER);
			return;
		}
		if (vertical.equals("bottom")) {
			setVerticalAlignment(SwingConstants.BOTTOM);
			return;
		}
		throw new IllegalArgumentException(
				"Error setting vertical alignment. Value: " + vertical
						+ " is not valid");
	}

	public String getTipiVerticalAlignment() {
		int v = getVerticalAlignment();
		switch (v) {
		case SwingConstants.TOP:
			return "top";

		case SwingConstants.BOTTOM:
			return "bottom";

		}
		return "center";
	}

	public String getTipiHorizontalAlignment() {
		int v = getHorizontalAlignment();
		switch (v) {
		case SwingConstants.LEFT:
			return "left";
		case SwingConstants.CENTER:
			return "center";
		case SwingConstants.RIGHT:
			return "right";
		case SwingConstants.TRAILING:
			return "trailing";
		}
		return "leading";
	}
}
