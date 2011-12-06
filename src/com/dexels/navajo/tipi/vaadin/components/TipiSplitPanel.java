package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.HorizontalSplitPanel;

public class TipiSplitPanel extends TipiVaadinComponentImpl {
	private static final long serialVersionUID = 5587251071656604056L;

	@Override
	public Object createContainer() {
		HorizontalSplitPanel panel = new HorizontalSplitPanel();
//		panel.setSizeFull();
		return panel;
	}
//
//	@Override
//	protected void addToVaadinContainer(ComponentContainer currentContainer, Component component, Object constraints) {
//		super.addToVaadinContainer(currentContainer, component, constraints);
////		component.setWidth("100%");
//	}


	  public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
		    HorizontalSplitPanel v = (HorizontalSplitPanel) getVaadinContainer();
		    	System.err.println(">>>> SIZIZIZIZZII: "+v.getWidth()+" :: "+v.getHeight());
				v.setWidth(getMainWindow().getWidth(), getMainWindow().getWidthUnits());
				v.setHeight(getMainWindow().getHeight(), getMainWindow().getHeightUnits());
		    	System.err.println(">>>> SIZIZIZIZZI2: "+v.getWidth()+" :: "+v.getHeight());

		    	if ("position".equals(name)) {
		        	int value = (Integer)object;
		        	v.setSplitPosition(value, Sizeable.UNITS_PIXELS);
		        }
		        if ("positionReverse".equals(name)) {
		        	int value = (Integer)object;
		        	v.setSplitPosition(value, Sizeable.UNITS_PIXELS,true);
		        }

	  }

}
