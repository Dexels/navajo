package com.dexels.navajo.tipi.impl;


import com.dexels.navajo.tipi.tipixml.*;
import java.awt.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.swingclient.*;
import com.dexels.navajo.swingclient.components.*;
import javax.swing.event.*;
import com.dexels.navajo.document.*;
import java.awt.event.*;
import tipi.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.tipi.components.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AdvancedTipiTable extends DefaultTipi {
  AdvancedMessageTablePanel amt;
  private String initMessagePath, dataMessagePath, initMethod, newDataPath, requiredMessagePath;
  private Map columnAttributes = new HashMap();

  public AdvancedTipiTable() {
  }
  public Container createContainer() {
    MessageTablePanel amt = new MessageTablePanel();
    // Don't register actionPerformed, that is done elsewhere.
    amt.addListSelectionListener(new ListSelectionListener(){
    public void valueChanged(ListSelectionEvent e){
      messageTableSelectionChanged(e);
    }
    });
    return amt;
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
  amt = (AdvancedMessageTablePanel)getContainer();
  TipiColumnAttributeParser cap = new TipiColumnAttributeParser();
  initMessagePath = (String)elm.getAttribute("initmessagepath");
  dataMessagePath = (String)elm.getAttribute("datamessagepath");
  initMethod = (String)elm.getAttribute("initmethod");
  newDataPath = (String)elm.getAttribute("newdatapath");
  requiredMessagePath = (String)elm.getAttribute("requiredmessagepath");
  super.load(elm,instance,context);
  Vector children = elm.getChildren();
  for (int i = 0; i < children.size(); i++) {
    XMLElement child = (XMLElement) children.elementAt(i);
    if (child.getName().equals("column")) {
      String label = (String)child.getAttribute("label");
      String name = (String)child.getAttribute("name");
      String editableString = (String)child.getAttribute("editable");
      boolean editable = "true".equals(editableString);
      amt.addColumn(name,label,editable);
      amt.messageChanged();
    }
    if(child.getName().equals("column-attribute")){
      String name = (String) child.getAttribute("name");
      String type= (String) child.getAttribute("type");
      if(name != null && type != null && !name.equals("") && !type.equals("")){
        columnAttributes.put(name, cap.parseAttribute(child));
      }
    }
  }
  amt.setColumnAttributes(columnAttributes);
}


public void messageTableSelectionChanged(ListSelectionEvent e){
  if (e.getValueIsAdjusting()) {
    return;
  }

  //System.err.println("Table selection changed!");
  try{
    performTipiEvent("onSelectionChanged", e);
  }catch(TipiException ex){
    ex.printStackTrace();
  }
}

public void messageTableActionPerformed(ActionEvent ae) {
  try {
    performTipiEvent("onActionPerformed",ae);
  }
  catch (TipiException ex) {
    ex.printStackTrace();
  }
}

public void loadData(Navajo n, TipiContext tc) throws TipiException {
  super.loadData(n,tc);
  AdvancedMessageTablePanel mtp = (AdvancedMessageTablePanel)getContainer();
  if(initMessagePath != null && n != null){
    Message m = n.getMessage(initMessagePath);
    if (m != null) {
      if(initMethod != null && dataMessagePath != null && newDataPath != null && requiredMessagePath != null){
        mtp.setService(m, initMethod, dataMessagePath, newDataPath, requiredMessagePath);
        mtp.loadPanelData();
      }else{
        System.err.println("Whoops!!");
        System.err.println("InitMethod: " + initMethod);
        System.err.println("DataMP    : " + dataMessagePath);
        System.err.println("newDP     : " + newDataPath);
        System.err.println("requiredMP: " + requiredMessagePath);
      }
    }else{
      System.err.println("Pipo!");
      System.err.println("initMessage was null!!");
    }
  }
}

public void setComponentValue(String name, Object object) {
//    System.err.println("-------------------->SETTING VALUE OF TABLE: "+name+" "+object.toString());
//  if (name.equals("filtersvisible")) {
//    setFiltersVisible(Boolean.valueOf(object.toString()).booleanValue());
//  }
  if (name.equals("hideColumn")) {
    setColumnVisible(object.toString(), false);
  }
  if (name.equals("showColumn")) {
    setColumnVisible(object.toString(), true);
  }
//  if (name.equals("columnsvisible")) {
//    setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
//  }
  super.setComponentValue(name, object);
}

private void setColumnVisible(String name, boolean visible){
  MessageTablePanel mm = (MessageTablePanel)getContainer();
  if(visible){
    mm.addColumn(name, name, false);
  }else{
    if(name.equals("selected")){
      //System.err.println("Selected column: " + mm.getSelectedColumn());
      mm.removeColumn(mm.getSelectedColumn());
    }else{
      mm.removeColumn(name);
    }
  }
}

//public void setFiltersVisible(boolean b) {
//  MessageTablePanel mtp = (MessageTablePanel)getContainer();
//  mtp.setFiltersVisible(b);
//}
//
//public void setColumnsVisible(boolean b){
//  MessageTablePanel mtp = (MessageTablePanel)getContainer();
//  mtp.setColumnsVisible(b);
//}

public Object getComponentValue(String name) {
//    System.err.println("Request for: " + name);
  if(name != null){
    if (name.equals("selectedMessage")) {
      Message m = amt.getSelectedMessage();
      return m;
    }
    else if (name.equals("selectedIndex")) {
      if(amt.getSelectedMessage() == null){
        return "-1";
      }
      return String.valueOf(amt.getSelectedMessage().getIndex());
    }
    else {
      return super.getComponentValue(name);
    }
  }else{
    return null;
  }
}


}