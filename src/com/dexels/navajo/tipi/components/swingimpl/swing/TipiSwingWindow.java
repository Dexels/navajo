package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.swing.*;

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
public class TipiSwingWindow extends JInternalFrame {

	private Point position = null;
	private Dimension oldSize = null;

	public TipiSwingWindow() {
		// setBackground(new Color(0.0f,0.8f,0.0f,0.2f));
		addComponentListener(new ComponentListener() {

			public void componentHidden(ComponentEvent c) {
			}

			public void componentMoved(ComponentEvent c) {
				int oldX = position != null ? position.x : -1;
				int x = getLocation().x;
				if (oldX != x) {
					firePropertyChange("x", oldX, x);
				}
				int oldY = position != null ? position.y : -1;
				int y = getLocation().y;
				if (oldY != y) {
					firePropertyChange("y", oldY, y);
				}
				position = getLocation();
			}

			public void componentResized(ComponentEvent c) {
				int oldWidth = oldSize != null ? oldSize.width : -1;
				int w = getSize().width;
				if (oldWidth != w) {
					firePropertyChange("w", oldWidth, w);
				}
				int oldHeight = oldSize != null ? oldSize.height : -1;
				int h = getSize().height;
				if (oldHeight != h) {
					firePropertyChange("h", oldHeight, h);
				}
				oldSize = getSize();

			}

			public void componentShown(ComponentEvent c) {
			}
		});

	}

	public void setTitle(String title) {
		String old = getTitle();
		super.setTitle(title);
		firePropertyChange("title", old, title);
	}

	public void setIconUrl(URL u) {
		setFrameIcon(new ImageIcon(u));
	}

	public void setX(int x) {
		Rectangle r = getBounds();
		int oldX = r.x;
		r.x = x;
		setBounds(r);
		firePropertyChange("x", oldX, x);
	}

	public void setY(int y) {
		Rectangle r = getBounds();
		int oldY = r.y;
		r.y = y;
		setBounds(r);
		firePropertyChange("y", oldY, y);
	}

	public void setH(int h) {
		Rectangle r = getBounds();
		int oldH = r.height;
		r.height = h;
		setBounds(r);
		firePropertyChange("h", oldH, h);
	}

	public void setW(int w) {
		Rectangle r = getBounds();
		int oldW = r.width;
		r.width = w;
		setBounds(r);
		firePropertyChange("w", oldW, w);
	}

}
