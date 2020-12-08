/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myProperty != null) {
					myProperty.setValue(isSelected());
				}
			}
		});
	}

	@Override
	public final Property getProperty() {
		return myProperty;
	}

	@Override
	public final void update() {
		if (myProperty != null) {
			myProperty.setAnyValue(isSelected());
		}
	}

	@Override
	public final void setProperty(Property p) {
		myProperty = p;
		if (p == null) {
			return;
		}
		
		setEnabled(p.isDirIn());
		
		if (myProperty.getValue() == null) {
			// If myProperty's value is null, features such as sorting will not 
			// work. Therefore explicitly set to false
			myProperty.setAnyValue(false);
			setSelected(false);
		} else {
			setSelected(myProperty.getValue().equals("true"));
		}
	}

}
