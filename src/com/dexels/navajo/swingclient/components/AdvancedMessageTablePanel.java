package com.dexels.navajo.swingclient.components;

import com.dexels.navajo.document.*;
import com.dexels.navajo.client.*;
import com.dexels.navajo.swingclient.*;

import java.util.*;
import javax.swing.event.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.document.nanoimpl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class AdvancedMessageTablePanel extends MessageTablePanel implements CellEditorListener, ResponseListener {

  private String updateService = null;
  private String insertService = null;
  private String deleteService = null;
  private Message initMessage = null;
  private String initMethod = null;
  private String loadMessagePath = null;
  private String insertMessagePath = null;
  private String requiredMessagePath = null;
  private int nrOfDeletedRows = 0;
  private int nrOfInsertedRows = 0;
  private Set changedMessages = new HashSet();
  private ArrayList insertedMessages = new ArrayList();
  private ArrayList messageTableListeners = new ArrayList();

  public AdvancedMessageTablePanel() {
    addCellEditorListener(this);
  }

  public void commit(){
    System.err.println("Committing AMTP");
    Navajo rootDoc = null;
    ArrayList methods = null;
    addBusyPanel();
    // Changed Messages

    // NOTE: Inserted messages are not changed!!!!!!!!
    Iterator it = changedMessages.iterator();
    int changeCount = 0;
    while(it.hasNext()){
      Message current = (Message)it.next();
      current.getProperty("Update").setValue("true");
      if(changeCount == 0){
        rootDoc = current.getRootDoc();
        methods = rootDoc.getAllMethods();
      }
      System.err.println("Updated: " + current.getName());
      changeCount++;
    }
    if(changeCount > 0){
      String updateMethod = null;
      for(int j=0;j<methods.size();j++){
        Method currentMethod = (Method)methods.get(j);
        if(currentMethod.getName().startsWith("ProcessUpdate")){
          updateMethod = currentMethod.getName();
          System.err.println("UpdateMethod found: " + updateMethod);
          break;
        }
      }
      if(updateMethod != null){
        //AdvancedNavajoClient.doAsyncSend(rootDoc, updateMethod, this, "update");
        try {
          NavajoClientFactory.getClient().doSimpleSend(rootDoc, updateMethod);
        }
        catch (ClientException ex1) {
          ex1.printStackTrace();
        }
      }
    }

    // Inserted Messages
    if(methods != null){
      methods.clear();
    }
    String insertMethod = null;
    nrOfInsertedRows = insertedMessages.size();
    System.err.println("Inserted rows: " + nrOfInsertedRows);
    for(int i=0;i<insertedMessages.size();i++){
      Message current = (Message)insertedMessages.get(i);
      if(i == 0){
        rootDoc = getMessage().getRootDoc();
        methods = rootDoc.getAllMethods();
        for(int j=0;j<methods.size();j++){
          Method currentMethod = (Method) methods.get(j);
          if(currentMethod.getName().startsWith("ProcessInsert")){
            insertMethod = currentMethod.getName();
            System.err.println("InsertMethod found: " + insertMethod);
            break;
          }
        }
      }
      try {
        if (insertMethod != null) {
          Navajo m = NavajoFactory.getInstance().createNavajo();
          if (requiredMessagePath != null) {
            Message required = rootDoc.getMessage(requiredMessagePath);
            m.addMessage(required);
          }
          m.addMessage(current);
          //AdvancedNavajoClient.doAsyncSend(m, insertMethod, this, "insert");
          NavajoClientFactory.getClient().doSimpleSend(m, insertMethod);
       }
        System.err.println("Inserted: " + current.getName());
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if(nrOfInsertedRows > 0){
//      addBusyPanel();
    }
    reload();
  }

  public void delete(){
    addBusyPanel();
    Navajo rootDoc = null;
    ArrayList methods = null;
    ArrayList selectedMsgs = getSelectedMessages();
    if(selectedMsgs != null && SwingClient.getUserInterface().areYouSure()){
      nrOfDeletedRows = selectedMsgs.size();
      for(int i=0;i<selectedMsgs.size();i++){
        Message selected = (Message)selectedMsgs.get(i);
        rootDoc = selected.getRootDoc();
        selected.getProperty("Delete").setValue("true");
        methods = rootDoc.getAllMethods();
        String deleteMethod = null;
        for(int j=0;j<methods.size();j++){
          Method currentMethod = (Method)methods.get(j);
          if(currentMethod.getName().startsWith("ProcessDelete")){
            deleteMethod = currentMethod.getName();
            System.err.println("DeleteMethod: " + deleteMethod);
            break;
          }
        }
        if(deleteMethod != null){
          //AdvancedNavajoClient.doAsyncSend(rootDoc, deleteMethod, this, "delete");
//          AdvancedNavajoClient.doSimpleSend(rootDoc, deleteMethod);
          try {
            NavajoClientFactory.getClient().doSimpleSend(rootDoc, deleteMethod);
          }
          catch (ClientException ex) {
            ex.printStackTrace();
          }
        }
      }
    }
    reload();
  }

  public void insert(){
    if(insertMessagePath != null){
      Message insertMessage = getMessage().getRootDoc().getMessage(insertMessagePath);
      if(insertMessage != null){
//        Message newRow = insertMessage.copy(NavajoFactory.getInstance().createNavajo());
        Navajo n = insertMessage.getRootDoc();
        Message newRow = n.copyMessage(insertMessage,NavajoFactory.getInstance().createNavajo());
        insertedMessages.add(newRow);
        addSubMessage(newRow);
        rebuildSort();
      }else{
        System.err.println("InsertMessage not found");
      }
    }else{
      System.err.println("insertMessage datapath not specified");
    }

  }

  public void insert(Message msg){
    System.err.println("Inserting external message");
    insertedMessages.add(msg);
    addSubMessage(msg);
    rebuildSort();
  }

  private void reload(){
    System.err.println("Reloading");
    if(changedMessages != null && insertedMessages != null){
      changedMessages.clear();
      insertedMessages.clear();
    }
    try {
      NavajoClientFactory.getClient().doAsyncSend(initMessage.getRootDoc(), initMethod, this, "reload");
    }
    catch (ClientException ex) {
      ex.printStackTrace();
    }
//    AdvancedNavajoClient.doAsyncSend(initMessage.getRootDoc(), initMethod, this, "reload");
  }

  public void receive(Navajo n, String id){
//    System.err.println("RECEIVED: " + n.toXml().toString());
    if(id.equals("update")){
      System.err.println("Received 'update'");
      reload();
    }
    if(id.equals("insert")){
      nrOfInsertedRows--;
      if(nrOfInsertedRows == 0){
        reload();
      }
      System.err.println("Received 'insert' id: " + nrOfInsertedRows);
    }
    if(id.equals("delete")){
      nrOfDeletedRows--;
      if(nrOfDeletedRows == 0){
        reload();
      }
      System.err.println("Received 'delete' id: " + nrOfDeletedRows);
    }
    if(id.equals("reload")){
      System.err.println("Received 'reload'");
      Message loadMsg = n.getMessage(loadMessagePath);
      setMessage(loadMsg);
      removeBusyPanel();
    }
  }

  public void loadPanelData(){
    addBusyPanel();
    reload();
  }

  public Set getChangedMessages() {
    return changedMessages;
  }

  public void editingStopped(ChangeEvent e) {
    System.err.println("ADVMESSAGETABLE: STOPPED EDITING");
    Object o = e.getSource();
    if (MessageTable.class.isInstance(o)) {
      MessageTable current = (MessageTable)o;
      Message currentMsg = current.getSelectedMessage();
      System.err.println("Stopped editing: " + currentMsg.getFullMessageName());
      if(!currentMsg.getFullMessageName().equals(loadMessagePath + insertMessagePath)){
        changedMessages.add(currentMsg);
      }else{
        System.err.println("Your editing an inserted Message");
      }
    }
  }

  public void editingCanceled(ChangeEvent e) {
    System.err.println("Editing cancelled.. too bad");
    /*Object o = e.getSource();
    if (PropertyControlled.class.isInstance(o)) {
      PropertyControlled current = (PropertyControlled)o;
      Property p = current.getProperty();
      Message parent = p.getParent();
      System.err.println("CANCELED_MESSAGE\n"+parent.toXml(null).toString());
    }*/
  }

  public void setService(Message initMessage, String initMethod, String dataPath, String newDataPath, String requiredDataPath){
    this.initMessage = initMessage;
    this.initMethod = initMethod;
    this.loadMessagePath = dataPath;
    this.insertMessagePath = newDataPath;
    this.requiredMessagePath = requiredDataPath;
  }

  public void setService(Message initMessage, String initMethod, String dataPath, String newDataPath){
    this.initMessage = initMessage;
    this.initMethod = initMethod;
    this.loadMessagePath = dataPath;
    this.insertMessagePath = newDataPath;
  }

  public Message getInitMessage(){
    return initMessage;
  }
  public void setMessage(Message m) {
    super.setMessage( m);
    changedMessages.clear();
  }

  // overridden
  public void rowSelected(ListSelectionEvent e) {
    fireRowSelected(getSelectedMessage());
  }

  public void addMessageTableListener(MessageTableListener mtl) {
    throw new UnsupportedOperationException("Not yet implemented");
//    messageTableListeners.add(mtl);
  }

  public void removeMessageTableListener(MessageTableListener mtl) {
    messageTableListeners.remove(mtl);
  }

  protected void fireTableLoaded(Message m) {
    for (int i = 0; i < messageTableListeners.size(); i++) {
      MessageTableListener current = (MessageTableListener)messageTableListeners.get(i);
      current.tableLoaded(m);
    }
  }

  protected void fireRowUpdated(Message m) {
    for (int i = 0; i < messageTableListeners.size(); i++) {
      MessageTableListener current = (MessageTableListener)messageTableListeners.get(i);
      current.rowUpdated(m);
    }
  }

  protected void fireRowDeleted(Message m) {
    for (int i = 0; i < messageTableListeners.size(); i++) {
      MessageTableListener current = (MessageTableListener)messageTableListeners.get(i);
      current.rowDeleted(m);
    }
  }
  protected void fireRowInserted(Message m) {
    for (int i = 0; i < messageTableListeners.size(); i++) {
      MessageTableListener current = (MessageTableListener)messageTableListeners.get(i);
      current.rowInserted(m);
    }
  }

  protected void fireRowSelected(Message m) {
    for (int i = 0; i < messageTableListeners.size(); i++) {
      MessageTableListener current = (MessageTableListener)messageTableListeners.get(i);
      current.rowSelected(m);
    }
  }
}