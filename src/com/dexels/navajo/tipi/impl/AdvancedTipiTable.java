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
import com.dexels.navajo.client.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AdvancedTipiTable extends DefaultTipi {
  MessageTablePanel amt;
  private String initMessagePath, dataMessagePath, initMethod, newDataPath, requiredMessagePath, updateMethod, deleteMethod, insertMethod, deleteFlag, updateFlag;
  private Map columnAttributes = new HashMap();
  private Message initMessage, requiredMessage;

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
    amt.addKeyListener(new KeyListener(){
      public void keyTyped(KeyEvent e){
        keyTapped(e);
      }
      public void keyPressed(KeyEvent e){
      }
      public void keyReleased(KeyEvent e){
      }
    });
    return amt;
  }

  private void keyTapped(KeyEvent e){
    if(e.getKeyCode() == KeyEvent.VK_TAB){
      System.err.println("You pressed TAB!");
      amt = (MessageTablePanel)getContainer();
      int cols = amt.getTable().getColumnCount();
      int selected = amt.getTable().getSelectedColumn();
      if(selected < cols){
        amt.getTable().getColumnModel().getSelectionModel().setSelectionInterval(selected+1, selected+1);
      }else{
        System.err.println("End of row!!");
      }
    }
  }

  protected void performComponentMethod(String name, XMLElement invocation, TipiComponentMethod compMeth) {
    if (name.equals("insert")) {
      try {
        System.err.println("Insert called");
        if(newDataPath != null){
          amt = (MessageTablePanel) getContainer();
          amt.addSubMessage(getNavajo().getMessage(newDataPath));
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if(name.equals("delete")){
      System.err.println("Delete called");


    }
    if(name.equals("update")){
      System.err.println("Update called");

    }

    //    super.performComponentMethod( name,  invocation,  compMeth);
  }


  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
  amt = (MessageTablePanel)getContainer();
  TipiColumnAttributeParser cap = new TipiColumnAttributeParser();

  // =======================================================
  initMessagePath = (String)elm.getAttribute("initmessage");
  TipiPathParser pp = new TipiPathParser(this, context, initMessagePath);
  initMessage = pp.getMessage();

  // =========================================================

  dataMessagePath = (String)elm.getAttribute("datamessagepath");
  initMethod = (String)elm.getAttribute("initmethod");
  newDataPath = (String)elm.getAttribute("newdatapath");

  // =====================================================================
  requiredMessagePath = (String)elm.getAttribute("requiredmessage");
  TipiPathParser qq = new TipiPathParser(this, context, requiredMessagePath);
  requiredMessage = qq.getMessage();

  // =====================================================================

  insertMethod = (String)elm.getAttribute("insertmethod");
  updateMethod = (String)elm.getAttribute("updatemethod");
  deleteMethod = (String)elm.getAttribute("deletemethod");
  deleteFlag = (String)elm.getAttribute("deleteflag");
  updateFlag = (String)elm.getAttribute("updateflag");

  super.load(elm,instance,context); // Mmm vreemde plek

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

  // We can start trying to get our data
  if(initMessage != null ){
    if(initMethod != null){
       loadData(context.doSimpleSend(initMessage.getRootDoc(), initMethod), context);
    }
  }else{
    System.err.println("---> Could not find initMessage, proceeding without it");
    try {
      if(initMethod != null){
        loadData(NavajoClientFactory.getClient().doSimpleSend(initMethod), context);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}


public void messageTableSelectionChanged(ListSelectionEvent e){
  if (e.getValueIsAdjusting()) {
    return;
  }
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
 MessageTablePanel mtp = (MessageTablePanel)getContainer();
 if(dataMessagePath != null && n != null){
   Message m = n.getMessage(dataMessagePath);
   if (m != null) {
     mtp.setMessage(m);
   }else{
     System.err.println("Load called in AdvancedTipiTable, but the load_data does not contain the right dataMessage[" + dataMessagePath +"]");
   }
 }

}



public void setComponentValue(String name, Object object) {
    System.err.println("-------------------->SETTING VALUE OF TABLE: "+name+" "+object.toString());
  if (name.equals("filtersvisible")) {
    setFiltersVisible(Boolean.valueOf(object.toString()).booleanValue());
  }
  if (name.equals("hideColumn")) {
    setColumnVisible(object.toString(), false);
  }
  if (name.equals("showColumn")) {
    setColumnVisible(object.toString(), true);
  }
  if (name.equals("columnsvisible")) {
    setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
  }
  super.setComponentValue(name, object);
}

private void setColumnVisible(String name, boolean visible){
  MessageTablePanel mm = (MessageTablePanel)getContainer();
  if(visible){
    mm.addColumn(name, name, false);
  }else{
    if(name.equals("selected")){
      mm.removeColumn(mm.getSelectedColumn());
    }else{
      mm.removeColumn(name);
    }
  }
}

public void setFiltersVisible(boolean b) {
  MessageTablePanel mtp = (MessageTablePanel)getContainer();
  mtp.setFiltersVisible(b);
}

public void setColumnsVisible(boolean b){
  MessageTablePanel mtp = (MessageTablePanel)getContainer();
  mtp.setColumnsVisible(b);
}

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