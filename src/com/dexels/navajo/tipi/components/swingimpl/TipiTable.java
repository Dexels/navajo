package com.dexels.navajo.tipi.components.swingimpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0();
 */
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.tipixml.*;

public class TipiTable
    extends TipiSwingDataComponentImpl {
  private String messagePath = "";
  private MessageTablePanel mm;
  private Map columnAttributes = new HashMap();
  private boolean showHeader = true;
  public Object createContainer() {
    MessageTablePanel mm = new MessageTablePanel();
    // Don't register actionPerformed, that is done elsewhere.
    mm.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        messageTableSelectionChanged(e);
      }
    });
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return mm;
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    mm = (MessageTablePanel) getContainer();
    TipiSwingColumnAttributeParser cap = new TipiSwingColumnAttributeParser();
    messagePath = (String) elm.getAttribute("messagepath");
    super.load(elm, instance, context);
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("column")) {
        String label = (String) child.getAttribute("label");
        String name = (String) child.getAttribute("name");
        String editableString = (String) child.getAttribute("editable");
        boolean editable = "true".equals(editableString);
        //System.err.println("Adding column " + name + ", editable: " + editable);
        mm.addColumn(name, label, editable);
        mm.messageChanged();
      }
      if (child.getName().equals("column-attribute")) {
        String name = (String) child.getAttribute("name");
        String type = (String) child.getAttribute("type");
        if (name != null && type != null && !name.equals("") && !type.equals("")) {
          columnAttributes.put(name, cap.parseAttribute(child));
        }
      }
    }
    mm.setColumnAttributes(columnAttributes);
  }

  public void messageTableSelectionChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      return;
    }
    try {
      performTipiEvent("onSelectionChanged", e, true);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void messageTableActionPerformed(ActionEvent ae) {
    try {
      performTipiEvent("onActionPerformed", ae, false);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {
    super.loadData(n, tc);
    //Thread.currentThread().dumpStack();
    MessageTablePanel mtp = (MessageTablePanel) getContainer();
    if (messagePath != null && n != null) {
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
    if (name.equals("headervisible")) {
//      System.err.println("\n\n\n\nSETTING HEADER VISIBLE:  "+Boolean.valueOf(object.toString()).booleanValue());
      setHeaderVisible(Boolean.valueOf(object.toString()).booleanValue());
    }
    if (name.equals("selectedindex")) {
      setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
    }
    if (name.equals("autoresize")) {
      if ("all".equals(object)) {
        mm.setAutoResize(JTable.AUTO_RESIZE_ALL_COLUMNS);
      }
      if ("last".equals(object)) {
        mm.setAutoResize(JTable.AUTO_RESIZE_LAST_COLUMN);
      }
      if ("next".equals(object)) {
        mm.setAutoResize(JTable.AUTO_RESIZE_NEXT_COLUMN);
      }
      if ("subsequent".equals(object)) {
        mm.setAutoResize(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
      }
      if ("off".equals(object)) {
        mm.setAutoResize(JTable.AUTO_RESIZE_OFF);
      }
    }
    if (name.equals("columnDefinitionSavePath")) {
      setColumnDefinitionSavePath(object.toString());
    }
    super.setComponentValue(name, object);
  }

  private void setColumnVisible(String name, boolean visible) {
    MessageTablePanel mm = (MessageTablePanel) getContainer();
    if (visible) {
      mm.addColumn(name, name, false);
    }
    else {
      if (name.equals("selected")) {
        //System.err.println("Selected column: " + mm.getSelectedColumn());
        mm.removeColumn(mm.getSelectedColumn());
      }
      else {
        mm.removeColumn(name);
      }
    }
  }

  public void setHeaderVisible(boolean b) {
    showHeader = b;
    mm.setHeaderVisible(b);
  }

  public void setFiltersVisible(boolean b) {
    MessageTablePanel mtp = (MessageTablePanel) getContainer();
    mtp.setFiltersVisible(b);
  }

  public void setColumnsVisible(boolean b) {
    MessageTablePanel mtp = (MessageTablePanel) getContainer();
    mtp.setColumnsVisible(b);
  }

  public Object getComponentValue(String name) {
//    System.err.println("Request for: " + name);
    if (name != null) {
      if (name.equals("selectedMessage")) {
        Message m = mm.getSelectedMessage();
        if (m != null) {
//          System.err.println("\nRETRIEVING SELECTED MESSAGE:\n");
//          m.write(System.err);
//          System.err.println("***************** END OF MESSAGE *****************");
        }
        return m;
      }
      else if (name.equals("selectedIndex")) {
        if (mm.getSelectedMessage() == null) {
          return "-1";
        }
        return new Integer(mm.getSelectedMessage().getIndex());
      }
      else {
        return super.getComponentValue(name);
      }
    }
    else {
      return null;
    }
  }

  protected void performComponentMethod(String name, TipiComponentMethod compMeth) {
    int count = mm.getRowCount();
    if (count != 0) {
      if ("selectNext".equals(name)) {
        int r = mm.getSelectedRow();
        if ( (r < count - 1)) {
          mm.setSelectedRow(r + 1);
        }
        return;
      }
      if ("selectPrevious".equals(name)) {
        int r = mm.getSelectedRow();
        if ( (r > 0)) {
          mm.setSelectedRow(r - 1);
        }
        return;
      }
      if ("selectFirst".equals(name)) {
        mm.setSelectedRow(0);
      }
      if ("selectLast".equals(name)) {
        mm.setSelectedRow(count - 1);
      }
    }
    if ("fireAction".equals(name)) {
      mm.fireActionEvent();
    }
  }

  public void setColumnDefinitionSavePath(String path) {
    mm.setColumnDefinitionSavePath(path);
  }
}