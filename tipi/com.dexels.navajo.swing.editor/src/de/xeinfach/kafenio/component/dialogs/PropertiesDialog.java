/*
GNU Lesser General Public License

PropertiesDialog
Copyright (C) 2003 Howard Kistler
changes to PropertiesDialog
Copyright (C) 2003-2004 Karsten Pawlik

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package de.xeinfach.kafenio.component.dialogs;

import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;

/** 
 * Description: Class for providing a dialog that lets the user specify values for tag attributes
 * 
 * @author Howard Kistler, Karsten Pawlik
 */
public class PropertiesDialog extends AbstractKafenioDialog {

	private static LeanLogger log = new LeanLogger("PropertiesDialog.class");

	private Hashtable htInputFields;
	private Object[] panelContents;
	private int objectCount;

	/**
	 * creates a new Properties Dialog using the given values.
	 * @param parent parent frame
	 * @param fields property fields
	 * @param types property field types
	 * @param values property values
	 * @param title window title
	 * @param bModal boolean value.
	 */
	public PropertiesDialog(KafenioPanel parent, 
							String[] fields, 
							String[] types, 
							String[] values, 
							String title, 
							boolean bModal)
	{
		super(parent, title, bModal);
		
		htInputFields = new Hashtable();
		panelContents = new Object[(fields.length * 2)];
		objectCount = 0;

		for(int iter = 0; iter < fields.length; iter++) {
			String fieldName = fields[iter];
			String fieldType = types[iter];
			Object fieldComponent;

			if(fieldType.equals("text")) {
				fieldComponent = new JTextField(3);
				if(values[iter] != null && values[iter].length() > 0) {
					((JTextField)(fieldComponent)).setText(values[iter]);
				}
			} else if(fieldType.equals("bool")) {
				fieldComponent = new JCheckBox();
				if(values[iter] != null) {
					((JCheckBox)(fieldComponent)).setSelected(values[iter] == "true");
				}
			} else if(fieldType.equals("combo")) {
				fieldComponent = new JComboBox();
				if(values[iter] != null) {
					StringTokenizer stParse = new StringTokenizer(values[iter], ",", false);
					while(stParse.hasMoreTokens()) {
						((JComboBox)(fieldComponent)).addItem(stParse.nextToken());
					}
				}
			} else {
				fieldComponent = new JTextField(3);
			}
			
			htInputFields.put(fieldName, fieldComponent);
			panelContents[objectCount] = fieldName; // Translatrix.getTranslationString(fieldName);
			panelContents[objectCount + 1] = fieldComponent;
			objectCount += 2;
		}

		init(panelContents);
	}

	/**
	 * creates a new Properties Dialog using the given values.
	 * @param parent parent frame
	 * @param fields property fields
	 * @param types property field types
	 * @param title window title
	 * @param bModal boolean value.
	 */
	public PropertiesDialog(KafenioPanel parent, String[] fields, String[] types, String title, boolean bModal) {
		this(parent, fields, types, new String[fields.length], title, bModal);
	}

	/**
	 * @see de.xeinfach.kafenio.component.dialogs.AbstractKafenioDialog#setDefaultValues(java.lang.Object)
	 */
	public void setDefaultValues(Object value) {
		if(value.equals(getButtonLabels()[0])) {
			setVisible(false);
		} else {
			setVisible(false);
		}
	}

	/**
	 * returns the value of a field
	 * @param fieldName name of a field
	 * @return returns the value of a field
	 */
	public String getFieldValue(String fieldName) {
		Object dataField = htInputFields.get(fieldName);
		if(dataField instanceof JTextField) {
			return ((JTextField)dataField).getText();
		} else if(dataField instanceof JCheckBox) {
			if(((JCheckBox)dataField).isSelected()) {
				return "true";
			} else {
				return "false";
			}
		} else if(dataField instanceof JComboBox) {
			return (String)(((JComboBox)dataField).getSelectedItem());
		} else {
			return (String)null;
		}
	}

	/**
	 * @return returns the value of the decision the user made when clicking one of the buttons.
	 */
	public String getDecisionValue() {
		return getJOptionPane().getValue().toString();
	}

}

