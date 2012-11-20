package com.dexels.navajo.tipi.swingclient.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

@SuppressWarnings("deprecation")
public final class MultipleSelectionPropertyCheckboxGroup extends BasePanel
		implements PropertyControlled, PropertyChangeListener {
	private static final long serialVersionUID = -5094941150514654170L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MultipleSelectionPropertyCheckboxGroup.class);
	private Property myProperty;
	// private ArrayList mySelectionList = new ArrayList();
	private List<ItemListener> myListeners = new ArrayList<ItemListener>();
	private boolean columnMode = false;
	private int columns = 2;

	public MultipleSelectionPropertyCheckboxGroup() {
		this.setLayout(new GridBagLayout());
		setOpaque(false);
	}

	public void addCheckboxListener(ItemListener l) {
		myListeners.add(l);
	}

	public void removeCheckboxListener(ItemListener l) {
		myListeners.remove(l);
	}

	private void fireListeners(ItemEvent e) {
		for (int i = 0; i < myListeners.size(); i++) {
			ItemListener il = myListeners.get(i);
			il.itemStateChanged(e);
		}
	}

	public void setColumnMode(boolean b) {
		columnMode = b;
	}

	public void setColumns(int cols) {
		columns = cols;
	}

	public final void setVerticalScrolls(boolean b) {
		// ignore

	}

	public final void setHorizontalScrolls(boolean b) {
		// ignore
	}

	/** @todo Add action / changelisteners to this class */

	public final Property getProperty() {
		return myProperty;
	}

	public final void setProperty(Property p) {
		try {
			if (myProperty != null) {
				myProperty.removePropertyChangeListener(this);
			}
			myProperty = p;
			ArrayList<Selection> selections = myProperty.getAllSelections();

			if (selections.size() <= 0) {
				logger.info("Watch it! No selection property!");
			} else {
				removeAll();
				int col = 0;
				int row = 0;
				for (int i = 0; i < selections.size(); i++) {
					Selection current = selections.get(i);
					SelectionCheckBox cb = new SelectionCheckBox();
					cb.setOpaque(false);
					cb.setSelection(current, myProperty);
					cb.setSelected(current.isSelected());
					if (columnMode) {
						int req = (int) Math.ceil(selections.size() / columns) - 1; // offset
																					// with
																					// 1
																					// because
																					// gridbag
																					// starts
																					// at
																					// 0
						if (row + 1 > req) {
							add(cb, new GridBagConstraints(col, row, 1, 1, 1.0,
									1.0, GridBagConstraints.NORTHWEST,
									GridBagConstraints.HORIZONTAL, new Insets(
											2, 2, 2, 2), 0, 0));
						} else {
							add(cb, new GridBagConstraints(col, row, 1, 1, 1.0,
									0.0, GridBagConstraints.NORTHWEST,
									GridBagConstraints.HORIZONTAL, new Insets(
											2, 2, 2, 2), 0, 0));
						}
						row++;
						if (row > req) {
							row = 0;
							col++;
						}
					} else {
						add(cb, new GridBagConstraints(0, i, 1, 1, 1, 1.0,
								GridBagConstraints.NORTHWEST,
								GridBagConstraints.BOTH,
								new Insets(0, 0, 0, 0), 0, 0));
					}
				}
			}
			myProperty.addPropertyChangeListener(this);
			// updateUI();
		} catch (NavajoException ex) {
			ex.printStackTrace();
		}
	}

	public final void update() {
		// dummy
	}

	public void propertyChange(PropertyChangeEvent e) {
		if (e.getPropertyName().equals("value")) {
			fireListeners(null);
		}
		revalidate();
	}

}
