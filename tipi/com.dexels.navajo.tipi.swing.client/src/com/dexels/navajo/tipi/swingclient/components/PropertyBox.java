package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

public class PropertyBox extends BaseComboBox implements PropertyControlled,
		PropertyChangeListener {

	private static final long serialVersionUID = 473768705535369366L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(PropertyBox.class);
	private Property myProperty = null;

	private Object lastSelection;

	public PropertyBox() {

		// PropertyCellRenderer renderer2 = new PropertyCellRenderer();
		// this.setRenderer(renderer2);
		this.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				this_itemStateChanged(e);
			}
		});
		// setBackground(Color.white);
		setEditable(false);
	}

	@Override
	public final Property getProperty() {
		return myProperty;
	}

	@Override
	public final void update() {
		if (myProperty != null) {
			lastSelection = myProperty.getTypedValue();
		}
	}

	public final Object getLastSelection() {
		return lastSelection;
	}

	public final void loadProperty(Property p) {
		lastSelection = p.getTypedValue();
		try {
			if (p.getEvaluatedType().equals(Property.SELECTION_PROPERTY)) {
				myProperty = p;
				loadCombobox(p);
			} else {
				System.err
						.println("Attempting to load property box from non-selection property");
			}
		} catch (NavajoException e1) {
			e1.printStackTrace();
		}
		setEnabled(p.isDirIn());

	}

	@Override
	public void setEditable(boolean b) {
		setEnabled(b);
	}

	public void setTextEditingEnabled(boolean b) {
		super.setEditable(b);
	}

	@Override
	public final void setProperty(Property p) {
		if (myProperty != null) {
			myProperty.removePropertyChangeListener(this);
		}
		// TODO NULL stuff. How to reset the property to null?
		if (p == null) {
			return;
		} else {
			p.addPropertyChangeListener(this);
		}

		if (p.getType().equals(Property.SELECTION_PROPERTY)) {
			loadProperty(p);
			return;
		} else {
			if (myProperty == null) {
				System.err
						.println("Setting property to propertyBox without loading first!");
				// return;
			}

			if (p.getValue() != null) {
				setToKey((p.getValue()).trim());
			}
			setEnabled(p.isDirIn());
			// if (p.isDirOut()) {
			// setForeground(Color.black);
			// setBackground(SystemColor.control);
			// }
		}
	}

	private final void setSelectionProperty() throws NavajoException {
		Selection s = (Selection) getSelectedItem();
		if (s == null) {
			return;
		}
		if (myProperty != null) {
			myProperty.setSelected(s);
		}

	}

	public final Selection getSelectedSelection() {
		Object o = super.getSelectedItem();
		if (Selection.class.isInstance(o)) {
			return (Selection) o;
		}
		System.err
				.println("Error: Can not return selection from box: Not of type Selection");
		return null;

	}

	final void this_itemStateChanged(ItemEvent e) {
		if (myProperty == null) {
			logger.info("Property box changed before it was set!");
		}
		try {
			setSelectionProperty();
		} catch (NavajoException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource().equals(myProperty)) {
			logger.info("change: " + evt.getOldValue() + " new: "
					+ evt.getNewValue());
			setProperty(myProperty);
		} else {
			// huh?!
			logger.info("snappetniet");
		}
	}

}
