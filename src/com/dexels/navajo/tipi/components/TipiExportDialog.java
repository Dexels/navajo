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

public class TipiExportDialog extends DefaultTipiDialog{
  private JDialog d = null;
  TipiExportSortingPanel sp;
  TipiExportSeparatorPanel sep;
  private String msgPath;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton proceedButton = new JButton();
  JButton cancelButton = new JButton();
  JPanel container;
  JButton backButton = new JButton();
  private int current = 0;
  private Message data;

  public TipiExportDialog() {
  }



  private void jbInit() throws Exception {
    backButton.setEnabled(false);
    container = new JPanel();
    d.getContentPane().setLayout(gridBagLayout1);
    proceedButton.setText("Verder");
    proceedButton.addActionListener(new TipiExportDialog_proceedButton_actionAdapter(this));
    cancelButton.setText("Annuleren");
    cancelButton.addActionListener(new TipiExportDialog_cancelButton_actionAdapter(this));
    backButton.setText("Terug");
    backButton.addActionListener(new TipiExportDialog_backButton_actionAdapter(this));
    d.getContentPane().add(container,     new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), -1000, -1000));
    container.setLayout(new CardLayout());
    sp = new TipiExportSortingPanel();
    sep = new TipiExportSeparatorPanel();
    container.add(sp, "Sort");
    container.add(sep, "Separator");
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
//    System.err.println("LoadData called in TipiExportDialog: " + msgPath);
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

    if(current == 1){
      Vector props = sp.getExportedPropertyNames();
      String[] filter = null;
      String separator = sep.getSelectedSeparator();
      exportData(props, filter, separator);
      d.setVisible(false);
      myContext.disposeTipi(this);
      return;
    }
    backButton.setEnabled(true);
    CardLayout c = (CardLayout)container.getLayout();
    c.next(container);
    current++;
    if(current == 1){
      proceedButton.setText("Voltooien");
    }else {
      proceedButton.setText("Verder >>");
    }
  }

  private void exportData(Vector properties, String[] filter, String separator){
    boolean filtering = false;
    HashMap descIdMap = sp.getDescriptionIdMap();
    HashMap descPropMap = sp.getDescriptionPropertyMap();

    if(data != null){
      JFileChooser fd = new JFileChooser("Opslaan");
      fd.showSaveDialog(this.getParent().getContainer());
      File out = fd.getSelectedFile();
      FileWriter fw = null;
      try {
        fw = new FileWriter(out);
      }
      catch (IOException ex1) {
        ex1.printStackTrace();
      }
      ArrayList subMsgs = data.getAllMessages();
      for(int i=0;i<subMsgs.size();i++){
        Message current = (Message)subMsgs.get(i);
        //ArrayList props = current.getAllProperties();
        boolean new_line = true;
        boolean line_complies_to_filter = false;
        String line = "";
        for(int j=0;j<properties.size();j++){
          Property current_prop = current.getProperty( (String) properties.get(j));
          String propValue;
          if(current_prop.getType() == Property.DATE_PROPERTY){
            Date d = (Date)current_prop.getTypedValue();
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
            propValue = df.format(d);
          }else{
            propValue = current_prop.getValue();
          }
          if (properties.contains(current_prop.getName())) {
            line_complies_to_filter = true;
            if (new_line) {
              line = line + "\"" + propValue + "\"";
              new_line = false;
            }
            else {
              line = line + separator + "\"" + propValue + "\"";
            }
          }
        }
        // Write the constructed line
        try {
          if(line_complies_to_filter){
            fw.write(line + System.getProperty("line.separator"));
          }
        }
        catch (IOException ex) {
          ex.printStackTrace();
        }
      }
      try {
        fw.flush();
        fw.close();
      }
      catch (IOException ex2) {
        ex2.printStackTrace();
      }
    }
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    d.hide();
    myContext.disposeTipi(this);
  }

  void backButton_actionPerformed(ActionEvent e) {
    CardLayout c = (CardLayout)container.getLayout();
    c.previous(container);
    proceedButton.setEnabled(true);
    current--;
    if(current == 0){
      backButton.setEnabled(false);
    }
    if(current == 1){
      proceedButton.setText("Voltooien");
    }else {
      proceedButton.setText("Verder");
    }

  }
}

class TipiExportDialog_proceedButton_actionAdapter implements java.awt.event.ActionListener {
  TipiExportDialog adaptee;

  TipiExportDialog_proceedButton_actionAdapter(TipiExportDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.proceedButton_actionPerformed(e);
  }
}

class TipiExportDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  TipiExportDialog adaptee;

  TipiExportDialog_cancelButton_actionAdapter(TipiExportDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class TipiExportDialog_backButton_actionAdapter implements java.awt.event.ActionListener {
  TipiExportDialog adaptee;

  TipiExportDialog_backButton_actionAdapter(TipiExportDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.backButton_actionPerformed(e);
  }
}