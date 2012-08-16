package com.dexels.navajo.tipi.vaadin.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.dexels.navajo.tipi.vaadin.components.grid.GridLayoutData;
import com.dexels.navajo.tipi.vaadin.components.grid.Insets;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.GridLayout;


public class TipiGridPanel extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 2024561392168539561L;
	private GridLayout gridLayout;
	private final List<Integer> myWidths = new ArrayList<Integer>();
	private int gridwidth = 0;
    private int currentx = 0;

    private int currenty = 0;
    private final Set<Coordinate> availabilityMatrix = new HashSet<Coordinate>();

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiGridPanel.class);
	
	@Override
	public Object createContainer() {
		gridLayout = new GridLayout();
		return gridLayout;
	}

	



	@Override
	protected void addToVaadinContainer(ComponentContainer currentContainer,Component component, Object constraints) {
		GridLayoutData myData = parseGridConstraints((String)constraints);
		if(myData==null) {
			myData = new GridLayoutData();
			myData.setColumnSpan(1);
			myData.setRowSpan(1);
//			super.addToVaadinContainer(currentContainer, component, constraints);
//	        updateAvailability(currentx, currenty, currentx +1, currenty + 1);
//
//			advance();
//			return;
		} 
        updateAvailability(currentx, currenty, currentx + myData.getColumnSpan(), currenty + myData.getRowSpan());
		int endcolumn = myData.getColumnSpan()-1+currentx;
		int endrow = myData.getRowSpan()-1+currenty;
		if(endrow>=gridLayout.getRows()) {
			gridLayout.setRows(endrow+1);
		}
		gridLayout.addComponent(component, currentx, currenty,endcolumn,endrow);
		gridLayout.setComponentAlignment(component, myData.getAlignment());
		int currentWidth = myWidths.get(currentx);
		logger.debug("Adding component: "+currentx+" :: "+currenty+" >> "+myWidths.get(currentx));
		component.setWidth(currentWidth,Sizeable.UNITS_PIXELS);
        advance();
	}





	public void initBeforeBuildingChildren(XMLElement instance, XMLElement classdef, XMLElement def) {
		  String ss = instance.getStringAttribute("columnWidth");
		  if (ss==null && def != null) {
            ss = def.getStringAttribute("columnWidth");
        }
        if (ss!=null) {
              parseColumns(ss.substring(1,ss.length()-1));
        } else {
            logger.debug("oh dear, no columnwidth");
        }
         gridLayout.setColumns(gridwidth);
	  }	 
	  
	  
	   public GridLayoutData parseGridConstraints(String txt) {
		   if(txt==null) {
			   return null;
		   }
	        StringTokenizer st = new StringTokenizer(txt, ";");
	        GridLayoutData myData = new GridLayoutData();
	        try {
				while (st.hasMoreTokens()) {
				    String next = st.nextToken();
				    StringTokenizer current = new StringTokenizer(next, ":");
				    String key = current.nextToken();
				    String value = current.nextToken();
				    setProperty(key, value, myData);
				}
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("Error parsing constraint for child of: "+getPath()+" Parsing: "+txt);
			} 

	        return myData;
	    }

	  
		protected void parseColumns(String sizes) {
			StringTokenizer st = new StringTokenizer(sizes);
			myWidths.clear();
//			int count = 0;
			while (st.hasMoreTokens()) {
				String element = (String) st.nextElement();
	            if (!element.endsWith("*")) {
//	                setFixed(count);
	            } else {
	                element = element.substring(0,element.length()-1);
	            }
	            int val = new Integer(element).intValue();
				myWidths.add(new Integer(val));
//				addColumnStrut(count, val);
//				count++;
			}
	        gridwidth = myWidths.size();
		}

		   private void setProperty(String key, String value, GridLayoutData myData) {
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
//		            int height = 1;
//		            if (value.endsWith("*")) {
//		                value = value.substring(0, value.length() - 1);
//		            }
//		            // Not used anyway for now
//		            height = Integer.parseInt(value);
//		            if (c instanceof Sizeable) {
//						Sizeable s = (Sizeable)c;
//						s.setHeight(new Extent(height,Extent.PX));
//					}
//		            setRowHeight(currenty, new Extent(height, Extent.PX));
		            // GridLayoutData gd = new GridLayoutData();
		            // addHeightStrut(myD, height, c);

		        }
		    }


		    private Insets parseInsets(String value) {
		        StringTokenizer st = new StringTokenizer(value, " ");
		        int top = Integer.parseInt(st.nextToken());
		        int right = Integer.parseInt(st.nextToken());
		        int bottom = Integer.parseInt(st.nextToken());
		        int left = Integer.parseInt(st.nextToken());
		        return new Insets(left, top, right, bottom);

		    }
		
		    
		    private Alignment parseAlignment(String value) {
		        StringTokenizer st = new StringTokenizer(value, " ");
		        String horizontalStr = st.nextToken();
		        String verticalStr = st.nextToken();
		        if ("left".equals(horizontalStr)) {
			        if ("bottom".equals(verticalStr)) {
			        	return Alignment.BOTTOM_LEFT;
			        }
			        if ("top".equals(verticalStr)) {
			        	return Alignment.TOP_LEFT;
			        }
			        if ("middle".equals(verticalStr)) {
			        	return Alignment.MIDDLE_LEFT;
			        }

		        }
		        if ("right".equals(horizontalStr)) {
			        if ("bottom".equals(verticalStr)) {
			        	return Alignment.BOTTOM_RIGHT;
			        }
			        if ("top".equals(verticalStr)) {
			        	return Alignment.TOP_RIGHT;
			        }
			        if ("middle".equals(verticalStr)) {
			        	return Alignment.MIDDLE_RIGHT;
			        }
		        }
		        if ("center".equals(horizontalStr)) {
			        if ("bottom".equals(verticalStr)) {
			        	return Alignment.BOTTOM_CENTER;
			        }
			        if ("top".equals(verticalStr)) {
			        	return Alignment.TOP_CENTER;
			        }
			        if ("middle".equals(verticalStr)) {
			        	return Alignment.MIDDLE_CENTER;
			        }
		        }
		        
		        //return new Alignment(horizontal, vertical);
	        	return Alignment.MIDDLE_CENTER;
		        
		    }
		    
	    private void updateAvailability(int xstart, int ystart, int xend, int yend) {
	        for (int y = ystart; y < yend; y++) {
	            for (int x = xstart; x < xend; x++) {
	                boolean c = isOccupied(x, y);
	                if (c) {
	                    logger.debug("Oh dear, already occupied!");
	                }
	                availabilityMatrix.add(new Coordinate(x, y));

	            }
	        }
	    }

	    private void advance() {
	        while (isOccupied(currentx, currenty)) {
	            if (currentx >= gridwidth - 1) {
	                currentx = 0;
	                currenty++;
	            } else {
	                currentx++;
	            }
	        }
	    }

	    private boolean isOccupied(int x, int y) {
	        for (Iterator<Coordinate> iter = availabilityMatrix.iterator(); iter.hasNext();) {
	            Coordinate element = iter.next();
	            if (element.equals(new Coordinate(x, y))) {
	                return true;
	            }
	        }
	        return false;
	    }

	    class Coordinate implements Serializable {
	        
	    	private static final long serialVersionUID = -1568182756938385054L;

			public int x;

	        public int y;

	        public Coordinate(int x, int y) {
	            this.x = x;
	            this.y = y;
	        }

	        public boolean equals(Object o) {
	            if (o == null) {
	                return false;
	            }
	            if (!(o instanceof Coordinate)) {
	                return false;
	            }
	            Coordinate c = (Coordinate) o;
	            return c.x == x && c.y == y;
	        }

	        public String toString() {
	            return "{" + x + "," + y + "}";
	        }

			@Override
			public int hashCode() {
				return y<<16 + x;
			}
	        
	        
	    }

}
