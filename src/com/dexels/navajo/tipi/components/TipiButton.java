package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import java.awt.*;
import java.util.*;
import com.dexels.navajo.document.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiButton extends TipiComponent {


  private TipiEvent myEvent =  null;
//  private TipiContainer myParent = null;
  private Navajo myNavajo = null;
  private TipiContext myContext = null;
  private Tipi myTipi = null;
  private JButton myButton;

  public TipiButton() {
    setContainer(createContainer());
  }

  public Container getContainer() {
    return myButton;
  }

  public Container createContainer() {
    myButton = new JButton();
    return myButton;
  }

//  public void setValue(String s) {
//    myButton.setText(s);
//    System.err.println("SETTING VALUE OF BUTTON: "+s);
//  }

  public void setTipi(Tipi t) {
    myTipi = t;
  }


  public void setComponentValue(String name, Object object) {
    myButton.setText((String)object);
  }

  public Object getComponentValue(String name) {
    return myButton.getText();
  }

  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
   }
   public void setContainerLayout(LayoutManager layout){
     throw new UnsupportedOperationException("Can not set layout of container of class: "+getClass());
   }


  public void load(XMLElement e, XMLElement instance, TipiContext tc) throws TipiException {
    super.load(e,instance,tc);
    myContext = tc;
    if (e==null) {
      myButton.setText((String)instance.getAttribute("value"));
      return;
    }

    Navajo n;
    if (myTipi!=null) {
      n = myTipi.getNavajo();
    } else {
     n = new Navajo();
    }
/** @todo Replace this one with a generic solution */
    myButton.setText((String)e.getAttribute("value"));

  }
}