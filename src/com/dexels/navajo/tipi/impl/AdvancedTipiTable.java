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

public class AdvancedTipiTable extends DefaultTipi implements CellEditorListener {
  MessageTablePanel amt;
  private String initMessagePath, dataMessagePath, initMethod, newDataPath, requiredMessagePath, updateMethod, deleteMethod, insertMethod, deleteFlag, updateFlag;
  private Map columnAttributes = new HashMap();
  private Message initMessage;
  private ArrayList requiredMessages = new ArrayList();
  private ArrayList insertedMessages = new ArrayList();
  private ArrayList changedMessages = new ArrayList();

  public AdvancedTipiTable() {
  }
  public Container createContainer() {
    MessageTablePanel amt = new MessageTablePanel();
    amt.addCellEditorListener(this);
    // Don't register actionPerformed, that is done elsewhere.
    amt.addListSelectionListener(new ListSelectionListener(){
    public void valueChanged(ListSelectionEvent e){
      messageTableSelectionChanged(e);
    }
    });
    return amt;
  }

  public void editingCanceled(ChangeEvent e){
    // mmm..
  }

  public void editingStopped(ChangeEvent e){
//    System.err.println("ADVTIPITABLE: STOPPED EDITING");
    Object o = e.getSource();
    if (MessageTable.class.isInstance(o)) {
      MessageTable current = (MessageTable)o;
      Message currentMsg = current.getSelectedMessage();
//      System.err.println("Stopped editing: " + currentMsg.getFullMessageName());
      //if(!currentMsg.getFullMessageName().equals(dataMessagePath + newDataPath)){
      if (!insertedMessages.contains(currentMsg)) {
//        System.err.println("PUTTING message in changedMessages!");
        changedMessages.add(currentMsg);
      }else{
//        System.err.println("You're editing an inserted Message");
      }
    }

  }

  protected void performComponentMethod(String name,TipiComponentMethod compMeth) {
    if (name.equals("insert")) {
      try {

        if(newDataPath != null){
          amt = (MessageTablePanel) getContainer();
          Message insertMessage = (getNavajo().getMessage(newDataPath)).copy(getNavajo());
          amt.addSubMessage(insertMessage);
          insertedMessages.add(insertMessage);
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if(name.equals("delete")){
//      System.err.println("Delete called");
      amt = (MessageTablePanel) getContainer();
      ArrayList selected = amt.getTable().getSelectedMessages();
      for(int i=0;i<selected.size();i++){
        Message current = (Message) selected.get(i);
        if(deleteFlag == null){
          deleteFlag = "Delete";
        }
        current.getProperty(deleteFlag).setValue(true);
      }
      if(deleteMethod != null){
        try{
          TipiContext.getInstance().enqueueAsyncSend(getNavajo(),getPath(), deleteMethod, this);
          if(initMessage != null){
            TipiContext.getInstance().enqueueAsyncSend(initMessage.getRootDoc(), getPath(),initMethod, this);
            //loadData(TipiContext.getInstance().doSimpleSend(initMessage.getRootDoc(), initMethod), TipiContext.getInstance());
          }else{
            TipiContext.getInstance().enqueueAsyncSend(getNavajo(), getPath(),initMethod, this);
            //loadData(TipiContext.getInstance().doSimpleSend(null,initMethod), TipiContext.getInstance());
          }
        }catch(Exception e){
          e.printStackTrace();
        }
      }else{
        throw new RuntimeException("ERROR: Cannot delete without specified deleteMethod attribute");
      }
    }

    if(name.equals("update")){
      try{
        if(updateMethod != null){
          for(int i=0;i<changedMessages.size();i++){

            Message current = (Message)changedMessages.get(i);
            if(updateFlag == null){
              updateFlag = "Update";
            }
            current.getProperty(updateFlag).setValue(true);
          }
          if(changedMessages.size() > 0){
            TipiContext.getInstance().enqueueAsyncSend(getNavajo(), getPath(),updateMethod, this);
            changedMessages.clear();
          }
        }

        if(insertMethod != null){
          for(int i=0;i<insertedMessages.size();i++){
            Message current = (Message) insertedMessages.get(i);
            Navajo n = NavajoFactory.getInstance().createNavajo();
            current.setName(newDataPath.substring(1));
            n.addMessage(current);
            System.err.println("Adding inserted message: " + current.getName());
            if(requiredMessages != null){
              for(int j=0;j<requiredMessages.size();j++){
                System.err.println("Adding required message");
                n.addMessage( (Message) requiredMessages.get(j));
              }
            }
            System.err.println("Sending:");
//            n.write(System.err);
            TipiContext.getInstance().enqueueAsyncSend(n, getPath(),insertMethod, this);
          }
          insertedMessages.clear();
        }
        if(initMessage != null){
          TipiContext.getInstance().enqueueAsyncSend(initMessage.getRootDoc(), getPath(),initMethod, this);
        //loadData(TipiContext.getInstance().doSimpleSend(initMessage.getRootDoc(), initMethod), TipiContext.getInstance());
      }else{
          TipiContext.getInstance().enqueueAsyncSend(getNavajo(),getPath(), initMethod, this);
        //loadData(TipiContext.getInstance().doSimpleSend(null,initMethod), TipiContext.getInstance());
      }

       }catch(Exception e){
          e.printStackTrace();
       }
    }
    //    super.performComponentMethod( name,  invocation,  compMeth);
  }


  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
  amt = (MessageTablePanel)getContainer();
  TipiColumnAttributeParser cap = new TipiColumnAttributeParser();

  // =======================================================
  initMessagePath = (String)elm.getAttribute("initpath");
//  System.err.println("initMessagePath = " + initMessagePath);
  TipiPathParser pp = new TipiPathParser(this, context, initMessagePath);
  if (pp.getPathType() == pp.PATH_TO_MESSAGE) {
    initMessage = pp.getMessage();
    if (initMessage == null)
      throw new TipiException("Found empty message for path: " + initMessagePath);
  }
  else if (pp.getPathType() == pp.PATH_TO_TIPI) {
    if (pp.getTipi() == null)
       throw new TipiException("Found empty Tipi: " + initMessagePath);
     if (pp.getTipi().getNavajo() == null)
       throw new TipiException("Found empty Navajo: " + pp.getTipi().getNavajo());
    initMessage = pp.getTipi().getNavajo().getRootMessage();
  }
  else
    initMessage = null;

  // =========================================================

  dataMessagePath = (String)elm.getAttribute("datamessagepath");
  initMethod = (String)elm.getAttribute("initmethod");
  newDataPath = (String)elm.getAttribute("newdatapath");

  // =====================================================================
  requiredMessagePath = (String)elm.getAttribute("requiredmessage");
  StringTokenizer tok = new StringTokenizer(requiredMessagePath, ";");
  while(tok.hasMoreTokens()){
    String path = tok.nextToken();
    TipiPathParser qq = new TipiPathParser(this, context, path);
    requiredMessages.add(qq.getMessage());
    System.err.println("Adding: " + path);
  }

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
       loadData(context.doSimpleSend(initMessage.getRootDoc(), initMethod, this), context);
    }
  }else{
//    System.err.println("---> Could not find initMessage, proceeding without it");
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
//    System.err.println("-------------------->SETTING VALUE OF TABLE: "+name+" "+object.toString());
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
  if (name.equals("selectedIndex")) {
    amt.setSelectedRow(Integer.parseInt(object.toString()));
  }
  super.setComponentValue(name, object);
}

public Object getComponentValue(String name) {
//    System.err.println("Request for: " + name);
    if (name.equals("selectedMessage")) {
      Message m = amt.getSelectedMessage();
      return m;
    }
    if (name.equals("selectedIndex")) {
      if (amt.getSelectedMessage() == null) {
        return "-1";
      }
      return String.valueOf(amt.getSelectedMessage().getIndex());
    }

    return super.getComponentValue(name);
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



}