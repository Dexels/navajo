package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import com.dexels.navajo.tipi.components.echoimpl.TipiEchoDataComponentImpl;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.SplitPane;

public class EchoGridLayoutImpl extends EchoLayoutImpl {

	public EchoGridLayoutImpl() {
		
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

		
		Grid sp = new Grid(maxwidth);
	
		for (int i = 0; i < childComponents.size(); i++) {
			Component current = (Component)childComponents.get(i);
			sp.add(current);
		}
	
		System.err.println("Parent class: "+myParent.getClass());
		((TipiEchoDataComponentImpl)myParent).getInnerComponent().add(sp);
	}

}
