package com.dexels.navajo.beheer;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import com.dexels.navajo.server.ConditionData;
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

public class ConditionPanel extends BaseNavajoPanel {

  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JTextField conditionField = new JTextField();
  JLabel jLabel4 = new JLabel();
  JTextField commentField = new JTextField();

//  boolean newEntry = false;
//  boolean error = false;
  int userId=0;
  int serviceId= -1;

  ConditionData cd = new ConditionData();
  String serviceName ="";
  String userName ="";
  String condition ="";
  String oldCondition="";
  String comment ="";
  JComboBox serviceBox = new JComboBox();

  JPanel centerPanel = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public ConditionPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ConditionPanel(ConditionData cd1, RootPanel frame, String serviceName1, String userName1, int userId1) {

    if(cd1==null)
      newEntry=true;
    else
      cd = cd1;

    userId=userId1;
    rootPanel=frame;
    serviceName = serviceName1;
    userName=userName1;
    oldCondition=cd.condition;

    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    title="Condition for "+userName;
    Vector services = rootPanel.auth.allServices(rootPanel.access);
    for(int i = 0; i < services.size(); i++){
      serviceBox.addItem(services.get(i));
    }
    serviceBox.setSelectedItem(serviceName);

    jLabel2.setText("Service:");
    jLabel3.setText("Condition:");

    conditionField.setText(cd.condition);
    commentField.setText(cd.comment);


    jLabel4.setText("Comment:");


    centerPanel.setLayout(gridBagLayout1);
    centerPanel.setBackground(Color.yellow);
    centerPanel.add(commentField, new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 24, 122, 34), 486, -4));
    centerPanel.add(jLabel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(66, 24, 0, 0), 0, 0));
    centerPanel.add(jLabel3, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(7, 24, 0, 425), 40, 0));
    centerPanel.add(conditionField, new GridBagConstraints(0, 2, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 24, 0, 34), 486, -4));
    centerPanel.add(jLabel4, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(12, 24, 0, 451), 12, 0));
    centerPanel.add(serviceBox, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(66, 7, 0, 222), 124, -4));
    this.setLayout(borderLayout1);
    this.add(centerPanel, BorderLayout.CENTER);
    applyTemplate2();
  }

  void okButton_actionPerformed(ActionEvent e) {
    condition =  conditionField.getText();
    comment = commentField.getText();
    serviceName=(String)serviceBox.getSelectedItem();

    try{
      serviceId = Integer.parseInt(rootPanel.auth.simpleSelect(rootPanel.access, "id", "services", "name", serviceName));
    }
    catch(Exception ex){
      ex.printStackTrace(System.out);
    }

    if(condition.equals("")){
      error=true;
      errorField.setText("Please fill the condition field");
    }
    else if(serviceName.equals("")){
      error=true;
      errorField.setText("Please choose a service");
    }
    else{
      try{
        Vector[] conditions=rootPanel.auth.select(rootPanel.access, new String[]{"condition"}, "conditions", new String[]{"service_id","user_id"}, new String[]{Integer.toString(serviceId), Integer.toString(userId)});

        String existingcondition="";
        for(int i =0; i<conditions[0].size()&&!error; i++){
          existingcondition =(String)conditions[0].get(i);
          System.err.println("existingcondition: " + existingcondition);
          if(condition.equals(existingcondition)&&!condition.equals(oldCondition)){
            error=true;
            errorField.setText("The defined condition allready exist for this service");
          }
        }
      }
      catch(Exception ex){
        ex.printStackTrace(System.out);
      }
    }
    if(!error){
      if(newEntry){
        try {
          rootPanel.auth.addCondition(rootPanel.access, userName, serviceName, condition, comment);
        }
        catch(Exception ex){
          ex.printStackTrace(System.out);
          errorWithDb();
        }
      }
      else{
        try {
          String selected_service_id=rootPanel.auth.simpleSelect(rootPanel.access, "id", "services", "name", serviceName);
          rootPanel.auth.update(rootPanel.access,"conditions", new String[]{"service_id", "condition", "comment"},
          new String[]{selected_service_id, condition, comment}, new String[]{"id"}, new String[]{Integer.toString(cd.id)});

        }
        catch(Exception ex){
          ex.printStackTrace(System.out);
          errorWithDb();
        }
      }
      if(!error){
        UserPanel up = new UserPanel(userName, rootPanel, UserPanel.CONDITION_TAB);
        rootPanel.changeContentPane(up);
      }
    }
    error=false;
    condition=oldCondition;
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    UserPanel up = new UserPanel(userName, rootPanel, UserPanel.CONDITION_TAB);
    rootPanel.changeContentPane(up);
  }

  void deleteButton_actionPerformed(ActionEvent e) {
    try {
      rootPanel.auth.deleteCondition(rootPanel.access, cd.id);
      UserPanel up = new UserPanel(userName, rootPanel, UserPanel.CONDITION_TAB);
      rootPanel.changeContentPane(up);
    }
    catch(Exception ex){
      ex.printStackTrace(System.out);
      errorWithDb();
    }
  }

}