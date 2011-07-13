package com.dexels.navajo.tipi.vaadin.layout;


import java.awt.BorderLayout;

import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiBorderLayoutOld
    extends TipiLayoutImpl {
	GridLayout layout = null;
	Label eastLabel = new Label("east");
	Label westLabel = new Label("west");
	Label northLabel = new Label("north");
	Label southLabel = new Label("south");
	Label centerLabel = new Label("center");

	public TipiBorderLayoutOld() {
		eastLabel.setWidth("0px");
		westLabel.setWidth("0px");
	}

  public void createLayout() {
	  System.err.println("CREATING BORDERLAYOUT");
	  layout =  new GridLayout(3,3);
	  
	 layout.setWidth("500px");
	 layout.setHeight("300px");
	 layout.addComponent(centerLabel,1,1,1,1);
	 centerLabel.setWidth("100%");
	 centerLabel.setHeight("100%");
	 
	 layout.addComponent(westLabel,0,1,0,1);
	 westLabel.setWidth("0px");
	 layout.addComponent(eastLabel,2,1,2,1);
	 eastLabel.setWidth("0px");
	 layout.addComponent(northLabel,0,0,2,0);
	 northLabel.setHeight("0px");
	 layout.addComponent(southLabel,0,2,2,2);
	 southLabel.setHeight("0px");
		
	 setLayout(layout);
  }

  public Object createDefaultConstraint(int index) {
    switch (index) {
      case 0:
        return BorderLayout.CENTER;
      case 1:
        return BorderLayout.NORTH+":50";
      case 2:
        return BorderLayout.SOUTH+":50";
      case 3:
        return BorderLayout.EAST+":50";
      case 4:
        return BorderLayout.WEST+":50";
      default:
        return null;
    }
  }

  protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public Object parseConstraint(String text, int index) {

	  return text;
//	  if (text == null) {
//      return null;
//    }
//	  int ind = text.indexOf(":");
//    if(ind!=-1) {
//    	text = text.substring(0,ind);
//    }
//	  if (text.equals("center") || text.equals(BorderLayout.CENTER)) {
//      return BorderLayout.CENTER;
//    }
//    if (text.equals("north") || text.equals(BorderLayout.NORTH)) {
//      return BorderLayout.NORTH;
//    }
//    if (text.equals("south") || text.equals(BorderLayout.SOUTH)) {
//      return BorderLayout.SOUTH;
//    }
//    if (text.equals("east") || text.equals(BorderLayout.EAST)) {
//      return BorderLayout.EAST;
//    }
//    if (text.equals("west") || text.equals(BorderLayout.WEST)) {
//      return BorderLayout.WEST;
//    }
//    return BorderLayout.CENTER;
  }
  
	public void addToLayout(Object component, Object constraints) {
		System.err.println("Adding to layout with constraints: "+constraints);
		int index = layout.getComponentCount();
		Component cc = (Component)component;
		String constr = (String) constraints;
		if(constr==null) {
			constr = (String)createDefaultConstraint(index);
		}
		String text;
		String sizetext = null;
		  int ind = constr.indexOf(":");
//		  int size = -1;
		    if(ind!=-1) {
		    	text = constr.substring(0,ind);
		    	sizetext = constr.substring(ind+1,constr.length())+"px";
//		    	size = Integer.parseInt(sizetext);
		    	
		    } else {

		    	text = constr;
		    }
		text = text.toLowerCase();
		System.err.println("RESOLVED TEXT: "+text+" size: "+sizetext);
		if("north".equals(text)) {
			layout.removeComponent(northLabel);
			layout.addComponent(cc,0,0,2,0);
			cc.setHeight(sizetext);
			cc.setWidth("100%");
		}
		if("center".equals(text)) {
			layout.removeComponent(centerLabel);
			layout.addComponent(cc,1,1,1,1);
			cc.setHeight("100%");
			cc.setWidth("100%");
		}
		if("west".equals(text)) {
			System.err.println("WESTSIZE: "+sizetext);
			layout.removeComponent(westLabel);
			layout.addComponent(cc,0,1,0,1);
			cc.setHeight("100%");
			cc.setWidth(sizetext);
		}
		if("east".equals(text)) {
			layout.removeComponent(eastLabel);
			layout.addComponent(cc,2,1,2,1);
			cc.setHeight("100%");
			cc.setWidth(sizetext);
		}
		if("south".equals(text)) {
			layout.removeComponent(southLabel);
			layout.addComponent(cc,0,2,2,2);
			cc.setHeight(sizetext);
			cc.setWidth("100%");
		}
	
	}
		
}