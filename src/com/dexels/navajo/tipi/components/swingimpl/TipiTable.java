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
import java.awt.*;

import com.dexels.navajo.parser.*;
import javax.swing.border.*;
import java.awt.print.*;
import java.util.List;

public class TipiTable
    extends TipiSwingDataComponentImpl
    implements ChangeListener {
  private String messagePath = "";
  private MessageTablePanel mm;
  private Map columnAttributes = new HashMap();
  private boolean showHeader = true;
  private final Map columnSize = new HashMap();
  private static final String FILTERMODE_BABY = "baby";
  private static final String FILTERMODE_ADVANCED = "advanced";
  private MessageTableFooterRenderer myFooterRenderer = null;
  private final ArrayList conditionalRemarks = new ArrayList();
  private final Map columnDividers = new HashMap();
//  private final List columnLabelList = new ArrayList();
  private Message myMessage = null;
  private JPanel remarkPanel = null;
//  private String remarkTitle = null;
  private String remarkBorder = null;
  private String titleExpression = null;

  private boolean ignoreColumns = false;

  public Object createContainer() {
    MessageTablePanel mm = new MessageTablePanel();
    mm.setShowRowHeaders(false);
    // Don't register actionPerformed, that is done elsewhere.
    mm.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        messageTableSelectionChanged(e);
      }
    });
    mm.addChangeListener(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return mm;
  }

  private final void updateTableColumns(final MessageTablePanel mtp) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        mtp.createColumnModel();
        for (int i = 0; i < columnSize.size(); i++) {
          int ii = ( (Integer) columnSize.get(new Integer(i))).intValue();
          final int index = i;
          final int value = ii;
//          System.err.println("Setting column: " + i + " to: " + ii);
          mtp.setColumnWidth(index, value);
        }
      }
    });
  }

//  private void refreshColumnLabels(Navajo n, Message m) {
//    for (int i = 0; i < columnLabelList.size(); i++) {
//      String label = (String) columnLabelList.get(i);
//      String labelString = (String) columnLabelList.get(i);
//      try {
//        Operand evalLabel = this.getContext().evaluate(labelString, this, null, m);
//        if (evalLabel != null) {
//          labelString = "" + evalLabel.value;
//        }
//      }
//      catch (Exception ex) {
//      }
//      mm.setColumnLabel(i, labelString);
//    }
//  }

  public final void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    mm = (MessageTablePanel) getContainer();
//    columnLabelList.clear();
    mm.removeAllColumns();
    removeAllAggregate();
    columnSize.clear();
    mm.setFooterRenderer(null);
    TipiSwingColumnAttributeParser cap = new TipiSwingColumnAttributeParser();
    messagePath = (String) elm.getAttribute("messagepath");
    if (messagePath != null) {
      if (messagePath.startsWith("'") && messagePath.endsWith("'")) {
        messagePath = messagePath.substring(1, messagePath.length() - 1);
//        System.err.println("MESSAGEPATH: "+messagePath);
      }
    }
    super.load(elm, instance, context);
    Vector children = elm.getChildren();
    int columnCount = 0;
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("column")) {
        ignoreColumns = false;
        String label = (String) child.getAttribute("label");
//        columnLabelList.add(label);
        String name = (String) child.getAttribute("name");
        String editableString = (String) child.getAttribute("editable");
        String aggr = child.getStringAttribute("aggregate");
        String typehint = child.getStringAttribute("typeHint");
        if (aggr != null) {
//          System.err.println("Found aggregate: " + aggr);
          addAggregate(i, aggr);
        }
        boolean editable = "true".equals(editableString);
        int size = child.getIntAttribute("size", -1);
//        System.err.println("Putting size for column # "+columnCount+" to: "+size);
        columnSize.put(new Integer(columnCount), new Integer(size));
//        String sizeString = (String) child.getAttribute("size");
        String labelString = label;
//        System.err.println("Label to evaluate: "+labelString);

        try {
          Operand evalLabel = this.getContext().evaluate(labelString, this, null, null);
          if (evalLabel != null) {

            labelString = "" + evalLabel.value;
            //System.err.println("Label evaluated to: "+labelString);

          } else {
            //System.err.println("Null evaluated label.");
          }
        }
        catch (Exception ex) {
          ex.printStackTrace();
          //System.err.println("Exception while evaluating label: "+label);
        }

        if (size != -1) {
//          int size = Integer.parseInt(sizeString);
          mm.addColumn(name, labelString, editable, size);
        }
        else {
          mm.addColumn(name, labelString, editable);
        }
        if (typehint != null) {
          mm.setTypeHint(name, typehint);
        }
        mm.messageChanged();
        columnCount++;
      }
      if (child.getName().equals("column-attribute")) {
        String name = (String) child.getAttribute("name");
        String type = (String) child.getAttribute("type");
        if (name != null && type != null && !name.equals("") && !type.equals("")) {
          columnAttributes.put(name, cap.parseAttribute(child));
        }
      }
      if (child.getName().equals("remarks")) {
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
      if (child.getName().equals("columndivider")) {
        double width = child.getDoubleAttribute("width");
        int index = child.getIntAttribute("index");
//        mm.addC
        mm.addColumnDivider(index, (float) width);
        columnDividers.put(new Integer(index), new Double(width));
      }
    }
    mm.setColumnAttributes(columnAttributes);
  }

  public XMLElement store() {
    TipiSwingColumnAttributeParser cap = new TipiSwingColumnAttributeParser();
    XMLElement xx = super.store();
    MessageTablePanel mm = (MessageTablePanel) getContainer();
    MessageTableModel mtm = mm.getTable().getMessageModel();
    if (!ignoreColumns) {
      for (int i = 0; i < mtm.getColumnCount(); i++) {
       String id = mtm.getColumnId(i);
       String name = mtm.getColumnName(i);
       boolean isEditable = mtm.isColumnEditable(i);
       XMLElement columnDefinition = new CaseSensitiveXMLElement();
       columnDefinition.setName("column");
       columnDefinition.setAttribute("name", id);
       columnDefinition.setAttribute("label", name);
       columnDefinition.setAttribute("editable", "" + isEditable);
       String typeHint = mm.getTypeHint(id);
       if (typeHint != null) {
         columnDefinition.setAttribute("typeHint", typeHint);
       }
       String aggr = getAggregateFunction(i);
       if (aggr != null) {
         columnDefinition.setAttribute("aggregate", aggr);
       }
//      System.err.println("Getting size for column # "+i);
       Integer sizeInt = (Integer) columnSize.get(new Integer(i));
       if (sizeInt != null) {
         columnDefinition.setIntAttribute("size", (sizeInt.intValue()));
       }
       xx.addChild(columnDefinition);
     }
    }
    for (Iterator iter = columnAttributes.keySet().iterator(); iter.hasNext(); ) {
      String name = (String) iter.next();
      ColumnAttribute ca = (ColumnAttribute) columnAttributes.get(name);
      xx.addChild(cap.storeAttribute(ca));
    }
    XMLElement remarks = new CaseSensitiveXMLElement();
    remarks.setName("remarks");
    remarks.setAttribute("title", titleExpression);
    remarks.setAttribute("border", remarkBorder);
    xx.addChild(remarks);
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
    for (Iterator iter = columnDividers.keySet().iterator(); iter.hasNext(); ) {
      Integer item = (Integer) iter.next();
      XMLElement cdiv = new CaseSensitiveXMLElement();
      cdiv.setName("columndivider");
      cdiv.setAttribute("index", item);
      cdiv.setAttribute("width", columnDividers.get(item));
      xx.addChild(cdiv);
    }
    return xx;
  }

  public void messageTableSelectionChanged(ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) {
      return;
    }
    updateConditionalRemarks();
    try {
      MessageTablePanel mm = (MessageTablePanel) getContainer();
      Map tempMap = new HashMap();
      tempMap.put("selectedIndex", new Integer(mm.getSelectedRow()));
      tempMap.put("selectedMessage", mm.getSelectedMessage());
      performTipiEvent("onSelectionChanged", tempMap, false);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void messageTableActionPerformed(ActionEvent ae) {
    try {
      MessageTablePanel mm = (MessageTablePanel) getContainer();
      Map tempMap = new HashMap();
      tempMap.put("selectedIndex", new Integer(mm.getSelectedRow()));
      tempMap.put("selectedMessage", mm.getSelectedMessage());
      performTipiEvent("onActionPerformed", tempMap, false);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void loadData(final Navajo n, TipiContext tc,String method) throws TipiException {
    super.loadData(n, tc,method);
    //Thread.currentThread().dumpStack();
    flushAggregateValues();
    updateConditionalRemarks();
    final MessageTablePanel mtp = (MessageTablePanel) getContainer();
    if (messagePath != null && n != null) {
      final Message m = n.getMessage(messagePath);
      myMessage = m;
      if (m != null) {
        runSyncInEventThread(new Runnable() {
          public void run() {
//            refreshColumnLabels(n, m);
            // if no columnsdefined:
            if (columnSize.size()==0) {
              mtp.createColumnsFromDef(m);
              ignoreColumns = true;
            }
            mtp.setMessage(m);
//            System.err.println("MEssage set in TitiTable");
//            updateTableColumns(mtp);
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
    if (name.equals("sortable")) {
      mm.setSortingAllowed(Boolean.valueOf(object.toString()).booleanValue());
    }
    if (name.equals("headervisible")) {
      setHeaderVisible(Boolean.valueOf(object.toString()).booleanValue());
    }
    if (name.equals("readOnly")) {
      mm.setReadOnly(Boolean.valueOf(object.toString()).booleanValue());
    }
    if (name.equals("selectedindex")) {
      mm.setSelectedRow( ( (Integer) object).intValue());
//      setColumnsVisible(Boolean.valueOf(object.toString()).booleanValue());
    }
    if (name.equals("rowHeight")) {
      mm.setRowHeight( ( (Integer) object).intValue());
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
    if (name.equals("filtermode")) {
      mm.setFilterMode("" + object);
    }
    if (name.equals("refreshAfterEdit")) {
      mm.setRefreshAfterEdit(Boolean.valueOf(object.toString()).booleanValue());
    }
    if (name.equals("highColor")) {
      mm.setHighColor( (Color) object);
    }
    if (name.equals("lowColor")) {
      mm.setLowColor( (Color) object);
    }
    if (name.equals("selectedColor")) {
      mm.setSelectedColor( (Color) object);
    }
    super.setComponentValue(name, object);
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
        return m;
      }
      if (name.equals("selectedMessages")) {
        ArrayList all = mm.getSelectedMessages();
        if(all != null && all.size() > 0){
          Navajo n = NavajoFactory.getInstance().createNavajo();
          Message array = NavajoFactory.getInstance().createMessage(n, ((Message)all.get(0)).getName(), Message.MSG_TYPE_ARRAY);
          for(int i=0;i<all.size();i++){
//            System.err.println("debug... adding messega");
            Message cur = (Message)all.get(i);
            array.addMessage(cur);
          }
//          array.write(System.err);
          return array;
        }else{
          System.err.println("AAp.. all is null of 0");
        }
      }
      if (name.equals("filteredMessage")) {
        Message m = mm.getMessageAsPresentedOnTheScreen(true);
        return m;
      }
      else if (name.equals("selectedIndex")) {
        if (mm.getSelectedMessage() == null) {
          return new Integer( -1);
        }
        return new Integer(mm.getSelectedMessage().getIndex());
      }
      if (name.equals("rowCount")) {
        return new Integer(mm.getRowCount());
      }
      if (name.equals("columnCount")) {
        return new Integer(mm.getColumnCount());
      }
      return super.getComponentValue(name);
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
      if ("repaint".equals(name)) {
        mm.fireDataChanged();
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
      if ("showEditDialog".equals(name)) {
        Operand title = compMeth.getEvaluatedParameter("title", event);
        try {
          String titleString;
          titleString = title == null ? "Aap" : "" + title.value;
          mm.showEditDialog(titleString,mm.getSelectedRow());
        }
        catch (Exception ex1) {
          ex1.printStackTrace();
        }
      }
    }
    if ("print".equals(name)) {
      print( (Printable) getContainer());
    }
    if ("export".equals(name)) {
      Operand filename = compMeth.getEvaluatedParameter("filename", event);
      Operand delimiter = compMeth.getEvaluatedParameter("delimiter", event);
      mm.getTable().exportTable((String)filename.value, (String)delimiter.value);
    }
    if ("fireAction".equals(name)) {
        try {
            performTipiEvent("onActionPerformed", null, false);
//      for (int i = 0; i < getEventList().size(); i++) {
//        TipiEvent current = (TipiEvent) getEventList().get(i);
//        if (current.isTrigger("onActionPerformed", "aap")) {
//          try {
//            current.performAction(current);
//          }
//          catch (TipiException ex) {
//            ex.printStackTrace();
//          }
//        }
//      }
        } catch (TipiException e) {
             e.printStackTrace();
        }
    }
  }

  public void setColumnDefinitionSavePath(String path) {
    mm.setColumnDefinitionSavePath(path);
  }

  public void stateChanged(ChangeEvent e) {
    Map m = (Map) e.getSource();
//    System.err.println("Event map: "+m);
    flushAggregateValues();
    updateConditionalRemarks();
    mm.repaint();
    try {
      performTipiEvent("onValueChanged", m, false);
    }
    catch (TipiException ex) {
      ex.printStackTrace();
    }
  }

  public void addAggregate(int columnIndex, String expression) {
    if (myFooterRenderer == null) {
      myFooterRenderer = new MessageTableFooterRenderer(this);
    }
    mm.setFooterRenderer(myFooterRenderer);
    myFooterRenderer.addAggregate(columnIndex, expression);
  }

  public void flushAggregateValues() {
    if (myFooterRenderer != null) {
      myFooterRenderer.flushAggregateValues();
    }
  }

  public void removeAggregate(int columnIndex) {
    if (myFooterRenderer != null) {
      myFooterRenderer.removeAggregate(columnIndex);
    }
  }

  public void removeAllAggregate() {
    if (myFooterRenderer != null) {
      myFooterRenderer.removeAllAggregate();
    }
    mm.setFooterRenderer(null);
  }

  public String getAggregateFunction(int column) {
    if (myFooterRenderer != null) {
      return myFooterRenderer.getAggregateFunction(column);
    }
    return null;
  }

  public void updateConditionalRemarks() {
    if (remarkPanel == null || conditionalRemarks.size() == 0) {
      return;
    }
    remarkPanel.removeAll();
    int complied = 0;
    for (int i = 0; i < conditionalRemarks.size(); i++) {
      ConditionalRemark current = (ConditionalRemark) conditionalRemarks.get(i);
      Operand oo = getContext().evaluate(current.getCondition(), this, null, myMessage);
      boolean complies = false;
      if (oo.value != null) {
        Boolean b = (Boolean) oo.value;
        complies = b.booleanValue();
      }
      if (complies) {
        Operand o = myContext.evaluate(current.getRemark(), this, null, mm.getMessage());
        Operand q = myContext.evaluate(current.getColor(), this, null, mm.getMessage());
        Operand r = myContext.evaluate(current.getFont(), this, null, mm.getMessage());
        Color c = q == null ? null : (Color) q.value;
        Font f = r == null ? null : (Font) r.value;
//        Operand o = evaluate(current.getRemark(),this,null);
        remarkPanel.add(createRemark("" + o.value, c, f),
                        new GridBagConstraints(0, complied, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
        System.err.println("COMPLYING:  ");
        complied++;
      }
    }
    remarkPanel.setVisible(complied > 0);
    remarkPanel.revalidate();
    mm.revalidate();
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

  public void addConditionalRemark(String remark, String condition, String c, String font) {
    ConditionalRemark cr = new ConditionalRemark(this, remark, condition, -1, c, font);
    conditionalRemarks.add(cr);
    System.err.println("************************\nCreating remark panel\n********************************\n");
    if (remarkPanel == null) {
      createRemarkPanel();
    }
    System.err.println("size:");
  }

  private final void createRemarkPanel() {
    remarkPanel = new JPanel();
    Operand r = myContext.evaluate(remarkBorder, this, null, mm.getMessage());
    Border b = r == null ? null : (Border) r.value;
    if (b != null) {
      remarkPanel.setBorder(b);
    }
    remarkPanel.setVisible(false);
    remarkPanel.setLayout(new GridBagLayout());
//    mm.add(remarkPanel, BorderLayout.SOUTH);
    mm.add(remarkPanel, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
   mm.revalidate();
  }
}
