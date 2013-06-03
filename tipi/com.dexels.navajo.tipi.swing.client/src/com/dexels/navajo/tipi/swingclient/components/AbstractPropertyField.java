package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;

public abstract class AbstractPropertyField extends JTextField implements
		FocusListener, PropertyChangeListener {

	private static final long serialVersionUID = -7775450101839857448L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(AbstractPropertyField.class);
	
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

	// @Override
	// public boolean isOpaque() {
	// return true;
	// }
	public void setEditing(boolean b) {
		isEditing = b;
	}

	@Override
	public void setEnabled(boolean b) {
	}

	public void setProperty(Property p) {
		if (p != myProperty) {
			if (myProperty != null) {
				myProperty.removePropertyChangeListener(this);
			}
			setPropertyValue(p);
			if (myProperty != null) {
				myProperty.addPropertyChangeListener(this);
			}
		} else {
			setPropertyValue(p);
		}

	}

	/**
	 * Similar to setProperty, only will not listen for property changes
	 * 
	 * @param p
	 */
	public void setPropertyValue(Property p) {
		myProperty = p;

		if (myProperty == null) {
			setText("");
			return;
		}
		AbstractPropertyField.super.setEditable(myProperty.isDirIn());
		AbstractPropertyField.super.setEnabled(myProperty.isDirIn());
		String s = getPresentationFormat(myProperty.getTypedValue());
		setText(s);
		setEditing(false);
		if (hasFocus()) {
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
						String editingFormat = getEditingFormat(myProperty
								.getTypedValue());
						setText(editingFormat);
						selectAll();
						setEditing(true);
					}
				};
				if (SwingUtilities.isEventDispatchThread()) {
					runAction.run();
				} else {
					SwingUtilities.invokeAndWait(runAction);
				}
			} catch (InterruptedException e) {
				logger.error("Error: ",e);
			} catch (InvocationTargetException e) {
				logger.error("Error: ",e);
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

		if (!isEditing()) {
			return;
		}
		if (myProperty == null) {
			return;
		}
		Object value = parseProperty(getText());
		myProperty.setAnyValue(value);
		// value can have changed as a result of the onValueChanged event triggered by setAnyValue
		presentObject(myProperty.getTypedValue());
		setEditing(false);
	}

	public void propertyChange(PropertyChangeEvent e) {
		Property p = (Property) e.getSource();
		if (p != myProperty) {
			logger.info("Mysterious anomaly!");
		}
		presentObject(parseProperty(p.getValue()));
	}

	protected void presentObject(Object o) {
		final String s = getPresentationFormat(o);
		try {
			Runnable runAction = new Runnable() {
				public void run() {
					setText(s);
				}
			};
			if (SwingUtilities.isEventDispatchThread()) {
				runAction.run();
			} else {
				SwingUtilities.invokeAndWait(runAction);
			}
		} catch (InterruptedException e) {
			logger.error("Error: ",e);
		} catch (InvocationTargetException e) {
			logger.error("Error: ",e);
		}
	}

	protected String getPresentationFormat(Object newValue) {
		if (newValue == null) {
			return "";
		}
		return newValue.toString();
	}

	public void update() {
		updateProperty();
	}

}
