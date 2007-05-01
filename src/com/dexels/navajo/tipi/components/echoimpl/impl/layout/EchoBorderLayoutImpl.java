package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import java.util.StringTokenizer;

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
		int northSize = -1;
		int southSize = -1;
		for (int i = 0; i < childComponents.size(); i++) {
			Component current = (Component)childComponents.get(i);
			Object constraint = constraints.get(current);
			String con = ((String)constraint).toLowerCase();
			String height = null;
			
			System.err.println("BRAA: "+con+" component # "+i);
			
			if(con.contains(":")) {
				StringTokenizer st = new StringTokenizer(""+con,":");
				con = st.nextToken();
				height = st.nextToken();
				System.err.println("CON: "+con+" height = "+height);
			}
			System.err.println("Checking constraint: "+con+" component # "+i);
			if("north".equals(con)) {
				northComponent = current;
				if(height!=null) {
					northSize = Integer.parseInt(height);
				}

			}
			if("south".equals(con)) {
				southComponent = current;
				if(height!=null) {
					southSize = Integer.parseInt(height);
				}
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
//			southComponent.setBackground(new Color(255,0,0));
			sp = embedComponents(southComponent,centerComponent,true,southSize);
		} else {
			sp = centerComponent;
		}
		if(northComponent!=null) {
//			northComponent.setBackground(new Color(255,0,0));
			sp = embedComponents(northComponent,sp,false,northSize);
		} else {
			if(sp==null) {
			sp = centerComponent;
			}
		}
//		myParent.addToContainer(sp, null);
	System.err.println("Adding class: "+ sp.getClass()+"Parent class: "+((TipiEchoDataComponentImpl)myParent).getInnerComponent().getClass());

		((TipiEchoDataComponentImpl)myParent).getInnerComponent().add(sp);
		
	}

	private SplitPane embedComponents(Component firstComponent, Component secondComponent,boolean invert, int size) {
		if(size<0) {
			size = 38; // default size
		}
		System.err.println("Using size: "+size);
		SplitPane sp = new SplitPane(invert?SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP:SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
//		sp.setBackground(new Color(0,0,255));
		sp.add(firstComponent);
		sp.add(secondComponent);
		sp.setResizable(false);
		sp.setSeparatorPosition(new Extent(size,Extent.PX));
		sp.setSeparatorColor(new Color(0,0,0));
		sp.setSeparatorHeight(new Extent(0,Extent.PX));
		sp.setSeparatorWidth(new Extent(0,Extent.PX));
		return sp;
	}

}
