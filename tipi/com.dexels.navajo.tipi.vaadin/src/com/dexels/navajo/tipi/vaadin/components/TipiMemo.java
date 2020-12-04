/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.TextArea;

public class TipiMemo extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 8935212015368949120L;
	private TextArea textArea;
	@Override
	public Object createContainer() {
		textArea = new TextArea();
		textArea.setSizeFull();
		return textArea;
	}
	  @Override
	public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
		        if (name.equals("text")) {
		        	textArea.setValue( object);
		        }
	  }
}
