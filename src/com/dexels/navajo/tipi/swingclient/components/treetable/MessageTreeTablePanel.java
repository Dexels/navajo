package com.dexels.navajo.tipi.swingclient.components.treetable;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.components.*;

import java.lang.reflect.*;
//import com.dexels.sportlink.client.swing.*;

public class MessageTreeTablePanel
    extends BasePanel
    implements Ghostable {
  private MessageTreeTable messageTable = new MessageTreeTable();
  JScrollPane jScrollPane1 = new JScrollPane();
  BorderLayout borderLayout1 = new BorderLayout();
  private ArrayList columnIds = new ArrayList();
  private ArrayList columnLabels = new ArrayList();
  private ArrayList columnEnabled = new ArrayList();
  private boolean ghosted = false;
  private boolean enabled = true;
  private BasePanel columnsPanel = new BasePanel();
  private FilterPanel filterPanel = new FilterPanel();

  public MessageTreeTablePanel() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setMessage(Message m) {
    setMessage(m, new String[] {});
    for (int i = 0; i < columnIds.size(); i++) {
      messageTable.addColumn( (String) columnIds.get(i),
                             (String) columnLabels.get(i),
                             ( (Boolean) columnEnabled.get(i)).booleanValue());
    }

  }

  public final void setFiltersVisible(boolean b) {
    filterPanel.setVisible(b);
  }

  public void commit() {
    super.commit();
    Message m = getMessage();
  }

  public void setModel(TreeTableModel tta) {
    messageTable.setModel(tta);
  }

  public MessageTreeTableModel getModel(){
    return messageTable.getMessageModel();
  }

  public Message getMessage() {
    return (Message) messageTable.getMessageModel().getRoot();
  }

//  public void setSelectedMessage(Message m) {
//    messageTable.setSelectedMessage(m);
//  }

  public void addColumn(String id, String label, boolean editable) {
    messageTable.addColumn(id, label, editable);
    columnIds.add(id);
    columnLabels.add(label);
    columnEnabled.add(Boolean.valueOf(editable));
  }

  public void setHeaderTextResource(ResourceBundle r) {
    messageTable.setHeaderTextResource(r);
  }


  public void setColumnWidth(final int id, final int size) {
//    MainApplication.runSyncInEventThread(new Runnable() {
//      public void run() {
        messageTable.setColumnWidth(id, size);
//      }
//    });
  }

  public void setMessage(final Message m, final String[] columns,
                         final String[] exclustion) {
//    MainApplication.runSyncInEventThread(new Runnable() {
//      public void run() {

        messageTable.setMessage(m, columns, exclustion);
        if (LazyMessage.class.isInstance(m)) {
          MessageTreeTableModel mttm = messageTable.getMessageModel();
          LazyMessage lm = (LazyMessage) m;
          lm.addMessageListener(mttm);
          lm.startUpdateThread();
//        }
//        updateUI();
      }
//    });

  }

  public void setMessage(final Message m, final String[] columns) {
    // todo: Remove listeners from previous message?
    if (SwingUtilities.isEventDispatchThread()) {
      messageTable.setMessage(m, columns);
      MessageTreeTableModel mttm = messageTable.getMessageModel();
      if (LazyMessage.class.isInstance(m)) {
        LazyMessage lm = (LazyMessage) m;
        lm.addMessageListener(mttm);
        lm.startUpdateThread();
      }
    } else {
      try {
        SwingUtilities.invokeAndWait(new Runnable() {
          public void run() {

            messageTable.setMessage(m, columns);
            MessageTreeTableModel mttm = messageTable.getMessageModel();
            if (LazyMessage.class.isInstance(m)) {
              LazyMessage lm = (LazyMessage) m;
              lm.addMessageListener(mttm);
              lm.startUpdateThread();
            }
//        updateUI();
          }
        });
      }
      catch (InvocationTargetException ex) {
        ex.printStackTrace();
      }
      catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
  }

  public Message getFlatVersion(){
    MessageTreeTableModel trm = getModel();
    Object root = trm.getRoot();
    Message data = NavajoFactory.getInstance().createMessage(NavajoFactory.getInstance().createNavajo(), "Data", Message.MSG_TYPE_ARRAY);

    for(int i = 0;i < trm.getChildCount(root);i++) {
      Object child = trm.getChild(root, i);
      addProperties(data, child, trm);
    }
    return data;
  }

  private void addProperties(Message m, Object node, TreeTableModel trm) {
    try {
      for(int i = 0;i < trm.getChildCount(node);i++) {
        Message row = NavajoFactory.getInstance().createMessage(m.getRootDoc(), "Data", Message.MSG_TYPE_ARRAY_ELEMENT);
        Object deepestNode = trm.getChild(node, i);
        for(int j = 0;j < trm.getColumnCount();j++) {
          int width = 60;
          Object v = trm.getValueAt(node, j);
          Object vdeep = trm.getValueAt(deepestNode, j);
          String value = (v != null ? v.toString() : "");
          if("".equals(value)) {
            value = (vdeep != null ? vdeep.toString() : "");
          }
          String name = trm.getColumnName(j);
          if("".equals(name) || name == null || " ".equals(name)){
            name = "--"+j;
          }
          if(j == 0){
            Property p = NavajoFactory.getInstance().createProperty(m.getRootDoc(), "--x", "string", node.toString(), width, " ", "out");
            row.addProperty(p);
            Property q = NavajoFactory.getInstance().createProperty(m.getRootDoc(), "--y", "string", deepestNode.toString(), width, "  ", "out");
            row.addProperty(q);
          } else{
            Property p = NavajoFactory.getInstance().createProperty(m.getRootDoc(), name, "string", value, width, name, "out");
            row.addProperty(p);
          }
        }
        m.addMessage(row);
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }


  private final void jbInit() throws Exception {
    this.setLayout(new GridBagLayout());
    this.add(jScrollPane1,
             new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                                    GridBagConstraints.WEST,
                                    GridBagConstraints.BOTH,
                                    new Insets(0, 0, 0, 0), 0, 0));
    this.add(filterPanel,
             new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0,
                                    GridBagConstraints.WEST,
                                    GridBagConstraints.HORIZONTAL,
                                    new Insets(0, 0, 0, 0), 0, 0));

    filterPanel.setVisible(false);
    jScrollPane1.getViewport().add(messageTable, null);
  }

  public void setNodeColor(String depth, Color c){
    messageTable.setNodeColor(depth, c);
  }

  public Color getNodeColor(String depth){
    return messageTable.getNodeColor(depth);
  }


  public FilterPanel getFilterPanel(){
    return filterPanel;
  }

  public final void addTablePrinter(TablePrintInterface pi) {
    filterPanel.addTablePrinter(pi);
    filterPanel.setTreeTable(this);
  }

  public void addChangeListener(ListSelectionListener l) {
    messageTable.addListSelectionListener(l);
  }

  public void reset() {
    messageTable.reset();
  }

  public void setSelectedRow(int i) {
    messageTable.setSelectedRow(i);
  }

  public Message getSelectedMessage() {
    return messageTable.getSelectedMessage();
  }

  public void addActionListener(ActionListener e) {
    messageTable.addActionListener(e);
  }

  public void removeActionListener(ActionListener e) {
    messageTable.removeActionListener(e);
  }

  public boolean isGhosted() {
    return ghosted;
  }

  public void setGhosted(boolean g) {
    ghosted = g;
    messageTable.setEnabled(enabled && (!ghosted));
    super.setEnabled(enabled && (!ghosted));
  }

  public void setEnabled(boolean e) {
    enabled = e;
    messageTable.setEnabled(enabled && (!ghosted));
    super.setEnabled(enabled && (!ghosted));
  }
}
