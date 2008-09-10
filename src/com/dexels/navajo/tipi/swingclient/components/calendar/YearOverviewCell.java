package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.awt.*;
import javax.swing.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class YearOverviewCell
    extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JLabel jLabel1 = new JLabel();
  CalendarConstants myConstants = new CalendarConstants();
  CalendarTable calendarTable1 = new CalendarTable(myConstants);
  private boolean selected = false;
  private Message myData;

  public YearOverviewCell() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private final void jbInit() throws Exception {
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("");
    this.setLayout(borderLayout1);
    this.add(jLabel1, BorderLayout.NORTH);
    this.add(calendarTable1, BorderLayout.CENTER);
    this.setBackground(CalendarConstants.getColor(CalendarConstants.BACKGROUND_COLOR));
    myConstants.setRowHeight(20);
    myConstants.setColumnWidth(20);
    calendarTable1.setCalendarConstants(myConstants);
    calendarTable1.setSelectAllImageVisible(false);
    calendarTable1.rebuildUI();

  }

  private final void loadData() {
    try {
      ClientInterface client = NavajoClientFactory.createDefaultClient();
      client.setUsername("ROOT");
      client.setPassword("");
      client.setServerUrl("www.dexels.nl/sportlink/knvb/servlet/Postman");
      Navajo n = client.doSimpleSend("InitQueryCalendar");
      n.getMessage("Calendar").getProperty("CalendarName").setValue("DEFAULT");
      n.getMessage("Calendar").getProperty("Month").setValue(calendarTable1.getMonth());
      n.getMessage("Calendar").getProperty("Year").setValue(calendarTable1.getYear());
      //System.err.println("Loading with NAVAJO: " + n.toString());
      Navajo data = client.doSimpleSend(n, "ProcessQueryCalendar");
      myData = data.getMessage("Days");
      calendarTable1.setMessage(myData);
      //System.err.println("Got days: " + myData.toString());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setMonth(int month) {
    calendarTable1.setMonth(month);
    jLabel1.setText(calendarTable1.getMonthString());
//    System.err.println("Month: " + calendarTable1.getMonth() + " has name: " + calendarTable1.getMonthString());
    if (myData == null) {
      loadData();
    }
  }

  public void setSelected(boolean state) {
    selected = state;
  }

  @Override
public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (selected) {
      Graphics2D g2 = (Graphics2D) g;
      Composite old = g2.getComposite();
      AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
      g2.setColor(Color.blue);
      g2.setComposite(ac);
      g2.fillRect(2, 2, this.getWidth() - 2, this.getHeight() - 2);
      g2.setComposite(old);
    }
    calendarTable1.rebuildUI();
  }
}