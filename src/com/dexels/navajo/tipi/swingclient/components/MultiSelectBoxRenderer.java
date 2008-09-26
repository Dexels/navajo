package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.document.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class MultiSelectBoxRenderer implements ListCellRenderer {
  JCheckBox cb = new JCheckBox();

  public MultiSelectBoxRenderer() {
  }

  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    final Selection sel = (Selection)value;
    cb.setOpaque(false);
    cb.setText(sel == null ? "" : sel.toString());
    cb.setSelected(sel.isSelected());
    return cb;
  }

}
