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
  private JDialog d = new JDialog();
  TipiExportSortingPanel sp;
  TipiExportFilterPanel fp;
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
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }



  private void jbInit() throws Exception {
    backButton.setEnabled(false);
    container = new JPanel();
    d.getContentPane().setLayout(gridBagLayout1);
    proceedButton.setText("Verder >>");
    proceedButton.addActionListener(new TipiExportDialog_proceedButton_actionAdapter(this));
    cancelButton.setText("Annuleren");
    cancelButton.addActionListener(new TipiExportDialog_cancelButton_actionAdapter(this));
    backButton.setText("<< Terug");
    backButton.addActionListener(new TipiExportDialog_backButton_actionAdapter(this));
    d.getContentPane().add(container,     new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), -1000, -1000));
    container.setLayout(new CardLayout());
    sp = new TipiExportSortingPanel();
    fp = new TipiExportFilterPanel();
    sep = new TipiExportSeparatorPanel();
    container.add(sp, "Sort");
    container.add(fp, "Filter");
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
    System.err.println("LoadData called in TipiExportDialog: " + msgPath);
    data = n.getMessage(msgPath);
    sp.setMessage(data);
    fp.setDescriptionPropertyMap(sp.getDescriptionPropertyMap());
  }


  public void setComponentValue(String name, Object value){
    super.setComponentValue(name, value);
    if("messagepath".equals(name)){
      msgPath = (String)value;
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
   return d;
  }

  void proceedButton_actionPerformed(ActionEvent e) {
    //System.err.println("current_proceed: " + current);
    if(current == 2){
      Vector props = sp.getExportedPropertyNames();
      System.err.println("Exporting: " + props.toString());
      String[] filter = fp.getFilter();
      System.err.println("Filter: '" + filter[0] + "' '" + filter[1] + "' '" + filter[2] + "'");
      String separator = sep.getSelectedSeparator();
      System.err.println("Separator: '" + separator + "'");
      exportData(props, filter, separator);
      this.setVisible(false);
      return;
    }
    backButton.setEnabled(true);
    CardLayout c = (CardLayout)container.getLayout();
    c.next(container);
    current++;
    if(current == 1){
      fp.updateAvailableFilters(sp.getExportedPropertyDescriptions());
    }
    if(current == 2){
      proceedButton.setText("Voltooien");
    }else {
      proceedButton.setText("Verder >>");
    }
  }

  private void exportData(Vector properties, String[] filter, String separator){
    boolean exact = "Exact".equals(filter[1]);
    boolean from = "Vanaf".equals(filter[1]);
    boolean to = "Tot".equals(filter[1]);
    boolean filtering = !"Geen filter".equals(filter[0]);
    HashMap descIdMap = sp.getDescriptionIdMap();
    HashMap descPropMap = sp.getDescriptionPropertyMap();
    String filterPropName = (String)descIdMap.get(filter[0]);
//    Property filterProperty = (Property) descPropMap.get(filter[0]);
//    System.err.println("FilterPropertyType: " + filterProperty.getType());
//    filterProperty.setValue(filter[2]);
//    System.err.println("FilterPropertyType: " + filterProperty.getType());
    Property filterProperty = null;
    try {
      filterProperty = NavajoFactory.getInstance().createProperty(NavajoFactory.getInstance().createNavajo(), filterPropName, ((Property) descPropMap.get(filter[0])).getType(), filter[2], 10, filter[0], "out");
      System.err.println("FilterPropertyType: " + filterProperty.getType());
    }
    catch (NavajoException ex3) {
      ex3.printStackTrace();
    }

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
        ArrayList props = current.getAllProperties();
        boolean new_line = true;
        boolean line_complies_to_filter = false;
        String line = "";
        for(int j=0;j<props.size();j++){
          Property current_prop = (Property)props.get(j);
          if(properties.contains(current_prop.getName())){
            //System.err.println("Property: " + current_prop.getName());
            if(filtering){
              if (current_prop.getName().equals(filterPropName)) {
                if (exact) {
                  if(current_prop.getTypedValue().equals(filterProperty.getTypedValue())){
                    line_complies_to_filter = true;
                  }
                } else if(from){
                  Date fromDate = (Date)filterProperty.getTypedValue();
                  Date currentDate = (Date)current_prop.getTypedValue();
                  System.err.println("Comparing if date [" + currentDate.toString() + "] is after [" + fromDate.toString() + "]" );
                  if(fromDate.before(currentDate)){
                    line_complies_to_filter = true;
                  }
                }else if(to){
                  Date fromDate = (Date)filterProperty.getTypedValue();
                  Date currentDate = (Date)current_prop.getTypedValue();
                  System.err.println("Comparing if date [" + currentDate.toString() + "] is before [" + fromDate.toString() + "]" );
                  if(fromDate.after(currentDate)){
                    line_complies_to_filter = true;
                  }
                } else if(current_prop.getType().equals(Property.STRING_PROPERTY)){
                  //System.err.println("Comparing(Startswith): " + current_prop.getValue() + ", " + filter[2]);
                  if (current_prop.getValue().startsWith(filter[2])) {
                    line_complies_to_filter = true;
                  }
                }
//                if (new_line) {
//                  line = line + "\"" + current_prop.getValue() + "\"";
//                  new_line = false;
//                }
//                else {
//                  line = line + separator + "\"" + current_prop.getValue() + "\"";
//                }
              }
            }
            else{
              //System.err.println("Not filtering");
              line_complies_to_filter = true;
            }
            if(new_line){
              //System.err.println(" -- new line -- ");
              line = line + "\"" + current_prop.getValue() + "\"";
              new_line = false;
            }else{
              line = line + separator + "\"" + current_prop.getValue() + "\"";
            }

          }
        }
        // Write the constructed line
        try {
          if(line_complies_to_filter){
            fw.write(line + "\n");
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
    this.setVisible(false);
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
    if(current == 2){
      proceedButton.setText("Voltooien");
    }else {
      proceedButton.setText("Verder >>");
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