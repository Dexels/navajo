package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiAdvancedTable
    extends TipiDataComponentImpl
    implements CellEditorListener {
  MessageTablePanel amt;
  private String initMessagePath, dataMessagePath, initMethod, newDataPath,
      requiredMessagePath, updateMethod, deleteMethod, insertMethod, deleteFlag,
      updateFlag;
  private Map columnAttributes = new HashMap();
  private Message initMessage;
  private ArrayList requiredMessages = new ArrayList();
  private ArrayList insertedMessages = new ArrayList();
  private ArrayList changedMessages = new ArrayList();
  public TipiAdvancedTable() {
  }

  public Object createContainer() {
    MessageTablePanel amt = new MessageTablePanel();
    amt.addCellEditorListener(this);
    // Don't register actionPerformed, that is done elsewhere.
    amt.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        messageTableSelectionChanged(e);
      }
    });
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return amt;
  }

  public void editingCanceled(ChangeEvent e) {
    // mmm..
  }

  public void editingStopped(ChangeEvent e) {
    Object o = e.getSource();
    if (MessageTable.class.isInstance(o)) {
      MessageTable current = (MessageTable) o;
      Message currentMsg = current.getSelectedMessage();
      if (!insertedMessages.contains(currentMsg)) {
        changedMessages.add(currentMsg);
      }
      else {
      }
    }
  }

  protected void performComponentMethod(String name,
                                        TipiComponentMethod compMeth, TipiEvent event) {
    if (name.equals("insert")) {
      try {
        if (newDataPath != null) {
          amt = (MessageTablePanel) getContainer();
          Message insertMessage = (getNavajo().getMessage(newDataPath)).copy(
              getNavajo());
          amt.addSubMessage(insertMessage);
          insertedMessages.add(insertMessage);
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if (name.equals("delete")) {
      amt = (MessageTablePanel) getContainer();
      ArrayList selected = amt.getTable().getSelectedMessages();
      for (int i = 0; i < selected.size(); i++) {
        Message current = (Message) selected.get(i);
        if (deleteFlag == null) {
          deleteFlag = "Delete";
        }
        current.getProperty(deleteFlag).setValue(true);
      }
      if (deleteMethod != null) {
        try {
//          myContext.enqueueAsyncSend(getNavajo(), getPath(),
//                                      deleteMethod, this,false,event);

          myContext.performTipiMethod(this,getNavajo(), getPath(),
                                     deleteMethod, false,event,-1,null,null,null);
          if (initMessage != null) {
            myContext.performTipiMethod(this,getNavajo(), getPath(),
                                       initMethod, false,event,-1,null,null,null);
//            myContext.enqueueAsyncSend(initMessage.getRootDoc(),
//                                       getPath(), initMethod, this,false,event);
          }
          else {
//            myContext.enqueueAsyncSend(getNavajo(), getPath(),
//                                       initMethod, this,false,event);
            myContext.performTipiMethod(this,getNavajo(), getPath(),
                                        initMethod, false,event,-1,null,null,null);
          }
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
      else {
        throw new RuntimeException(
            "ERROR: Cannot delete without specified deleteMethod attribute");
      }
    }
    if (name.equals("update")) {
      try {
        if (updateMethod != null) {
          for (int i = 0; i < changedMessages.size(); i++) {
            Message current = (Message) changedMessages.get(i);
            if (updateFlag == null) {
              updateFlag = "Update";
            }
            current.getProperty(updateFlag).setValue(true);
          }
          if (changedMessages.size() > 0) {
//            myContext.enqueueAsyncSend(getNavajo(), getPath(),
//                                       updateMethod, this,false,event);
            myContext.performTipiMethod(this,getNavajo(), getPath(),
                                        updateMethod, false,event,-1,null,null,null);
            changedMessages.clear();
          }
        }
        if (insertMethod != null) {
          for (int i = 0; i < insertedMessages.size(); i++) {
            Message current = (Message) insertedMessages.get(i);
            Navajo n = NavajoFactory.getInstance().createNavajo();
            current.setName(newDataPath.substring(1));
            n.addMessage(current);
            System.err.println("Adding inserted message: " + current.getName());
            if (requiredMessages != null) {
              for (int j = 0; j < requiredMessages.size(); j++) {
                n.addMessage( (Message) requiredMessages.get(j));
              }
            }
            myContext.performTipiMethod(this,n, getPath(),
                                        insertMethod, false,event,-1,null,null,null);
//            myContext.enqueueAsyncSend(n, getPath(),
//                                       insertMethod, this,false,event);
          }
          insertedMessages.clear();
        }
        amt.clearTable();
        if (initMessage != null) {
          myContext.performTipiMethod(this,initMessage.getRootDoc(), getPath(),
                                      initMethod, false,event,-1,null,null,null);
//          myContext.enqueueAsyncSend(initMessage.getRootDoc(),
//                                     getPath(), initMethod, this,false,event);
        }
        else {
          myContext.performTipiMethod(this,getNavajo(), getPath(),
                                      initMethod, false,event,-1,null,null,null);
//          myContext.enqueueAsyncSend(getNavajo(), getPath(),
//                                     initMethod, this,false,event);
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
   }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws
      com.dexels.navajo.tipi.TipiException {
    amt = (MessageTablePanel) getContainer();
    TipiSwingColumnAttributeParser cap = new TipiSwingColumnAttributeParser();
    initMessagePath = (String) elm.getAttribute("initpath");
    if (initMessagePath != null) {
      TipiPathParser pp = new TipiPathParser(this, context, initMessagePath);
      if (pp.getPathType() == pp.PATH_TO_MESSAGE) {
        initMessage = pp.getMessage();
        if (initMessage == null) {
          throw new TipiException("Found empty message for path: " +
                                  initMessagePath);
        }
      }
      else if (pp.getPathType() == pp.PATH_TO_TIPI) {
        if (pp.getTipi() == null) {
          throw new TipiException("Found empty Tipi: " + initMessagePath);
        }
        if (pp.getTipi().getNavajo() == null) {
          throw new TipiException("Found empty Navajo: " + pp.getTipi().getNavajo());
        }
        initMessage = pp.getTipi().getNavajo().getRootMessage();
      }
      else {
        initMessage = null;
        // =========================================================
      }
    }
    else {
      System.err.println("Not gonna work...");
      return;
    }
    dataMessagePath = (String) elm.getAttribute("datamessagepath");
    initMethod = (String) elm.getAttribute("initmethod");
    newDataPath = (String) elm.getAttribute("newdatapath");
    // =====================================================================
    requiredMessagePath = (String) elm.getAttribute("requiredmessage");
    StringTokenizer tok = new StringTokenizer(requiredMessagePath, ";");
    while (tok.hasMoreTokens()) {
      String path = tok.nextToken();
      TipiPathParser qq = new TipiPathParser(this, context, path);
      requiredMessages.add(qq.getMessage());
      System.err.println("Adding: " + path);
    }
    // =====================================================================
    insertMethod = (String) elm.getAttribute("insertmethod");
    updateMethod = (String) elm.getAttribute("updatemethod");
    deleteMethod = (String) elm.getAttribute("deletemethod");
    deleteFlag = (String) elm.getAttribute("deleteflag");
    updateFlag = (String) elm.getAttribute("updateflag");
    super.load(elm, instance, context); // Mmm vreemde plek
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("column")) {
        String label = (String) child.getAttribute("label");
        String name = (String) child.getAttribute("name");
        String editableString = (String) child.getAttribute("editable");
        boolean editable = "true".equals(editableString);
        amt.addColumn(name, label, editable);
        amt.messageChanged();
      }
      if (child.getName().equals("column-attribute")) {
        String name = (String) child.getAttribute("name");
        String type = (String) child.getAttribute("type");
        if (name != null && type != null && !name.equals("") && !type.equals("")) {
          columnAttributes.put(name, cap.parseAttribute(child));
        }
      }
    }
    amt.setColumnAttributes(columnAttributes);
    // We can start trying to get our data
    if (initMessage != null) {
      if (initMethod != null) {
        try {
          loadData(context.doSimpleSend(initMessage.getRootDoc(), initMethod, this, false),
                   context);
        }
        catch (TipiBreakException ex1) {
          // will never happen. breakOnError is false. This is fine.
          ex1.printStackTrace();
        }
      }
    }
    else {
      try {
        if (initMethod != null) {
          loadData(NavajoClientFactory.getClient().doSimpleSend(initMethod),
                   context);
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public void messageTableSelectionChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      return;
    }
    try {
      MessageTablePanel mm = (MessageTablePanel) getContainer();
      Map tempMap = new HashMap();
      tempMap.put("selectedIndex",new Integer(mm.getSelectedRow()));
      tempMap.put("selectedMessage",mm.getSelectedMessage());
      performTipiEvent("onSelectionChanged", tempMap, true);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void messageTableActionPerformed(ActionEvent ae) {
    try {
      performTipiEvent("onActionPerformed", null, false);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {
    super.loadData(n, tc);
    MessageTablePanel mtp = (MessageTablePanel) getContainer();
    if (dataMessagePath != null && n != null) {
      Message m = n.getMessage(dataMessagePath);
      if (m != null) {
        mtp.setMessage(m);
      }
      else {
        System.err.println("Load called in AdvancedTipiTable, but the load_data does not contain the right dataMessage[" +
                           dataMessagePath + "]");
      }
    }
  }

  public void setComponentValue(String name, Object object) {
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
      amt.setSelectedRow(((Integer)object).intValue());
    }
    super.setComponentValue(name, object);
  }

  public Object getComponentValue(String name) {
    if (name.equals("selectedMessage")) {
      Message m = amt.getSelectedMessage();
      return m;
    }
    if (name.equals("selectedIndex")) {
      if (amt.getSelectedMessage() == null) {
        return new Integer(-1);
      }
      return new Integer(amt.getSelectedMessage().getIndex());
    }
    return super.getComponentValue(name);
  }

  private final void setColumnVisible(String name, boolean visible) {
    MessageTablePanel mm = (MessageTablePanel) getContainer();
    if (visible) {
      mm.addColumn(name, name, false);
    }
    else {
      if (name.equals("selected")) {
        mm.removeColumn(mm.getSelectedColumn());
      }
      else {
        mm.removeColumn(name);
      }
    }
  }

  public void setFiltersVisible(boolean b) {
    MessageTablePanel mtp = (MessageTablePanel) getContainer();
    mtp.setFiltersVisible(b);
  }

  public void setColumnsVisible(boolean b) {
    MessageTablePanel mtp = (MessageTablePanel) getContainer();
    mtp.setColumnsVisible(b);
  }
}
