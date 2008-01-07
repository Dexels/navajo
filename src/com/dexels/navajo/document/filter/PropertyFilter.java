package com.dexels.navajo.document.filter;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import com.dexels.navajo.document.*;

import java.beans.*;
import java.util.*;

public final class PropertyFilter {
  private String myPropertyName;
  private Property myValue;
  private String myOperator;

  public PropertyFilter(String propName, Property value, String operator) {
    myPropertyName = propName;
    myValue = value;
    myOperator = operator;
  }

  public PropertyFilter(String propName, String value, String type, String operator) throws NavajoException {
	    myPropertyName = propName;
	    // TODO: Add support for 
	    myValue = NavajoFactory.getInstance().createProperty(null, "unknown", type, value, 0, "", Property.DIR_IN);
	    myOperator = operator;
	  }

  
  public final boolean compliesWith(Message m) throws NavajoException {

	 if ("*".equals(myPropertyName)) {
		 List<Property> props = m.getAllProperties();
		 for (Property property : props) {
			if(checkProperty(property)) {
				return true;
			}
		 }
		 return false;
	 } else {
	    Property p = m.getProperty(myPropertyName);
	    return checkProperty(p);
	}
  }

private boolean checkProperty(Property p) throws NavajoException {
	if (p == null) {
      System.err.println("Property can not be found");
      return true;
    }
    if ("==".equals(myOperator)) {
      return p.compareTo(myValue)==0;
    }
    if ("!=".equals(myOperator)) {
      return p.compareTo(myValue)!=0;
    }
    if (">".equals(myOperator)) {
      return p.compareTo(myValue)>0;
    }
    if ("<".equals(myOperator)) {
      return p.compareTo(myValue)<0;
    }
    if (">=".equals(myOperator)) {
      return p.compareTo(myValue)>=0;
    }
    if ("<=".equals(myOperator)) {
      return p.compareTo(myValue)<=0;
    }
    if ("startsWith".equals(myOperator) && "string".equals(p.getType())) {
    	if(p.getValue() != null){
        return p.getValue().toLowerCase().startsWith(((String)myValue.getTypedValue()).toLowerCase());
    	}else{
    		return false;
    	}
    }
    if ("startsWith".equals(myOperator)) {
      if(p.getType().equals(Property.SELECTION_PROPERTY)){
        return p.getSelected().getName().startsWith(myValue.getSelected().getName().toLowerCase());
      }else{
        return p.getValue().startsWith(myValue.getValue());
      }
    }
    if ("endsWith".equals(myOperator) && "string".equals(p.getType())) {
    	if(p.getValue() != null){
        return p.getValue().toLowerCase().endsWith(((String)myValue.getTypedValue()).toLowerCase());
    	}else{
    		return false;
    	}
    }
    if ("endsWith".equals(myOperator)) {
      if(p.getType().equals(Property.SELECTION_PROPERTY)){
        return p.getSelected().getName().endsWith(myValue.getSelected().getName().toLowerCase());
      }else{
        return p.getValue().endsWith(myValue.getValue());
      }
    }
    if ("contains".equals(myOperator) && "string".equals(p.getType())) {
    	if(p.getValue() != null){
        return p.getValue().toLowerCase().indexOf(((String)myValue.getTypedValue()).toLowerCase()) >= 0;
    	}else{
    		return false;
    	}
    }
    if ("contains".equals(myOperator)) {
      if(p.getType().equals(Property.SELECTION_PROPERTY)){
        String name = "";
        if(myValue.getType().equals(Property.SELECTION_PROPERTY)){
          name = myValue.getSelected().getName();
        }else{
          name = myValue.getValue();
        }
        ArrayList l = p.getAllSelections();
        for(int i=0;i<l.size();i++){
          Selection s = (Selection)l.get(i);
          if(s.getName().equals(name)){
            return true;
          }
        }
        return false;
      }else{
      	if(p.getValue() != null){
      		return p.getValue().indexOf(myValue.getValue()) >= 0;
      	}else{
      		return false;
      	}
      }
    }
    if ("regularExpression".equals(myOperator)) {
    	if(p.getValue() != null){
    		return p.getValue().matches(myValue.getValue());
    	}else{
    		return false;
    	}
    }

    return true;
}
}
