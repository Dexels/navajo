package com.dexels.navajo.tipi.components.calendar;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class TipiCalendarTable extends JTable {
  private CalendarTableCellEditor myEditor = new CalendarTableCellEditor();
  private CalendarTableCellRenderer myRenderer = new CalendarTableCellRenderer();
  private CalendarTableModel myDefaultModel = new CalendarTableModel();
  private MultiCalendarModel myMultiModel = new MultiCalendarModel();
  private CalendarManager myManager;
  public final int SELECTION_CONTINOUS = 1;
  public final int SELECTION_NORMAL = 0;
  int modelGetCount = 0;
  private int selectionMode = SELECTION_NORMAL;

  public TipiCalendarTable() {
    setShowHorizontalLines(false);
    setShowVerticalLines(false);
    setIntercellSpacing(new Dimension(0, 0));
    setDefaultEditor(Object.class, myEditor);
    // Order is important!! Only setTable after adding the default model
    myMultiModel.addCalendar(myDefaultModel);
    myMultiModel.setTable(this);
    setModel(myMultiModel);
    setDefaultRenderer(Object.class, myRenderer);
    setCellSelectionEnabled(true);
    this.setTableHeader(null);
    this.setRowHeight(CalendarConstants.DEFAULT_ROW_HEIGHT);
    this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    this.addKeyListener(new KeyListener() {
      public void keyPressed(KeyEvent e) {
        //System.err.println("You pressed: " + e.getKeyText(e.getKeyCode()));
      }

      public void keyReleased(KeyEvent e) {
        //System.err.println("You released: " + e.getKeyText(e.getKeyCode()));
        registerKeyEvent(e);
      }

      public void keyTyped(KeyEvent e) {
        //System.err.println("You typed: " + e.getKeyText(e.getKeyCode()));
        // Gives weird unrecognized keycodes.
      }

    });

    this.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {
        cellClicked(e);
      }

      public void mouseExited(MouseEvent e) {
        return;
      }

      public void mouseEntered(MouseEvent e) {
        return;
      }

      public void mousePressed(MouseEvent e) {
        return;
      }

      public void mouseReleased(MouseEvent e) {
        return;
      }
    });
  }

  public void addCalendar(CalendarTableModel model){
    myMultiModel.addCalendar(model);
  }

  private void registerKeyEvent(KeyEvent e) {
    if (myManager == null) {
      myManager = new DefaultCalendarManager();
      myManager.fireCalendarEvent(new CalendarEvent(this, e));
    }
    else {
      myManager.fireCalendarEvent(new CalendarEvent(this, e));
    }
  }

  public ArrayList getSelectedDays() {
    ArrayList selected = new ArrayList();
    for (int i = 1; i < this.getRowCount(); i++) {
      for (int j = 1; j < this.getColumnCount(); j++) {
        if (isCellSelected(i, j)) {
          Object o = getModel().getValueAt(i, j);
          if (Day.class.isInstance(o)) {
            Day d = (Day) o;
            selected.add(d);
          }
        }
      }
    }
    return selected;
  }

  public void cellClicked(MouseEvent e) {
    if (myManager == null) {
      myManager = new DefaultCalendarManager();
      myManager.fireCalendarEvent(new CalendarEvent(this, e));
    }
    else {
      myManager.fireCalendarEvent(new CalendarEvent(this, e));
    }
  }

  public boolean isCellSelected(int row, int col) {
    if (getSelectedColumn() == 0) {
      setColumnSelectionInterval(1, 7);
    }
    if (getSelectedRow() == 0) {
      setRowSelectionInterval(0, getRowCount() - 1);
    }

    if (selectionMode == SELECTION_CONTINOUS) {
      int minRow = getSelectionModel().getMinSelectionIndex();
      int maxRow = getSelectionModel().getMaxSelectionIndex();

      if (row > minRow && row < maxRow) {
        return true; // all cells between start and end but not start and end
      }

      if ( (row == minRow || row == maxRow) && minRow != maxRow) {
        int anchorCol = getColumnModel().getSelectionModel().getAnchorSelectionIndex();
        int leadCol = getColumnModel().getSelectionModel().getLeadSelectionIndex();
        int anchorRow = getSelectionModel().getAnchorSelectionIndex();
        int leadRow = getSelectionModel().getLeadSelectionIndex();

        boolean toRight = leadCol > anchorCol;
        boolean toBottom = leadRow > anchorRow;

        if (toBottom) {
          if (row == maxRow) {
            return col <= leadCol;
          }
          else { // minRow
            return col >= anchorCol;
          }
        }
        else if (row == maxRow) { // to top
          return col <= anchorCol;
        }
        else { // minRow
          return col >= leadCol;
        }
      }
      return super.isCellSelected(row, col);
    }
    else {
      return super.isCellSelected(row, col);
    }
  }

  public int getYear() {
    return myDefaultModel.getYear();
  }

  public void setYear(int year) {
    myDefaultModel.setYear(year);
    if (myManager != null) {
      myManager.fireCalendarEvent(new CalendarEvent(this, null));
    }
  }

  public void setMonth(int month) {
    myDefaultModel.setMonth(month);
    if (myManager != null) {
      myManager.fireCalendarEvent(new CalendarEvent(this, null));
    }
  }

  public String getMonthString() {
    return myDefaultModel.getMonthString();
  }

  public int getMonth() {
    return myDefaultModel.getMonth();
  }

  public Day getDay(String dayOfYear) {
    return myDefaultModel.getDay(dayOfYear);
  }

  public void setSelectionMode(int mode) {
    selectionMode = mode;
  }

  public void setCalendarManager(CalendarManager m) {
    myManager = m;
    myManager.setSource(this);
    myDefaultModel.setManager(myManager);
  }

  public CalendarManager getManager() {
    return myManager;
  }

  public void rebuildUI(){
    setRowHeight(CalendarConstants.DEFAULT_ROW_HEIGHT);
  }

  public void nextMonth(){
    if (getMonth() == 11) {
      setMonth(0);
    } else {
      setMonth(getMonth() + 1);
    }
    repaint();
  }

  public void previousMonth(){
    if (getMonth() == 0) {
      setMonth(11);
    } else {
      setMonth(getMonth() - 1);
    }
    repaint();
  }

  public void load(Message m) {
    System.err.println("Loading Table!!");
    if (BasePanel.class.isInstance(myManager)) {
       ( (BasePanel) myManager).setLoadMessage(m);
       ( (BasePanel) myManager).load();
    }
  }

  public void init(Message m) {
    if (BasePanel.class.isInstance(myManager)) {
       ( (BasePanel) myManager).setInitMessage(m);
       ( (BasePanel) myManager).init();
    }

  }

}
