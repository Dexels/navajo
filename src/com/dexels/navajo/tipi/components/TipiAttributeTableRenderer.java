package com.dexels.navajo.tipi.components;

import java.awt.Component;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import com.dexels.navajo.tipi.TipiValue;
import java.util.*;
import com.dexels.navajo.tipi.*;
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
  private TipiComponent myComponent = null;
  public TipiAttributeTableRenderer() {

  }
/** @todo Optimize: Creates a lot of garbage. All components keep getting reinstantiated. Frank. */
  public Component getTableCellRendererComponent(JTable source, Object value, boolean isSelected, boolean isSomething, int row, int column) {

    //System.err.println("Requesting redering of: [" + row + "," + column +"] got Object: " + value.getClass() + " with value: " + value.toString());
    Object liveValue = null;

    if(column == 0 && value != null){
      myLabel = new JLabel();
      myLabel.setText(value.toString());
      return myLabel;
    }
    if(column == 1 && TipiValue.class.isInstance(value)){
      TipiValue tv = (TipiValue)value;
      String type = tv.getType();
      if (!tv.getDirection().equals("in")) {
        liveValue = myComponent.getValue(tv.getName());
      }
      if(type.equals("selection")){
        myComboBox = new JComboBox(tv.getValidSelectionValuesAsVector());
        if (liveValue!=null) {
          myComboBox.setSelectedItem(liveValue);
        } else {
          myComboBox.setSelectedItem(tv.getValue());
        }

        return myComboBox;
      }
      if(type.equals("string") || type.equals("integer") || type.equals("resource")){
        myTextField = new JTextField();
        if (liveValue!=null) {
          myTextField.setText(liveValue.toString());
        } else {
          myTextField.setText(tv.getValue());
        }
      }
      if(type.equals("boolean")){
        Vector v = new Vector();
        v.addElement(new Boolean(true));
        v.addElement(new Boolean(false));
        myComboBox = new JComboBox(v);
        if (liveValue!=null) {
          myComboBox.setSelectedItem(liveValue);
        } else {
          myComboBox.setSelectedItem(tv.getValue());
        }
        return myComboBox;
      }
//      if(type.equals("border") || type.equals("font") || type.equals("path") || type.equals("messagepath") || type.equals("tipipath") || type.equals("componentpath") || type.equals("propertypath") || type.equals("attriutepath") || type.equals("color")){
//        myExternalSelectionCell = new TipiAttributeTableExternalSelectionCell();
//        myExternalSelectionCell.setText(tv.getValue());
//        return myExternalSelectionCell;
//      }

      myLabel = new JLabel();
      if (liveValue!=null) {
       myLabel.setText(liveValue.toString());
     } else {
       myLabel.setText(tv.getValue());
     }
      return myLabel;
    }
    myLabel = new JLabel();
    if (value==null) {
      myLabel.setText("?!");
    } else {

      myLabel.setText(value.toString());
    }
    return myLabel;
  }
  public void setSelectedComponent(TipiComponent tc) {
    myComponent = tc;
  }
}