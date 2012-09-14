package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipiSwingOffsetPanel extends JPanel {
	private static final long serialVersionUID = 6578024799595962293L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingOffsetPanel.class);
	
	private int x;
	private int y;
	private JPanel myClient = new JPanel();

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		int old = this.x;
		this.x = x;
		layoutOffsetPanel();
		firePropertyChange("x", old, x);
	}

	public void setY(int y) {
		int old = this.y;
		this.y = y;
		layoutOffsetPanel();
		firePropertyChange("y", old, y);
	}

	public JPanel getClient() {
		return myClient;
	}

	public TipiSwingOffsetPanel() {
		// setLayout(null);
		add(myClient);
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				logger.debug("Bounds: " + getBounds());
				logger.debug("clBounds: " + myClient.getBounds());
				layoutOffsetPanel();
			}

			public void componentShown(ComponentEvent e) {
				layoutOffsetPanel();
			}
		});
	}

	// public void doLayout() {
	// super.doLayout();
	// layoutOffsetPanel();
	// }

	private void layoutOffsetPanel() {
		myClient.doLayout();
		myClient.setBounds(new Rectangle(getLocation().x + x, getLocation().y
				+ y, myClient.getPreferredSize().width, myClient
				.getPreferredSize().height));
		// myClient.setLocation(x, y);
	}
}
