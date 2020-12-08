/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.dexels.navajo.tipi.vaadin.document.AdHocProperty;
import com.vaadin.data.Property;
import com.vaadin.ui.TextArea;

public class TipiTextArea extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 1561760767992331660L;
	private TextArea textField;
	private Property p;

	@Override
	public Object createContainer() {
		p = new AdHocProperty("", String.class) {
			private static final long serialVersionUID = -8607024176202410618L;

			@Override
			public String toString() {
				return (String) getValue();
			}
		};

		textField = new TextArea("", p);
		return textField;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		if (name.equals("text")) {
			p.setValue(object);
			return;
		}
		super.setComponentValue(name, object);
	}

	@Override
	public Object getComponentValue(final String name) {
		if (name.equals("text")) {
			return p.getValue();
		}
		return super.getComponentValue(name);

	}

}
