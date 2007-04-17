package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import com.dexels.navajo.tipi.components.echoimpl.TipiEchoDataComponentImpl;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SplitPane;

public class EchoBorderLayoutImpl extends EchoLayoutImpl {

	public EchoBorderLayoutImpl() {
		
	}
	public void commitToParent() {
		Component northComponent = null;
		Component southComponent = null;
		Component centerComponent = null;
		for (int i = 0; i < childComponents.size(); i++) {
			Component current = (Component)childComponents.get(i);
			Object constraint = constraints.get(current);
			String con = ((String)constraint).toLowerCase();
			System.err.println("Checking constraint: "+con+" component # "+i);
			if("north".equals(con)) {
				northComponent = current;
			}
			if("south".equals(con)) {
				southComponent = current;
			}
			if("center".equals(con)) {
				centerComponent = current;
			}
		}
		if(centerComponent==null) {
			if(childComponents.size()==0) {
				return;
			}
			if(myParent==null) {
				throw new UnsupportedOperationException("No parent found!");
			}
			throw new UnsupportedOperationException("Every border layout time should have one 'center' component: parent: "+myParent.getPath()+" # of children: "+childComponents.size());
		}
		Component sp = null;
		if(southComponent!=null) {
			sp = embedComponents(southComponent,centerComponent,true);
		} else {
			sp = centerComponent;
		}
		if(northComponent!=null) {
			sp = embedComponents(northComponent,sp,false);
		} else {
			if(sp==null) {
			sp = centerComponent;
			}
		}
//		myParent.addToContainer(sp, null);
		System.err.println("Parent class: "+myParent.getClass());
		((TipiEchoDataComponentImpl)myParent).getInnerComponent().add(sp);
		
	}

	private Component embedComponents(Component firstComponent, Component secondComponent,boolean invert) {
		SplitPane sp = new SplitPane(invert?SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP:SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		sp.add(firstComponent);
		sp.add(secondComponent);
		sp.setResizable(false);
		sp.setSeparatorPosition(new Extent(38,Extent.PX));
		sp.setSeparatorColor(new Color(0,0,0));
		sp.setSeparatorHeight(new Extent(0,Extent.PX));
		sp.setSeparatorWidth(new Extent(0,Extent.PX));
		return sp;
	}

}
