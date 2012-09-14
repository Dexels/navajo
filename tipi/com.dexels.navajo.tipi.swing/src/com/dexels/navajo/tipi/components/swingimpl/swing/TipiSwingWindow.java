package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;


public class TipiSwingWindow extends JInternalFrame {

	private static final long serialVersionUID = -1660981988162626561L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingWindow.class);
	private Point position = null;
	private Dimension oldSize = null;

	public TipiSwingWindow() {
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

	public void setIconUrl(URL u) {
		setFrameIcon(new ImageIcon(u));
	}

	public void setIconUrl(Object u) {
		setFrameIcon(getIcon(u));
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

}
