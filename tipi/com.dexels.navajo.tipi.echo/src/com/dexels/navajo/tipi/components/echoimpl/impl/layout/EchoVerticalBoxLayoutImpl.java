package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.LayoutData;
import nextapp.echo2.app.PaneContainer;

import com.dexels.navajo.tipi.components.echoimpl.TipiEchoDataComponentImpl;

import echopointng.ContainerEx;

public class EchoVerticalBoxLayoutImpl extends EchoLayoutImpl {

	
	private final static Logger logger = LoggerFactory
			.getLogger(EchoVerticalBoxLayoutImpl.class);
	public EchoVerticalBoxLayoutImpl() {
		
	}
	
	
	public void commitToParent() {
		int maxwidth = 0;
		for (int i = 0; i < childComponents.size(); i++) {
			Component current = (Component)childComponents.get(i);
			TipiEchoGridBagConstraints tt = (TipiEchoGridBagConstraints)constraints.get(current);
			if(tt!=null) {
				int width = tt.getColumnSpan()+tt.getColumn();
				if(width>maxwidth) {
					maxwidth = width;
				}
			}
		}

		
		Column sp = new Column();
		logger.info("Creating");
		for (int i = 0; i < childComponents.size(); i++) {
			Component current = (Component)childComponents.get(i);
			if(current instanceof PaneContainer) {
				ContainerEx cc = new ContainerEx();
				cc.setHeight(new Extent(100,Extent.PERCENT));
				cc.setWidth(new Extent(100,Extent.PERCENT));
	        	
				cc.add(current);
				sp.add(cc);
				LayoutData layoutData = (LayoutData)constraints.get(current);
				logger.info("LAYOUT: "+layoutData);
				cc.setLayoutData(layoutData);
				
			} else {
				sp.add(current);
				LayoutData layoutData = (LayoutData)constraints.get(current);
				logger.info("LAYOUT: "+layoutData);
				current.setLayoutData(layoutData);
			}
		}
	
		logger.info("Parent class: "+myParent.getClass());
		((TipiEchoDataComponentImpl)myParent).getInnerComponent().add(sp);
	}

}
