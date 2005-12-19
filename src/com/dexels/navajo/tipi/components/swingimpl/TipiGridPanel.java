package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.Box;
import javax.swing.JPanel;

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

public class TipiGridPanel extends TipiSwingDataComponentImpl {

	private JPanel gridComponent;

	private int currentx = 0;
	private int currenty = 1;
	private int gridwidth = 0;
	private final ArrayList myWidths = new ArrayList();
	
	// List of array of boolean
	private final ArrayList availabilityMatrix = new ArrayList(); 
	
	public TipiGridPanel() {
	}

	public Object createContainer() {
		gridComponent = new JPanel();
		return gridComponent;
	}

	 public void addToContainer(Object o, Object constraints){
		 String constr = (String)constraints;
		 Component c = (Component)o;
		 gridComponent.add(c,parseGridConstraints(constr));
	 }
	 
	 public GridBagConstraints parseGridConstraints(String txt) {
		 StringTokenizer st = new StringTokenizer(txt,";");
		 GridBagConstraints myData = new GridBagConstraints();
		 myData.gridx = currentx;
		 myData.gridy = currenty;
		 
		 while (st.hasMoreTokens()) {
			String next = st.nextToken();
			StringTokenizer current = new StringTokenizer(next,":");
			String key = current.nextToken();
			String value = current.nextToken();
			setProperty(key,value,myData);
		}
		updateAvailability(myData);
		advance();
		 return myData;
	 }
	 
	 private void updateAvailability(GridBagConstraints myData) {
		 // make sure the grid is high enough:
		 int maxy = myData.gridy + myData.gridheight;
		 while (maxy>=availabilityMatrix.size()) {
			availabilityMatrix.add(new boolean[gridwidth]);
		 }
		 // double nested loop to flip availability:
		 for (int y = 0; y < myData.gridheight; y++) {
			 Boolean[] row = (Boolean[])availabilityMatrix.get(y+myData.gridy);
			 for (int x = 0; x < myData.gridwidth; x++) {
				 if (row[x].booleanValue()) {
					System.err.println("Oh dear, already occupied!");
				} 
				 row[x] = new Boolean(true);
			 }
		}
		 
	}

	private void advance() {
		while (isOccupied(currentx,currenty)) {
			if (currentx==gridwidth-1) {
				currentx = 0;
				currenty++;
			} else {
				currentx++;
			}
		}
	 }

	 private boolean isOccupied(int x, int y) {
		 Boolean[] row = (Boolean[])availabilityMatrix.get(y);
		 return row[x].booleanValue();
	 }
	 
	private void setProperty(String key, String value, GridBagConstraints myData) {
		if ("align".equals(key)) {
			myData.anchor = parseAlignment(value,myData);
		}
		if ("padding".equals(key)) {
			myData.insets = parseInsets(value);
		}
		if ("colspan".equals(key)) {
			myData.gridwidth = Integer.parseInt(value);
		}
		if ("rowspan".equals(key)) {
			myData.gridheight = Integer.parseInt(value);
		}
		
	}

	private int parseAlignment(String value,GridBagConstraints myData) {
		StringTokenizer st = new StringTokenizer(value," ");
		String horizontalStr = st.nextToken();
		String verticalStr = st.nextToken();
//		int horizontal = Alignment.DEFAULT;
//		int vertical = Alignment.DEFAULT;
		int anchor = myData.anchor;
		if ("left".equals(horizontalStr) && "top".equals(verticalStr)) {
			anchor = GridBagConstraints.NORTHWEST;
		}	
		if ("left".equals(horizontalStr)&& "center".equals(verticalStr)) {
			anchor = GridBagConstraints.WEST;
		}
		if ("left".equals(horizontalStr)&& "bottom".equals(verticalStr)) {
			anchor = GridBagConstraints.SOUTHWEST;
		}

		if ("center".equals(horizontalStr) && "top".equals(verticalStr)) {
			anchor = GridBagConstraints.NORTH;
		}	
		if ("center".equals(horizontalStr)&& "center".equals(verticalStr)) {
			anchor = GridBagConstraints.CENTER;
		}
		if ("center".equals(horizontalStr)&& "bottom".equals(verticalStr)) {
			anchor = GridBagConstraints.SOUTH;
		}
		
		if ("right".equals(horizontalStr) && "top".equals(verticalStr)) {
			anchor = GridBagConstraints.NORTHEAST;
		}	
		if ("right".equals(horizontalStr)&& "center".equals(verticalStr)) {
			anchor = GridBagConstraints.EAST;
		}
		if ("right".equals(horizontalStr)&& "bottom".equals(verticalStr)) {
			anchor = GridBagConstraints.SOUTHEAST;
		}		
		return anchor;
	}

	private Insets parseInsets(String value) {
		StringTokenizer st = new StringTokenizer(value," ");
		int top = Integer.parseInt(st.nextToken());
		int right = Integer.parseInt(st.nextToken());
		int bottom = Integer.parseInt(st.nextToken());
		int left = Integer.parseInt(st.nextToken());
//		return new Insets(left,top,right,bottom);
		return new Insets(top,left,bottom,right);
		
	}
	
	//
	// public void setContainerLayout(Object l){
	//
	// }

	public void setComponentValue(final String name, final Object object) {

		 if ("columnWidth".equals(name)) {
			 parseColumns((String)object);

		 }

		 // Effe denken wat ik hier mee moet.
//		 if ("height".equals(name)) {
//			 int height = ((Integer)object).intValue();
//			 gridComponent.setHeight(new Extent(height,Extent.PX));
//		 }
		super.setComponentValue(name, object);
	}

	private void parseColumns(String sizes) {
		StringTokenizer st = new StringTokenizer(sizes);
		myWidths.clear();
		int count = 0;
		while (st.hasMoreTokens()) {
			String element = (String) st.nextElement();
			int val = new Integer(element).intValue();
			myWidths.add(new Integer(val));
			addColumnStrut(count, val);
		}
	}

	private void addColumnStrut(int x, int val) {
		Component c = Box.createHorizontalStrut(val); 
		gridComponent.add(c,new GridBagConstraints(x,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));
	}

}
