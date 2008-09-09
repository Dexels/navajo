package com.dexels.navajo.tipi.components.echoimpl.impl.layout;

import java.awt.*;

import nextapp.echo2.app.layout.GridLayoutData;

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
public class TipiVerticalBoxLayout
    extends TipiLayoutImpl {
  private XMLElement myDefinition = null;
  private EchoVerticalBoxLayoutImpl echoVerticalBx;
 
  public TipiVerticalBoxLayout() {
  }

  public void createLayout() {
	  echoVerticalBx = new EchoVerticalBoxLayoutImpl();
	  echoVerticalBx.setParentComponent(myComponent);
    setLayout(echoVerticalBx);
  }

 
  
  public void loadLayout(XMLElement def, TipiComponent t) throws TipiException {
	// TODO Auto-generated method stub
	super.loadLayout(def, t);
}

protected void setValue(String name, TipiValue tv) {
    throw new UnsupportedOperationException("Not implemented.");
  }

  public Object parseConstraint(String text, int index) {
	  return null;
//	  TipiEchoGridBagConstraints t =  new TipiEchoGridBagConstraints();
//	  t.parse(text, index);
//	  return t;
  }
  
  

  public void commitLayout() {
	  echoVerticalBx.commitToParent();
  }
}