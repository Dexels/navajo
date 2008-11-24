package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;

import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiTab
    extends TipiSwingDataComponentImpl {
  public Object createContainer() {
	  TipiSwingTab myLabel = new TipiSwingTab();
//    TipiHelper th = new TipiSwingHelper();
//    th.initHelper(this);
//     addHelper(th);
    return myLabel;
  }

@Override
protected void setComponentValue(String name, Object object) {
	System.err.println("Setting: "+name+" to: "+object);
	super.setComponentValue(name, object);
}

  
  



 
}
