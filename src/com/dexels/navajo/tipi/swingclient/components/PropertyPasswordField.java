package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.dexels.navajo.document.*;

/**
 * <p>
 * Title:
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

public class PropertyPasswordField extends JPasswordField implements PropertyControlled {

	public String textValue;
	public Property initProperty = null;
	// public ResourceBundle localResource;
	public String toolTipText = "";

	// ConditionErrorParser cep = new ConditionErrorParser();

	public PropertyPasswordField() {

		this.setMinimumSize(new Dimension(4, ComponentConstants.PREFERRED_HEIGHT));
		this.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				selectAll();
			}

		});
	
		this.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				this_focusLost(e);
			}

			@Override
			public void focusGained(FocusEvent e) {
				this_focusGained(e);
			}
		});

	}

	public Property getProperty() {
		return initProperty;
	}

	public void setProperty(Property p) {
		if (p == null) {
			// System.err.println("Setting to null property. Ignoring");
			return;
		}

		initProperty = p;
		textValue = (String) p.getTypedValue();

		// Trim the value
		if (textValue != null) {
			textValue = textValue.trim();
		}

		setText(textValue);
		setEnabled(p.isDirIn());
		setEditable(p.isDirIn());
		if ((toolTipText = p.getDescription()) != null) {
			setToolTipText(toolTipText);
		} else {
			toolTipText = p.getName();
			setToolTipText(toolTipText);
		}
	}


	public void this_focusLost(FocusEvent e) {
		textValue = new String(getPassword());
		if (initProperty != null) {
			initProperty.setValue(textValue);
		}

	}

	public void this_focusGained(FocusEvent e) {
		if (isEditable()) {
		}
		Component c = getParent();
		if (BasePanel.class.isInstance(c)) {
			BasePanel parentPanel = (BasePanel) c;
			parentPanel.setFocus();
		}
	}

	public void update() {
		if (initProperty == null) {
			return;
		}
		textValue = new String(getPassword());
		if (textValue != null) {
			initProperty.setValue(textValue);
		}
	}

	public void setValidationMessageName(String name) {
		// initProperty.setMessageName(name); // What is this, remove..
	}

}
