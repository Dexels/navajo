package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
//import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.MessageTableFooterRenderer;
import com.dexels.navajo.swingclient.components.*;
import javax.swing.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiTableLayer
    extends TipiMegaTableLayer {
  private final ArrayList columns = new ArrayList();
  private final ArrayList columnSize = new ArrayList();
  private final ArrayList columnName = new ArrayList();

  private boolean columnsButtonVisible = false;
  private boolean filtersVisible = false;
  private boolean useScrollBars = true;
  private boolean headerVisible = true;
  private int rowHeight = 15;

  private final Map aggregateMap = new HashMap();

  public TipiTableLayer(TipiMegaTable tmt) {
    super(tmt);
  }


  public void loadLayer(XMLElement elt) {
    super.loadLayer(elt);
    columns.clear();
    columnSize.clear();
    columnName.clear();
//    flushAggregateValues();

    columnsButtonVisible = elt.getBooleanAttribute("columnsButtonVisible","true","false",false);
    filtersVisible = elt.getBooleanAttribute("filtersVisible","true","false",false);
    useScrollBars = elt.getBooleanAttribute("useScrollBars","true","false",true);
    headerVisible = elt.getBooleanAttribute("headerVisible","true","false",true);

    Vector children = elt.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      loadColumn(i,child);
    }
  }

  private void loadColumn(int index, XMLElement child) {
    String name = (String) child.getAttribute("name");
    columns.add(name);
    int size = child.getIntAttribute("size", -1);
    String aggr = child.getStringAttribute("aggregate");
    if (aggr!=null) {
      aggregateMap.put(new Integer(index),aggr);
    }
    String label = child.getStringAttribute("label");
    columnName.add(label);
    columnSize.add(new Integer(size));
  }

  private void updateTableColumns(final MessageTablePanel mtp) {
    mtp.createColumnModel();
    for (int i = 0; i < columnSize.size(); i++) {
      int ii = ( (Integer) columnSize.get(i)).intValue();
      final int index = i;
      final int value = ii;
      System.err.println("Setting column: " + i + " to: " + ii);
      mtp.setColumnWidth(index, value);
    }
  }

  public void loadData(Navajo n, Message current, Stack layerStack, JComponent currentPanel) {
//    System.err.println("Table. Loading with message: "+current.getName());
//    System.err.println("My messagePatH: "+messagePath);
//    System.err.println("Talbe. My stack: "+layerStack);
//    System.err.println("MESSAGE:");
    final MessageTableFooterRenderer myFooterRenderer = new MessageTableFooterRenderer(myTable);
    final MessageTablePanel mtp = new MessageTablePanel();
    mtp.setFooterRenderer(myFooterRenderer);
//    mtp.setUseScrollBars();
    currentPanel.add(mtp,BorderLayout.CENTER);
    setupTable(mtp);
    for (Iterator iter = aggregateMap.keySet().iterator(); iter.hasNext(); ) {
      Integer item = (Integer)iter.next();
      myFooterRenderer.addAggregate(item.intValue(),(String)aggregateMap.get(item));
    }

    mtp.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent ce) {
        myFooterRenderer.propUpdate();
        myFooterRenderer.flushAggregateValues();
        mtp.repaintHeader();
        mtp.revalidate();
        mtp.repaint();
      }
    });

//    System.err.println("\n\nCurrent: ");
//    current.write(System.err);
//    System.err.println("\n\n");
    Message tableData = current.getMessage(messagePath);
//    System.err.println("\n\nTableData: ");
//    tableData.write(System.err);
//    System.err.println("\n\n");

    if (tableData.getArraySize() > 0) {
      Message first = tableData.getMessage(0);
      for (int j = 0; j < columns.size(); j++) {
        String column = (String) columns.get(j);
        String label = (String) columnName.get(j);
        Property p = first.getProperty(column);
        if (p != null) {
          mtp.addColumn(p.getName(), label==null?p.getDescription():label, p.isDirIn());
        }
      }
    }
    mtp.setFooterRenderer(myFooterRenderer);


    mtp.setMessage(tableData);
    updateTableColumns(mtp);
  }


  private void setupTable(MessageTablePanel mtp) {
    mtp.setColumnsVisible(columnsButtonVisible);
    mtp.setFiltersVisible(filtersVisible);
    mtp.setUseScrollBars(useScrollBars);
    mtp.setHeaderVisible(headerVisible);
    if (rowHeight > 0) {
      mtp.setRowHeight(rowHeight);
    }
//    for (int i = 0; i < columns.size(); i++) {
//      mtp.addColumn((String)columns.get(i),""aap,false,100);
//    }
  }


  public XMLElement store() {

    XMLElement newElt = super.store();
    newElt.setAttribute("type","table");
    newElt.setAttribute("columnsButtonVisible",columnsButtonVisible?"true":"false");
     newElt.setAttribute("filtersVisible",filtersVisible?"true":"false");
     newElt.setAttribute("useScrollBars",useScrollBars?"true":"false");
     newElt.setAttribute("headerVisible",headerVisible?"true":"false");


    for (int i = 0; i < columns.size(); i++) {
      XMLElement xxx = new CaseSensitiveXMLElement();
      xxx.setName("column");
      xxx.setAttribute("name",((String)columns.get(i)));
      xxx.setIntAttribute("size",((Integer)columnSize.get(i)).intValue());
      String label = (String)columnName.get(i);
      if (label!=null) {
        xxx.setAttribute("label",label);
      }
      String aggr = (String)aggregateMap.get(new Integer(i));
      if (aggr!=null) {
        xxx.setAttribute("aggregate",aggr);
      }
      newElt.addChild(xxx);
    }
    return newElt;
  }

//  public void addAggregate(int columnIndex, String expression) {
//    if (myFooterRenderer==null) {
//      myFooterRenderer = new MessageTableFooterRenderer(myTable);
//    }
//    myFooterRenderer.addAggregate(columnIndex,expression);
//  }
//  public void flushAggregateValues() {
//    if (myFooterRenderer!=null) {
//      myFooterRenderer.flushAggregateValues();
//    }
//  }
//
//
//  public void removeAggregate(int columnIndex) {
//    if (myFooterRenderer!=null) {
//      myFooterRenderer.removeAggregate(columnIndex);
//    }
//  }
//
//  public void removeAllAggregate() {
//    if (myFooterRenderer!=null) {
//      myFooterRenderer.removeAllAggregate();
//    }
//  }
//
//  public String getAggregateFunction(int column) {
//    if (myFooterRenderer!=null) {
//      return myFooterRenderer.getAggregateFunction(column);
//    }
//    return null;
//  }

}
