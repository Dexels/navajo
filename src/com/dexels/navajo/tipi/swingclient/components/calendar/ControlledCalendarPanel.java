package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.text.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class ControlledCalendarPanel
    extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel calendarViewControls = new JPanel();
  JPanel calendarManagerPanel = new JPanel();
  JPanel calendarMainPanel = new JPanel();
  CardLayout cardLayout1 = new CardLayout();
  JPanel yearViewPanel = new JPanel();
  JPanel monthViewPanel = new JPanel();
  JPanel dayViewPanel = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  YearOverviewTable yearOverviewTable1 = new YearOverviewTable();
  JPanel timeZoomPanel = new JPanel();
  JSlider timeSlider = new JSlider();
  JLabel yearLabel = new JLabel();
  JLabel monthLabel = new JLabel();
  JLabel dayLabel = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  TitledBorder titledBorder1;
  JPanel jPanel1 = new JPanel();
  TitledBorder titledBorder2;
  JButton previousMonthButton = new JButton();
  JButton nextMonthBUtton = new JButton();
  JComboBox monthSelection = new JComboBox(getMonths());
  JComboBox yearSelection = new JComboBox(getYears());
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  CalendarConstants myConstants = new CalendarConstants();
  CalendarTable calendarTable1 = new CalendarTable(myConstants);
  JPanel jPanel2 = new JPanel();
  JLabel monthTitle = new JLabel();

  public ControlledCalendarPanel() {
    try {
      myConstants.setRowHeight(80);
      myConstants.setColumnWidth(80);
      jbInit();
      loadData();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private final void jbInit() throws Exception {
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(171, 171, 171)), "Zoom niveau");
    titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(171, 171, 171)), "Datum selectie");
    timeZoomPanel.setBorder(titledBorder1);
    timeZoomPanel.setPreferredSize(new Dimension(216, 82));
    timeZoomPanel.setLayout(gridBagLayout1);
    this.setLayout(borderLayout1);
    calendarViewControls.setMinimumSize(new Dimension(1, 1));
    calendarViewControls.setPreferredSize(new Dimension(1, 80));
    calendarViewControls.setLayout(gridBagLayout3);
    calendarManagerPanel.setBackground(SystemColor.control);
    calendarManagerPanel.setMinimumSize(new Dimension(1, 1));
    calendarManagerPanel.setPreferredSize(new Dimension(1, 1));
    calendarMainPanel.setBorder(BorderFactory.createEtchedBorder());
    calendarMainPanel.setLayout(cardLayout1);
    yearViewPanel.setLayout(borderLayout2);
    monthViewPanel.setLayout(borderLayout3);
    dayViewPanel.setLayout(borderLayout4);
    yearLabel.setText("Jaar");
    monthLabel.setText("Maand");
    dayLabel.setText("Dag");
    timeSlider.setMajorTickSpacing(30);
    timeSlider.setMaximum(60);
    timeSlider.setMinorTickSpacing(30);
    timeSlider.setPaintTicks(true);
    timeSlider.setValue(0);
    timeSlider.addChangeListener(new ControlledCalendarPanel_timeSlider_changeAdapter(this));
    jPanel1.setBorder(titledBorder2);
    jPanel1.setLayout(gridBagLayout2);
    previousMonthButton.setText("<<");
    previousMonthButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        previousMonthButton_actionPerformed(e);
      }
    });
    nextMonthBUtton.setText(">>");
    nextMonthBUtton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        nextMonthBUtton_actionPerformed(e);
      }
    });
    calendarTable1.setCalendarConstants(myConstants);
    dayViewPanel.setPreferredSize(new Dimension(0, 0));
    this.setMinimumSize(new Dimension(800, 600));
    this.setPreferredSize(new Dimension(1025, 768));
    monthTitle.setFont(new java.awt.Font("Dialog", 1, 14));
    monthTitle.setText("Maand");
    this.add(calendarManagerPanel, BorderLayout.EAST);
    this.add(calendarMainPanel, BorderLayout.CENTER);
    calendarMainPanel.add(yearViewPanel, "Year");
    calendarMainPanel.add(monthViewPanel, "Month");
    monthViewPanel.add(jScrollPane1, BorderLayout.CENTER);
    monthViewPanel.add(jPanel2, BorderLayout.NORTH);
    jPanel2.add(monthTitle, null);
    jScrollPane1.getViewport().add(calendarTable1, null);
    jScrollPane1.getViewport().setBackground(calendarTable1.getConstants().getColor(CalendarConstants.BACKGROUND_COLOR));
    calendarMainPanel.add(dayViewPanel, "Day");
    yearViewPanel.add(yearOverviewTable1, BorderLayout.CENTER);
    this.add(calendarViewControls, BorderLayout.NORTH);
    calendarViewControls.add(timeZoomPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), -70, -6));
    timeZoomPanel.add(timeSlider, new GridBagConstraints(0, 0, 3, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
    timeZoomPanel.add(yearLabel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    timeZoomPanel.add(monthLabel, new GridBagConstraints(1, 1, 1, 2, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    timeZoomPanel.add(dayLabel, new GridBagConstraints(2, 1, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    calendarViewControls.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 267, 14));
    jPanel1.add(previousMonthButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(nextMonthBUtton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(monthSelection, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(yearSelection, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

    yearOverviewTable1.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() > 1) {
          timeSlider.setValue(30);
          calendarTable1.setMonth(yearOverviewTable1.getSelectedMonth());
          monthTitle.setText(calendarTable1.getMonthString());
          setMainPanelTo("Month");
          calendarTable1.rebuildUI();
        }
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }

      public void mousePressed(MouseEvent e) {
      }
    });
  }

  void timeSlider_stateChanged(ChangeEvent e) {
    int currentValue = timeSlider.getValue();
    if (currentValue <= 15) {
      timeSlider.setValue(0);
      timeSlider.setSnapToTicks(true);
      setMainPanelTo("Year");
      return;
    }
    if (currentValue > 15 && currentValue <= 45) {
      timeSlider.setValue(30);
      monthTitle.setText(calendarTable1.getMonthString());
      setMainPanelTo("Month");
      calendarTable1.rebuildUI();
      return;
    }
    if (currentValue > 45) {
      timeSlider.setValue(60);
      setMainPanelTo("Day");
      return;
    }
  }

  private final void setMainPanelTo(String panel) {
    CardLayout cl = (CardLayout) calendarMainPanel.getLayout();
    cl.show(calendarMainPanel, panel);
  }

  private Vector getYears() {
    Vector m = new Vector();
    SimpleDateFormat d = new SimpleDateFormat("yyyy");
    int current_year = Calendar.getInstance().get(Calendar.YEAR);
    for (int i = -2; i < 3; i++) {
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, current_year + i);
      String year = d.format(c.getTime());
      m.addElement(year);
    }
    return m;
  }

  private Vector getMonths() {
    Vector m = new Vector();
    Calendar c = Calendar.getInstance();
    SimpleDateFormat d = new SimpleDateFormat("MMMM");
    for (int i = 0; i < 12; i++) {
      c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
      c.set(Calendar.MONTH, i);
      String month = d.format(c.getTime());
      m.addElement(month);
    }
    return m;
  }

  void previousMonthButton_actionPerformed(ActionEvent e) {
    calendarTable1.previousMonth();
    monthTitle.setText(calendarTable1.getMonthString());
  }

  void nextMonthBUtton_actionPerformed(ActionEvent e) {
    calendarTable1.nextMonth();
    monthTitle.setText(calendarTable1.getMonthString());
  }

  private final void loadData() {
    try {

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}

class ControlledCalendarPanel_timeSlider_changeAdapter
    implements javax.swing.event.ChangeListener {
  ControlledCalendarPanel adaptee;

  ControlledCalendarPanel_timeSlider_changeAdapter(ControlledCalendarPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void stateChanged(ChangeEvent e) {
    adaptee.timeSlider_stateChanged(e);
  }
}