package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
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

public class TipiGridPanel extends TipiPanel {

	private JPanel gridComponent;

	private int currentx = 0;
	private int currenty = 1;
//	private int count = 0;
	private int gridwidth = 0;
	private final ArrayList myWidths = new ArrayList();
	private final Map heightStrutComponentMap = new HashMap();
    private final Set fixedSet = new HashSet();
//	private final Map heightStrutConstraiintMap = new HashMap();
	// List of array of boolean
	private final Set availabilityMatrix = new HashSet();

private GridBagLayout gridBagLayout; 
	
	public TipiGridPanel() {
	}

	
	
	
    
	
	
	
	public Object createContainer() {
		gridComponent = (JPanel) super.createContainer();
gridBagLayout = new GridBagLayout();
		//        gridComponent = new JPanel();
		gridComponent.setLayout(gridBagLayout);
           TipiHelper th = new TipiSwingHelper();
            th.initHelper(this);
            addHelper(th);
           		return gridComponent;
	}
    
	
	 public void setContainerLayout(Object layout) {
	     // ignore
     }

    public void addToContainer(Object o, Object constraints){
		 String constr = (String)constraints;
		 Component c = (Component)o;
		 JComponent jc = null;
		 if (c instanceof JComponent) {
			 jc = (JComponent)c;
//             System.err.println("Adding component to GridPanel: "+jc+" with pref. size: "+jc.getPreferredSize());
		 } else {
		     System.err.println("Scheizze!");
         }
//         jc.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5));
//         System.err.println("|||>"+constr+"<|||");
//         jc.setVisible(true);
         //         System.err.println("GRIDPANEL::: ADD TO CONTAINER: ::: "+o);
//		 c.setBackground(new Color(t.nextFloat(),t.nextFloat(),t.nextFloat()));
         GridBagConstraints gc = parseGridConstraints(constr,jc);
//         gridComponent.add(new JLabel(">>"+o.toString()),gc);
         System.err.println("GC: "+gc);
         System.err.println("Preferred of component to add: "+c.getPreferredSize()+" isset: "+c.isPreferredSizeSet()+" component: "+c);
         gridComponent.add(c,gc);

         System.err.println("Added to container. LAyout indications: ");
//         System.err.println("Max: "+ gridBagLayout.maximumLayoutSize(gridComponent));
//         System.err.println("Min: "+ gridBagLayout.minimumLayoutSize(gridComponent));
         System.err.println("Pref:"+ gridBagLayout.preferredLayoutSize(gridComponent));
    }
    
    
    
	 
	  public void initBeforeBuildingChildren(XMLElement instance, XMLElement classdef, XMLElement def) {
		  String ss = instance.getStringAttribute("columnWidth");
		  if (ss==null && def != null) {
            ss = def.getStringAttribute("columnWidth");
        }
          if (ss!=null) {
              parseColumns(ss.substring(1,ss.length()-1));
        } else {
            System.err.println("oh dear, no columnwidth");
        }

	  }	 
	 
	 public GridBagConstraints parseGridConstraints(final String txt, JComponent component) {
		 GridBagConstraints myData = new GridBagConstraints() {
			 public String toString() {
				 return this.ipadx+" - "+ipady+" "+txt;
			 }
		 };
		 myData.fill=GridBagConstraints.BOTH;
		 myData.weightx=isFixed(currentx)?0:1;
         myData.weighty=0;
		 myData.gridx = currentx;
		 myData.anchor = GridBagConstraints.NORTHWEST;
		 myData.gridy = currenty;
		 if (txt!=null) {
			 StringTokenizer st = new StringTokenizer(txt,";");
			 while (st.hasMoreTokens()) {
				String next = st.nextToken();
				StringTokenizer current = new StringTokenizer(next,":");
				String key = current.nextToken();
				String value = current.nextToken();
				setProperty(key,value,myData, component);
			}
		}
		 myData.insets = new Insets(3,3,3,3);
		 updateComponentSize(component,myData);
		updateAvailability(myData.gridx,myData.gridy,myData.gridx+myData.gridwidth,myData.gridy+myData.gridheight);
		advance();
		if(myData.gridx==(myWidths.size()-1)) {
			myData.weightx = 1;
		}
		return myData;
	 }
	 
	 private void updateComponentSize(JComponent component, GridBagConstraints myData) {
		 if (component==null) {
			return;
		 }
		 Integer h = ((Integer)heightStrutComponentMap.get(component));
		 int height = 0;
		 if (h==null) {
			 height = 0;
		 } else {
			 height = h.intValue();
		 }
		 Insets ins = myData.insets;
		 int vertical = height;
		 vertical -= ins.top;
		 vertical -= ins.bottom;
		 int horizontal = getCurrentWidth();
		 horizontal -= ins.left;
		 horizontal -= ins.right;
         if (horizontal<=0) {
            horizontal = Integer.MAX_VALUE;
        }
         
         if (vertical<=0) {
             vertical = Integer.MAX_VALUE;
         }
        
//         System.err.println("UPDATING::::: "+horizontal+" / "+vertical);
//         System.err.println("PREF: "+component.getPreferredSize().width+" -- "+component.getPreferredSize().height);
//         System.err.println("min: "+component.getMinimumSize().width+" -- "+component.getMinimumSize().height);
//         System.err.println("SIZE: "+component.getSize().width+" -- "+component.getSize().height);
//         
         if (component!=null) {
		     // if no height set, leave the maximum size alone
             if (h!=null) {
//                    component.setMaximumSize(new Dimension(horizontal,vertical));
                    }
             if (isFixed(currentx)) {
//                 System.err.println("Harrr. Not good. Bad prefsize.");
//                component.setPreferredSize(new Dimension(horizontal,5));
            }
             component.setMaximumSize(new Dimension(horizontal,vertical));
             
//             component.setPreferredSize(new Dimension(horizontal,vertical));
             	}
		
	}

    private int getCurrentWidth() {
        return ((Integer)myWidths.get(currentx)).intValue();
    }

	private void updateAvailability(int xstart, int ystart, int xend, int yend) {
		 for (int y = ystart; y < yend; y++) {
 			 for (int x = xstart; x < xend; x++) {
				 boolean c = isOccupied(x,y);
				 if (c) {
					System.err.println("Oh dear, already occupied!");
				} 
				 availabilityMatrix.add(new Coordinate(x,y));
				 
 			 }
		} 
	}


	private void advance() {
		while (isOccupied(currentx,currenty)) {
			if (currentx>=gridwidth-1) {
				currentx = 0;
				currenty++;
			} else {
				currentx++;
			}
		}
	 }

	 private boolean isOccupied(int x, int y) {
			for (Iterator iter = availabilityMatrix.iterator(); iter.hasNext();) {
				Coordinate element = (Coordinate) iter.next();
				if (element.equals(new Coordinate(x,y))) {
					return true;
				}
			}
			return false;
	 }
	 
	private void setProperty(String key, String value, GridBagConstraints myData, JComponent current) {
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
		if ("height".equals(key)) {
		    if (value.endsWith("*")) {
                int height = Integer.parseInt(value.substring(0, value.length()-1));
                addHeightStrut(currenty, height, current);
//                current.setMaximumSize(new Dimension(current.getMaximumSize().width,height));
                myData.weighty = 1;
            } else {
                int height = Integer.parseInt(value);
                addHeightStrut(currenty, height, current);
                current.setMaximumSize(new Dimension(current.getMaximumSize().width,height));
                current.setMinimumSize(new Dimension(0,height));
            }
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
		if ("left".equals(horizontalStr)&& "middle".equals(verticalStr)) {
			anchor = GridBagConstraints.WEST;
		}
		if ("left".equals(horizontalStr)&& "bottom".equals(verticalStr)) {
			anchor = GridBagConstraints.SOUTHWEST;
		}

		if ("center".equals(horizontalStr) && "top".equals(verticalStr)) {
			anchor = GridBagConstraints.NORTH;
		}	
		if ("center".equals(horizontalStr)&& "middle".equals(verticalStr)) {
			anchor = GridBagConstraints.CENTER;
		}
		if ("center".equals(horizontalStr)&& "bottom".equals(verticalStr)) {
			anchor = GridBagConstraints.SOUTH;
		}
		
		if ("right".equals(horizontalStr) && "top".equals(verticalStr)) {
			anchor = GridBagConstraints.NORTHEAST;
		}	
		if ("right".equals(horizontalStr)&& "middle".equals(verticalStr)) {
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
		 if ("height".equals(name)) {
			 int height = ((Integer)object).intValue();
//			 gridComponent.setHeight(new Extent(height,Extent.PX));
			 gridComponent.setMinimumSize(new Dimension(0,height));
			 gridComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE,height));
			 }
//		 if ("maxheight".equals(name)) {
//			 int height = ((Integer)object).intValue();
////			 gridComponent.setHeight(new Extent(height,Extent.PX));
//			 gridComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE,height));
//		 }	
		 super.setComponentValue(name, object);
	}
	private void addHeightStrut(int y, int height, JComponent current) {
		int actualHeight = height;
		Component existingComponent = (Component)heightStrutComponentMap.get(new Integer(y));

		if (existingComponent!=null) {
//			GridBagLayout gbl = (GridBagLayout)gridComponent.getLayout();
//			GridBagConstraints gb = gbl.getConstraints(existingComponent);
			if (existingComponent.getHeight()>height) {
				actualHeight = existingComponent.getHeight();
			}
			gridComponent.remove(existingComponent);
		}
		Component c = Box.createVerticalStrut(actualHeight); 
		heightStrutComponentMap.put(new Integer(y),c);
			gridComponent.add(c,new GridBagConstraints(0,y,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0));
	}

	protected void parseColumns(String sizes) {
//		System.err.println("PARSING COLUMNS::::::::::: "+sizes);
		StringTokenizer st = new StringTokenizer(sizes);
		myWidths.clear();
		int count = 0;
		while (st.hasMoreTokens()) {
			String element = (String) st.nextElement();
            if (!element.endsWith("*")) {
                setFixed(count);
            } else {
                element = element.substring(0,element.length()-1);
//                System.err.println("Starr. reuslt: "+element);                
            }
            int val = new Integer(element).intValue();
			myWidths.add(new Integer(val));
			addColumnStrut(count, val);
			count++;
		}
        gridwidth = myWidths.size();
	}

	private void setFixed(int count) {
	    fixedSet.add(new Integer(count));
    }

    private boolean isFixed(int count) {
        return fixedSet.contains(new Integer(count));
    }

    private void addColumnStrut(int x, int val) {
 		Component c = Box.createHorizontalStrut(1); 
		gridComponent.add(c,new GridBagConstraints(x,0,1,1,0,0,GridBagConstraints.CENTER,GridBagConstraints.NONE,new Insets(0,0,0,0),val-1,0));
	}

	
	class Coordinate {
		public int x;
		public int y;
		public Coordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public boolean equals(Object o) {
			if (o==null) {
				return false;
			}
			if (!(o instanceof Coordinate)) {
				return false;
			}
			Coordinate c = (Coordinate)o;
//			System.err.println("Comparing: "+this+" and "+c);
			return c.x == x && c.y==y;
		}
		public String toString() {
			return "{"+x+","+y+"}";
		}
	}
}
