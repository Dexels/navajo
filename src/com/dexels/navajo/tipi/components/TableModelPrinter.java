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
import com.dexels.navajo.document.*;

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
      if(title != null){
        setTitle(title.getValue());
      }
      if(subTitle != null){
        setSubTitle(subTitle.getValue());
      }

      //System.err.println("Path to table: " + table.getValue());
      TipiPathParser pp = new TipiPathParser((TipiComponent)this, myContext, table.getValue());
      TipiComponent comp = pp.getComponent();
      Container c = comp.getContainer();
      if(MessageTablePanel.class.isInstance(c) && pp.getPathType() == pp.PATH_TO_TIPI){          // Swing dependancy

        //System.err.println("Yup we got a table...");
        MessageTablePanel t = (MessageTablePanel) c;
        myTable = (JTable)t.getTable();
        tm = myTable.getModel();
      }else if(pp.getPathType() == pp.PATH_TO_MESSAGE){
        System.err.println("Ah,. you want me to make a table for you? fine I'll try");
        Message data = pp.getMessage();
        //replaceNewLines(data);
        MessageTablePanel newPanel = new MessageTablePanel();
        int columns = 0;
        if(data.getAllMessages().size() > 0 && data.getType() == Message.MSG_TYPE_ARRAY){
          Message firstRow = data.getMessage(0);
          ArrayList props = firstRow.getAllProperties();
          columns = props.size();
          for(int j=0;j<columns;j++){
            Property current = (Property)props.get(j);
            newPanel.addColumn(current.getName(), current.getDescription(), false);
          }
          newPanel.setMessage(data);
          myTable = (JTable)newPanel.getTable();
          tm = myTable.getModel();
          if(columns > 0){
            for(int k=0;k<columns;k++){
              tm.getValueAt(0,k);
            }
          }
        }else{
          throw new RuntimeException("Well, put a filled ArrayMessage in there then..");
        }

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

  private void replaceNewLines(Message data){
    ArrayList kids = data.getAllMessages();
    for(int i=0;i<kids.size();i++){
      Message current = (Message)kids.get(i);
      ArrayList limbs = current.getAllProperties();
      for(int j=0;j<limbs.size();j++){
        Property p = (Property)limbs.get(j);
        //System.err.println("Reading: " + p.getValue());
        p.setValue(removeNewLines(p.getValue()));
        //System.err.println("Written: " + p.getValue());
      }
    }
  }

  private String removeNewLines(String str) {
    if(str != null){
    char line_break = 0x0a;
    StringBuffer result = new StringBuffer(str.length());
    for (int i = 0; i < str.length()-1; i++) {
      char c = str.charAt(i);
      char d = str.charAt(i+1);
      if (c == '\\' && d == 'n') {
        result.append(line_break);
        i++;
      }
      else {
        result.append(c);
      }
    }
    char a = str.charAt(str.length()-2);
    char b = str.charAt(str.length()-1);
      if (a == '\\' && b == 'n') {
      }
      else {
        result.append(b);
      }
      return result.toString();
    }else{
      return null;
    }
  }

  private void constructReport(){
    report = new JFreeReport();
    PageFormat p = report.getDefaultPageFormat();
    p.setOrientation(PageFormat.LANDSCAPE);
    Paper paper = new Paper();
    paper.setSize(8.27*72,11.69*72);
    paper.setImageableArea(.25*72, .25*72, 8*72, 10.5*72);
    p.setPaper(paper);
    Font tableFont = new Font("Serif", Font.PLAIN, 8);
    double offset = 0.0;
    TableColumnModel tcm = myTable.getColumnModel();

    for(int i=0;i<tm.getColumnCount();i++){
      double width = tcm.getColumn(i).getPreferredWidth();
      //System.err.println("Width: " + width);
      TextElement t = ItemFactory.createStringElement("Kolommetje", new Rectangle2D.Double(offset, 0.0, width, 20.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), tableFont, "-", tm.getColumnName(i));
      report.getItemBand().addElement(t);
      offset += width/1.8;
    }
    addReportHeader(report);
  }

  private void printData(){
    try {
      report.setData(tm);
      JFrame top = (JFrame)TipiContext.getInstance().getTopLevel();
      PreviewDialog preview = new PreviewDialog(report, top);
      preview.setSize(800, 600);
      preview.setLocationRelativeTo(TipiContext.getInstance().getTopLevel().getContentPane());
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

  private void addReportHeader(JFreeReport report){
    Font titleFont = new Font("Serif", Font.PLAIN, 20);
    Font subTitleFont = new Font("Serif", Font.BOLD, 10);
    Font headerFont = new Font("Serif", Font.BOLD, 8);
    Element le = ItemFactory.createLabelElement("Title", new Rectangle2D.Double(0.0, 0.0, -100.0, 25.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), titleFont, myTitle);
    Element sub = ItemFactory.createLabelElement("SubTitle", new Rectangle2D.Double(0.0, 30.0, -100.0, 25.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), subTitleFont, mySubTitle);
    report.getPageHeader().addElement(le);
    report.getPageHeader().addElement(sub);
    double offset = 0.0;
    TableColumnModel tcm = myTable.getColumnModel();
    for(int i=0;i<tm.getColumnCount();i++){
      double width = tcm.getColumn(i).getPreferredWidth();
      Element t = ItemFactory.createLabelElement("KolomHeadertje", new Rectangle2D.Double(offset, 60.0, width, 20.0), Color.black, ElementAlignment.LEFT.getOldAlignment(), ElementAlignment.MIDDLE.getOldAlignment(), headerFont, tm.getColumnName(i));
      report.getPageHeader().addElement(t);
      offset += width/1.8;
    }
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("title")) {
      setTitle(object.toString());
    }
    if (name.equals("subtitle")) {
      setSubTitle(object.toString());
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
