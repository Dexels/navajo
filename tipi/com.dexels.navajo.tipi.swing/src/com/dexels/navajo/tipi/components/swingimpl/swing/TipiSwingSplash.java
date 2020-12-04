/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TipiSwingSplash extends JWindow implements Runnable {

	private static final long serialVersionUID = -6303305182241612381L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingSplash.class);
	
	JLabel imageLabel = new JLabel();
	Thread t;
	ImageIcon img;
	JProgressBar jProgressBar1 = new JProgressBar();
	private String myIconName;

	public TipiSwingSplash(String iconName) {
		myIconName = iconName;
		try {
			jbInit();
			t = new Thread(this);
			jProgressBar1.setForeground(Color.decode("#213075"));
			jProgressBar1.setBorderPainted(true);
			jProgressBar1.setIndeterminate(true);
			jProgressBar1.setStringPainted(true);
			jProgressBar1.setString("");
			this.t.start();
			setCentered();
		} catch (Exception e) {
			logger.error("Error detected",e);
		}
	}

	@Override
	public void run() {
		
	}

	public void setInfoText(String info) {
		jProgressBar1.setString(info);
	}

	private final void setCentered() {
		pack();
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		if (img != null) {
			int x = (int) ((d.getWidth() / 2) - (img.getIconWidth() / 2));
			int y = (int) ((d.getHeight() / 2) - (img.getIconHeight() / 2));
			this.setLocation(x, y);
		}
	}

	private final void jbInit() throws Exception {
		this.addWindowStateListener(new DefaultTipiSplash_this_windowStateAdapter(
				this));
		img = new ImageIcon(getClass().getClassLoader().getResource(myIconName));
		imageLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		jProgressBar1.setBorder(BorderFactory.createLineBorder(Color.black));
		imageLabel.setDebugGraphicsOptions(0);
		imageLabel
				.addComponentListener(new DefaultTipiSplash_imageLabel_componentAdapter(
						this));
		if (img != null) {
			imageLabel.setIcon(img);
			this.setSize(img.getIconWidth(), img.getIconHeight()
					+ jProgressBar1.getHeight());
		}
		this.getContentPane().add(imageLabel, BorderLayout.CENTER);
		this.getContentPane().add(jProgressBar1, BorderLayout.SOUTH);
	}

	void this_windowStateChanged(WindowEvent e) {
		System.exit(0);
	}

	void imageLabel_componentHidden(ComponentEvent e) {
		t = null;
		System.exit(0);
	}
}

class DefaultTipiSplash_this_windowStateAdapter implements
		java.awt.event.WindowStateListener {
	TipiSwingSplash adaptee;

	DefaultTipiSplash_this_windowStateAdapter(TipiSwingSplash adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void windowStateChanged(WindowEvent e) {
		adaptee.this_windowStateChanged(e);
	}
}

class DefaultTipiSplash_imageLabel_componentAdapter extends
		java.awt.event.ComponentAdapter {
	TipiSwingSplash adaptee;

	DefaultTipiSplash_imageLabel_componentAdapter(TipiSwingSplash adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		adaptee.imageLabel_componentHidden(e);
	}
}
