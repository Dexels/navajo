package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.text.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.dexels.navajo.document.*;
import javax.swing.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingExportFilterPanel
    extends JPanel {
  JPanel jPanel1 = new JPanel();
  TitledBorder titledBorder1;
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  ButtonGroup bg = new ButtonGroup();
  private String separator = ", ";
  JLabel filterOn = new JLabel();
  JLabel filterType = new JLabel();
  JLabel filterValue = new JLabel();
  JComboBox filterOnBox = new JComboBox();
  JComboBox filterTypeBox = new JComboBox();
  JTextField filterValueField = new JTextField();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  private HashMap descriptionPropertyMap;
  private static SimpleDateFormat navajoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private static SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
  private static SimpleDateFormat inputFormat1 = new SimpleDateFormat("dd-MM-yy");
  private static SimpleDateFormat inputFormat2 = new SimpleDateFormat("dd/MM/yy");
  private static SimpleDateFormat inputFormat3 = new SimpleDateFormat("ddMMyy");
  private final int FILTER_DATE = 0;
  private final int FILTER_STRING = 1;
  private final int FILTER_INT = 2;
  private int myType = FILTER_STRING;
  public TipiSwingExportFilterPanel() {
    try {
      SwingUtilities.invokeAndWait(new Runnable() {
        public void run() {
          try {
            jbInit();
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    Vector filterTypes = new Vector();
    filterTypes.addElement("Exact");
    filterTypes.addElement("Begint met");
    filterTypeBox = new JComboBox(filterTypes);
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(171, 171, 171)), "Filter");
    this.setLayout(gridBagLayout2);
    jPanel1.setBorder(titledBorder1);
    jPanel1.setDebugGraphicsOptions(0);
    jPanel1.setMaximumSize(new Dimension(32767, 32767));
    jPanel1.setLayout(gridBagLayout1);
    filterOn.setText("Filter op");
    filterType.setText("Filter type");
    filterValue.setText("Filter waarde");
    filterValueField.setText("");
    filterOnBox.addFocusListener(new TipiExportFilterPanel_filterOnBox_focusAdapter(this));
    this.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                             , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 184, 74));
    jPanel1.add(filterOn, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0
                                                 , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(filterType, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(filterValue, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(filterOnBox, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(filterTypeBox, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
        , GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(filterValueField, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
        , GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    /** @todo Replace by dispose? */
    this.setVisible(false);
  }

  public void setDescriptionPropertyMap(HashMap m) {
    descriptionPropertyMap = m;
  }

  public void updateAvailableFilters(Vector filters) {
    filterOnBox.removeAllItems();
    filterOnBox.addItem("Geen filter");
    for (int i = 0; i < filters.size(); i++) {
      filterOnBox.addItem( (String) filters.elementAt(i));
    }
  }

  public String[] getFilter() {
    String[] filter = new String[3];
    filter[0] = (String) filterOnBox.getSelectedItem();
    filter[1] = (String) filterTypeBox.getSelectedItem();
    if (myType == FILTER_STRING) {
      filter[2] = filterValueField.getText();
    }
    else {
      try {
        Date d = inputFormat1.parse(filterValueField.getText());
        filter[2] = navajoDateFormat.format(d);
      }
      catch (Exception e1) {
        try {
          Date d = inputFormat2.parse(filterValueField.getText());
          filter[2] = navajoDateFormat.format(d);
        }
        catch (Exception e2) {
          try {
            Date d = inputFormat3.parse(filterValueField.getText());
            filter[2] = navajoDateFormat.format(d);
          }
          catch (Exception e3) {
            System.err.println("Whoops wrong date format typed..");
            filter[2] = filterValueField.getText();
          }
        }
      }
    }
    return filter;
  }

  void filterOnBox_focusLost(FocusEvent e) {
    if (filterOnBox.getSelectedIndex() > 0 && descriptionPropertyMap != null) {
      String item = (String) filterOnBox.getSelectedItem();
      Property p = (Property) descriptionPropertyMap.get(item);
      if (p != null) {
        if (p.getType().equals(Property.DATE_PROPERTY)) {
          filterTypeBox.removeAllItems();
          filterTypeBox.addItem("Exact");
          filterTypeBox.addItem("Vanaf");
          filterTypeBox.addItem("Tot");
          myType = FILTER_DATE;
        }
        else {
          filterTypeBox.removeAllItems();
          filterTypeBox.addItem("Exact");
          filterTypeBox.addItem("Begint met");
          myType = FILTER_STRING;
        }
      }
      filterTypeBox.updateUI();
    }
  }
}

class TipiExportFilterPanel_filterOnBox_focusAdapter
    extends java.awt.event.FocusAdapter {
  TipiSwingExportFilterPanel adaptee;
  TipiExportFilterPanel_filterOnBox_focusAdapter(TipiSwingExportFilterPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void focusLost(FocusEvent e) {
    adaptee.filterOnBox_focusLost(e);
  }
}
