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
import com.dexels.navajo.tipi.internal.*;

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
    if (messagePath.startsWith("'") && messagePath.endsWith("'")) {
      messagePath = messagePath.substring(1,messagePath.length()-1);
      System.err.println("MESSAGEPATH: "+messagePath);
    }
    super.load(elm, instance, context);
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("column")) {
        String label = (String) child.getAttribute("label");
        String name = (String) child.getAttribute("name");
        String editableString = (String) child.getAttribute("editable");
        boolean editable = "true".equals(editableString);
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

  public XMLElement store() {
    XMLElement xx = super.store();
    MessageTablePanel mm = (MessageTablePanel) getContainer();
    MessageTableModel mtm = mm.getTable().getMessageModel();
    for (int i = 0; i < mtm.getColumnCount(); i++) {
      String id = mtm.getColumnId(i);
      String name = mtm.getColumnName(i);

      XMLElement columnDefinition = new CaseSensitiveXMLElement();
      columnDefinition.setName("column");
      columnDefinition.setAttribute("name",id);
      columnDefinition.setAttribute("label",name);
      xx.addChild(columnDefinition);
    }
    return xx;
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
      MessageTablePanel mm = (MessageTablePanel) getContainer();
      Map tempMap = new HashMap();
      tempMap.put("selectedIndex",new Integer(mm.getSelectedRow()));
      tempMap.put("selectedMessage",mm.getSelectedMessage());
      performTipiEvent("onActionPerformed", tempMap, true);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {
    super.loadData(n, tc);
    //Thread.currentThread().dumpStack();
    final MessageTablePanel mtp = (MessageTablePanel) getContainer();
    if (messagePath != null && n != null) {
      final Message m = n.getMessage(messagePath);
      if (m != null) {
        runSyncInEventThread(new Runnable() {
          public void run() {
            mtp.setMessage(m);
          }
        });
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
    if (name.equals("headervisible")) {
      setHeaderVisible(Boolean.valueOf(object.toString()).booleanValue());
    }
    if (name.equals("selectedindex")) {
      mm.setSelectedRow(((Integer)object).intValue());
//      setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
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
    if (name != null) {
      if (name.equals("selectedMessage")) {
        Message m = mm.getSelectedMessage();
        if (m != null) {
        }
        return m;
      }
      else if (name.equals("selectedIndex")) {
        if (mm.getSelectedMessage() == null) {
          return new Integer(-1);
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

  protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
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
      for (int i = 0; i < getEventList().size(); i++) {
        TipiEvent current = (TipiEvent)getEventList().get(i);
        if (current.isTrigger("onActionPerformed","aap")) {
          try {
            current.performAction(current);
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      }
    }
  }

  public void setColumnDefinitionSavePath(String path) {
    mm.setColumnDefinitionSavePath(path);
  }
}
