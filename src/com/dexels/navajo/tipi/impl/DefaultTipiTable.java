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
import com.dexels.navajo.tipi.tipixml.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.dexels.navajo.document.*;
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
    if (e.getValueIsAdjusting()) {
      return;
    }

    System.err.println("Table selection changed!");
    try{
      performAllEvents(TipiEvent.TYPE_SELECTIONCHANGED, e);
    }catch(TipiException ex){
      ex.printStackTrace();
    }
  }

  public void messageTableActionPerformed(ActionEvent ae) {
    System.err.println("Actionperformed!!!! (TipiTable)");
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
      Message m = n.getMessage(messagePath);
      if (m != null) {
        mtp.setMessage(m);
      }
    }
  }

  public void setComponentValue(String name, Object object) {
    System.err.println("SETTING VALUE OF TABLE: "+name+" "+object.toString());
    if (name.equals("filtersVisible")) {
      setFiltersVisible(Boolean.valueOf(object.toString()).booleanValue());
    }

  }

  public void setFiltersVisible(boolean b) {
    MessageTablePanel mtp = (MessageTablePanel)getContainer();
    mtp.setFiltersVisible(b);
  }
  public Object getComponentValue(String name) {
    System.err.println("Request for: " + name);
    if(name.equals("selectedMessage")){
       return mm.getSelectedMessage();
    }else if(name.equals("selectedIndex")){
      return String.valueOf(mm.getSelectedMessage().getIndex());
    }else{
      return super.getComponentValue(name);
    }
  }

}