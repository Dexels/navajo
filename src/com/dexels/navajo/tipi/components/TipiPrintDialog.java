package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.impl.*;
import java.awt.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.*;
import java.util.Date;
import java.util.Calendar;
import com.dexels.navajo.document.Navajo;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiPrintDialog extends DefaultTipiDialog{
  private JDialog d = null;
  TipiExportSortingPanel sp;
  TipiExportFilterPanel fp;
  //TipiExportSeparatorPanel sep;
  private String msgPath;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton proceedButton = new JButton();
  JButton cancelButton = new JButton();
  JPanel container;
  JButton backButton = new JButton();
  private int current = 0;
  private Message data;

  public TipiPrintDialog() {
  }



  private void jbInit() throws Exception {
    backButton.setEnabled(false);
    container = new JPanel();
    d.getContentPane().setLayout(gridBagLayout1);
    proceedButton.setText("Verder >>");
    proceedButton.addActionListener(new TipiPrintDialog_proceedButton_actionAdapter(this));
    cancelButton.setText("Annuleren");
    cancelButton.addActionListener(new TipiPrintDialog_cancelButton_actionAdapter(this));
    backButton.setText("<< Terug");
    backButton.addActionListener(new TipiPrintDialog_backButton_actionAdapter(this));
    d.getContentPane().add(container,     new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), -1000, -1000));
    container.setLayout(new CardLayout());
    sp = new TipiExportSortingPanel();
    fp = new TipiExportFilterPanel();
    //sep = new TipiExportSeparatorPanel();
    container.add(sp, "Sort");
    container.add(fp, "Filter");
    //container.add(sep, "Separator");
    d.getContentPane().add(proceedButton,       new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    d.getContentPane().add(cancelButton,     new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    d.getContentPane().add(backButton,   new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    d.setSize(new Dimension(500, 400));
    CardLayout c = (CardLayout)container.getLayout();
    c.first(container);
  }

  public void setContainerLayout(LayoutManager m){
  }

  public void loadData(Navajo n, TipiContext tc) throws com.dexels.navajo.tipi.TipiException {
    super.loadData(n, tc);
//    System.err.println("LoadData called in TipiPrintDialog: " + msgPath);
//    data = n.getMessage(msgPath);
//    sp.setMessage(data);
//    fp.setDescriptionPropertyMap(sp.getDescriptionPropertyMap());
  }


  public void setComponentValue(String name, Object value){
    super.setComponentValue(name, value);
    if("messagepath".equals(name)){
      msgPath = (String)value;
      TipiPathParser pp = new TipiPathParser(null, TipiContext.getInstance(), msgPath);
      data = pp.getMessage();
      sp.setMessage(data);
      fp.setDescriptionPropertyMap(sp.getDescriptionPropertyMap());
      // Ja hij komt hier ook langs..
    }
  }


  public Container getContainer(){
    if(d == null){
     return createContainer();
   }else{
     return d;
   }
  }

    public Container createContainer() {
      d = (JDialog)super.createContainer();
      try {
        jbInit();
      }
      catch(Exception e) {
        e.printStackTrace();
      }
      return d;
    }

  void proceedButton_actionPerformed(ActionEvent e) {
    //System.err.println("current_proceed: " + current);
    if(current == 1){
      Vector props = sp.getExportedPropertyNames();
      System.err.println("Exporting: " + props.toString());
      String[] filter = fp.getFilter();
      System.err.println("Filter: '" + filter[0] + "' '" + filter[1] + "' '" + filter[2] + "'");
      printData(props, filter);
      d.setVisible(false);
      myContext.disposeTipi(this);
      return;
    }
    backButton.setEnabled(true);
    CardLayout c = (CardLayout)container.getLayout();
    c.next(container);
    current++;
    if(current == 1){
      fp.updateAvailableFilters(sp.getExportedPropertyDescriptions());
      proceedButton.setText("Voltooien");
    }else {
      proceedButton.setText("Verder >>");
    }
  }

  private void printData(Vector properties, String[]filter){
    // Maak een nieuwe message die door de xslt gaat.
    boolean exact = "Exact".equals(filter[1]);
    boolean from = "Vanaf".equals(filter[1]);
    boolean to = "Tot".equals(filter[1]);
    boolean filtering = !"Geen filter".equals(filter[0]);
    HashMap descIdMap = sp.getDescriptionIdMap();
    HashMap descPropMap = sp.getDescriptionPropertyMap();
    String filterPropName = (String)descIdMap.get(filter[0]);
    Property filterProperty = null;
    try {
      if(filtering){
        filterProperty = NavajoFactory.getInstance().createProperty(NavajoFactory.getInstance().createNavajo(), filterPropName, ( (Property) descPropMap.get(filter[0])).getType(), filter[2], 10, filter[0], "out");
        System.err.println("FilterPropertyType: " + filterProperty.getType());
      }
    }
    catch (NavajoException ex3) {
      ex3.printStackTrace();
    }

    Navajo n =  NavajoFactory.getInstance().createNavajo();
    Message newMsg = NavajoFactory.getInstance().createMessage(n, data.getName(), data.getType());

    for(int i=0;i<data.getAllMessages().size();i++){
      boolean line_complies_to_filter = false;
      Message current = data.getMessage(i);
      Message insert =  NavajoFactory.getInstance().createMessage(n, current.getName());
      ArrayList props = current.getAllProperties();
      if(filtering){
        for(int j=0;j<properties.size();j++){
          Property p = current.getProperty((String)properties.get(j));
          if (p.getName().equals(filterPropName)) {
            if (exact) {
              if(p.getTypedValue().equals(filterProperty.getTypedValue())){
                line_complies_to_filter = true;
              }
            } else if(from){
              Date fromDate = (Date)filterProperty.getTypedValue();
              Date currentDate = (Date)p.getTypedValue();
              System.err.println("Comparing if date [" + currentDate.toString() + "] is after [" + fromDate.toString() + "]" );
              if(fromDate.before(currentDate)){
                line_complies_to_filter = true;
              }
            }else if(to){
              Date fromDate = (Date)filterProperty.getTypedValue();
              Date currentDate = (Date)p.getTypedValue();
              System.err.println("Comparing if date [" + currentDate.toString() + "] is before [" + fromDate.toString() + "]" );
              if(fromDate.after(currentDate)){
                line_complies_to_filter = true;
              }
            } else if(p.getType().equals(Property.STRING_PROPERTY)){
              System.err.println("Checking: " + p.getValue() + ":" + filter[2]);
              if (p.getValue().startsWith(filter[2])) {
                line_complies_to_filter = true;
              }
            }
          }
          if(properties.contains(p.getName())){
             insert.addProperty(p);
          }
        }
      }else{
        line_complies_to_filter = true;
        for(int j=0;j<properties.size();j++){
          Property p = current.getProperty((String)properties.get(j));
          if(properties.contains(p.getName())){
             insert.addProperty(p);
          }
        }
      }

      if(line_complies_to_filter){
        newMsg.addMessage(insert);
      }
    }

    PrintComponent pc = new PrintComponent();
//    pc.showPrintPreview(true);
    pc.printMessage(newMsg, "tipi/members.xsl");

  }

  void cancelButton_actionPerformed(ActionEvent e) {
    d.hide();
    myContext.disposeTipi(this);
  }

  void backButton_actionPerformed(ActionEvent e) {
    //System.err.println("current_back: " + current);
    CardLayout c = (CardLayout)container.getLayout();
    c.previous(container);
    proceedButton.setEnabled(true);
    current--;
    //System.err.println("Current new: " + current);
    if(current == 0){
      backButton.setEnabled(false);
    }
    if(current == 1){
      proceedButton.setText("Voltooien");
    }else {
      proceedButton.setText("Verder >>");
    }

  }
}

class TipiPrintDialog_proceedButton_actionAdapter implements java.awt.event.ActionListener {
  TipiPrintDialog adaptee;

  TipiPrintDialog_proceedButton_actionAdapter(TipiPrintDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.proceedButton_actionPerformed(e);
  }
}

class TipiPrintDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  TipiPrintDialog adaptee;

  TipiPrintDialog_cancelButton_actionAdapter(TipiPrintDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class TipiPrintDialog_backButton_actionAdapter implements java.awt.event.ActionListener {
  TipiPrintDialog adaptee;

  TipiPrintDialog_backButton_actionAdapter(TipiPrintDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.backButton_actionPerformed(e);
  }
}
