package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import java.util.StringTokenizer;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.SplitPane;

import com.dexels.navajo.tipi.components.echoimpl.TipiEchoDataComponentImpl;

public class EchoBorderLayoutImpl extends EchoLayoutImpl {

	public EchoBorderLayoutImpl() {
		
	}
	public void commitToParent() {
		Component northComponent = null;
		Component southComponent = null;
		Component eastComponent = null;
		Component westComponent = null;
		Component centerComponent = null;
		int northSize = -1;
		int southSize = -1;
		int eastSize = -1;
		int westSize = -1;
		for (int i = 0; i < childComponents.size(); i++) {
			Component current = (Component)childComponents.get(i);
			Object constraint = constraints.get(current);
			String con = ((String)constraint).toLowerCase();
			String height = null;
			
			if(con.contains(":")) {
				StringTokenizer st = new StringTokenizer(""+con,":");
				con = st.nextToken();
				height = st.nextToken();
				}
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
			if("east".equals(con)) {
				eastComponent = current;
				if(height!=null) {
					eastSize = Integer.parseInt(height);
				}

			}
			if("west".equals(con)) {
				westComponent = current;
				if(height!=null) {
					westSize = Integer.parseInt(height);
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
			sp = embedComponents(southComponent,centerComponent,southSize, SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP);
		} else {
			sp = centerComponent;
		}
		if(northComponent!=null) {
//			northComponent.setBackground(new Color(255,0,0));
			sp = embedComponents(northComponent,sp,northSize, SplitPane.ORIENTATION_VERTICAL);
		} else {
			if(sp==null) {
			sp = centerComponent;
			}
		}
		if(eastComponent!=null) {
//			northComponent.setBackground(new Color(255,0,0));
			sp = embedComponents(eastComponent,sp,eastSize, SplitPane.ORIENTATION_HORIZONTAL_RIGHT_LEFT);
		} else {
			if(sp==null) {
			sp = centerComponent;
			}
		}
		if(westComponent!=null) {
//			northComponent.setBackground(new Color(255,0,0));
			sp = embedComponents(westComponent,sp,westSize, SplitPane.ORIENTATION_HORIZONTAL);
		} else {
			if(sp==null) {
			sp = centerComponent;
			}
		}
//		myParent.addToContainer(sp, null);
	
		((TipiEchoDataComponentImpl)myParent).getInnerComponent().add(sp);
		
	}

	private SplitPane embedComponents(Component firstComponent, Component secondComponent, int size, int orientation) {
		if(size<0) {
			size = 38; // default size
		}
		System.err.println("Using size: "+size);
		SplitPane sp = new SplitPane(orientation);
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
