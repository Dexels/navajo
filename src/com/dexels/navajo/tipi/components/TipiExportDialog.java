package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.impl.*;
import java.awt.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import javax.swing.*;
import java.awt.event.*;

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
  private String msgPath;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton proceedButton = new JButton();
  JButton cancelButton = new JButton();
  JPanel container;
  JButton backButton = new JButton();
  private int current = 0;

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
    container.add(sp, "Sort");
    container.add(new TipiExportFilterPanel(), "Filter");
    container.add(new TipiExportSeparatorPanel(), "Separator");
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
    System.err.println("LoadData called in TipiExportDialog");
    sp.setMessage(n.getMessage(msgPath));
    super.loadData(n, tc);
  }


  public void setComponentValue(String name, Object value){
    super.setComponentValue(name, value);
    if("messagepath".equals(name)){
      msgPath = (String)value;
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
    System.err.println("current_proceed: " + current);
    if(current == 2){
      return;
      // VOltooien
    }
    backButton.setEnabled(true);
    CardLayout c = (CardLayout)container.getLayout();
    c.next(container);
    current++;
    System.err.println("Current new: " + current);
    if(current == 2){
      proceedButton.setText("Voltooien");
    }else {
      proceedButton.setText("Verder >>");
    }
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

  void backButton_actionPerformed(ActionEvent e) {
    System.err.println("current_back: " + current);
    CardLayout c = (CardLayout)container.getLayout();
    c.previous(container);
    proceedButton.setEnabled(true);
    current--;
    System.err.println("Current new: " + current);
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