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
