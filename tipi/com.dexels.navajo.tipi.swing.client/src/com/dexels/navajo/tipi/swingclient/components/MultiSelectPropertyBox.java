package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.ItemEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

/**
 * <p>
 * Title: Seperate project for Navajo Swing client
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class MultiSelectPropertyBox extends BaseComboBox implements
		PropertyControlled {

	// ResourceBundle myResource;

	private static final long serialVersionUID = 1387588426549269939L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MultiSelectPropertyBox.class);
	
	private Property myProperty = null;
	private Property myValueProperty = null;

	// private Message validationMsg;
	// private ArrayList rules = new ArrayList();

	public MultiSelectPropertyBox() {
		this.setRenderer(new PropertyCellRenderer());
		this.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// this_itemStateChanged(e);
			}
		});
		setEditable(false);
		this.setRenderer(new MultiSelectBoxRenderer());
	}

	public void gainFocus() {
		// gar nichts
	}

	public final Property getProperty() {
		if (myValueProperty != null) {
			return myValueProperty;
		}
		return myProperty;
	}

	public final void update() {
		// required method
	}

	public final void loadProperty(Property p) {
		if (p.getType().equals(Property.SELECTION_PROPERTY)) {
			myProperty = p;

			loadCombobox(p);
		} else {
			System.err
					.println("Attempting to load property box from non-selection property");
		}
		setEnabled(p.isDirIn());

	}

	@Override
	public final void setEditable(boolean b) {
		setEnabled(b);
	}

	// public void paint(Graphics g) {
	//
	// }

	public final void setProperty(Property p) {
		if (p == null) {
			logger.info("Resetting property to null.");
			myValueProperty = null;
			return;
		}

		if (p.getType().equals(Property.SELECTION_PROPERTY)) {
			// logger.info("Reseting property box to a selection property: ");
			loadProperty(p);
			return;
			// myProperty = p;
			// loadCombobox(p);
		} else {
			if (myProperty == null) {
				System.err
						.println("Setting property to propertyBox without loading first!");
				// return;
			}
			myValueProperty = p;

			if (p.getValue() != null) {
				setToKey((p.getValue()).trim());
			}
			setEnabled(p.isDirIn());

		}
	}

	private final void setSelectionProperty() {
		Selection s = (Selection) getSelectedItem();
		if (s == null) {
			return;
		}
		for (int i = 0; i < getItemCount(); i++) {
			Selection current = (Selection) getItemAt(i);
			if (current != null && current.getValue() != null
					&& current.getValue().equals(s.getValue())) {
				boolean b = current.isSelected();
				current.setSelected(!b);
			}
		}
		// setPopupVisible(true);

	}

	private final void setValueProperty() {
		myValueProperty.setValue(getSelectedId());
		// logger.info("SetTO: " + getSelectedId());
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
			// return;
		}
		if (myValueProperty == null) {
			if (e.getStateChange() == 2) {
				setSelectionProperty();
			}
		} else {
			setValueProperty();
		}
	}

	/**
	 * Causes the combo box to close its popup window.
	 * 
	 * @todo Implement this javax.swing.JComboBox method
	 */
	@Override
	public void hidePopup() {
		// logger.info("..errr..I'll hide u");
	}

}
