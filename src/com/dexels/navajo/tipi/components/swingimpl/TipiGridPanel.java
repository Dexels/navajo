package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.*;

import javax.swing.Box;
import javax.swing.JPanel;

import com.dexels.navajo.tipi.tipixml.XMLElement;

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
	private final Set availabilityMatrix = new HashSet(); 
	
	public TipiGridPanel() {
	}

	public Object createContainer() {
		gridComponent = new JPanel();
		gridComponent.setLayout(new GridBagLayout());
		return gridComponent;
	}

	 public void addToContainer(Object o, Object constraints){
		 String constr = (String)constraints;
		 Component c = (Component)o;
		 gridComponent.add(c,parseGridConstraints(constr));
	 }
	 
	  public void initBeforeBuildingChildren(XMLElement instance, XMLElement classdef) {
		  String ss = instance.getStringAttribute("columnWidth");
		  parseColumns(ss.substring(1,ss.length()-1));

	  }	 
	 
	 public GridBagConstraints parseGridConstraints(String txt) {
		 GridBagConstraints myData = new GridBagConstraints();
		 if (txt==null) {
			return myData;
		}
		 System.err.println("Parsing constraints: "+txt+" >> "+getPath()+" gridwidth: "+gridwidth);
		 StringTokenizer st = new StringTokenizer(txt,";");
		 myData.gridx = currentx;
		 myData.gridy = currenty;
		 System.err.println("Adding at: "+currentx+"/"+currenty+" grid: "+gridwidth);
		 while (st.hasMoreTokens()) {
			String next = st.nextToken();
			StringTokenizer current = new StringTokenizer(next,":");
			String key = current.nextToken();
			String value = current.nextToken();
			setProperty(key,value,myData);
		}
			System.err.println("updateAvailability to constraint: ");
		updateAvailability(myData);
		advance();
		 System.err.println("Advanced to: "+currentx+"/"+currenty);
		 System.err.println("Grid constraints: "+myData.gridx+"/"+myData.gridy+" size: "+myData.gridwidth+"/"+myData.gridheight+" I am: "+getId()+" gridwidth: "+gridwidth);
		 return myData;
	 }
	 
	 private void updateAvailability(GridBagConstraints myData) {
		 // make sure the grid is high enough:
//		 int maxy = myData.gridy + myData.gridheight;
		 // double nested loop to flip availability:
		 for (int y = 1; y <= myData.gridheight; y++) {
 			 for (int x = 0; x < myData.gridwidth; x++) {
				 boolean c = isOccupied(x,y);
				 if (c) {
					System.err.println("Oh dear, already occupied!");
				} 
//				 row[x] = true;
				 System.err.println("Occupying: ["+x+"/"+y+"]");
//				 System.err.println("=================================\n"+availabilityMatrix+"=================================");
//				 row.set(x,new Boolean(true));
				 availabilityMatrix.add(new Coordinate(x,y));
				 
 			 }
		}
		 
	}

//	private void fillRowUntil(ArrayList row, int x) {
//		while (x>=row.size()) {
//			row.add(new Boolean(false));
//		}
//	}

	private void advance() {
		System.err.println("ADVANCING. currentx: "+currentx+"/"+currenty);
		while (isOccupied(currentx,currenty)) {
			System.err.println("checking");
			if (currentx==gridwidth-1) {
				System.err.println("New row");
				currentx = 0;
				currenty++;
			} else {
				System.err.println("next column");
				currentx++;
			}
		}
	 }

	 private boolean isOccupied(int x, int y) {
			System.err.println("Checking: "+x+"/"+y);
			System.err.println("# of availability stuff: "+availabilityMatrix.size());
			for (Iterator iter = availabilityMatrix.iterator(); iter.hasNext();) {
			Coordinate element = (Coordinate) iter.next();
			if (element.equals(new Coordinate(x,y))) {
				return true;
			}
		}
			return false;
			//		 boolean c = availabilityMatrix.contains(new Coordinate(x,y));
//		 return c;
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
//			 parseColumns((String)object);

		 }

		 // Effe denken wat ik hier mee moet.
//		 if ("height".equals(name)) {
//			 int height = ((Integer)object).intValue();
//			 gridComponent.setHeight(new Extent(height,Extent.PX));
//		 }
		super.setComponentValue(name, object);
	}

	private void parseColumns(String sizes) {
		System.err.println("PARSING COLUMNS::::::::::: "+sizes);
		StringTokenizer st = new StringTokenizer(sizes);
		myWidths.clear();
		int count = 0;
		while (st.hasMoreTokens()) {
			String element = (String) st.nextElement();
			int val = new Integer(element).intValue();
			myWidths.add(new Integer(val));
			addColumnStrut(count, val);
		}
        gridwidth = myWidths.size();
	}

	private void addColumnStrut(int x, int val) {
		Component c = Box.createHorizontalStrut(val); 
		gridComponent.add(c,new GridBagConstraints(x,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));
	}

	
	class Coordinate {
		public int x;
		public int y;
		public Coordinate(int x, int y) {
			this.x = x;
			this.x = y;
		}
		public boolean equals(Object o) {
			if (o==null) {
				return false;
			}
			if (!(o instanceof Coordinate)) {
				return false;
			}
			Coordinate c = (Coordinate)o;
			return c.x == x && c.y==y;
		}
	}
}
