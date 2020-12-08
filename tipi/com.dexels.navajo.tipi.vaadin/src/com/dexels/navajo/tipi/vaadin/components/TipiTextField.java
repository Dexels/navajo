/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

public class TipiTextField extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 1561760767992331660L;
	private TextField textField;

	@Override
	public Object createContainer() {
		textField = new TextField();
		return textField;
	}

	public void setText(String s) {
		textField.setCaption(s);
	}

	public String getText() {
		return textField.getCaption();
	}
	
	  @Override
	public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
			Component v = getVaadinContainer();
		        if (name.equals("text")) {
		          v.setCaption( ""+ object);
		        }
	  }

}
