package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;

import com.dexels.navajo.document.Property;

//import com.dexels.sportlink.client.swing.components.*;

public final class PropertyCheckBox extends JCheckBox implements
		PropertyControlled {
	private static final long serialVersionUID = 7272685312953373721L;
	private Property myProperty = null;

	public PropertyCheckBox() {
		this.setOpaque(false);
		this.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myProperty != null) {
					myProperty.setValue(isSelected());
				}
			}
		});
	}

	public final Property getProperty() {
		return myProperty;
	}

	public final void update() {
		if (myProperty != null) {
			myProperty.setAnyValue(isSelected());
		}
	}

	public final void setProperty(Property p) {
		myProperty = p;
		if (p == null) {
			return;
		}
		if (myProperty.getValue() != null) {
			setEnabled(p.isDirIn());
			setSelected(myProperty.getValue().equals("true"));
		} else {
			setSelected(false);
			// setEnabled(false);
		}
		setSelected(myProperty.getValue() != null
				&& myProperty.getValue().equals("true"));
	}

}
