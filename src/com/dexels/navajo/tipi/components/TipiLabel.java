package com.dexels.navajo.tipi.components;

import com.dexels.navajo.swingclient.components.*;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.net.*;
import tipi.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiLabel extends SwingTipiComponent {
  private JLabel myLabel = null;
  public TipiLabel() {
    initContainer();
  }

  public Container createContainer() {
    myLabel = new JLabel();
    return myLabel;
  }

  public void addTipiEvent(TipiEvent te) {
  }

  public void addComponent(TipiComponent c, TipiContext context, Map props) {
  }
  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
   }
//  public void load(XMLElement e, XMLElement instance, TipiContext tc) {
//    ((JLabel)getContainer()).setText((String)instance.getAttribute("value"));
//  }

 //  public void setText(String s) {
//    ((JLabel)getContainer()).setText(s);
//  }
 public void setComponentValue(String name, Object object) {
   super.setComponentValue(name,object);
    if (name.equals("text")) {
      myLabel.setText((String)object);
    }
    if (name.equals("icon")) {
      setImage( (String) object);
      myLabel.revalidate();
    }

  }
  public void setImage(String img) {
    System.err.println("----------> Setting image: " + img);
    if (img != null) {
      ImageIcon i;
      try {
        URL iu = new URL(img);
        i = new ImageIcon(iu);
      }
      catch (Exception e) {
        i = new ImageIcon(MainApplication.class.getResource(img));
      }
      if (i != null) {
        System.err.println("----------> Setting icon!");
        myLabel.setIcon(i);
      }
    }

  }

}