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
  public TipiAttributeTableRenderer() {

  }

  public Component getTableCellRendererComponent(JTable source, Object value, boolean isSelected, boolean isSomething, int row, int column) {

    //System.err.println("Requesting redering of: [" + row + "," + column +"] got Object: " + value.getClass() + " with value: " + value.toString());

    if(column == 0 && Boolean.class.isInstance(value)){
      boolean sel = ((Boolean)value).booleanValue();
      JCheckBox myCheckBox = new JCheckBox();
      myCheckBox.setSelected(sel);
      return myCheckBox;
    }
    if(column == 1 && value != null){
      JLabel myLabel = new JLabel();
      myLabel.setText(value.toString());
      return myLabel;
    }
    if(column == 2 && TipiValue.class.isInstance(value)){
      TipiValue tv = (TipiValue)value;
      String type = tv.getType();
      if(type.equals("selection")){
        JComboBox myComboBox = new JComboBox(tv.getValidSelectionValuesAsVector());
        myComboBox = new JComboBox(tv.getValidSelectionValuesAsVector());
        return myComboBox;
      }
      if(type.equals("string") || type.equals("integer")){
        JLabel myLabel = new JLabel();
        myLabel.setText(tv.getValue());
      }
      if(type.equals("boolean")){
        Vector v = new Vector();
        v.addElement(new Boolean(true));
        v.addElement(new Boolean(false));
        JComboBox myComboBox = new JComboBox(v);
        myComboBox = new JComboBox(v);
        return myComboBox;
      }
      JLabel myLabel = new JLabel();
      myLabel.setText(tv.getValue());
      return myLabel;
    }
    JLabel myLabel = new JLabel();
    myLabel.setText("?");
    return myLabel;

  }

}