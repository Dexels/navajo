package com.dexels.navajo.tipi.vaadin.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;

public class TipiRow extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -8463738923426799145L;
	
	private final static Logger logger = LoggerFactory.getLogger(TipiRow.class);
	
	@Override
	public Object createContainer() {
		return new HorizontalLayout();
	}

	@Override
	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
		HorizontalLayout hl = (HorizontalLayout)currentContainer;
		super.addToVaadinContainer(currentContainer, component, constraints);
		if(constraints!=null) {
			int ratio;
			try {
				ratio = Integer.parseInt((String) constraints);
				if(ratio==100) {
					component.setWidth("100%");
				}
				hl.setExpandRatio(component, ratio);
			} catch (NumberFormatException e) {
				logger.debug("Not a number. Ignoring");
			}
		}
//		component.setWidth("100%");
		
	}

	
}
