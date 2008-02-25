package com.dexels.navajo.tipi.swingclient.components.sort;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/**
 * @deprecated
 */
import com.dexels.navajo.document.*;
/**
 * @deprecated
 */
public class PropertyFilter {
  private Property myProperty;
  private String myValue;
  public PropertyFilter(Property p, String value) {
    myProperty = p;
    myValue = value;
  }

  public boolean compliesWith(Message m) {
    Property p = m.getProperty(myProperty.getName());
    if (p==null) {
      return true;
    }
    if (myProperty.getType().equals(Property.SELECTION_PROPERTY)) {
      Selection s = null;
      try {
        s = p.getSelectionByValue(myValue);
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
        return true;
      }
      if (s!=null) {
        return s.isSelected();
      } else {
        return false;
      }
    }

    return p.getValue().equals(myProperty.getValue());
  }
}
