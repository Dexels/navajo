package com.dexels.navajo.beheer;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class DefinitionPanel extends BaseNavajoPanel {

  JComboBox typeField = new JComboBox();
  JLabel typeLabel = new JLabel();
  JLabel nameLabel = new JLabel();
  JTextField nameField = new JTextField();
  String type="";
  String def_name="";
  String oldDef_name="";
  int parameter_id=-1;

  JPanel centerPanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  GridBagLayout gridBagLayout1 = new GridBagLayout();


  public DefinitionPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public DefinitionPanel(int parameter_id1, String name, RootPanel frame){
    rootPanel=frame;
    def_name=name;
    oldDef_name=def_name;
    parameter_id=parameter_id1;
    System.err.println("parameter_id: " +parameter_id);
    if(parameter_id==-1){
      newEntry=true;
    }

    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    title="Definition";
    nameField.setText(def_name);
    nameLabel.setText("Name");
    typeLabel.setText("Type");
    typeField.addItem("string");
    typeField.addItem("int");
    typeField.addItem("float");

    type=rootPanel.auth.simpleSelect(rootPanel.access, "type", "definitions", "parameter_id", Integer.toString(parameter_id));
    typeField.setSelectedItem(type);


//    xYLayout1=templateLayout;

    centerPanel.setLayout(gridBagLayout1);
    centerPanel.setBackground(Color.yellow);
    centerPanel.add(typeField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(18, 23, 175, 198), 0, 0));
    centerPanel.add(typeLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(20, 20, 175, 0), 8, 0));
    centerPanel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(68, 20, 0, 0), 0, 0));
    centerPanel.add(nameField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(68, 23, 0, 69), 250, 0));
    this.setLayout(borderLayout1);
    this.add(centerPanel, BorderLayout.CENTER);
    applyTemplate2();
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    rootPanel.changeToBeheerPanel();
  }

  void okButton_actionPerformed(ActionEvent e) {
    type=(String)typeField.getSelectedItem();
    def_name=nameField.getText();

    if(def_name.equals("")){
      error=true;
      errorField.setText("Please fill definition name");
    }
    else{
      try{
        Vector[] definitionNames = rootPanel.auth.select(rootPanel.access, "select name from definitions", 1);
        for(int i = 0; i<definitionNames[0].size()&&!error; i++){
          if(def_name.equals((String)definitionNames[0].get(i))&&!def_name.equals(oldDef_name)){
            error=true;
            errorField.setText("Definition name allready exist");
          }
        }
      }
      catch(Exception ex){
        ex.printStackTrace(System.out);
        errorWithDb();
      }
      if(!error){
        if(newEntry ){
          try{
            rootPanel.auth.addDefinition(rootPanel.access, def_name, type);
          }
          catch(Exception ex){
            ex.printStackTrace(System.err);
            errorWithDb();
          }
        }
        else{
          try{
            rootPanel.auth.update(rootPanel.access,"definitions", new String[]{"name", "type"}, new String[]{def_name, type}, new String[]{"parameter_id"}, new String[]{Integer.toString(parameter_id)} );
          }
          catch(Exception ex){
            ex.printStackTrace(System.err);
            errorWithDb();
          }
        }

        if(!error){
          rootPanel.changeToBeheerPanel();
        }
      }
    }
    error=false;
    def_name=oldDef_name ;
  }

  void deleteButton_actionPerformed(ActionEvent e) {
    try{
      rootPanel.auth.deleteDefinition(rootPanel.access, def_name);
    }
    catch(Exception ex){
      ex.printStackTrace(System.err);
    }
    rootPanel.changeToBeheerPanel();
  }

}