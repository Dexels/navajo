package com.dexels.navajo.tipi.vaadin.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.VerticalLayout;

public class TipiPanel extends TipiVaadinComponentImpl {
	private static final long serialVersionUID = 5587251071656604056L;
	private final static Logger logger = LoggerFactory
			.getLogger(TipiMessagePanel.class);
	
	@Override
	public Object createContainer() {
		VerticalLayout panel = new VerticalLayout();
		panel.addListener(new LayoutClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
            public void layoutClick(LayoutClickEvent event) {
                try {
                	performTipiEvent("onActionPerformed", null, true);
				} catch (TipiBreakException e) {
					logger.error("Error: ",e);
				} catch (TipiException e) {
					logger.error("Error: ",e);
				}
            }
        });
		return panel;
	}

	@Override
	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
		super.addToVaadinContainer(currentContainer, component, constraints);
		component.setWidth("100%");
	}


	  @Override
	public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
			Component v = getVaadinContainer();

		        if ("logo".equals(name)) {
	                v.setIcon( getResource(object));
		        }

	  }

}
