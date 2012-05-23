package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

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
public class TipiGridLayout
    extends TipiLayoutImpl {
	private static final long serialVersionUID = 6706479367509952989L;
  private EchoGridLayoutImpl echoGridLayoutImpl;
 
  public TipiGridLayout() {
  }

  public void createLayout() {
	  echoGridLayoutImpl = new EchoGridLayoutImpl();
	  echoGridLayoutImpl.setParentComponent(myComponent);
    setLayout(echoGridLayoutImpl);
  }

 
  
  public void loadLayout(XMLElement def, TipiComponent t) throws TipiException {
	super.loadLayout(def, t);
}

protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public Object parseConstraint(String text, int index) {
  
	  TipiEchoGridBagConstraints t =  new TipiEchoGridBagConstraints();
	  t.parse(text, index);
	  return t;
  }
  
  

  public void commitLayout() {
	  echoGridLayoutImpl.commitToParent();
  }
}