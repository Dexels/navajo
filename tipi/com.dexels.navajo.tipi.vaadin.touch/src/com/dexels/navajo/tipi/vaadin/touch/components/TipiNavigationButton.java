package com.dexels.navajo.tipi.vaadin.touch.components;

import java.util.Map;

import com.dexels.navajo.tipi.vaadin.components.TipiButton;
import com.vaadin.addon.touchkit.ui.NavigationButton;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class TipiNavigationButton extends TipiButton {

	private static final long serialVersionUID = -5043895594246857632L;

	@Override
	public Object createContainer() {
		NavigationButton nb = new NavigationButton(){
			private static final long serialVersionUID = 4645763999780625837L;
			@Override
			protected void fireClick() {
		    	System.err.println("KLIK!");
		    }
		    @Override
		    public void changeVariables(Object source, Map<String, Object> variables) {
		    }

		};
		nb.setTargetView(null);
        nb.setStyleName("v-touchkit-navbutton");
        nb.setCaption("aaap");
        nb.addListener(new ClickListener() {
			
			private static final long serialVersionUID = 5975819383218437406L;

			@Override
			public void buttonClick(ClickEvent event) {
System.err.println("KLIIIIK!");		

			}
			
		});
		return nb;
	}


}
