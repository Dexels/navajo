package com.dexels.navajo.tipi.swingclient.components;

import com.dexels.navajo.document.*;

import java.awt.event.*;
import java.util.*;
import java.awt.*;
import java.beans.*;

public class PropertyBox extends BaseComboBox implements PropertyControlled, Ghostable, Validatable, PropertyChangeListener {
	ResourceBundle myResource;

	private boolean ghosted = false;
	private boolean enabled = true;
	private Property myProperty = null;
	private Property myValueProperty = null;
	private Message validationMsg;
	private int myValidationState = Validatable.VALID;
	private ArrayList rules = new ArrayList();

	private Object lastSelection;

	public PropertyBox() {
		try {
			String prop = null;
			try {
				prop = System.getProperty("com.dexels.navajo.propertyMap");
			} catch (SecurityException e) {
			}
			if (prop != null) {
				myResource = ResourceBundle.getBundle(prop);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		
		this.setRenderer(new PropertyCellRenderer());
		this.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				this_itemStateChanged(e);
			}
		});
		setBackground(Color.white);
		setEditable(false);
	}

	public void gainFocus() {
		// gar nichts
	}

	public final void setValidationMessage(Message msg) {
		validationMsg = msg;
	}

	public final void checkValidation() {
		if (validationMsg != null && validationMsg.getName().equals("ConditionErrors")) {
			checkValidation(validationMsg);
		} else {
			setValidationState(VALID);
		}
	}

	public final void checkValidation(Message msg) {
		ArrayList conditions = msg.getAllMessages();
		for (int i = 0; i < conditions.size(); i++) {
			Message current = (Message) conditions.get(i);
			Property conditionCode = current.getProperty("Id");
			int code = Integer.parseInt(conditionCode.getValue());
			for (int j = 0; j < rules.size(); j++) {
				int rule = ((Integer) rules.get(j)).intValue();
				if (rule == code) {
					setValidationState(INVALID);
					System.err
							.println("-------------------------------------------------------------------------------==>> I am invalid!! : "
									+ this);
					return;
				}
			}
		}
		setValidationState(VALID);
	}

	@Override
	public final void addValidationRule(int state) {
		System.out.println("Adding validation rule: " + state);
		rules.add(new Integer(state));
	}

	public final void clearValidationRules() {
		rules.clear();
	}

	public final void setValidationState(int state) {
		myValidationState = state;
		switch (myValidationState) {
		case VALID:
			// myConditionRuleIds.clear();
			this.setBackground(Color.white);
			break;
		case INVALID:
			this.setBackground(Color.red);
			break;
		case EMPTY:
			this.setBackground(Color.blue);
			break;
		case ADJUSTED:
			this.setBackground(Color.yellow);
			break;
		}
	}

	public final Property getProperty() {
		if (myValueProperty != null) {
			return myValueProperty;
		}
		return myProperty;
	}

	public final void update() {
		if(myProperty!=null) {
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
				System.err.println("Attempting to load property box from non-selection property");
			}
		} catch (NavajoException e1) {
			e1.printStackTrace();
		}
		setEnabled(p.isDirIn());
		if (p.isDirOut()) {
			setForeground(Color.darkGray);
//			setBackground(SystemColor.control);
		}
		// updateUI();
		String toolTipText;
		try {
			if (myResource != null) {
				toolTipText = myResource.getString(p.getName());
				setToolTipText(toolTipText);
			} else {
				toolTipText = p.getName();
				setToolTipText(toolTipText);
			}

		} catch (MissingResourceException e) {
			if ((toolTipText = p.getDescription()) != null) {
				setToolTipText(toolTipText);
			} else {
				toolTipText = p.getName();
				setToolTipText(toolTipText);
			}
		}
	}

	@Override
	public void setEditable(boolean b) {
		setEnabled(b);
	}
	
	public void setTextEditingEnabled(boolean b) {
		super.setEditable(b);
	}

	// public void paint(Graphics g) {
	//
	// }

	public final void setProperty(Property p) {
		if(myProperty!=null) {
			myProperty.removePropertyChangeListener(this);
		}
		if (p == null) {
			System.err.println("Resetting property to null.");
			myValueProperty = null;
			return;
		} else {
			p.addPropertyChangeListener(this);

		}

		if (p.getType().equals(Property.SELECTION_PROPERTY)) {
			// System.err.println("Reseting property box to a selection
			// property: ");
			loadProperty(p);
			return;
			// myProperty = p;
			// loadCombobox(p);
		} else {
			if (myProperty == null) {
				System.err.println("Setting property to propertyBox without loading first!");
				// return;
			}
			myValueProperty = p;

			if (p.getValue() != null) {
				setToKey((p.getValue()).trim());
			}
			setEnabled(p.isDirIn());
			if (p.isDirOut()) {
				setForeground(Color.black);
				setBackground(SystemColor.control);
			}
			// updateUI();
			// setEditable(p.isDirIn());
		}
	}


	private final void setSelectionProperty() throws NavajoException {
		Selection s = (Selection) getSelectedItem();
		if (s == null) {
			return;
		}
		if ( myProperty == null ) {
			if ( myValueProperty != null ) {
				System.err.println("myValueProperty = " + myValueProperty);
			}
		} else {
			myProperty.setSelected(s);
		}

	}

	private final void setValueProperty() {
		myValueProperty.setValue(getSelectedId());
		// System.out.println("SetTO: " + getSelectedId());
	}

	public final Selection getSelectedSelection() {
		Object o = super.getSelectedItem();
		if (Selection.class.isInstance(o)) {
			return (Selection) o;
		}
		System.err.println("Error: Can not return selection from box: Not of type Selection");
		return null;

	}

	public final void setSelection(Selection s) {
		removeAllItems();
		addSelection(s);
		this.setToValue(s);
	}

	final void this_itemStateChanged(ItemEvent e) {
		if (myProperty == null) {
			System.err.println("Property box changed before it was set!");
		}
		if (myValueProperty == null) {
			try {
				setSelectionProperty();
			} catch (NavajoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			setValueProperty();
			try {
				setSelectionProperty();
			} catch (NavajoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	
		setChanged(true);
	}

	public final boolean isGhosted() {
		return ghosted;
	}

	public final void setGhosted(boolean g) {
		ghosted = g;
		super.setEnabled(enabled && (!ghosted));
	}

	@Override
	public  void setEnabled(boolean e) {
		enabled = e;
		super.setEnabled(enabled && (!ghosted));
	}

	public final int getValidationState() {
		return myValidationState;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getSource().equals(myProperty)) {
			System.err.println("change: "+evt.getOldValue()+" new: "+evt.getNewValue());
			setProperty(myProperty);
		} else {
			//huh?!
			System.err.println("snappetniet");
//			myProperty.removePropertyChangeListener(this);
		}
	}

}
