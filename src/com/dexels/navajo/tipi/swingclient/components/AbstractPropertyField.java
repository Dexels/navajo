package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.sun.corba.se.spi.legacy.connection.*;

public abstract class AbstractPropertyField extends JTextField implements FocusListener, PropertyChangeListener {

	protected Property myProperty = null;

	protected abstract String getEditingFormat(Object o);

	protected abstract Object parseProperty(String text);

	public AbstractPropertyField() {
		addFocusListener(this);
	}

	public Property getProperty() {
		return myProperty;
	}

	public void setEnabled(boolean b) {
		System.err.println("Ignoring set enabled!");
	}

	public void setProperty(Property p) {
		if(p!=myProperty) {
			if (myProperty != null) {
				myProperty.removePropertyChangeListener(this);
			}
			myProperty = p;
			myProperty.addPropertyChangeListener(this);
		}

		AbstractPropertyField.super.setEditable(myProperty.isDirIn());
		AbstractPropertyField.super.setEnabled(myProperty.isDirIn());
		String s = getPresentationFormat(myProperty.getTypedValue());
		setText(s);
	}

	public void focusGained(FocusEvent e) {
		editProperty();
	}

	public void editProperty() {
		if (myProperty != null) {
			try {
				Runnable runAction = new Runnable() {
					public void run() {
						String editingFormat = getEditingFormat(myProperty.getTypedValue());
						setText(editingFormat);
						selectAll();
					}
				};
				if(SwingUtilities.isEventDispatchThread()) {
					runAction.run();
				} else {
					SwingUtilities.invokeAndWait(runAction);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public void focusLost(FocusEvent e) {
		if (myProperty != null) {
			updateProperty();
		}
	}

	protected void updateProperty() {
		Object value = parseProperty(getText());
		myProperty.setAnyValue(value);
		presentObject(value);
		System.err.println("Value: " + myProperty.getValue());
	}

	public void propertyChange(PropertyChangeEvent e) {
		Property p = (Property) e.getSource();
		if (p != myProperty) {
			System.err.println("Mysterious anomaly!");
		}
		presentObject(e.getNewValue());
	}

	protected void presentObject(Object o) {
		final String s = getPresentationFormat(o);
		try {
			Runnable runAction = new Runnable() {
				public void run() {
					setText(s);
				}
			};
			if(SwingUtilities.isEventDispatchThread()) {
				runAction.run();
			} else {
				SwingUtilities.invokeAndWait(runAction);
			}			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	protected String getPresentationFormat(Object newValue) {
		if (newValue == null) {
			return "";
		}
		return newValue.toString();
	}

}
