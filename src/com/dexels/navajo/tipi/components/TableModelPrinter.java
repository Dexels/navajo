package com.dexels.navajo.tipi.components;


import javax.swing.*;
import com.jrefinery.report.*;
import javax.swing.table.*;
import java.awt.print.*;
import com.jrefinery.report.preview.*;
import com.jrefinery.report.io.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
import java.net.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.swingclient.components.MessageTablePanel;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class TableModelPrinter extends DefaultTipi{

  private JTable myTable;
  private TableModel tm;
  private String myTitle;
  private String mySubTitle;
  private JFreeReport report;
  private Map columnsToPrint = new HashMap();
  public TableModelPrinter() {
  }

  public TableModelPrinter(JTable t) {
    myTable = t;
    tm = myTable.getModel();

  }

  private void setTitle(String title){
    myTitle = title;
  }

  public void setSubTitle(String sub){
    mySubTitle = sub;
  }

  private void setTable(JTable t){
    myTable = t;
    tm = myTable.getModel();
  }

  private void setReportTemplate(URL template){
    try{
      System.err.println("Looking for template " + template);
      if(template != null){
        ReportGenerator gen = ReportGenerator.getInstance();
        report = gen.parseReport(template);
      }else{
        System.err.println("Whoops NULL template: " + template);
      }
    }catch(Exception e){
      e.printStackTrace();
    }

  }

  protected void performComponentMethod(String name, XMLElement invocation, TipiComponentMethod compMeth) {
    if (name.equals("print")) {
      TipiMethodParameter table = compMeth.getParameter("tablepath");
      TipiMethodParameter template = compMeth.getParameter("template");
      TipiMethodParameter title = compMeth.getParameter("title");
      TipiMethodParameter subTitle = compMeth.getParameter("subtitle");
      setTitle(title.getValue());
      setSubTitle(subTitle.getValue());

      System.err.println("Path to table: " + table.getValue());
      TipiPathParser pp = new TipiPathParser((TipiComponent)this, myContext, table.getValue());
      TipiComponent comp = pp.getComponent();
      Container c = comp.getContainer();
      if(MessageTablePanel.class.isInstance(c)){          // Swing dependancy

        System.err.println("Yup we got a table...");
        MessageTablePanel t = (MessageTablePanel) c;
        myTable = (JTable)t.getTable();
        tm = myTable.getModel();
      }else{
        System.err.println("Could not find the specified Table...");
      }

      if(template != null){
        URL temp = getClass().getClassLoader().getResource(template.getValue());
        setReportTemplate(temp);
      }else{
        constructReport();
      }
      printData();
    }
  }

  private void constructReport(){
    report = new JFreeReport();
    PageFormat p = new PageFormat();
    p.setOrientation(PageFormat.LANDSCAPE);
    report.setDefaultPageFormat(p);
    double offset = 5.0;
    TableColumnModel tcm = myTable.getColumnModel();
    for(int i=0;i<tm.getColumnCount();i++){
      double width = tcm.getColumn(i).getWidth();
      System.err.println("Width: " + width);
      TextElement t = ItemFactory.createStringElement("Kolommetje", new Rectangle2D.Double(offset, 0.0, 150.0, 20.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), null, "-", tm.getColumnName(i));
      report.getItemBand().addElement(t);
      offset += width/2;
    }
    addReportHeader(report);
  }

  private void printData(){
    try {
      report.setData(tm);
      PreviewDialog preview = new PreviewDialog(report);
      preview.setSize(800, 600);
      preview.setModal(true);
      preview.show();
    }
    catch (ReportProcessingException ex) {
      ex.printStackTrace();
    }
  }

  private void addReportHeader(JFreeReport report){
    Font titleFont = new Font("Serif", Font.PLAIN, 20);
    Font headerFont = new Font("Serif", Font.BOLD, 10);
    Element le = ItemFactory.createLabelElement("Title", new Rectangle2D.Double(0.0, 0.0, 150.0, 40.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), titleFont, myTitle);
    Element sub = ItemFactory.createLabelElement("SubTitle", new Rectangle2D.Double(0.0, 30.0, 150.0, 40.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), titleFont, myTitle);
    report.getPageHeader().addElement(le);
    double offset = 5.0;
    TableColumnModel tcm = myTable.getColumnModel();
    for(int i=0;i<tm.getColumnCount();i++){
      double width = tcm.getColumn(i).getWidth();
      Element t = ItemFactory.createLabelElement("KolomHeadertje", new Rectangle2D.Double(offset, 60.0, 150.0, 20.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), headerFont, tm.getColumnName(i));
      report.getPageHeader().addElement(t);
      offset += width/2;
    }
  }




  public void removeFromContainer(Component c) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiBase abstract method*/
  }
  public void addToContainer(Component c, Object constraints) {
    /**@todo Implement this com.dexels.navajo.tipi.TipiBase abstract method*/
  }
  public void registerEvents() {
    /**@todo Implement this com.dexels.navajo.tipi.TipiComponent abstract method*/
  }
  public Container createContainer() {
    /**@todo Implement this com.dexels.navajo.tipi.TipiBase abstract method*/
    // Not implemented
    return null;
  }

  public void load(XMLElement def, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    super.load(def, instance, context);
    if(instance != null){
      Vector kiddos = instance.getChildren();
      for(int i=0;i<kiddos.size();i++){
        XMLElement kid = (XMLElement)kiddos.get(i);
        if(kid.getName().equals("column")){
          String name = kid.getStringAttribute("name");
          columnsToPrint.put(name, new Boolean(true));
        }
      }
    }
  }


}
