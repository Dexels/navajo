package com.dexels.navajo.tipi.swingclient.components.sort;

import javax.swing.table.*;

import javax.swing.*;

import java.awt.*;
import java.util.*;

import com.dexels.navajo.tipi.swingclient.components.*;

import java.awt.event.*;

/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class CustomTableHeaderRenderer
    extends JButton implements TableCellRenderer {

  public static final int ASCENDING = 1;

  public static final int DESCENDING = 2;

  public static final int NONE = 0;

//  private int sortingState = NONE;

  private ImageIcon up = new ImageIcon(TableSorter.class.getResource("up.png"));

  private ImageIcon down = new ImageIcon(TableSorter.class.getResource(
      "down.png"));

  private ImageIcon none = new ImageIcon(TableSorter.class.getResource(
      "none.png"));

  private int columnIndex;

  private final Map sortingMap = new HashMap();

  public CustomTableHeaderRenderer() {
    super();
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
//    setSortingState(NONE);

  }


  public void setSortingState(int column, int s) {
    sortingMap.clear();
    sortingMap.put(new Integer(column),new Integer(s));
//    sortingState = s;
    updateSorting(column);

  }

  private final void updateSorting(int column) {
    int sortingState = NONE;
    Integer sortInt = (Integer)sortingMap.get(new Integer(column));
    if (sortInt!=null) {
      sortingState = sortInt.intValue();
    }
    switch (sortingState) {
      case ASCENDING:
        changeIcon(down);
        break;
      case DESCENDING:
        changeIcon(up);
        break;
      case NONE:
        changeIcon(none);
        break;
    }
    repaint();
  }

  private final void changeIcon(Icon newIcon) {
    //Icon oldIcon = getIcon();
//    firePropertyChange(ICON_CHANGED_PROPERTY,oldIcon,newIcon);
    setIcon(newIcon);
  }

  public int getSortingState(int column) {
    int sortingState = NONE;
    Integer sortInt = (Integer)sortingMap.get(new Integer(column));
    if (sortInt!=null) {
      sortingState = sortInt.intValue();
    }
    return sortingState;
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus, int row,
                                                 int column) {
    updateSorting(column);
     setText("" + table.getModel().getColumnName(column));
     revalidate();
      ((MessageTable)table).setHeaderHeight(getPreferredSize().height);
    return this;
  }

  private final void jbInit() throws Exception {
    this.setHorizontalAlignment(SwingConstants.LEADING);
    this.setHorizontalTextPosition(SwingConstants.LEADING);
    this.setMargin(new Insets(0, 3, 0, 0));
  }

//  protected void printComponent(Graphics g) {
//    Color c = getBackground();
//    setBackground(Color.white);
//    super.printComponent(g);
//    setBackground(c);
//  }


}
