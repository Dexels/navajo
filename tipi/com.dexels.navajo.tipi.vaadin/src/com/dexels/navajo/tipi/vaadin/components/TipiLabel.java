/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Label;

public class TipiLabel extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 1561760767992331660L;
	private Label label;

	@Override
	public Object createContainer() {
		label = new Label();
		label.setSizeUndefined();
		return label;
	}

//	public void setText(String s) {
//		label.setCaption(s);
//	}
//
//	public String getText() {
//		return label.getCaption();
//	}
	
	  @Override
	public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
			Label l = (Label) getVaadinContainer();
		        if (name.equals("text")) {
		            l.setCaption( ""+ object);
		        }
		        if ("icon".equals(name)) {
	                l.setIcon( getResource(object));
		        }
                if ("tooltip".equals(name)) {
                    l.setDescription( object == null ? (String) null : object.toString());
                }
		        if("cssClass".equals(name)){
		        
		        	l.addStyleName(""+object);
		        }
	  }

}
