package com.dexels.navajo.tipi.impl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.swingclient.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.dexels.navajo.document.*;

public class DefaultTipiTable extends DefaultTipi {
  private String messagePath = "";

  public DefaultTipiTable() {
    setContainer(createContainer());
  }

  public Container createContainer() {
    return new MessageTablePanel();
  }

  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
  }
  public void setContainerLayout(LayoutManager layout){
    throw new UnsupportedOperationException("Can not set layout of container of class: "+getClass());
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    MessageTablePanel mm = (MessageTablePanel)getContainer();
    messagePath = (String)elm.getAttribute("messagepath");
    super.load(elm,instance,context);
    mm.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        messageTableActionPerformed(e);
      }
    });
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("column")) {
        String label = (String)child.getAttribute("label");
        String name = (String)child.getAttribute("name");
//      boolean editable = (((String)child.getAttribute("editable")).equals("true"));
        boolean editable = false;
        mm.addColumn(name,label,editable);
      }
    }
  }

  public void messageTableActionPerformed(ActionEvent ae) {
    System.err.println("AdtionPErformed!");
    System.err.println(">>>"+myEventList);
    performAllEvents(TipiEvent.TYPE_ONACTIONPERFORMED);
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {
    super.loadData(n,tc);
//    System.err.println("LOADING DATA: "+n.toXml());
    MessageTablePanel mtp = (MessageTablePanel)getContainer();
    if(messagePath != null){
      Message m = n.getByPath(messagePath);
      if (m != null) {
        mtp.setMessage(m);
      }
    }
  }

}