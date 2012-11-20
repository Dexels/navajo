package com.dexels.navajo.tipi.swingclient.components;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @deprecated
 */

public class IconButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(IconButtonPanel.class);
	
	// ResourceBundle res;
	JButton newButton = new JButton();
	JButton saveButton = new JButton();
	JButton deleteButton = new JButton();
	JButton insertButton = new JButton();
	JButton cancelButton = new JButton();
	FlowLayout flowLayout1 = new FlowLayout();
	private List<ActionListener> listeners = new ArrayList<ActionListener>();
	public final static String NEW_BUTTON = "NEW";
	public final static String SAVE_BUTTON = "SAVE";
	public final static String DELETE_BUTTON = "DELETE";
	public final static String INSERT_BUTTON = "INSERT";
	public final static String CANCEL_BUTTON = "CANCEL";
	public final static String OK_BUTTON = "OK";
	JButton okButton = new JButton();

	public IconButtonPanel() {
		try {
			jbInit();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
		setAllVisible(false);
	}

	private final void jbInit() throws Exception {
		newButton.setActionCommand(NEW_BUTTON);
		newButton.setIcon(new ImageIcon(UserInterface.class
				.getResource("add-v2.png")));
		newButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newButton_actionPerformed(e);
			}
		});
		saveButton.setActionCommand(SAVE_BUTTON);
		saveButton.setIcon(new ImageIcon(UserInterface.class
				.getResource("save-v2.png")));
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveButton_actionPerformed(e);
			}
		});
		deleteButton.setActionCommand(DELETE_BUTTON);
		deleteButton.setIcon(new ImageIcon(UserInterface.class
				.getResource("delete-v2.png")));
		deleteButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteButton_actionPerformed(e);
			}
		});
		insertButton.setActionCommand(INSERT_BUTTON);
		insertButton.setIcon(new ImageIcon(UserInterface.class
				.getResource("add-v2.png")));
		insertButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertButton_actionPerformed(e);
			}
		});
		cancelButton.setActionCommand(CANCEL_BUTTON);
		cancelButton.setIcon(new ImageIcon(UserInterface.class
				.getResource("publish_x.png")));
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelButton_actionPerformed(e);
			}
		});
		okButton.setIcon(new ImageIcon(UserInterface.class
				.getResource("tick.png")));
		okButton.setActionCommand(OK_BUTTON);
		okButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				okButton_actionPerformed(e);
			}
		});
		this.setLayout(flowLayout1);
		flowLayout1.setAlignment(FlowLayout.LEFT);

		this.add(insertButton, null);
		this.add(deleteButton, null);
		this.add(saveButton, null);
		this.add(newButton, null);
		this.add(okButton, null);
		this.add(cancelButton, null);
	}

	public void setButtonAlignment(int buttonAlignment) {
		flowLayout1.setAlignment(buttonAlignment);
	}

	public void setButtonVisible(String button, boolean state) {
		if (button.equals(NEW_BUTTON)) {
			newButton.setVisible(state);
		}
		if (button.equals(SAVE_BUTTON)) {
			saveButton.setVisible(state);
		}
		if (button.equals(INSERT_BUTTON)) {
			insertButton.setVisible(state);
		}
		if (button.equals(DELETE_BUTTON)) {
			deleteButton.setVisible(state);
		}
		if (button.equals(CANCEL_BUTTON)) {
			cancelButton.setVisible(state);
		}
		if (button.equals(OK_BUTTON)) {
			okButton.setVisible(state);
		}
	}

	public JButton getButton(String button) {
		if (button.equals(NEW_BUTTON)) {
			return newButton;
		}
		if (button.equals(SAVE_BUTTON)) {
			return saveButton;
		}
		if (button.equals(INSERT_BUTTON)) {
			return insertButton;
		}
		if (button.equals(DELETE_BUTTON)) {
			return deleteButton;
		}
		if (button.equals(CANCEL_BUTTON)) {
			return cancelButton;
		}
		if (button.equals(OK_BUTTON)) {
			return okButton;
		}
		return null;
	}

	public void setAllVisible(boolean b) {
		newButton.setVisible(b);
		saveButton.setVisible(b);
		insertButton.setVisible(b);
		deleteButton.setVisible(b);
		cancelButton.setVisible(b);
		okButton.setVisible(b);
	}

	public void addActionListener(java.awt.event.ActionListener al) {
		listeners.add(al);
	}

	public void buttonPressed(ActionEvent e) {
		for (int i = 0; i < listeners.size(); i++) {
			java.awt.event.ActionListener current = listeners.get(i);
			current.actionPerformed(e);
		}
	}

	void newButton_actionPerformed(ActionEvent e) {
		buttonPressed(e);
	}

	void saveButton_actionPerformed(ActionEvent e) {
		buttonPressed(e);
	}

	void deleteButton_actionPerformed(ActionEvent e) {
		buttonPressed(e);
	}

	void insertButton_actionPerformed(ActionEvent e) {
		buttonPressed(e);
	}

	void cancelButton_actionPerformed(ActionEvent e) {
		buttonPressed(e);
	}

	void okButton_actionPerformed(ActionEvent e) {
		buttonPressed(e);
	}

	@Override
	public void requestFocus() {
		okButton.requestFocus();
	}
}
