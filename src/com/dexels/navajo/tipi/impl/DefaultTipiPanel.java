package com.dexels.navajo.tipi.impl;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import com.dexels.navajo.tipi.components.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiPanel extends DefaultTipi {

  public Container createContainer() {
    return new TipiSwingPanel(this);
  }

  public void addToContainer(Component c, Object constraints) {
    getContainer().add(c,constraints);
  }
  public void removeFromContainer(Component c) {
    getContainer().remove(c);
  }

  public DefaultTipiPanel() {
    initContainer();
  }

//  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
//    super.load(definition,instance,context);
//  }

  public void setComponentValue(String name, Object value){
    if("enabled".equals(name)){
      //System.err.println("=======================>> Woei! setting panel enabled");
      getContainer().setEnabled(value.equals("true"));
    }
    super.setComponentValue(name,value);
  }
}