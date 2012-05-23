package com.dexels.navajo.tipi.vaadin.layout;


import java.util.HashMap;
import java.util.Map;

import org.vaadin.addon.borderlayout.BorderLayout;
import org.vaadin.addon.borderlayout.BorderLayout.Constraint;

import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.vaadin.ui.Component;

/**
 * 
 * @author frank
 *
 */
public class TipiBorderLayout
    extends TipiLayoutImpl {
	private static final long serialVersionUID = 157789680391171301L;

	BorderLayout layout = null;
	
	private final Map<BorderLayout.Constraint,Integer> sizeMap= new HashMap<BorderLayout.Constraint, Integer>();
  public TipiBorderLayout() {
  }

  public void createLayout() {
	 layout =  new BorderLayout();
	 layout.setSizeFull();
	  setLayout(layout);
  }

  protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }
  
  
  public Constraint createDefaultConstraint(int index) {
//	  Thread.dumpStack();
	  switch (index) {
	      case 0:
	        return BorderLayout.Constraint.CENTER;
	      case 1:
	        return BorderLayout.Constraint.NORTH;
	      case 2:
	        return BorderLayout.Constraint.SOUTH;
	      case 3:
	        return BorderLayout.Constraint.EAST;
	      case 4:
	        return BorderLayout.Constraint.WEST;
	      default:
	        return null;
	    }
	  }
  
  
  
  @Override

  protected Object parseConstraint(String text, int index) {

//	  return text;
	  if (text == null) {
      return null;
    }
	  int ind = text.indexOf(":");
    String sizeText = null;
    int sz=-1;
    if(ind!=-1) {
    	sizeText = text.substring(ind+1,text.length());
    	text = text.substring(0,ind);
    	sz = Integer.parseInt(sizeText);
    }
    text = text.toLowerCase();
	  if (text.equals("center") ) {
      return BorderLayout.Constraint.CENTER;
    }
    if (text.equals("north") || text.equals(BorderLayout.Constraint.NORTH)) {
    	if(sz>=0) {
    		sizeMap.put(BorderLayout.Constraint.NORTH,sz);
    	}
    	return BorderLayout.Constraint.NORTH;
    }
    if (text.equals("south") || text.equals(BorderLayout.Constraint.SOUTH)) {
    	if(sz>=0) {
    		sizeMap.put(BorderLayout.Constraint.SOUTH,sz);
    	}
      return BorderLayout.Constraint.SOUTH;
    }
    if (text.equals("east") || text.equals(BorderLayout.Constraint.EAST)) {
    	if(sz>=0) {
    		sizeMap.put(BorderLayout.Constraint.EAST,sz);
    	}
      return BorderLayout.Constraint.EAST;
    }
    if (text.equals("west") || text.equals(BorderLayout.Constraint.WEST)) {
    	if(sz>=0) {
    		sizeMap.put(BorderLayout.Constraint.WEST,sz);
    	}
      return BorderLayout.Constraint.WEST;
    }
    return BorderLayout.Constraint.CENTER;
  }
  
public void addToLayout(Object component, Object constraints) {
		if(constraints instanceof Constraint) {
			BorderLayout.Constraint bc = (Constraint) constraints;
			Integer size = sizeMap.get(constraints);
			Component c = (Component)component;
			if(size!=null) {
				if (constraints==Constraint.WEST || constraints==Constraint.EAST) {
					c.setWidth(""+size+"px");
				} else {
					c.setHeight(""+size+"px");
				}
			}
			if(bc==Constraint.CENTER) {
				c.setSizeFull();
			}
			layout.addComponent((Component) component,bc);
		} else {
			// TODO: test, I have no clue if this is correct
			Constraint bc = (Constraint) parseConstraint((String)constraints, sizeMap.size());
			addToLayout(component, bc);
		}

	}
}