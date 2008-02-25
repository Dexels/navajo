package com.dexels.navajo.tipi.swingclient.components;

import com.dexels.navajo.document.*;
import java.awt.*;
import java.util.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class PropertyHiddenField extends PropertyPasswordField {

  public PropertyHiddenField() {
    try {
		if (System.getProperty("com.dexels.navajo.propertyMap") != null) {
		  localResource = ResourceBundle.getBundle(System.getProperty("com.dexels.navajo.propertyMap"));
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
//		e.printStackTrace();
	}

  }

  public void setProperty(Property p) {
    if (p == null) {
      //System.err.println("Setting to null property. Ignoring");
      return;
    }

    initProperty = p;

    // Trim the value

    setText("xxxxx");
    setEnabled(false);
    setEditable(false);
    setBackground(SystemColor.control);

    try {
      if (localResource != null) {
        toolTipText = localResource.getString(p.getName());
      }
      setToolTipText(toolTipText);
    }
    catch (MissingResourceException e) {
      if ( (toolTipText = p.getDescription()) != null) {
        setToolTipText(toolTipText);
      }
      else {
        toolTipText = p.getName();
        setToolTipText(toolTipText);
      }

    }
    setChanged(false);
  }

}
