/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.swingclient.SwingClient;

@Deprecated
public class StandardDialog extends JDialog implements DialogConstants {

	private static final long serialVersionUID = 4560643968422875526L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(StandardDialog.class);
	private boolean doWindowClose = true;

	private boolean isCommitted = false;
	BorderLayout borderLayout1 = new BorderLayout();
	public IconButtonPanel iconButtonPanel = new IconButtonPanel();
	JToolBar dialogToolbar = new JToolBar();
	protected BasePanel mainPanel = new BasePanel();

	// BaseGlassPane myGlassPane = new BaseGlassPane();

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

	void this_windowClosed(WindowEvent e) {
	}

	public void showDialog() {
		setCommitted(false);
		SwingClient.getUserInterface().addDialog(this);
	}

	public StandardDialog() {
		init();
	}

	public StandardDialog(JFrame f) {
		super(f);
		baseinit();
		init();
	}

	public StandardDialog(JDialog f) {
		super(f);
		baseinit();
		init();
	}

	private void init() {
		dialogToolbar.setFloatable(false);
		try {
			jbInit();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	private void baseinit() {
		getContentPane().add(mainPanel);
		getContentPane().add(dialogToolbar, BorderLayout.SOUTH);
		getContentPane().addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				logger.info("Pressed dialog");
			}

			@Override
			public void keyReleased(KeyEvent e) {
				logger.info("Released dialog");
			}

			@Override
			public void keyTyped(KeyEvent e) {
				logger.info("Typed dialog");
			}
		});
		mainPanel.setLayout(borderLayout1);
		this.setResizable(true);
		logger.info("IN BaseDialog jbInit()..............");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				this_windowClosed(e);
			}
		});
	}

	public void commit() {
		if (BasePanel.class.isInstance(mainPanel)) {
			BasePanel myBase = mainPanel;
			// Reset condition errors.

			myBase.commit();
			if (myBase.hasExceptions()) {
				setDoWindowClose(false);
				setCommitted(false);
			}
		} else {
			System.err
					.println("----> StandarDialogs mainPanel is not of class BasePanel, but: "
							+ mainPanel.getClass());
		}
	}

	public void discard() {
	}

	public void addMainPanel(BasePanel p) {
		this.getContentPane().add(p, BorderLayout.CENTER);
		mainPanel = p;
	}

	public boolean doWindowClose() {
		return doWindowClose;
	}

	public void setDoWindowClose(boolean state) {
		doWindowClose = state;
	}

	public void setMode(int mode) {
		iconButtonPanel.setAllVisible(false);
		switch (mode) {
		case MODE_OK_CANCEL:
			iconButtonPanel.setButtonVisible(IconButtonPanel.OK_BUTTON, true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.CANCEL_BUTTON,
					true);
			break;
		case MODE_CONFIRM:
		case MODE_CLOSE:
			iconButtonPanel.setButtonVisible(IconButtonPanel.OK_BUTTON, true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.CANCEL_BUTTON,
					false);
			break;
		case MODE_OK_CANCEL_APPLY:
			iconButtonPanel.setButtonVisible(IconButtonPanel.SAVE_BUTTON, true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.OK_BUTTON, true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.CANCEL_BUTTON,
					true);
			break;
		case MODE_OK_CANCEL_APPLY_INSERT:
			iconButtonPanel.setButtonVisible(IconButtonPanel.SAVE_BUTTON, true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.OK_BUTTON, true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.CANCEL_BUTTON,
					true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.INSERT_BUTTON,
					true);
			break;
		case MODE_OK_CANCEL_INSERT_DELETE:
			iconButtonPanel.setButtonVisible(IconButtonPanel.DELETE_BUTTON,
					true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.OK_BUTTON, true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.CANCEL_BUTTON,
					true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.INSERT_BUTTON,
					true);
			break;
		case MODE_OK_CANCEL_APPLY_INSERT_DELETE:
			iconButtonPanel.setButtonVisible(IconButtonPanel.SAVE_BUTTON, true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.DELETE_BUTTON,
					true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.OK_BUTTON, true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.CANCEL_BUTTON,
					true);
			iconButtonPanel.setButtonVisible(IconButtonPanel.INSERT_BUTTON,
					true);
			break;
		}
		//
	}

	public boolean isCommitted() {
		return isCommitted;
	}

	public void setCommitted(boolean state) {
		isCommitted = state;
	}

	private final void jbInit() throws Exception {

		InputMap im = getLayeredPane().getInputMap(
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getLayeredPane().getActionMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK),
				"Save");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK),
				"Insert");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK),
				"SaveExit");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK),
				"Exit");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Exit");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK),
				"Fetch");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "Clear");
		am.put("Save", new KeyEventHandler(this, "Save"));
		am.put("Insert", new KeyEventHandler(this, "Insert"));
		am.put("SaveExit", new KeyEventHandler(this, "SaveExit"));
		am.put("Clear", new KeyEventHandler(this, "Clear"));
		am.put("Exit", new KeyEventHandler(this, "Exit"));
		am.put("Delete", new KeyEventHandler(this, "Delete"));

		dialogToolbar.setFloatable(false);
		this.getContentPane().setLayout(borderLayout1);
		setModal(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				this_windowClosing(e);
			}
		});
		iconButtonPanel.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iconButtonPanel_actionPerformed(e);
			}
		});
		dialogToolbar.add(iconButtonPanel, null);
		this.getContentPane().add(dialogToolbar, BorderLayout.SOUTH);
	}

	void this_windowClosing(WindowEvent e) {
		if (!isCommitted()) {
			discard();
		}
		// setVisible(false);
		closeWindow();
	}

	public void delete() {
	}

	public void newItem() {
	}

	public void insert() {
	}

	// public void closeWindow(){
	// super.closeWindow();
	// }

	void iconButtonPanel_actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(IconButtonPanel.OK_BUTTON)) {
			// logger.info("OK");
			if (!isCommitted()) {
				isCommitted = true;
				commit();
			}
			if (doWindowClose) {
				closeWindow();
			}
			setDoWindowClose(true);
			return;
		}
		if (e.getActionCommand().equals(IconButtonPanel.CANCEL_BUTTON)) {
			discard();
			// if(doWindowClose){
			closeWindow();
			// }

			return;
		}
		if (e.getActionCommand().equals(IconButtonPanel.DELETE_BUTTON)) {
			delete();
			return;
		}
		if (e.getActionCommand().equals(IconButtonPanel.NEW_BUTTON)) {
			newItem();
			return;
		}
		if (e.getActionCommand().equals(IconButtonPanel.INSERT_BUTTON)) {
			insert();
			return;
		}
		if (e.getActionCommand().equals(IconButtonPanel.SAVE_BUTTON)) {
			// logger.info("SAVE");
			isCommitted = false;
			commit();
			return;
		}

	}

	@Override
	public void setVisible(boolean parm1) {
		super.setVisible(parm1);
		iconButtonPanel.requestFocus();
	}

	public void handleException(Exception e) {
		throw new UnsupportedOperationException(
				"MenuAction does not implement handleException()");
	}

	public void receive(Navajo n, String method, String id) {
		throw new UnsupportedOperationException(
				"MenuAction does not implement receive()");
	}

	public void setWaiting(boolean b) {
		throw new UnsupportedOperationException(
				"MenuAction does not implement setWaiting()");
	}

	public void swingSafeReceive(final Navajo n, final String method,
			final String id) {
		if (SwingUtilities.isEventDispatchThread()) {
			receive(n, method, id);
		} else {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {

					@Override
					public void run() {
						receive(n, method, id);
					}
				});
			} catch (InterruptedException e) {
				logger.error("Error: ",e);
			} catch (InvocationTargetException e) {
				logger.error("Error: ",e);
			}
		}
	}
}
