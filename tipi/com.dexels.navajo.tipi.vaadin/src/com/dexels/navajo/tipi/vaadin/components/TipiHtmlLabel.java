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
