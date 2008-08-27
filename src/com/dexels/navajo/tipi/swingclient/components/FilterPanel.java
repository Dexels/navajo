package com.dexels.navajo.tipi.swingclient.components;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.treetable.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class FilterPanel
    extends JPanel {
  static ResourceBundle res = ResourceBundle.getBundle("com.dexels.navajo.tipi.swingclient.components.filterpanelstrings");
  JPanel flipPanel = new JPanel();
  JPanel columnPanel = new JPanel();
  ArrayList printers = new ArrayList();
  JComboBox columnSelectBox = new JComboBox();
  GenericPropertyComponent valueField = new GenericPropertyComponent();
  JButton addButton = new JButton();
  JButton clearButton = new JButton();
  String[] ignoreList;
  private int filterCount = 0;
  private String templateDir, mergedataDir, emailColumn;
  BaseLabel filteredRowCountLabel = new BaseLabel();
  // Specifiy merge data file.

//  public static final String mergeDataFile = System.getProperty("user.home") + System.getProperty("file.separator") + ( (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) ? "" : ".") + "sl-excel-export.csv";

  private MessageTable myTable;
  private MessageTreeTablePanel myTree;
  JButton columnsButton = new JButton();
  JButton columnsSaveButton = new JButton();
  JButton printButton = new JButton();
  JButton excelButton = new JButton();
  JButton wordButton = new JButton();
  JButton emailButton = new JButton();

  private HashMap nameIdMap = new HashMap();
  JComboBox operatorBox = new JComboBox();

  private boolean babyMode = false;

  private Message referenceMessage = null;
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  private String getMergedataFile() {
	 return System.getProperty("user.home") + System.getProperty("file.separator") + ( (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) ? "" : ".") + "sl-excel-export.csv";
  }
  
  public FilterPanel() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    setupAdvancedFilter();
  }

  void jbInit() throws Exception {
    flipPanel.setPreferredSize(new Dimension(700, 35));
//    columnPanel.setPreferredSize(new Dimension(150, 35));
    this.setLayout(new BorderLayout());
    columnsButton.setToolTipText(res.getString("changeColumnToolTip"));
    columnsButton.setIcon(new ImageIcon(FilterPanel.class.getResource("column_preferences.png")));
    columnsButton.setMargin(new Insets(0, 0, 0, 0));
    printButton.setToolTipText(res.getString("printToolTip"));
    printButton.setIcon(new ImageIcon(FilterPanel.class.getResource("print_small.png")));
    printButton.setMargin(new Insets(0, 0, 0, 0));
    printButton.setVisible(false);
    emailButton.setToolTipText("Email");
    emailButton.setIcon(new ImageIcon(FilterPanel.class.getResource("mail_small.png")));
    emailButton.setMargin(new Insets(0, 0, 0, 0));
    emailButton.setVisible(false);

    columnsSaveButton.setIcon(new ImageIcon(FilterPanel.class.getResource("save.png")));
    columnsSaveButton.setToolTipText(res.getString("saveToolTip"));
    columnsSaveButton.setMargin(new Insets(0, 0, 0, 0));
    columnsSaveButton.setVisible(false);
    columnsSaveButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        columnsSaveButton_actionPerformed(e);
      }
    });

    excelButton.setIcon(new ImageIcon(FilterPanel.class.getResource("excel.png")));
    excelButton.setToolTipText(res.getString("excelToolTip"));
    excelButton.setMargin(new Insets(0, 0, 0, 0));
    excelButton.setVisible(false);
    excelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Message data = null;
        if (myTable != null) {
          data = myTable.getMessageAsPresentedOnTheScreen(false);
        }
        if (myTree != null) {
          data = myTree.getFlatVersion();
        }
        if (data != null) {
          MergeUtils.exportMergeData(getMergedataFile(), data, ";");
          MergeUtils.openDocument(getMergedataFile());
        }
      }
    });

    emailButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        sendEmails();
      }
    });

    wordButton.setIcon(new ImageIcon(FilterPanel.class.getResource("word.png")));
    wordButton.setToolTipText(res.getString("wordToolTip"));
    wordButton.setMargin(new Insets(0, 0, 0, 0));
    wordButton.setVisible(false);
    wordButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        openLetter();
      }
    });

    printButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        printPressed(e);
      }
    });
    columnSelectBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        columnSelectBox_actionPerformed(e);
      }
    });
    addButton.setMargin(new Insets(0, 0, 0, 0));
    clearButton.setMargin(new Insets(0, 0, 0, 0));
    this.add(flipPanel, BorderLayout.CENTER);
    this.add(columnPanel, BorderLayout.NORTH);
    flipPanel.setLayout(gridBagLayout1);
    columnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
//    valueField.setPreferredSize(new Dimension(80, 25));
//    valueField.setText("");
    addButton.setText("");
    addButton.setIcon(new ImageIcon(FilterPanel.class.getResource("add-filter.gif")));
    addButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addButton_actionPerformed(e);
      }
    });
    clearButton.setText("");
    clearButton.setIcon(new ImageIcon(FilterPanel.class.getResource("clear-filter.gif")));
    clearButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clearButton_actionPerformed(e);
      }
    });
    columnsButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        columnsButton_actionPerformed(e);
      }
    });
    flipPanel.add(columnSelectBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
    flipPanel.add(operatorBox, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
    flipPanel.add(valueField, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 0, 0));
    flipPanel.add(addButton, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
    flipPanel.add(clearButton, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
    flipPanel.add(filteredRowCountLabel, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 1, 1, 1), 0, 0));
    columnPanel.add(columnsSaveButton);
    columnPanel.add(printButton);
    columnPanel.add(excelButton);
    columnPanel.add(wordButton);
    columnPanel.add(emailButton);
    columnPanel.add(columnsButton);
//    setVisible(false);
  }

  public void openLetter() {
    templateDir = (String) SwingClient.getUserInterface().getPreference(10, null);
    mergedataDir = (String) SwingClient.getUserInterface().getPreference(11, null);
    JFileChooser fc = new JFileChooser();
    fc.setDialogTitle("Open merge-template");
    if (templateDir != null) {
      fc.setCurrentDirectory(new File(templateDir));
    }
    fc.showOpenDialog(this);
    if (fc.getSelectedFile() != null) {
      if (mergedataDir != null) {
        Message data = null;
        if (myTable != null) {
          data = myTable.getMessageAsPresentedOnTheScreen(true);
        }
        if (myTree != null) {
          data = myTree.getFlatVersion();
        }
        if (data != null) {
          MergeUtils.openDocument(mergedataDir + "/merge.dat", data, fc.getSelectedFile().getPath());
        }
      }
    }
  }
  
  
  public void doEmail() {
      sendEmails();
  }
  public void doWord() {
      openLetter();
  }
  public void doExcel() {
      Message data = null;
      if (myTable != null) {
        data = myTable.getMessageAsPresentedOnTheScreen(false);
      }
      if (myTree != null) {
        data = myTree.getFlatVersion();
      }
      if (data != null) {
        MergeUtils.exportMergeData(getMergedataFile(), data, ";");
        MergeUtils.openDocument(getMergedataFile());
      }
	  
  }
  public void doSaveColumns() {
      columnsSaveButton_actionPerformed(null);

  }
  public void doChooseColumns() {
	  columnsButton_actionPerformed(null);
  }

  
  private void sendEmails() {
    MergeUtils.sendEmail(emailColumn, myTable.getMessage());
  }

  public void addTablePrinter(TablePrintInterface pi) {
    printers.add(pi);
    setPrintButtonVisible(true);
  }

  public void removeTablePrinter(TablePrintInterface pi) {
    printers.remove(pi);
    if (printers.size() == 0) {
      setPrintButtonVisible(false);
    }
  }

  private final void printPressed(ActionEvent e) {
    for (int i = 0; i < printers.size(); i++) {
      TablePrintInterface tip = (TablePrintInterface) printers.get(i);
      if (myTree != null) {
        tip.printTreeTable(myTree.getModel());
      }
      else {
        tip.printTable(myTable);
      }
    }
  }

  public void setMessageTable(MessageTable mt) {
    myTable = mt;
    loadColumns();
  }

  public void setTreeTable(MessageTreeTablePanel mt) {
    myTree = mt;
  }

  public void setIgnoreList(String[] list) {
    ignoreList = list;
  }

  public void loadColumns() {
    referenceMessage = null;
    nameIdMap.clear();
    columnSelectBox.removeAllItems();
    for (int i = 0; i < myTable.getMessageModel().getColumnCount(); i++) {
      String name = myTable.getMessageModel().getColumnName(i);
      String propId = myTable.getMessageModel().getColumnId(i);
      columnSelectBox.addItem(name);
      nameIdMap.put(name, propId);
    }
    if (myTable.getRowCount() == 0) {
      return;
    }
    int referenceRow = 0;
    if (myTable.getSelectedRow() > 0) {
      referenceRow = myTable.getSelectedRow();
    }
    referenceMessage = myTable.getMessageRow(referenceRow);
    if (referenceMessage != null && myTable.getMessageModel().getColumnCount() != 0) {
      loadPanel();
    }
  }

  void addButton_actionPerformed(ActionEvent e) {
    String selected = (String) columnSelectBox.getSelectedItem();
    if (selected != null) {
      String propId = (String) nameIdMap.get(selected);
      if (propId != null) {
        myTable.resetColorMap();
        /** @todo Fix */
        myTable.addPropertyFilter(propId, (Property) valueField.getProperty().clone(), ( (OperatorItem) operatorBox.getSelectedItem()).value);
        myTable.performFilters();    
        try {
			myTable.loadColumnsNavajo();
			myTable.resizeColumns(myTable.getMessage());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		myTable.getMessageModel().fireTableStructureChanged();
        filterCount++;
        setCountLabel(filterCount);
        filteredRowCountLabel.setText("" + myTable.getMessageModel().getFilteredRecordCount() + " rijen");
      }
    }
  }

  void clearButton_actionPerformed(ActionEvent e) {
    myTable.clearPropertyFilters();
    filterCount = 0;
    setCountLabel(filterCount);
    myTable.getMessageModel().fireTableStructureChanged();
    filteredRowCountLabel.setText("");
  }

  public void clearLabel() {
    filterCount = 0;
    setCountLabel(filterCount);
    filteredRowCountLabel.setText("");
  }

  private final void setCountLabel(int c) {
    if (babyMode) {
      if (c == 0) {
        clearButton.setEnabled(false);
        addButton.setEnabled(true);
      }
      else {
        addButton.setEnabled(false);
        clearButton.setEnabled(true);
      }
      addButton.setText("");
      clearButton.setText("");
      return;
    }

    if (c == 0) {
      clearButton.setEnabled(false);
      //clearButton.setText(res.getString("closeButtonText"));
      clearButton.setText("");
    }
    else {
//      clearButton.setText(res.getString("closeButtonPrefix") + c +
//                          res.getString("closeButtonPostfix"));
      clearButton.setText("" + c);
      clearButton.setEnabled(true);
    }
  }

  void columnsButton_actionPerformed(ActionEvent e) {
    showColumnManagementDialog();
    loadColumns();
//    cmmd.show();
//    myTable.resizeColumns();
  }

  public void showColumnManagementDialog() {
    ColumnManagementDialog cmmd = null;
    if (Dialog.class.isInstance(getTopLevelAncestor())) {
      cmmd = new ColumnManagementDialog( (Dialog) getTopLevelAncestor());
    }
    else if (Frame.class.isInstance(getTopLevelAncestor())) {
      cmmd = new ColumnManagementDialog( (Frame) getTopLevelAncestor());
    }
    if (cmmd != null) {
      cmmd.setIgnoreList(ignoreList);
      cmmd.setSize(500, 400);
      cmmd.setMessageTable(myTable);
      cmmd.setLocationRelativeTo(getTopLevelAncestor());
      cmmd.setVisible(true);
    }
  }

  void columnsSaveButton_actionPerformed(ActionEvent e) {
    System.err.println("Saving the lot...");
//     myTable.saveColunmns();
    try {
//      myTable.setColumnDefinitionSavePath("c:/vladb/columns.tml");
      myTable.saveColumnsNavajo();
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void setFiltersVisible(boolean value) {
    flipPanel.setVisible(value);
  }

  public void setColumnsVisible(boolean value) {
    columnPanel.setVisible(value);
  }

  public void setColumnsButtonVisible(boolean value) {
    columnsButton.setVisible(value);
  }

  public void setPrintButtonVisible(boolean value) {
    printButton.setVisible(value);
  }

  public void setSaveColumnButtonVisible(boolean visible) {
    columnsSaveButton.setVisible(visible);
  }

  public void setManageColumnButtonVisible(boolean visible) {
    columnsButton.setVisible(visible);
  }

  public void setExcelColumnButtonVisible(boolean visible) {
    excelButton.setVisible(visible);
    wordButton.setVisible(visible); // easy way out :P
  }

  public void setEmailButtonVisible(boolean visible, String columnName) {
    emailColumn = columnName;
    emailButton.setVisible(visible);
  }

  public void setWordColumnButtonVisible(boolean visible) {
    wordButton.setVisible(visible);
  }

  void columnSelectBox_actionPerformed(ActionEvent e) {
    if (myTable.getMessage() != null) {
      loadPanel();
    }
  }

  private final void loadPanel() {
    if (referenceMessage != null) {
      String prop = (String) columnSelectBox.getSelectedItem();
      String id = (String) nameIdMap.get(prop);
      valueField.setHardEnabled(true);
      Property p;
      if (referenceMessage.getProperty(id) != null) {
        p = (Property) referenceMessage.getProperty(id).clone();
        p.setDirection(Property.DIR_INOUT);
        p.setValue("");
      }
      else {
        p = null;
      }

      if (p != null && p.getType().equals(Property.SELECTION_PROPERTY)) {
        try {
          Message table = myTable.getMessage();
          Set s = new TreeSet();
          Navajo dumm = NavajoFactory.getInstance().createNavajo();
          for (int i = 0; i < table.getArraySize(); i++) {
            Message cur = table.getMessage(i);
            Property selProp = cur.getProperty(id).copy(dumm);
            ArrayList sels = selProp.getAllSelections();
            for (int j = 0; j < sels.size(); j++) {
              Selection sel = (Selection) sels.get(j);
              s.add(sel);
            }
          }
          Property q = NavajoFactory.getInstance().createProperty(dumm, id, "1", id, "in");
          Iterator it = s.iterator();
          while (it.hasNext()) {
            Selection qs = (Selection) it.next();
            q.addSelection(qs);
          }
          valueField.setProperty(q);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
      else {
        valueField.setProperty(p);
      }
      valueField.revalidate();
      valueField.doLayout();
      valueField.repaint();
    }
  }

  public void setFilterMode(String mode) {
    if (mode.equals("baby")) {
      setupBabyFilter();
    }
    else {
      setupAdvancedFilter();
    }
    clearButton_actionPerformed(null);
  }

  private final void setupBabyFilter() {
    operatorBox.setModel(new DefaultComboBoxModel(new Object[] {new OperatorItem(res.getString("operatorEquals"), "=="),

                                                  new OperatorItem(res.getString("operatorContains"), "contains"), new OperatorItem(res.getString("operatorGreater"), ">"), new OperatorItem(res.getString("operatorSmaller"), "<")}));

    valueField.setPreferredSize(new Dimension(140, 20));
    valueField.setLabelVisible(false);
    babyMode = true;

  }

  private final void setupAdvancedFilter() {
    operatorBox.setModel(new DefaultComboBoxModel(new Object[] {new OperatorItem(res.getString("operatorEquals"), "=="), new OperatorItem(res.getString("operatorNotEquals"), "!="), new OperatorItem(res.getString("operatorGreater"), ">"),
                                                  new OperatorItem(res.getString("operatorSmaller"), "<"), new OperatorItem(res.getString("operatorSmallerEquals"), "<="), new OperatorItem(res.getString("operatorGreaterEquals"), ">="),
                                                  new OperatorItem(res.getString("operatorStartsWith"), "startsWith"), new OperatorItem(res.getString("operatorEndsWith"), "endsWith"), new OperatorItem(res.getString("operatorContains"), "contains"),
                                                  new OperatorItem(res.getString("operatorRegularExpression"), "regularExpression")}));
    valueField.setPreferredSize(new Dimension(140, 20));
    valueField.setLabelVisible(false);
    babyMode = false;
  }

}

class OperatorItem {
  public String name;
  public String value;
  public OperatorItem(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public OperatorItem(String both) {
    this.name = both;
    this.value = both;
  }

  public String toString() {
    return name;
  }

  public static void main(String[] args) {
    Properties props = System.getProperties();
    props.list(System.out);
  }
}
