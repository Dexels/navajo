package com.dexels.navajo.swingclient.components.sort;



import javax.swing.table.*;

import javax.swing.*;

import java.awt.*;


/**

 * <p>Title: ShellApplet</p>

 * <p>Description: </p>

 * <p>Copyright: Copyright (c) 2002</p>

 * <p>Company: Dexels </p>

 * @author Frank Lyaruu

 * @version 1.0

 */



public class CustomTableHeaderRenderer extends JButton implements TableCellRenderer {



  public static final int ASCENDING = 1;

  public static final int DESCENDING = 2;

  public static final int NONE = 0;



  private int sortingState=NONE;

  private ImageIcon up = new ImageIcon(TableSorter.class.getResource("up.png"));

  private ImageIcon down = new ImageIcon(TableSorter.class.getResource("down.png"));

  private ImageIcon none = new ImageIcon(TableSorter.class.getResource("none.png"));

  private int columnIndex;





  public CustomTableHeaderRenderer() {

    super();

    try {

      jbInit();

    }

    catch(Exception e) {

      e.printStackTrace();

    }

    setSortingState(NONE);

  }



  public void setSortingState(int s) {

    sortingState = s;

    updateSorting();

  }



  private void updateSorting() {

    switch (sortingState) {

      case ASCENDING:

        setIcon(down);

        break;

      case DESCENDING:

        setIcon(up);

        break;

      case NONE:

        setIcon(none);

        break;

    }

  }



  public int getSortingState() {

    return sortingState;

  }

  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

    setText(""+value);

    return this;

  }

  private void jbInit() throws Exception {

    this.setHorizontalAlignment(SwingConstants.LEADING);

    this.setHorizontalTextPosition(SwingConstants.LEADING);
    this.setMargin(new Insets(0, 0, 0, 0));

  }

  public void setColumnIndex(int columnIndex) {

    this.columnIndex = columnIndex;

  }

  public int getColumnIndex() {

    return columnIndex;

  }



}