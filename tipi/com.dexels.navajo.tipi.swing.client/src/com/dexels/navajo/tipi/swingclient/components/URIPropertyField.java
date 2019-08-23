package com.dexels.navajo.tipi.swingclient.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;

/**
 * <p>
 * Title: Seperate project for Navajo Swing client
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class URIPropertyField extends TextPropertyField {
	private static final long serialVersionUID = -4554241617340878668L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(URIPropertyField.class);
	
	private boolean clickable = true;
	// private static final String WIN_ID = "Windows";
	private static final String WIN_PATH = "rundll32";
	private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
	private static final String UNIX_PATH = "mozilla";
	private static final String UNIX_FLAG = "";

	public URIPropertyField() {
		super();

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1 && clickable) {
					String uri = createURI();
					logger.info("Click!! -> URI: " + uri);
					loadURI(uri);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (isURI()) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isURI()) {
			this.setForeground(Color.blue);
			setClickable(true);
			if (getText() != null && !"".equals(getText())) {
				FontMetrics fm = g.getFontMetrics();
				int h = fm.getMaxAscent() + fm.getDescent() + 3;
				g.drawLine(getInsets().left, h, java.lang.Math.min(
						fm.stringWidth(getText()), getBounds().width
								- getInsets().right), h);
			}
		} else {
			this.setForeground(Color.black);
			setClickable(false);
		}
	}

	private boolean isURI() {
		String text = getText();
		if (text.startsWith("http://") || text.startsWith("https://") || text.startsWith("www.")
				|| text.indexOf("@") >= 0) {
			return true;
		}
		return false;
	}

	public void setClickable(boolean isClickable) {
		clickable = isClickable;
	}

	@Override
	public void setProperty(Property p) {
		super.setProperty(p);
		if (isURI()) {
			setToolTipText(p.getDescription() + " (Dubbelklik om te openen)");
		}
	}

	private final String createURI() {

		if (getProperty() != null) {
			String url = getProperty().getValue();
			if (url != null && !"".equals(url)) {
				if (url.indexOf("@") > 0) {
					if (!url.startsWith("mailto:")) {
						url = "mailto:" + url;
						return url;
					}
				} else if (!url.startsWith("http://") && !url.startsWith("https://")) {
					url = "http://" + url;
					return url;
				} else {
					return url;
				}
			} else {
				return url;
			}
		}
		return null;
	}

	private final void loadURI(String url) {
		if (url != null) {
			// boolean result = true;
			boolean windows = false;
			String os = System.getProperty("os.name").toLowerCase();
			if (os.indexOf("windows") >= 0) {
				windows = true;
			}
			String cmd = null;
			try {
				if (windows) {
					cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
					logger.info("Executing Windows command: " + cmd);
					Runtime.getRuntime().exec(cmd);
				} else {
					cmd = UNIX_PATH + " " + UNIX_FLAG + "'" + url + "'";
					logger.info("Executing UNIX command: " + cmd);
					Runtime.getRuntime().exec(cmd);
				}
			} catch (java.io.IOException ex) {
				// result = false;
				logger.info("Could not invoke browser, command=" + cmd);
				logger.info("Caught: " + ex);
			}
		}
	}


}
