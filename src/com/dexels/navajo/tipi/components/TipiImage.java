package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.*;
import nanoxml.*;
import java.awt.*;
import javax.swing.*;
import java.net.URL;
import java.util.*;
import tipi.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiImage extends TipiComponent {

  public TipiImage() {
    setContainer(createContainer());
  }

  public Container createContainer() {
    return new JLabel();
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
    String url = (String) e.getAttribute("url", null);
    setImage(url);
  }

  public void setImage(String img) {
    System.err.println("----------> Setting image: " + img);
    if(img != null){
      ImageIcon i = new ImageIcon(MainApplication.class.getResource(img));
      System.err.println("----------> Setting icon!");
      ((JLabel)getContainer()).setIcon(i);
      //((JLabel)getContainer()).setText("image: " + img);
    }

  }


}