package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.*;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiLabel extends TipiComponent {
  public TipiLabel() {
    setContainer(createContainer());
  }

  public Container createContainer() {
    return new JLabel();
  }
  public void setValue(String s) {
    ((JLabel)getContainer()).setText(s);
  }

  public void addTipiEvent(TipiEvent te) {
  }

  public void addComponent(TipiComponent c, TipiContext context, Map props) {
  }
  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
   }
   public void setContainerLayout(LayoutManager layout){
     throw new UnsupportedOperationException("Can not set layout of container of class: "+getClass());
   }

  public void load(XMLElement e, XMLElement instance, TipiContext tc) {
    ((JLabel)getContainer()).setText((String)instance.getAttribute("value"));
  }

  public void setText(String s) {
    ((JLabel)getContainer()).setText(s);
  }
}