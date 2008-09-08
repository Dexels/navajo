package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

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
public class TipiGridLayout
    extends TipiLayoutImpl {
  private XMLElement myDefinition = null;
  private EchoGridLayoutImpl echoGridLayoutImpl;
 
  public TipiGridLayout() {
  }

  public void createLayout() {
	  echoGridLayoutImpl = new EchoGridLayoutImpl();
	  echoGridLayoutImpl.setParentComponent(myComponent);
    setLayout(echoGridLayoutImpl);
  }

 
  
  public void loadLayout(XMLElement def, TipiComponent t) throws TipiException {
	// TODO Auto-generated method stub
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