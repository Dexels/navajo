package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.components.core.*;
import javax.swing.*;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.swingclient.components.*;
import java.util.*;
import java.awt.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiMultiTable
    extends TipiSwingDataComponentImpl {
  private JPanel myPanel = null;
  private boolean useTabs = true;
  private String outerMessageName = null;
  private String innerMessageName = null;
  private String titlePropertyName = "Title";
  private boolean columnsButtonVisible = false;
  private boolean filtersVisible = false;
  private boolean useScrollBars = true;
  private boolean headerVisible = false;
  private int rowHeight = -1;
  private final ArrayList columns = new ArrayList();
  private final ArrayList columnSize = new ArrayList();
  public TipiMultiTable() {
  }

  public Object createContainer() {
    /**@todo Implement this com.dexels.navajo.tipi.components.core.TipiComponentImpl abstract method*/
    myPanel = new JPanel();
    myPanel.setLayout(new BorderLayout());
    return myPanel;
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws
      com.dexels.navajo.tipi.TipiException {
    super.load(elm, instance, context);
    columns.clear();
    columnSize.clear();
    Vector children = elm.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("column")) {
        String name = (String) child.getAttribute("name");
        columns.add(name);
        int size = child.getIntAttribute("size", -1);
        columnSize.add(new Integer(size));
      }
    }
  }

  public XMLElement store() {
    XMLElement xx = super.store();
    for (int i = 0; i < columns.size(); i++) {
      XMLElement columnDefinition = new CaseSensitiveXMLElement();
      String cc = (String) columns.get(i);
      columnDefinition.setName("column");
      columnDefinition.setAttribute("name", cc);
      columnDefinition.setIntAttribute("size",
                                       ( (Integer) columnSize.get(i)).intValue());
      xx.addChild(columnDefinition);
    }
    return xx;
  }

  private final void reload() {
    try {
      if (myNavajo != null) {
        loadData(getNavajo(), myContext);
      }
      else {
        System.err.println("Can not reload, no navajo!");
      }
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public Object getComponentValue(String name) {
    System.err.println("In getter of multitable: name: " + name);
    if (name.equals("columnsButtonVisible")) {
      return new Boolean(columnsButtonVisible);
    }
    if (name.equals("filtersVisible")) {
      return new Boolean(filtersVisible);
    }
    if (name.equals("useScrollBars")) {
      return new Boolean(useScrollBars);
    }
    if (name.equals("headerVisible")) {
      return new Boolean(headerVisible);
    }
    if (name.equals("useTabs")) {
      return new Boolean(useTabs);
    }
    if (name.equals("outerMessageName")) {
      return outerMessageName;
    }
    if (name.equals("innerMessageName")) {
      return innerMessageName;
    }
    if (name.equals("titlePropertyName")) {
      return titlePropertyName;
    }
    return super.getComponentValue(name);
  }

//  private boolean columnButtonsVisible = false;
//  private boolean filtersVisible = false;
//  private boolean useScrollBars = true;
//  private boolean headerVisible = false;
  public void setComponentValue(String name, Object object) {
    System.err.println("In setter of multitable: name: " + name + " value: " +
                       object);
    if (name.equals("columnButtonVisible")) {
      columnsButtonVisible = (Boolean.valueOf(object.toString()).booleanValue());
      reload();
    }
    if (name.equals("filtersVisible")) {
      filtersVisible = (Boolean.valueOf(object.toString()).booleanValue());
      reload();
    }
    if (name.equals("useScrollBars")) {
      useScrollBars = (Boolean.valueOf(object.toString()).booleanValue());
      reload();
    }
    if (name.equals("headerVisible")) {
      headerVisible = (Boolean.valueOf(object.toString()).booleanValue());
      reload();
    }
    if (name.equals("useTabs")) {
      useTabs = (Boolean.valueOf(object.toString()).booleanValue());
      reload();
    }
    if (name.equals("outerMessageName")) {
      System.err.println("Setting outerMessage to: " + object);
      outerMessageName = (String) object;
      reload();
    }
    if (name.equals("innerMessageName")) {
      System.err.println("Setting innerMessage to: " + object);
      innerMessageName = (String) object;
      reload();
    }
    if (name.equals("titlePropertyName")) {
      titlePropertyName = object.toString();
      reload();
    }
    if (name.equals("rowHeight")) {
      rowHeight = ( (Integer) object).intValue();
      reload();
//      setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
    }
    super.setComponentValue(name, object);
  }

  private final void setupTable(MessageTablePanel mtp) {
    mtp.setColumnsVisible(columnsButtonVisible);
    mtp.setFiltersVisible(filtersVisible);
    mtp.setUseScrollBars(useScrollBars);
    mtp.setHeaderVisible(headerVisible);
    if (rowHeight > 0) {
      mtp.setRowHeight(rowHeight);
    }
  }

  private final void updateTableColumns(final MessageTablePanel mtp) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        mtp.createColumnModel();
        for (int i = 0; i < columnSize.size(); i++) {
          int ii = ( (Integer) columnSize.get(i)).intValue();
          final int index = i;
          final int value = ii;
//          System.err.println("Setting column: " + i + " to: " + ii);
          mtp.setColumnWidth(index, value);
        }
      }
    });
  }

  private final void buildTabs(Navajo n) {
    JTabbedPane jt = new JTabbedPane();
    myPanel.add(jt, BorderLayout.CENTER);
    Message m = n.getMessage(outerMessageName);
    for (int i = 0; i < m.getArraySize(); i++) {
      Message current = m.getMessage(i);
      Property titleProp = current.getProperty(titlePropertyName);
      String title = titleProp.getValue();
      Message inner = current.getMessage(innerMessageName);
//      System.err.println("INNER: ");
//      inner.write(System.err);
      MessageTablePanel mtp = new MessageTablePanel();
      setupTable(mtp);
      jt.addTab(title, mtp);
      if (inner.getArraySize() > 0) {
        Message first = inner.getMessage(0);
        for (int j = 0; j < columns.size(); j++) {
          String column = (String) columns.get(j);
          Property p = first.getProperty(column);
          if (p != null) {
            mtp.addColumn(p.getName(), p.getDescription(), p.isDirIn());
          }
        }
      }
      mtp.setMessage(inner);
      updateTableColumns(mtp);
//      for (int j = 0; j < columnSize.size(); j++) {
//        int s = ((Integer)columnSize.get(j)).intValue();
//        mtp.setColumnWidth(j,s);
//      }
    }
  }

  private final void buildPanels(Navajo n) {
    JPanel jt = new JPanel();
    jt.setLayout(new GridBagLayout());
    myPanel.add(jt, BorderLayout.CENTER);
    Message m = n.getMessage(outerMessageName);
//    System.err.println("Message path: "+outerMessageName);
//    System.err.println("Starting loop, "+m.getArraySize() +" elements.");
    for (int i = 0; i < m.getArraySize(); i++) {
      System.err.println("Message # " + i);
      Message current = m.getMessage(i);
//      current.write(System.err);
      Property titleProp = current.getProperty(titlePropertyName);
      if (titleProp == null) {
        System.err.println("NO TITLEPROP FOUND. Looking for: " +
                           titlePropertyName);
      }
      String title = titleProp.getValue();
      Message inner = current.getMessage(innerMessageName);
      MessageTablePanel mtp = new MessageTablePanel();
      setupTable(mtp);
      mtp.setBorder(BorderFactory.createTitledBorder(title));
      jt.add(mtp,
             new GridBagConstraints(0, i, 1, 1, 1, 1, GridBagConstraints.WEST,
                                    GridBagConstraints.BOTH,
                                    new Insets(2, 2, 2, 2), 0, 0));
//      jt.addTab(title, mtp);
      if (inner.getArraySize() > 0) {
        Message first = inner.getMessage(0);
        for (int j = 0; j < columns.size(); j++) {
          String column = (String) columns.get(j);
          Property p = first.getProperty(column);
          if (p != null) {
            mtp.addColumn(p.getName(), p.getDescription(), p.isDirIn());
          }
        }
      }
      mtp.setMessage(inner);
//      for (int j = 0; j < columnSize.size(); j++) {
//        int s = ((Integer)columnSize.get(j)).intValue();
//        mtp.setColumnWidth(j,s);
//      }
    }
  }

  public void loadData(final Navajo n, TipiContext context) throws
      TipiException {
    if (outerMessageName == null) {
      System.err.println("No outermessage");
      return;
    }
    else {
      System.err.println("Outer: " + outerMessageName);
      System.err.println("exists? " + n.getMessage(outerMessageName) != null);
    }
    Message outerMessage = n.getMessage(outerMessageName);
    if (outerMessage == null) {
      return;
    }
    if (innerMessageName == null) {
      System.err.println("No innermessage");
      return;
    }
    else {
      System.err.println("Inner: " + innerMessageName);
      System.err.println("exists? " + outerMessage.getMessage(innerMessageName) != null);
    }
    Message innerMessage = outerMessage.getMessage(innerMessageName);
    if (outerMessage != null) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          myPanel.removeAll();
          if (useTabs) {
            buildTabs(n);
          }
          else {
            buildPanels(n);
          }
          myPanel.doLayout();
        }
      });
    }
    else {
      System.err.println("Not loading outer message null!");
    }
    super.loadData(n, context);
  }
}
