package com.dexels.navajo.tipi.components;

import java.awt.Component;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import com.dexels.navajo.tipi.TipiValue;
import java.util.*;
/**
 * <p>Title: TipiAttributeTableRenderer</p>
 * <p>Description: Custom CellRenderer for the Instantiate AttributeTable.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public class TipiAttributeTableRenderer implements TableCellRenderer {
  private JComboBox myComboBox;
  private JLabel myLabel;
  private JTextField myTextField;
  private TipiAttributeTableExternalSelectionCell myExternalSelectionCell;
  public TipiAttributeTableRenderer() {

  }

  public Component getTableCellRendererComponent(JTable source, Object value, boolean isSelected, boolean isSomething, int row, int column) {

    //System.err.println("Requesting redering of: [" + row + "," + column +"] got Object: " + value.getClass() + " with value: " + value.toString());

    if(column == 0 && value != null){
      myLabel = new JLabel();
      myLabel.setText(value.toString());
      return myLabel;
    }
    if(column == 1 && TipiValue.class.isInstance(value)){
      TipiValue tv = (TipiValue)value;
      String type = tv.getType();
      if(type.equals("selection")){
        myComboBox = new JComboBox(tv.getValidSelectionValuesAsVector());
        myComboBox.setSelectedItem(tv.getValue());
        return myComboBox;
      }
      if(type.equals("string") || type.equals("integer") || type.equals("resource")){
        myTextField = new JTextField();
        myTextField.setText(tv.getValue());
      }
      if(type.equals("boolean")){
        Vector v = new Vector();
        v.addElement("true");
        v.addElement("false");
        myComboBox = new JComboBox(v);
        myComboBox.setSelectedItem(tv.getValue());
        return myComboBox;
      }
//      if(type.equals("border") || type.equals("font") || type.equals("path") || type.equals("messagepath") || type.equals("tipipath") || type.equals("componentpath") || type.equals("propertypath") || type.equals("attriutepath") || type.equals("color")){
//        myExternalSelectionCell = new TipiAttributeTableExternalSelectionCell();
//        myExternalSelectionCell.setText(tv.getValue());
//        return myExternalSelectionCell;
//      }

      myLabel = new JLabel();
      myLabel.setText(tv.getValue());
      return myLabel;
    }
    myLabel = new JLabel();
    myLabel.setText("?");
    return myLabel;
  }

}