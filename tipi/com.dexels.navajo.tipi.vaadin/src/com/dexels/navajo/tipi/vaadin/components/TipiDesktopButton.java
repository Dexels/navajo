/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;


public class TipiDesktopButton extends TipiButton {

	private static final long serialVersionUID = -8834396175368732336L;

	@Override
	public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
//			Button v = (Button) getVaadinContainer();
		        if (name.equals("textfont")) {
//		          v.setCaption( (String) object);
		        	//ignore
		        }
		        if ("tooltiptext".equals(name)) {
	                // ignore
		        }
	  }

}
