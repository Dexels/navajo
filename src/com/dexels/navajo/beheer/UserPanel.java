package com.dexels.navajo.beheer;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import java.awt.event.*;

import java.sql.*;
import java.util.ResourceBundle;
//import java.util.Random;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.document.*;
import java.util.Vector;
//import java.util.Hashtable;
import com.dexels.navajo.util.*;
import com.dexels.navajo.server.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class UserPanel extends BaseNavajoPanel{
  static final int AUTHORISE_TAB =0;
  static final int PARAM_TAB =1;
  static final int CONDITION_TAB =2;

  private int userId = -1;
  private String userName = "Geen verbinding";
  private String oldUserName ="";
  private String userPassword ="Geen verbinding";
  private int[] authorisedGroupIds;
  private int activeTab = 0;

  private JPanel centerPanel = new JPanel();

  private JTextField passwordField = new JTextField();
  private JTextField userNameField = new JTextField();

  private JLabel passwordLabel = new JLabel();
  private JLabel userIdField = new JLabel();
  private JLabel userIdLabel = new JLabel();
  private JLabel nameLabel = new JLabel();
  private JLabel conditionLabel = new JLabel();
  private JLabel paramValueLabel = new JLabel();
  private JLabel paramNameLabel = new JLabel();
  private JLabel conditionServiceLabel = new JLabel();
  private JLabel authedLabel = new JLabel();
  private JLabel addAuthLabel = new JLabel();

  private JScrollPane parametersScrollPane = new JScrollPane();
  private JButton newConditionButton = new JButton();
  private JScrollPane conditionsScrollPane = new JScrollPane();

  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JPanel conditionTab = new JPanel();
  private JPanel authorisationTab = new JPanel();
  private JPanel paramTab = new JPanel();
  private JPanel serviceGroupPanel = new JPanel();
  private JPanel parameterPanel = new JPanel();
  private JPanel conditionsPanel = new JPanel();
  private JButton addParamButton = new JButton();
  private JButton newAuthoriseButton = new JButton();
  private JScrollPane services = new JScrollPane();
  private JComboBox serviceGroupBox = new JComboBox();

  private BorderLayout borderLayout1 = new BorderLayout();
  private GridBagLayout gridBagLayout4 = new GridBagLayout();
  private VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  private VerticalFlowLayout verticalFlowLayout3 = new VerticalFlowLayout();
  private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  private BorderLayout borderLayout2 = new BorderLayout();
  private BorderLayout borderLayout3 = new BorderLayout();
  private FlowLayout flowLayout1 = new FlowLayout();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();

  public UserPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    };
  }

  public UserPanel(RootPanel frame) {
    rootPanel=frame;
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public UserPanel(String user, RootPanel frame, int active) {
    rootPanel=frame;
    userName = user;
    oldUserName = user;
    activeTab= active;

    if(userName.equals("")){
      userPassword = "";
      newEntry=true;
    }
    else{
      try {
        userId = Integer.parseInt(rootPanel.auth.simpleSelect(rootPanel.access,"id", "users", "name", user));
        userPassword = rootPanel.auth.simpleSelect(rootPanel.access,"password", "users", "id", Integer.toString(userId));
      }
      catch(SystemException ex) {
        ex.printStackTrace();
      }
    }
    try {
        jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    title="User";
    Vector definitions = rootPanel.auth.allDefinitions(rootPanel.access);
    Vector params = rootPanel.auth.allParametersForUser(rootPanel.access, userName);
    Vector serviceGroups = rootPanel.auth.allServiceGroups(rootPanel.access);
    Vector authorisations = rootPanel.auth.allAuthorisationForUser(rootPanel.access, userName);
    Vector conditions = rootPanel.auth.allConditionsForUser(rootPanel.access, userName);

    if(newEntry){
      userIdField.setText("new");
    }
    else{
      userIdField.setText(Integer.toString(userId));
    }
    authorisationTab.setLayout(borderLayout3);
    passwordLabel.setText("Password");
    passwordField.setText(userPassword);
    userNameField.setText(userName);
    userIdLabel.setText("UserID");
    nameLabel.setText("Name");
    parameterPanel.setLayout(verticalFlowLayout1);
    conditionLabel.setText("Condition");
    paramValueLabel.setText("Value");
    paramNameLabel.setText("Parameter");
    newConditionButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newConditionButton_actionPerformed(e);
      }
    });
    newConditionButton.setText("Create new conditions");
    conditionsPanel.setLayout(verticalFlowLayout3);
    paramTab.setLayout(gridBagLayout2);
    conditionServiceLabel.setText("Service");
    conditionTab.setLayout(gridBagLayout4);

    parameterPanel.setBackground(Color.white);
    conditionsScrollPane.getViewport().setBackground(SystemColor.control);
    paramTab.setBackground(SystemColor.control);
    parametersScrollPane.getViewport().setBackground(SystemColor.control);
    conditionsPanel.setBackground(Color.white);

    this.setLayout(borderLayout1);
    this.setBackground(Color.yellow);
    centerPanel.setBackground(Color.yellow);
    addParamButton.setText("Add new parameter");
    addParamButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addParamButton_actionPerformed(e);
      }
    });
    authedLabel.setHorizontalAlignment(SwingConstants.CENTER);
    authedLabel.setText("Authorised Groups");
    serviceGroupPanel.setBackground(Color.white);
    serviceGroupPanel.setLayout(verticalFlowLayout2);
    newAuthoriseButton.setText("Add");
    newAuthoriseButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newAuthoriseButton_actionPerformed(e);
      }
    });
    addAuthLabel.setHorizontalAlignment(SwingConstants.CENTER);
    addAuthLabel.setText("Add authorisation for:");


//    if(!newEntry){
      authorisedGroupIds=new int[authorisations.size()];

       for(int j =0; j<authorisations.size(); j++){
        int authorisedId = Integer.parseInt((String)authorisations.get(j));
        authorisedGroupIds[j]=authorisedId;
      }

      for(int i = 0; i<serviceGroups.size(); i++){
        boolean match = false;
        ServiceGroup sg = (ServiceGroup )serviceGroups.get(i);
        String groupName = sg.name;
        int groupId= sg.id;
    //      String servletName = sg.servlet;
        serviceGroupBox.addItem(groupName);

        for(int j =0; j<authorisedGroupIds.length&&!match; j++){
          if(groupId == authorisedGroupIds[j]){
            match=true;
            ServiceGroupRowPanel sgp = new ServiceGroupRowPanel(sg, userName, rootPanel);
            serviceGroupPanel.add(sgp);
          }
        }
      }

//        for(int j =0; j<params.size()&&!match; j++){
      for(int j =0; j<params.size(); j++){
        Parameter parameter = (Parameter)params.get(j);
        for(int i = 0; i<definitions.size(); i++){
          Parameter definition = (Parameter)definitions.get(i);
          if(parameter.def_id == definition.def_id){
            String value=parameter.value;
            String condition=parameter.condition;
//            int param_id=0;
            int id=parameter.id;
//            param_id=definition.def_id;
            ParameterRowPanel def = new ParameterRowPanel(definition.name, value, condition, id, rootPanel, userName);
            parameterPanel.add(def);
          }
        }
      }

      for(int i = 0; i<conditions.size(); i++){
        ConditionData conditionData = (ConditionData)conditions.get(i);
        ConditionRowPanel cp = new ConditionRowPanel (conditionData, rootPanel, userName, userId);
        conditionsPanel.add(cp);
      }

//    }

    centerPanel.setLayout(borderLayout2);
    this.add(centerPanel, BorderLayout.CENTER);

    JPanel generalInfoPanel = new JPanel(new XYLayout());
    generalInfoPanel.setBackground(Color.yellow);
    generalInfoPanel.add(userIdLabel, new XYConstraints(8, 2, 63, 21));
    generalInfoPanel.add(userIdField, new XYConstraints(94, 1, 32, 21));
    generalInfoPanel.add(userNameField, new XYConstraints(91, 23, 304, -1));
    generalInfoPanel.add(passwordField, new XYConstraints(91, 44, 304, -1));
    generalInfoPanel.add(passwordLabel, new XYConstraints(8, 44, 63, 21));
    generalInfoPanel.add(nameLabel, new XYConstraints(8, 23, 63, 21));
    centerPanel.add(generalInfoPanel, BorderLayout.NORTH);
    centerPanel.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(authorisationTab, "authorisation");

    JPanel authorisationTabFooter = new JPanel(new BorderLayout());

    authorisationTab.add(authedLabel, BorderLayout.NORTH);
    authorisationTabFooter.add(addAuthLabel, BorderLayout.WEST);
    authorisationTabFooter.add(serviceGroupBox, BorderLayout.CENTER);
    authorisationTabFooter.add(newAuthoriseButton, BorderLayout.EAST);
    authorisationTab.add(authorisationTabFooter, BorderLayout.SOUTH);
    //empty panels te serve as padding
    JPanel authFill1 = new JPanel(new BorderLayout());
    JPanel authFill2 = new JPanel(new BorderLayout());
    authFill1.setPreferredSize(new Dimension(10, 0));
    authFill2.setPreferredSize(new Dimension(10, 0));

    authorisationTab.add(authFill1, BorderLayout.WEST);
    authorisationTab.add(authFill2, BorderLayout.EAST);
    authorisationTab.add(services, BorderLayout.CENTER);

    jTabbedPane1.add(paramTab, "Parameters");
    paramTab.add(parametersScrollPane, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 0, 11), 349, 172));
    paramTab.add(addParamButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(6, 47, 11, 102), 0, 0));
    paramTab.add(paramValueLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 169, 0, 90), 0, 0));
    paramTab.add(paramNameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(6, 47, 0, 0), 0, 0));
    jTabbedPane1.add(conditionTab, "Conditions");
    conditionTab.add(conditionsScrollPane, new GridBagConstraints(0, 1, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 3, 0, 0), 472, 159));
    conditionTab.add(newConditionButton, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 9, 33, 0), 34, -2));
    conditionTab.add(conditionLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 93, 0, 0), 16, 1));
    conditionTab.add(conditionServiceLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 74, 0, 0), 29, 1));
    conditionsScrollPane.getViewport().add(conditionsPanel, null);
    parametersScrollPane.getViewport().add(parameterPanel, null);
    services.getViewport().add(serviceGroupPanel, null);
    applyTemplate2();
    services.getViewport();
    jTabbedPane1.setSelectedIndex(activeTab);
  }

  void okButton_actionPerformed(ActionEvent e) {

    userName= userNameField.getText();
    userPassword= passwordField.getText();

    if(userName.equals("")){
      error=true;
      errorField.setText("Please fill Username");
    }
    else{
      Vector names = new Vector();
      try{
        names = rootPanel.auth.allUsers(rootPanel.access);
      }
      catch(Exception ex){
        ex.printStackTrace(System.out);
        error=true;
        errorField.setText("Lost connection with database");
      }
      for(int i =0; i<names.size()&&!error; i++){
        if(userName.equals((String)names.get(i))&&!userName.equals(oldUserName)){
          error=true;
          errorField.setText("Username already exist");
        }
      }
    }

    if(!error){
      if(newEntry){
        try{
          int a= rootPanel.auth.addUser(rootPanel.access, userNameField.getText(), passwordField.getText());
        }
        catch(Exception ex){
          ex.printStackTrace();
        }
      }
      else{
        try{
          rootPanel.auth.update(rootPanel.access, "users", new String[]{"name", "password"}, new String[]{userName, userPassword}, new String[]{"id"}, new String[]{Integer.toString(userId)});
        }
        catch(Exception ex){
          ex.printStackTrace();
        }
      }
      rootPanel.changeToBeheerPanel();
    }
    userName= oldUserName;
    error=false;
  }

  void newAuthoriseButton_actionPerformed(ActionEvent e) {
    String selectedGroupName = (String)serviceGroupBox.getSelectedItem();
    String id = "";
    try{
      id=rootPanel.auth.simpleSelect(rootPanel.access, "id", "service_group", "name", selectedGroupName);
    }
    catch(Exception ex){
      ex.printStackTrace(System.out);
    }

    int selectedGroupId = Integer.parseInt(id);
    boolean allreadyInUse=false;
    for(int j =0; j<authorisedGroupIds.length&&!allreadyInUse; j++){
      if(selectedGroupId == authorisedGroupIds[j]){
        errorField.setText("Selected Service Group is allready authorised");
//        System.err.println("Selected Service Group is allready authorised");
        allreadyInUse=true;
      }
    }
    if(!allreadyInUse){
      try{
        rootPanel.auth.addAuthorisation(rootPanel.access, userName, selectedGroupId);
      }
      catch(Exception ex){
        ex.printStackTrace(System.out);
      }
      UserPanel up = new UserPanel(userName, rootPanel, AUTHORISE_TAB);
      rootPanel.changeContentPane(up);
    }
  }

  void newParamButton_actionPerformed(ActionEvent e) {
    DefinitionPanel definitionPanel = new DefinitionPanel(-1, "new", rootPanel);
    rootPanel.changeContentPane(definitionPanel);
  }

  void newConditionButton_actionPerformed(ActionEvent e) {
    ConditionPanel cp = new ConditionPanel(null, rootPanel, "", userName, userId);
    rootPanel.changeContentPane(cp);
  }

  void deleteButton_actionPerformed(ActionEvent e) {

    try{
      rootPanel.auth.deleteUser(rootPanel.access, userName);
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    rootPanel.changeToBeheerPanel();
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    rootPanel.changeToBeheerPanel();
  }

  private void addParamButton_actionPerformed(ActionEvent e) {
    //create new parameter for this user
    ParamPanel paramPanel = new ParamPanel(-1, "new", userName, "", "", rootPanel);
    rootPanel.changeContentPane(paramPanel);
  }

}
