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
import com.dexels.navajo.nanodocument.*;
import javax.swing.event.*;

public class DefaultTipiTable extends DefaultTipi {
  private String messagePath = "";
  private MessageTablePanel mm;

  public DefaultTipiTable() {
    initContainer();
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
    mm = (MessageTablePanel)getContainer();
    messagePath = (String)elm.getAttribute("messagepath");
    super.load(elm,instance,context);
    mm.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        messageTableActionPerformed(e);
      }
    });
    mm.addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent e){
          messageTableSelectionChanged(e);
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


  public void messageTableSelectionChanged(ListSelectionEvent e){
    try{
      performAllEvents(TipiEvent.TYPE_SELECTIONCHANGED, e);
    }catch(TipiException ex){
      ex.printStackTrace();
    }
  }

  public void messageTableActionPerformed(ActionEvent ae) {
    try {
      performAllEvents(TipiEvent.TYPE_ONACTIONPERFORMED,ae);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {
    super.loadData(n,tc);
//    System.err.println("LOADING DATA: "+n.toXml());
    MessageTablePanel mtp = (MessageTablePanel)getContainer();
    if(messagePath != null && n != null){
      Message m = n.getByPath(messagePath);
      if (m != null) {
        mtp.setMessage(m);
      }
    }
  }

  public void setComponentValue(String name, Object object) {
    System.err.println("Set component value not (yet) implemented!");
  }

  public Object getComponentValue(String name) {
    if(name.equals("selectedMessage")){
       return mm.getSelectedMessage();
    }else if(name.equals("selectedIndex")){
      return String.valueOf(mm.getSelectedMessage().getIndex());
    }else{
      return null;
    }
  }

}