package com.dexels.navajo.tipi.impl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0();
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
  private Map columnAttributes = new HashMap();

  public DefaultTipiTable() {
    initContainer();
  }

  public Container createContainer() {
    MessageTablePanel mm = new MessageTablePanel();
    // Don't register actionPerformed, that is done elsewhere.
    mm.addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent e){
          messageTableSelectionChanged(e);
        }
    });
    return mm;
  }

  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
  }
  public void removeFromContainer(Component c) {
    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
  }
  public void setContainerLayout(LayoutManager layout){
    throw new UnsupportedOperationException("Can not set layout of container of class: "+getClass());
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    mm = (MessageTablePanel)getContainer();
    TipiColumnAttributeParser cap = new TipiColumnAttributeParser();
    messagePath = (String)elm.getAttribute("messagepath");
    super.load(elm,instance,context);
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("column")) {
        String label = (String)child.getAttribute("label");
        String name = (String)child.getAttribute("name");
        String editableString = (String)child.getAttribute("editable");
        boolean editable = "true".equals(editableString);
        //System.err.println("Adding column " + name + ", editable: " + editable);
        mm.addColumn(name,label,editable);
        mm.messageChanged();
      }
      if(child.getName().equals("column-attribute")){
        String name = (String) child.getAttribute("name");
        String type= (String) child.getAttribute("type");
        if(name != null && type != null && !name.equals("") && !type.equals("")){
          columnAttributes.put(name, cap.parseAttribute(child));
        }
      }
    }
    mm.setColumnAttributes(columnAttributes);
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
    //System.err.println("Actionperformed!!!! (TipiTable)");
//    System.err.println(">>> "+ae.getActionCommand());
    try {
      performTipiEvent("onActionPerformed",ae);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {
    super.loadData(n,tc);
//    System.err.println("LOADING DATA: ");
    //Thread.currentThread().dumpStack();
    MessageTablePanel mtp = (MessageTablePanel)getContainer();
    if(messagePath != null && n != null){
      Message m = n.getMessage(messagePath);
      if (m != null) {
        mtp.setMessage(m);
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
    if (name.equals("selectedindex")) {
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
        //System.err.println("Selected column: " + mm.getSelectedColumn());
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
        Message m = mm.getSelectedMessage();
        return m;
      }
      else if (name.equals("selectedIndex")) {
        if(mm.getSelectedMessage() == null){
          return "-1";
        }
        return new Integer(mm.getSelectedMessage().getIndex());
      }
      else {
        return super.getComponentValue(name);
      }
    }else{
      return null;
    }
  }
  protected void performComponentMethod(String name, XMLElement invocation, TipiComponentMethod compMeth) {
    int count = mm.getRowCount();
    if (count!=0) {
      if ("selectNext".equals(name)) {
        int r = mm.getSelectedRow();
        if ((r<count-1)) {
          mm.setSelectedRow(r+1);
        }
        return;
      }
      if ("selectPrevious".equals(name)) {
        int r = mm.getSelectedRow();
        if ((r>0)) {
          mm.setSelectedRow(r-1);
        }
        return;
      }
      if ("selectFirst".equals(name)) {
        mm.setSelectedRow(0);
      }
      if ("selectLast".equals(name)) {
        mm.setSelectedRow(count-1);
      }
    }

    if ("fireAction".equals(name)) {
      mm.fireActionEvent();
    }

  }

}