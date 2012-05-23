package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import java.awt.BorderLayout;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiValue;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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
	private static final long serialVersionUID = -3861071282137448373L;
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