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
