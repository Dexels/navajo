package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import java.util.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiMessageStore extends DefaultTipi implements Tipi{

 private Object myObject;
 private Navajo myNavajo;

 public Container createContainer() {
   return null;
 }

 public void addToContainer(Component c, Object constraints) {
   throw new RuntimeException("Error cannot add a component to a messagestore tipi");
 }

 public DefaultTipiMessageStore() {
   System.err.println("Instantiating DefaultTipiMessageStore");
 }

 public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
   super.load(definition,instance,context);
   System.err.println("Loaded messagestore: " + getName());
 }

  public void setComponentValue(String name, Object object) {
    System.err.println("Setting value for store: " + object);
    myObject = object;
  }

  public Object getComponentValue(String name) {
    return myObject;
  }


}