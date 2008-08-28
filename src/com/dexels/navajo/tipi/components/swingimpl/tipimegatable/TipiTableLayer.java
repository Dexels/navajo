package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.swingclient.components.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiTableLayer
    extends TipiTableBaseLayer {
  private final List<String> columns = new ArrayList<String>();
  private final List<Integer> columnSize = new ArrayList<Integer>();
  private final List<String> columnName = new ArrayList<String>();
  private final List<String> columnTypes = new ArrayList<String>();
  private final Map<String,String> myTypeMap = new HashMap<String,String>();
  private String remarkBorder = null;
  private boolean columnsButtonVisible = false;
  private boolean filtersVisible = false;
  private boolean useScrollBars = true;
  private boolean headerVisible = true;
  private boolean readOnly = true;
  private boolean sortable = true;

  private int rowHeight = 16;
  private final Map<Integer,String> aggregateMap = new HashMap<Integer,String>();
  private final Map<Integer,Double> columnDividers = new HashMap<Integer,Double>();
  private final List<ConditionalRemark> conditionalRemarks = new ArrayList<ConditionalRemark>();
private MessageTablePanel myTablePanel;
  public TipiTableLayer(TipiMegaTable tmt) {
    super(tmt);
  }

  public void loadLayer(XMLElement elt) {
    super.loadLayer(elt);
    columns.clear();
    columnSize.clear();
    columnName.clear();
    columnTypes.clear();
    myTypeMap.clear();
//    flushAggregateValues();
    columnsButtonVisible = elt.getBooleanAttribute("columnsButtonVisible", "true", "false", false);
    filtersVisible = elt.getBooleanAttribute("filtersVisible", "true", "false", false);
    useScrollBars = elt.getBooleanAttribute("useScrollBars", "true", "false", true);
    headerVisible = elt.getBooleanAttribute("headerVisible", "true", "false", true);
    sortable = elt.getBooleanAttribute("sortable", "true", "false", true);

    readOnly = elt.getBooleanAttribute("readOnly", "true", "false", false);
    List<XMLElement> children = elt.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = children.get(i);
      String name = child.getName();
      if (name.equals("remarks")) {
        loadRemarks(i, child);
      }
      if (name.equals("column")) {
        loadColumn(i, child);
      }
      if (child.getName().equals("columndivider")) {
        double width = child.getDoubleAttribute("width");
        int index = child.getIntAttribute("index");
        columnDividers.put(new Integer(index), new Double(width));
      }
    }
  }

  public void updateLayer() {
  }

  private final void loadRemarks(int index, XMLElement child) {
    remarkBorder = (String) child.getAttribute("border");
    List<XMLElement> remarks = child.getChildren();
    for (int j = 0; j < remarks.size(); j++) {
      XMLElement remark = remarks.get(j);
      String condition = (String) remark.getAttribute("condition");
      String remarkString = (String) remark.getAttribute("remark");
      String colorString = (String) remark.getAttribute("color");
      String fontString = (String) remark.getAttribute("font");
      addConditionalRemark(remarkString, condition, colorString, fontString);
    }
  }

  public void addConditionalRemark(String remark, String condition, String c, String font) {
    ConditionalRemark cr = new ConditionalRemark(myTable, remark, condition, c, font);
    conditionalRemarks.add(cr);
  }

  private final void loadColumn(int index, XMLElement child) {
    String name = (String) child.getAttribute("name");
    columns.add(name);
    int size = child.getIntAttribute("size", -1);
    String aggr = child.getStringAttribute("aggregate");
    if (aggr != null) {
      aggregateMap.put(new Integer(index), aggr);
    }
    String label = child.getStringAttribute("label");
    String typeHint = child.getStringAttribute("typeHint");
    myTypeMap.put(name, typeHint);
//    try {
//      Operand result = myTable.getContext().evaluate(label,myTable,null);
//      System.err.println("Label evaluated: "+result);
//      columnName.add(result.value);
//    }
//    catch (Throwable ex) {
//      System.err.println("Can not evaluate column header. Did you use quotes? Switching to backup");
//    }

    columnTypes.add(typeHint);
    columnName.add(label);
    columnSize.add(new Integer(size));
  }

  private final void updateTableColumns(final MessageTablePanel mtp) {
		Runnable invocation = new Runnable(){

			public void run() {
				  mtp.createColumnModel();
				    for (int i = 0; i < columnSize.size(); i++) {
				      int ii = columnSize.get(i).intValue();
				      final int index = i;
				      final int value = ii;
				      mtp.setColumnWidth(index, value);
				    }
//				    mtp.getTable().loadColumnSizes();
				    mtp.getTable().createDefaultColumnsFromModel();

			}};
			SwingUtilities.invokeLater(invocation);
  }
  public void loadData(final Navajo n, final Message current, Stack<TipiTableBaseLayer> layerStack, JComponent currentPanel) {
    final MessageTableFooterRenderer myFooterRenderer = new MessageTableFooterRenderer(myTable);
    final TipiMessageTablePanel mtp = new TipiMessageTablePanel(myTable.getContext());
    myTablePanel = mtp;
    JPanel inbetweenPanel = new JPanel();
    inbetweenPanel.setLayout(new BorderLayout());
    currentPanel.add(inbetweenPanel, BorderLayout.CENTER);
    inbetweenPanel.add(mtp, BorderLayout.CENTER);
    setupTable(mtp);
    int i = 0;
    for (Iterator<String> iter = columnTypes.iterator(); iter.hasNext(); ) {
      String item = iter.next();
      mtp.setTypeHint( columnName.get(i), item);
//      System.err.println("Setting type hint: " + (String) columnName.get(i) + " - " + item);
      i++;
    }
    final RemarkPanel remarkPanel = createRemarkPanel(inbetweenPanel, current, mtp);
    myTable.addTableInstance(mtp, myFooterRenderer, remarkPanel, this);
    for (Iterator<Integer> iter = aggregateMap.keySet().iterator(); iter.hasNext(); ) {
      Integer item = iter.next();
      myFooterRenderer.addAggregate(item.intValue(), aggregateMap.get(item));
    }
    mtp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("table", mtp);
        m.put("selected", mtp.getSelectedMessage());
        try {
          myTable.performTipiEvent("onActionPerformed", m, false);
        }
        catch (TipiException ex) {
          ex.printStackTrace();
        }
      }
    });
    Message tableData = current.getMessage(messagePath);
    // If a table definition has been found:
//    System.err.println("Found definition.....");
    if (tableData.getDefinitionMessage() != null) {
      Message def = tableData.getDefinitionMessage();
      for (int j = 0; j < columns.size(); j++) {
        String column = columns.get(j);
        String label = columnName.get(j);
        Property p = def.getProperty(column);
        Object labelString = null;
        try {
          Operand evalLabel = myTable.getContext().evaluate(label, myTable, null, tableData);
          if (evalLabel != null) {
            labelString = evalLabel.value;
          }
        }
        catch (Exception ex) {
          labelString = null;
        }
        if (p != null) {
          mtp.addColumn(p.getName(), labelString == null ? p.getDescription() : labelString.toString(), true);
        }
      }
    }
    else {
      if (tableData.getArraySize() > 0) {
        Message first = tableData.getMessage(0);
        for (int j = 0; j < columns.size(); j++) {
          String column = columns.get(j);
          String label = columnName.get(j);
          Property p = first.getProperty(column);
          Object labelString = null;
          try {
            Operand evalLabel = myTable.getContext().evaluate(label, myTable, null, tableData);
            if (evalLabel != null) {
              labelString = evalLabel.value;
            }
          }
          catch (Exception ex) {
            labelString = null;
          }
          if (p != null) {
            mtp.addColumn(p.getName(), labelString == null ? p.getDescription() : labelString.toString(),true);
          }
        }
      }
    }
    if (!aggregateMap.isEmpty()) {
      mtp.setFooterRenderer(myFooterRenderer);
    }
    mtp.setMessage(tableData);
    updateTableColumns(mtp);
    mtp.updateTableSize();
    
    remarkPanel.updateConditionalRemarks();
//    System.err.println("MegaTable layer :"+mtp.getPreferredSize());
//    updateConditionalRemarks(remarkPanel, current);
  }

  private final void setupTable(MessageTablePanel mtp) {
    mtp.setColumnsVisible(columnsButtonVisible);
    mtp.setFiltersVisible(filtersVisible);
    mtp.setUseScrollBars(useScrollBars);
    mtp.setHeaderVisible(headerVisible);
    mtp.setReadOnly(readOnly);
    mtp.setSortingAllowed(sortable);
    mtp.setShowRowHeaders(false);
//    mtp.setC
  if (rowHeight > 0) {
      mtp.setRowHeight(rowHeight);
    }
    for (Iterator<Integer> iter = columnDividers.keySet().iterator(); iter.hasNext(); ) {
      Integer item = iter.next();
      Double size = columnDividers.get(item);
      mtp.addColumnDivider(item.intValue(),(float)size.doubleValue());
    }
  }

  public String getTypeHint(String id) {
    return myTypeMap.get(id);
  }

  public void setTypeHint(String id, String type) {
    myTypeMap.put(id, type);
  }

  public XMLElement store() {
    XMLElement newElt = super.store();
    newElt.setAttribute("type", "table");
    newElt.setAttribute("columnsButtonVisible", columnsButtonVisible ? "true" : "false");
    newElt.setAttribute("filtersVisible", filtersVisible ? "true" : "false");
    newElt.setAttribute("useScrollBars", useScrollBars ? "true" : "false");
    newElt.setAttribute("headerVisible", headerVisible ? "true" : "false");
    newElt.setAttribute("sortable", sortable ? "true" : "false");

    for (int i = 0; i < columns.size(); i++) {
      XMLElement xxx = new CaseSensitiveXMLElement();
      xxx.setName("column");
      String columnId = columns.get(i);
      String type = getTypeHint(columnId);
      xxx.setAttribute("name", columnId);
      xxx.setIntAttribute("size", columnSize.get(i).intValue());
      String label = columnName.get(i);
      if (label != null) {
        xxx.setAttribute("label", label);
      }
      String aggr = aggregateMap.get(new Integer(i));
      if (aggr != null) {
        xxx.setAttribute("aggregate", aggr);
      }
      if (type != null) {
        xxx.setAttribute("typeHint", type);
      }
      newElt.addChild(xxx);
    }
    XMLElement remarks = new CaseSensitiveXMLElement();
    remarks.setName("remarks");
    remarks.setAttribute("border", remarkBorder);
    newElt.addChild(remarks);
    for (int i = 0; i < conditionalRemarks.size(); i++) {
      ConditionalRemark current = conditionalRemarks.get(i);
      XMLElement rem = new CaseSensitiveXMLElement();
      rem.setName("remark");
      rem.setAttribute("remark", current.getRemark());
      rem.setAttribute("condition", current.getCondition());
      rem.setAttribute("color", current.getColor());
      rem.setAttribute("font", current.getFont());
      remarks.addChild(rem);
    }

    for (Iterator<Integer> iter = columnDividers.keySet().iterator(); iter.hasNext(); ) {
      Integer item = iter.next();
      Double width = columnDividers.get(item);
      XMLElement div = new CaseSensitiveXMLElement();
      div.setName("columndivider");
      div.setAttribute("index", ""+item.intValue());
      div.setAttribute("width", ""+width.doubleValue());
      newElt.addChild(div);
    }
    return newElt;
  }

  private RemarkPanel createRemarkPanel(JComponent parentPanel, Message currentMessage, MessageTablePanel mtp) {
    RemarkPanel remarkPanel = new RemarkPanel(myTable,  currentMessage, conditionalRemarks);
    Operand r = myTable.getContext().evaluate(remarkBorder, myTable, null, currentMessage);
    Border b = r == null ? null : (Border) r.value;
    if (b != null) {
      remarkPanel.setBorder(b);
    }
    remarkPanel.setVisible(false);
    remarkPanel.setLayout(new GridBagLayout());
    parentPanel.add(remarkPanel, BorderLayout.SOUTH);
    parentPanel.revalidate();
    return remarkPanel;
  }
  
  public int getCurrentSelection() {
      if (myTablePanel!=null) {
          System.err.println("Getting selection of a table. Got: "+myTablePanel.getSelectedRow());
        return myTablePanel.getSelectedRow();
    }
      System.err.println("Nothing selected in table.");
      return -1;
  }

  public void setCurrentSelection(int s) {
      if (myTablePanel!=null) {
          System.err.println("Table layer. Table found. setting to: "+s);
          if (s!=-1) {
              myTablePanel.setSelectedRow(s);
        
        } else {
            System.err.println("Ignoring. No row selected");
        }
          
          
              }
      
  }
}
