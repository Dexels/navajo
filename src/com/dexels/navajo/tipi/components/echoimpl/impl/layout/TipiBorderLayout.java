package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import java.awt.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiBorderLayout
    extends TipiLayoutImpl {
  private XMLElement myDefinition = null;
  private EchoBorderLayoutImpl echoBorderLayoutImpl;
 
  public TipiBorderLayout() {
  }

  public void createLayout() {
    echoBorderLayoutImpl = new EchoBorderLayoutImpl();
    echoBorderLayoutImpl.setParentComponent(myComponent);
    setLayout(echoBorderLayoutImpl);
  }

  public Object createDefaultConstraint(int index) {
    switch (index) {
      case 0:
        return BorderLayout.CENTER;
      case 1:
        return BorderLayout.NORTH;
      case 2:
        return BorderLayout.SOUTH;
      case 3:
        return BorderLayout.EAST;
      case 4:
        return BorderLayout.WEST;
      default:
        return null;
    }
  }

  
  public void loadLayout(XMLElement def, TipiComponent t) throws TipiException {
	// TODO Auto-generated method stub
	super.loadLayout(def, t);
}

protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public Object parseConstraint(String text, int index) {
	  return text;
	  
  }
  
  

  public void commitLayout() {
	  echoBorderLayoutImpl.commitToParent();
  }
}