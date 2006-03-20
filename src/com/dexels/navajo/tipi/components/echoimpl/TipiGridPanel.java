package com.dexels.navajo.tipi.components.echoimpl;

import java.util.ArrayList;
import java.util.StringTokenizer;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.layout.GridLayoutData;
import echopointng.ContainerEx;
import echopointng.GroupBox;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiGridPanel extends TipiEchoDataComponentImpl {

	private Grid gridComponent;
	private GroupBox myContainer;
//	private GroupBox myBox;
	public TipiGridPanel() {
	}

	public Object createContainer() {
		myContainer = new GroupBox((String)null);
		gridComponent = new Grid();
		myContainer.setBorder(null);
        //		gridComponent = myContainer;
//		myContainer.setBorder(null);
		myContainer.add(gridComponent);
		return myContainer;
	}

	
	 public Object getActualComponent() {
		 return myContainer;
	}
	 
	 public void disposeComponent() {
		 Component e = myContainer.getParent();
		 if (e!=null) {
			e.remove(myContainer);
		}
		 super.disposeComponent();
	 }
	public void addToContainer(Object o, Object constraints){
		 String constr = (String)constraints;
		 Component c = (Component)o;
		 gridComponent.add(c);
		 if (constr!=null) {
			 c.setLayoutData(parseGridConstraints(constr,c));
		}
//		myContainer.setWidth(myContainer.getWidth());
//		myContainer.setHeight(myContainer.getHeight());
	 }
	 
	 public GridLayoutData parseGridConstraints(String txt, Component c) {
		 StringTokenizer st = new StringTokenizer(txt,";");
		 GridLayoutData myData = new GridLayoutData();
		 while (st.hasMoreTokens()) {
			String next = st.nextToken();
            System.err.println("NEEXT: "+next);
			StringTokenizer current = new StringTokenizer(next,":");
			String key = current.nextToken();
			String value = current.nextToken();
			setProperty(key,value,myData,c);
		}
		 return myData;
	 }
	 
	private void setProperty(String key, String value, GridLayoutData myData, Component c) {
		if ("align".equals(key)) {
			myData.setAlignment(parseAlignment(value));
		}
		if ("padding".equals(key)) {
			myData.setInsets(parseInsets(value));
		}
		if ("colspan".equals(key)) {
			myData.setColumnSpan(Integer.parseInt(value));
		}
		if ("rowspan".equals(key)) {
			myData.setRowSpan(Integer.parseInt(value));
		}
		if ("height".equals(key)) {
			int height = Integer.parseInt(value);
			GridLayoutData gd = new GridLayoutData();
//			addHeightStrut(currenty, height, current);
		}
	}

	private Alignment parseAlignment(String value) {
		System.err.println("PARSING VALUE FOR ALIGNMENT: "+value);
		StringTokenizer st = new StringTokenizer(value," ");
		String horizontalStr = st.nextToken();
		String verticalStr = st.nextToken();
		int horizontal = Alignment.DEFAULT;
		int vertical = Alignment.DEFAULT;
		if ("left".equals(horizontalStr)) {
			horizontal = Alignment.LEFT;
		}
		if ("right".equals(horizontalStr)) {
			horizontal = Alignment.RIGHT;
		}
		if ("center".equals(horizontalStr)) {
			horizontal = Alignment.CENTER;
		}
		if ("bottom".equals(verticalStr)) {
			vertical = Alignment.BOTTOM;
		}
		if ("top".equals(verticalStr)) {
			vertical = Alignment.TOP;
		}
		if ("middle".equals(verticalStr)) {
			vertical = Alignment.CENTER;
		}
		return new Alignment(horizontal,vertical);
		
	}

	private Insets parseInsets(String value) {
		StringTokenizer st = new StringTokenizer(value," ");
		int top = Integer.parseInt(st.nextToken());
		int right = Integer.parseInt(st.nextToken());
		int bottom = Integer.parseInt(st.nextToken());
		int left = Integer.parseInt(st.nextToken());
		return new Insets(left,top,right,bottom);
		
	}
	
	//
	// public void setContainerLayout(Object l){
	//
	// }

	public void setComponentValue(final String name, final Object object) {

		 if ("columnWidth".equals(name)) {
			 Object[] columns = parseColumns((String)object);
			 gridComponent.setSize(columns.length);
			 for (int i = 0; i < columns.length; i++) {
				 gridComponent.setColumnWidth(i,new Extent(((Integer)columns[i]).intValue(),Extent.PX));
			}
			 return;
		 }
		 if ("height".equals(name)) {
			 int height = ((Integer)object).intValue();
			 gridComponent.setHeight(new Extent(height,Extent.PX));
			 return;
		 }
//		 if ("border".equals(name)) {
//			 System.err.println("Parsing border!!!!!!");
//			 if (object!=null && object instanceof String) {
//				myContainer.setTitle((String)object);
//				myContainer.setBorder(new Border(1,new Color(50,50,50),Border.STYLE_GROOVE));
//				 return;
//			} 
//		 }
		super.setComponentValue(name, object);
	}

	private Object[] parseColumns(String sizes) {
		StringTokenizer st = new StringTokenizer(sizes);
		ArrayList al = new ArrayList();
		while (st.hasMoreTokens()) {
			String element = (String) st.nextElement();
			al.add(new Integer(element));
		}
		return al.toArray();
	}

}
