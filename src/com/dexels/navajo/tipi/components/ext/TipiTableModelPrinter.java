package com.dexels.navajo.tipi.components.ext;

import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.print.*;
import javax.swing.*;
import javax.swing.table.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.jrefinery.report.*;
import com.jrefinery.report.io.*;
import com.jrefinery.report.preview.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */
public class TipiTableModelPrinter
    extends TipiComponentImpl {
  private JTable myTable;
  private TableModel tm;
  private String myTitle;
  private String mySubTitle;
  private JFreeReport report;
  private Map columnsToPrint = new HashMap();
  private int orientation = PageFormat.PORTRAIT;
  public TipiTableModelPrinter() {
  }

  public TipiTableModelPrinter(JTable t) {
    myTable = t;
    tm = myTable.getModel();
  }

  public void setOrientation(int orient) {
    orientation = orient;
  }

  private void setTitle(String title) {
    myTitle = title;
  }

  public void setSubTitle(String sub) {
    mySubTitle = sub;
  }

  private void setTable(JTable t) {
    myTable = t;
    tm = myTable.getModel();
  }

  private void setReportTemplate(URL template) {
    try {
      System.err.println("Looking for template " + template);
      if (template != null) {
        ReportGenerator gen = ReportGenerator.getInstance();
        report = gen.parseReport(template);
      }
      else {
        System.err.println("Whoops NULL template: " + template);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void performComponentMethod(String name, TipiComponentMethod compMeth) {
    if (name.equals("print")) {
      TipiValue table = compMeth.getParameter("tablepath");
      TipiValue title = compMeth.getParameter("title");
      TipiValue subTitle = compMeth.getParameter("subtitle");
      if (title != null) {
        setTitle(title.getValue());
      }
      if (subTitle != null) {
        setSubTitle(subTitle.getValue());
      }
      Operand o = compMeth.getEvaluatedParameter("tablepath");
      Operand template = (compMeth.getParameter("template") != null ? compMeth.getEvaluatedParameter("template") : null);
      System.err.println("o: class: " + o.value.getClass());
      System.err.println("template = " + template);
      if (TipiSwingDataComponentImpl.class.isInstance(o.value)) {
        //System.err.println("Yup we got a table...");
        TipiSwingDataComponentImpl comp = (TipiSwingDataComponentImpl) o.value;
//      TipiComponent comp = pp.getComponent();
        Container c = comp.getSwingContainer();
        MessageTablePanel t = (MessageTablePanel) c;
        myTable = (JTable) t.getTable();
        tm = myTable.getModel();
      }
      else {
        if (o.value instanceof Message) {
          System.err.println("Ah,. you want me to make a table for you? fine I'll try");
          Message data = (Message) o.value;
          MessageTablePanel newPanel = new MessageTablePanel();
          int columns = 0;
          if (data.getAllMessages().size() > 0 && data.getType() == Message.MSG_TYPE_ARRAY) {
            Message firstRow = data.getMessage(0);
            ArrayList props = firstRow.getAllProperties();
            columns = props.size();
            for (int j = 0; j < columns; j++) {
              Property current = (Property) props.get(j);
              newPanel.addColumn(current.getName(), current.getDescription(), false);
            }
            newPanel.setMessage(data);
            myTable = (JTable) newPanel.getTable();
            tm = myTable.getModel();
            if (columns > 0) {
              for (int k = 0; k < columns; k++) {
                tm.getValueAt(0, k);
              }
            }
          }
          else {
            throw new RuntimeException("Well, put a filled ArrayMessage in there then..");
          }
//
        }
      }
      if (template != null) {
        System.err.println("CLASS: " + template.value.getClass());
        URL temp = (URL) template.value;
        setReportTemplate(temp);
      }
      else {
        constructReport();
      }
      printData();
    }
  }

  private void replaceNewLines(Message data) {
    ArrayList kids = data.getAllMessages();
    for (int i = 0; i < kids.size(); i++) {
      Message current = (Message) kids.get(i);
      ArrayList limbs = current.getAllProperties();
      for (int j = 0; j < limbs.size(); j++) {
        Property p = (Property) limbs.get(j);
        //System.err.println("Reading: " + p.getValue());
        p.setValue(removeNewLines(p.getValue()));
        //System.err.println("Written: " + p.getValue());
      }
    }
  }

  private String removeNewLines(String str) {
    if (str != null) {
      char line_break = 0x0a;
      StringBuffer result = new StringBuffer(str.length());
      for (int i = 0; i < str.length() - 1; i++) {
        char c = str.charAt(i);
        char d = str.charAt(i + 1);
        if (c == '\\' && d == 'n') {
          result.append(line_break);
          i++;
        }
        else {
          result.append(c);
        }
      }
      char a = str.charAt(str.length() - 2);
      char b = str.charAt(str.length() - 1);
      if (a == '\\' && b == 'n') {
      }
      else {
        result.append(b);
      }
      return result.toString();
    }
    else {
      return null;
    }
  }

  private void constructReport() {
    report = new JFreeReport();
    PageFormat p = report.getDefaultPageFormat();
    p.setOrientation(orientation);
    Font tableFont = new Font("Serif", Font.PLAIN, 8);
    double offset = 0.0;
    TableColumnModel tcm = myTable.getColumnModel();
    for (int i = 0; i < tm.getColumnCount(); i++) {
      double width = tcm.getColumn(i).getPreferredWidth();
      //System.err.println("Width: " + width);
      TextElement t = ItemFactory.createStringElement("Kolommetje", new Rectangle2D.Double(offset, 0.0, width, 9.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), tableFont, "-", tm.getColumnName(i));
      offset += width / 1.75;
      if (offset <= p.getImageableWidth()) {
        report.getItemBand().addElement(t);
      }
      else {
        System.err.println("Column " + tm.getColumnName(i) + " does not fit on page!");
      }
    }
    addReportHeader(report);
  }

  private void printData() {
    try {
      report.setData(tm);
      JFrame top = (JFrame) myContext.getTopLevel();
      PreviewDialog preview = new PreviewDialog(report, top);
      preview.setSize(800, 600);
      preview.setLocationRelativeTo(((RootPaneContainer)myContext.getTopLevel()).getContentPane());
      preview.setTitle("Afdrukken " + myTitle);
      preview.setModal(true);
      preview.setJMenuBar(null);
      preview.show();
      report = null;
    }
    catch (ReportProcessingException ex) {
      ex.printStackTrace();
    }
  }

  private void addReportHeader(JFreeReport report) {
    Font titleFont = new Font("Serif", Font.PLAIN, 20);
    Font subTitleFont = new Font("Serif", Font.BOLD, 10);
    Font headerFont = new Font("Serif", Font.BOLD, 8);
    Element le = ItemFactory.createLabelElement("Title", new Rectangle2D.Double(0.0, 0.0, -100.0, 25.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), titleFont, myTitle);
    Element sub = ItemFactory.createLabelElement("SubTitle", new Rectangle2D.Double(0.0, 30.0, -100.0, 25.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), subTitleFont, mySubTitle);
    report.getPageHeader().addElement(le);
    report.getPageHeader().addElement(sub);
    double offset = 0.0;
    PageFormat p = report.getDefaultPageFormat();
    p.setOrientation(orientation);
    TableColumnModel tcm = myTable.getColumnModel();
    for (int i = 0; i < tm.getColumnCount(); i++) {
      double width = tcm.getColumn(i).getPreferredWidth();
      Element t = ItemFactory.createLabelElement("KolomHeadertje", new Rectangle2D.Double(offset, 60.0, width, 15.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), headerFont, tm.getColumnName(i));
      offset += width / 1.75;
      if (offset <= p.getImageableWidth()) {
        report.getPageHeader().addElement(t);
      }
      else {
        System.err.println("Header " + tm.getColumnName(i) + " does not fit on page!");
      }
    }
  }

  public void setComponentValue(String name, Object object) {
    if (name.equals("title")) {
      setTitle(object.toString());
    }
    if (name.equals("subtitle")) {
      setSubTitle(object.toString());
    }
    if (name.equals("orientation")) {
      String value = object.toString();
      if ("portrait".equals(value)) {
        setOrientation(PageFormat.PORTRAIT);
      }
      if ("landscape".equals(value)) {
        setOrientation(PageFormat.LANDSCAPE);
      }
    }
    super.setComponentValue(name, object);
  }

  public Object getComponentValue(String name) {
    if (name.equals("title")) {
      return myTitle;
    }
    if (name.equals("subtitle")) {
      return mySubTitle;
    }
    return super.getComponentValue(name);
  }

  public Object createContainer() {
    return null;
  }

  public void load(XMLElement def, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    super.load(def, instance, context);
    if (instance != null) {
      Vector kiddos = instance.getChildren();
      for (int i = 0; i < kiddos.size(); i++) {
        XMLElement kid = (XMLElement) kiddos.get(i);
        if (kid.getName().equals("column")) {
          String name = kid.getStringAttribute("name");
          columnsToPrint.put(name, new Boolean(true));
        }
      }
    }
  }
}
