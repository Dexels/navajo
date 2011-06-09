package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.terminal.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;

public class TipiButton extends TipiVaadinComponentImpl {

	@SuppressWarnings("serial")
	@Override
	public Object createContainer() {
		Button button = new Button();
		button.addListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					performTipiEvent("onActionPerformed", null, true);
				} catch (TipiBreakException e) {
					e.printStackTrace();
				} catch (TipiException e) {
					e.printStackTrace();
				}
			}
		});
		return button;
	}

//	public void setIcon(Object u) {
//		Button b = (Button) getVaadinContainer();
//		System.err.println("Setting icon to: "+u);
//		Resource r = getResource(u);
//		b.setIcon(r);
//	}

	  public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
			Component v = getVaadinContainer();
		        if (name.equals("text")) {
		          v.setCaption( (String) object);
		        }
		        if ("icon".equals(name)) {
	                v.setIcon( getResource(object));
		        }
	  }
}
