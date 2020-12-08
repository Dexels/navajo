/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import com.vaadin.ui.Label;

public class TipiHtmlLabel extends TipiLabel {

	private static final long serialVersionUID = 1561760767992331660L;
	private Label label;

	@Override
	public Object createContainer() {
		label =(Label)super.createContainer();
		label.setContentMode(Label.CONTENT_XHTML);
		return label;
	}


	  @Override
	public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
			Label v = (Label) getVaadinContainer();
			if (name.equals("text")) {
		        v.setValue(object);
	        	v.setCaption( null );
		        	
		        }
		        if ("icon".equals(name)) {
	                v.setIcon( getResource(object));
		        }
		        if("cssClass".equals(name)){
		        
		        	v.addStyleName(""+object);
		        }
	  }



}
