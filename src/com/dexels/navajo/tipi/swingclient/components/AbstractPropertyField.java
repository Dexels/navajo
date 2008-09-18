package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.*;

import javax.swing.*;

import com.dexels.navajo.document.*;

public abstract class AbstractPropertyField extends JTextField implements FocusListener, PropertyChangeListener {

	protected Property myProperty = null;

	protected abstract String getEditingFormat(Object o);

	protected abstract Object parseProperty(String text);

	private boolean isEditing = false;
	
	public AbstractPropertyField() {
		addFocusListener(this);
		setOpaque(true);
	}

	public Property getProperty() {
		return myProperty;
	}

	public boolean isEditing() {
		return isEditing;
	}
	
	@Override
	public boolean isOpaque() {
		return true;
	}
	public void setEditing(boolean b) {
		isEditing = b;
	}
	
	@Override
	public void setEnabled(boolean b) {
	}

	public void setProperty(Property p) {
		if(p!=myProperty) {
			if (myProperty != null) {
				myProperty.removePropertyChangeListener(this);
			}
			setPropertyValue(p);
			if(myProperty!=null) {
				myProperty.addPropertyChangeListener(this);
			}
		} else {
			setPropertyValue(p);
		}
	
	}
/**
 * Similar to setProperty, only will not listen for property changes
 * @param p
 */
	public void setPropertyValue(Property p) {
		myProperty = p;

		if(myProperty==null) {
			setText("");
			return;
		}
		AbstractPropertyField.super.setEditable(myProperty.isDirIn());
		AbstractPropertyField.super.setEnabled(myProperty.isDirIn());
		String s = getPresentationFormat(myProperty.getTypedValue());
		setText(s);
		setEditing(false);
		if(hasFocus()) {
			editProperty();
		}
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
						setEditing(true);
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
		} else {
			setEditing(true);
		}
	}

	public void focusLost(FocusEvent e) {
	
		if (myProperty != null) {
			updateProperty();
		}
		setEditing(false);

	}

	protected void updateProperty() {
		
		if(!isEditing()) {
			return;
		}
		if(myProperty==null) {
			return;
		}
		Object value = parseProperty(getText());
		myProperty.setAnyValue(value);
		presentObject(value);
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
