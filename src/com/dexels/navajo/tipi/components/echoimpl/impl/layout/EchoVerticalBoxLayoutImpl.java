package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import com.dexels.navajo.tipi.components.echoimpl.TipiEchoDataComponentImpl;

import echopointng.ContainerEx;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Column;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.LayoutData;
import nextapp.echo2.app.PaneContainer;
import nextapp.echo2.app.SplitPane;

public class EchoVerticalBoxLayoutImpl extends EchoLayoutImpl {

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
		System.err.println("Creating");
		for (int i = 0; i < childComponents.size(); i++) {
			Component current = (Component)childComponents.get(i);
			if(current instanceof PaneContainer) {
				ContainerEx cc = new ContainerEx();
				cc.setHeight(new Extent(100,Extent.PERCENT));
				cc.setWidth(new Extent(100,Extent.PERCENT));
	        	
				cc.add(current);
				sp.add(cc);
				LayoutData layoutData = (LayoutData)constraints.get(current);
				System.err.println("LAYOUT: "+layoutData);
				cc.setLayoutData(layoutData);
				
			} else {
				sp.add(current);
				LayoutData layoutData = (LayoutData)constraints.get(current);
				System.err.println("LAYOUT: "+layoutData);
				current.setLayoutData(layoutData);
			}
		}
	
		System.err.println("Parent class: "+myParent.getClass());
		((TipiEchoDataComponentImpl)myParent).getInnerComponent().add(sp);
	}

}
