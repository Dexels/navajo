package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
//import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.
    MessageTableFooterRenderer;
import com.dexels.navajo.swingclient.components.*;
import javax.swing.event.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.parser.*;
import javax.swing.border.*;
import java.awt.event.*;
import com.dexels.navajo.tipi.*;

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
  private String remarkBorder = null;
  private boolean columnsButtonVisible = false;
  private boolean filtersVisible = false;
  private boolean useScrollBars = true;
  private boolean headerVisible = true;
  private boolean readOnly = true;
  private int rowHeight = 15;
  private final Map aggregateMap = new HashMap();
  private final ArrayList conditionalRemarks = new ArrayList();
  public TipiTableLayer(TipiMegaTable tmt) {
    super(tmt);
  }

  public void loadLayer(XMLElement elt) {
    super.loadLayer(elt);
    columns.clear();
    columnSize.clear();
    columnName.clear();
//    flushAggregateValues();
    columnsButtonVisible = elt.getBooleanAttribute("columnsButtonVisible",
        "true", "false", false);
    filtersVisible = elt.getBooleanAttribute("filtersVisible", "true", "false", false);
    useScrollBars = elt.getBooleanAttribute("useScrollBars", "true", "false", true);
    headerVisible = elt.getBooleanAttribute("headerVisible", "true", "false", true);
    readOnly = elt.getBooleanAttribute("readOnly", "true", "false", true);

    Vector children = elt.getChildren();

    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      String name = child.getName();
      if (name.equals("remarks")) {
        loadRemarks(i, child);
      }
      if (name.equals("column")) {
        loadColumn(i, child);
      }
    }
  }

  private final void loadRemarks(int index, XMLElement child) {
    remarkBorder = (String) child.getAttribute("border");
    Vector remarks = child.getChildren();
    for (int j = 0; j < remarks.size(); j++) {
      XMLElement remark = (XMLElement) remarks.elementAt(j);
      String condition = (String) remark.getAttribute("condition");
      String remarkString = (String) remark.getAttribute("remark");
      String colorString = (String) remark.getAttribute("color");
      String fontString = (String) remark.getAttribute("font");
      addConditionalRemark(remarkString, condition, colorString, fontString);
    }
  }

  public void addConditionalRemark(String remark, String condition, String c,
                                   String font) {
    ConditionalRemark cr = new ConditionalRemark(myTable, remark, condition, -1,
                                                 c, font);
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
//    try {
//      Operand result = myTable.getContext().evaluate(label,myTable,null);
//      System.err.println("Label evaluated: "+result);
//      columnName.add(result.value);
//    }
//    catch (Throwable ex) {
//      System.err.println("Can not evaluate column header. Did you use quotes? Switching to backup");
//    }
    columnName.add(label);
    columnSize.add(new Integer(size));
  }

  private final void updateTableColumns(final MessageTablePanel mtp) {
    mtp.createColumnModel();
    for (int i = 0; i < columnSize.size(); i++) {
      int ii = ( (Integer) columnSize.get(i)).intValue();
      final int index = i;
      final int value = ii;
      System.err.println("Setting column: " + i + " to: " + ii);
      mtp.setColumnWidth(index, value);
    }
  }

  public void loadData(final Navajo n, final Message current, Stack layerStack,
                       JComponent currentPanel) {
    final MessageTableFooterRenderer myFooterRenderer = new
        MessageTableFooterRenderer(myTable);
    final MessageTablePanel mtp = new MessageTablePanel();
    JPanel inbetweenPanel = new JPanel();
    inbetweenPanel.setLayout(new BorderLayout());
    currentPanel.add(inbetweenPanel, BorderLayout.CENTER);
    inbetweenPanel.add(mtp, BorderLayout.CENTER);
//    mtp.setFooterRenderer(myFooterRenderer);
    setupTable(mtp);
    final JComponent remarkPanel = createRemarkPanel(inbetweenPanel, current);
    for (Iterator iter = aggregateMap.keySet().iterator(); iter.hasNext(); ) {
      Integer item = (Integer) iter.next();
      myFooterRenderer.addAggregate(item.intValue(),
                                    (String) aggregateMap.get(item));
    }
//    if (conditionalRemarks.size() > 0) {
//      remarkPanel = createRemarkPanel(inbetweenPanel, current);
//    }
    mtp.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent ce) {
//        myFooterRenderer.propUpdate();
        myFooterRenderer.flushAggregateValues();
        updateConditionalRemarks(remarkPanel, current);
        mtp.repaintHeader();
        mtp.revalidate();
        mtp.repaint();
      }
    });
    mtp.addCellEditorListener(new CellEditorListener() {
      public void editingStopped(ChangeEvent ce) {
        try {
          current.refreshExpression();
          System.err.println("Refreshed: " + current.getFullMessageName());
//          n.refreshExpression();
          mtp.fireDataChanged();
        }
        catch (NavajoException ex) {
          ex.printStackTrace();
        }
        myFooterRenderer.flushAggregateValues();
        updateConditionalRemarks(remarkPanel, current);
        mtp.repaintHeader();
        mtp.revalidate();
        mtp.repaint();
      }

      public void editingCanceled(ChangeEvent ce) {}
    });

    mtp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Map m = new HashMap();
        m.put("table",mtp);
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
    System.err.println("Found definition.....");
    if (tableData.getDefinitionMessage() != null) {
      Message def = tableData.getDefinitionMessage();
      for (int j = 0; j < columns.size(); j++) {
        String column = (String) columns.get(j);
        String label = (String) columnName.get(j);
        Property p = def.getProperty(column);
        Object labelString = null;
        try {
          Operand evalLabel = myTable.getContext().evaluate(label, myTable, null,
              tableData);
          if (evalLabel != null) {
            labelString = evalLabel.value;
          }
        }
        catch (Exception ex) {
          labelString = null;
        }
        if (p != null) {
          mtp.addColumn(p.getName(),
                        labelString == null ? p.getDescription() : labelString.toString(),
                        p.isDirIn());
        }
      }
    }
    else {
      if (tableData.getArraySize() > 0) {
        Message first = tableData.getMessage(0);
        for (int j = 0; j < columns.size(); j++) {
          String column = (String) columns.get(j);
          String label = (String) columnName.get(j);
          Property p = first.getProperty(column);
          Object labelString = null;
          try {
            Operand evalLabel = myTable.getContext().evaluate(label, myTable, null,
                tableData);
            if (evalLabel != null) {
              labelString = evalLabel.value;
            }
          }
          catch (Exception ex) {
            labelString = null;
          }
          if (p != null) {
            mtp.addColumn(p.getName(),
                          labelString == null ? p.getDescription() : labelString.toString(),
                          p.isDirIn());
          }
        }
//      if (tableData.getArraySize() > 0) {
//        Message first = tableData.getMessage(0);
//        for (int j = 0; j < columns.size(); j++) {
//          String column = (String) columns.get(j);
//          String label = (String) columnName.get(j);
//          Property p = first.getProperty(column);
//          if (p != null) {
//            mtp.addColumn(p.getName(),
//                          label == null ? p.getDescription() : label,
//                          p.isDirIn());
//          }
//        }
//      }
      }
    }
    if (!aggregateMap.isEmpty()) {
      mtp.setFooterRenderer(myFooterRenderer);

    }
    mtp.setMessage(tableData);
    updateTableColumns(mtp);
    updateConditionalRemarks(remarkPanel, current);
  }

  public void updateConditionalRemarks(JComponent remarkPanel, Message mm) {
    if (remarkPanel == null || conditionalRemarks.size() == 0) {
      return;
    }
    remarkPanel.removeAll();
    int complied = 0;
    for (int i = 0; i < conditionalRemarks.size(); i++) {
      ConditionalRemark current = (ConditionalRemark) conditionalRemarks.get(i);
      Operand oo = myTable.getContext().evaluate(current.getCondition(),
                                                 myTable, null, mm);
      boolean complies = false;
      if (oo != null && oo.value != null) {
        Boolean b = (Boolean) oo.value;
        complies = b.booleanValue();
      }
      if (complies) {
        Operand o = myTable.getContext().evaluate(current.getRemark(), myTable, null,
                                                  mm);
        Operand q = myTable.getContext().evaluate(current.getColor(), myTable, null,
                                                  mm);
        Operand r = myTable.getContext().evaluate(current.getFont(), myTable, null,
                                                  mm);
        Color c = q == null ? null : (Color) q.value;
        Font f = r == null ? null : (Font) r.value;
//        Operand o = evaluate(current.getRemark(),this,null);
        remarkPanel.add(createRemark("" + o.value, c, f),
                        new GridBagConstraints(0, complied, 1, 1, 1.0, 0.0,
                                               GridBagConstraints.WEST,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(1, 1, 1, 1), 0, 0));
        complied++;
      }
    }
    remarkPanel.setVisible(complied > 0);
    remarkPanel.revalidate();
//    mm.revalidate();
  }

  private final void setupTable(MessageTablePanel mtp) {
    mtp.setColumnsVisible(columnsButtonVisible);
    mtp.setFiltersVisible(filtersVisible);
    mtp.setUseScrollBars(useScrollBars);
    mtp.setHeaderVisible(headerVisible);
    mtp.setReadOnly(readOnly);
    if (rowHeight > 0) {
      mtp.setRowHeight(rowHeight);
    }
  }

  private Component createRemark(String remark, Color c, Font f) {
    JLabel ll = new JLabel(remark);
//    ll.setFont(ll.getFont().deriveFont(20.0f));
    if (f != null) {
      ll.setFont(f);
    }
    if (c != null) {
      ll.setForeground(c);
    }
    return ll;
  }

  public XMLElement store() {
    XMLElement newElt = super.store();
    newElt.setAttribute("type", "table");
    newElt.setAttribute("columnsButtonVisible",
                        columnsButtonVisible ? "true" : "false");
    newElt.setAttribute("filtersVisible", filtersVisible ? "true" : "false");
    newElt.setAttribute("useScrollBars", useScrollBars ? "true" : "false");
    newElt.setAttribute("headerVisible", headerVisible ? "true" : "false");
    for (int i = 0; i < columns.size(); i++) {
      XMLElement xxx = new CaseSensitiveXMLElement();
      xxx.setName("column");
      xxx.setAttribute("name", ( (String) columns.get(i)));
      xxx.setIntAttribute("size", ( (Integer) columnSize.get(i)).intValue());
      String label = (String) columnName.get(i);
      if (label != null) {
        xxx.setAttribute("label", label);
      }
      String aggr = (String) aggregateMap.get(new Integer(i));
      if (aggr != null) {
        xxx.setAttribute("aggregate", aggr);
      }
      newElt.addChild(xxx);
    }
    XMLElement remarks = new CaseSensitiveXMLElement();
    remarks.setName("remarks");
    remarks.setAttribute("border", remarkBorder);
    newElt.addChild(remarks);
    for (int i = 0; i < conditionalRemarks.size(); i++) {
      ConditionalRemark current = (ConditionalRemark) conditionalRemarks.get(i);
      XMLElement rem = new CaseSensitiveXMLElement();
      rem.setName("remark");
      rem.setAttribute("remark", current.getRemark());
      rem.setAttribute("condition", current.getCondition());
      rem.setAttribute("color", current.getColor());
      rem.setAttribute("font", current.getFont());
      remarks.addChild(rem);
    }
    return newElt;
  }

  private JComponent createRemarkPanel(JComponent parentPanel,
                                       Message currentMessage) {
    JPanel remarkPanel = new JPanel();
    Operand r = myTable.getContext().evaluate(remarkBorder, myTable, null,
                                              currentMessage);
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
}
