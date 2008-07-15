package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*; //import com.dexels.sportlink.client.swing.*;
//import com.dexels.sportlink.client.swing.components.*;
import com.dexels.navajo.document.*; //import com.dexels.navajo.nanoclient.NavajoLoadable;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.mousegestures.*;

/**
 * <p>
 * Title: SportLink Client:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class BaseDialog extends JDialog implements MouseGestureListener {
	protected JPanel mainPanel = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	MouseGestureParser mgp;

	// BaseGlassPane myGlassPane = new BaseGlassPane();

	public BaseDialog() {
		super(SwingClient.getUserInterface().getMainFrame());
		jBinit();

	}

	public BaseDialog(JFrame f) {
		super(f);
		jBinit();
	}

	public BaseDialog(JDialog f) {
		super(f);
		jBinit();
	}

	private void jBinit() {
		getContentPane().add(mainPanel);
		getContentPane().addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				System.err.println("Pressed dialog");
			}

			public void keyReleased(KeyEvent e) {
				System.err.println("Released dialog");
			}

			public void keyTyped(KeyEvent e) {
				System.err.println("Typed dialog");
			}
		});
		mainPanel.setLayout(borderLayout1);
		this.setResizable(true);
		System.err.println("IN BaseDialog jbInit()..............");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				this_windowClosed(e);
			}
		});
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void closeWindow() {
		setVisible(false);
		// Dispose() is neccessary for V2 client!
		dispose();
	}

	public void init(Message msg) {
	}

	public void load(Message msg) {
	}

	public void store(Message msg) {
	}

	public void insert(Message msg) {
	}

	public BaseGlassPane createGlassPane() {
		// BaseGlassPane bg = new BaseGlassPane();
		SportlinkBusyPanel bg = new SportlinkBusyPanel();
		// mgp = new MouseGestureParser(bg);
		// mgp.addMouseGestureListener(this);
		getRootPane().setGlassPane(bg);
		return bg;
	}

	public void mouseGestureReckognized(String id) {
		System.err.println("BaseDialog got: " + id);
	}

	public BaseGlassPane getBaseGlassPane() {
		if (getRootPane().getGlassPane() == null) {
			return createGlassPane();
		}
		if (!BaseGlassPane.class.isInstance(getRootPane().getGlassPane())) {
			BaseGlassPane bg = createGlassPane();
			getRootPane().setGlassPane(bg);
			return bg;
		}
		return (BaseGlassPane) getRootPane().getGlassPane();
	}

	void this_windowClosed(WindowEvent e) {
		System.err.println("in this_windowClosed()");
	}

	public void showDialog() {
		SwingClient.getUserInterface().addDialog(this);
	}

}
