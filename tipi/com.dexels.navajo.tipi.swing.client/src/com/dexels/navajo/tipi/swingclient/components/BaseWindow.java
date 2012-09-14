package com.dexels.navajo.tipi.swingclient.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.swingclient.SwingClient;
import com.dexels.navajo.tipi.swingclient.UserInterface;

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

public class BaseWindow extends JInternalFrame {

	private static final long serialVersionUID = 1671424109176553065L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseWindow.class);
	
	protected JPanel mainPanel = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	private Component myOldGlassPane = null;
	ImageIcon myIcon = new ImageIcon(
			UserInterface.class.getResource("images/logo_mini.gif"));

	public BaseWindow() {
		super();
		try {
			jbInit();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	public void commit() {
	}

	public void setCursor(int type) {
		this.getContentPane().setCursor(Cursor.getPredefinedCursor(type));
	}

	public void closeWindow() throws PropertyVetoException {
		setMaximum(false);
		setClosed(true);

		SwingClient.getUserInterface().closeWindow(this);
		setVisible(false);
	}

	public Component getOldGlassPane() {
		return myOldGlassPane;
	}

	public void mouseGestureReckognized(String id) {
		logger.info("BaseWindow got: " + id);
	}

	public void windowHasClosed() {
	}

	private final void jbInit() throws Exception {
		mainPanel.setLayout(borderLayout1);
		// mainPanel.setBackground(SystemColor.controlLtHighlight);

		getContentPane().add(mainPanel);
		this.setClosable(true);
		this.setIconifiable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setFrameIcon(myIcon);

		this.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {
				deactivateFrame();
			}
		});

	}

	void this_internalFrameClosed(InternalFrameEvent e) {
		try {
			closeWindow();
		} catch (PropertyVetoException ex) {
		}
	}

	public void deactivateFrame() {
	}
}
