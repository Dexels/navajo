package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.swingimpl.questioneditor.*;
import com.dexels.navajo.document.nanoimpl.*;
import java.awt.event.*;

//import com.borland.jbcl.layout.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingQuestionEditForm
    extends JPanel {
  GenericPropertyComponent nameField = new GenericPropertyComponent();
  GenericPropertyComponent idField = new GenericPropertyComponent();
  GenericPropertyComponent titleField = new GenericPropertyComponent();
  GenericPropertyComponent visibleConditionField = new GenericPropertyComponent();
  GenericPropertyComponent validationConditionField = new
      GenericPropertyComponent();
  GenericPropertyComponent errorMessageField = new GenericPropertyComponent();
//  JComboBox typeBox = new JComboBox();

  PropertyElementHelper myHelper = new PropertyElementHelper();

  GridBagLayout gridBagLayout1 = new GridBagLayout();


  private Message myMessage = null;

  private static final int INDENT = 120;
  JButton saveButton = new JButton();
  JPanel switchPanel = new JPanel();
  CardLayout cardLayout1 = new CardLayout();
  JPanel groupPanel = new JPanel();
  JPanel questionPanel = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JPanel questionListPanel = new JPanel();
  JButton newGroupButton = new JButton();
  JButton newQuestionButton = new JButton();
  JButton deleteGroupButton = new JButton();
  JLabel newQuestionLabel = new JLabel();
  JTextField newQuestionField = new JTextField();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JTextField newGroupField = new JTextField();
  JLabel newGroupLabel = new JLabel();
  GridBagLayout gridBagLayout4 = new GridBagLayout();
  JTextField renameGroupField = new JTextField();
  JLabel renameGroupLabel = new JLabel();
  JButton renameGroupButton = new JButton();

  private final TipiSwingGroupPanel myPanel;

  public TipiSwingQuestionEditForm(TipiSwingGroupPanel pp) {
    myPanel = pp;
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    nameField.setLabelIndent(INDENT);
    idField.setLabelIndent(INDENT);
    titleField.setLabelIndent(INDENT);
    visibleConditionField.setLabelIndent(INDENT);
    validationConditionField.setLabelIndent(INDENT);
    errorMessageField.setLabelIndent(INDENT);
    nameField.setHardEnabled(true);
    idField.setHardEnabled(true);
    titleField.setHardEnabled(true);
    visibleConditionField.setHardEnabled(true);
    validationConditionField.setHardEnabled(true);
    errorMessageField.setHardEnabled(true);
//    typeBox.setModel(new DefaultComboBoxModel(Property.VALID_DATA_TYPES));
  }

  private final void jbInit() throws Exception {
    this.setLayout(new BorderLayout());
    newGroupButton.setText("New Group");
    newGroupButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newGroupButton_actionPerformed(e);
      }
    });
    newQuestionButton.setText("New question");
    newQuestionButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newQuestionButton_actionPerformed(e);
      }
    });
    deleteGroupButton.setText("Delete group");
    deleteGroupButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteGroupButton_actionPerformed(e);
      }
    });
    newQuestionLabel.setText("New question:");
    newQuestionField.setText("");
    groupPanel.setLayout(gridBagLayout3);
    questionListPanel.setLayout(gridBagLayout4);
    newGroupField.setText("");
    newGroupLabel.setRequestFocusEnabled(true);
    newGroupLabel.setToolTipText("");
    newGroupLabel.setText("New Group:");
    renameGroupLabel.setText("Rename group");
    renameGroupButton.setText("Rename");
    renameGroupButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        renameGroupButton_actionPerformed(e);
      }
    });
    renameGroupField.setText("");
    add(switchPanel,BorderLayout.CENTER);
    saveButton.setText("Save");
    saveButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveButton_actionPerformed(e);
      }
    });
    switchPanel.setLayout(cardLayout1);
    questionPanel.setLayout(gridBagLayout2);
    questionPanel.add(nameField,  new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    questionPanel.add(titleField,  new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    questionPanel.add(idField,  new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
//    this.add(typeBox, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
//                                             , GridBagConstraints.CENTER,
//                                             GridBagConstraints.HORIZONTAL,
//                                             new Insets(0, 0, 0, 0), 0, 0));
    questionPanel.add(errorMessageField,  new GridBagConstraints(0, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    questionPanel.add(visibleConditionField,  new GridBagConstraints(0, 6, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    questionPanel.add(validationConditionField,
              new GridBagConstraints(0, 7, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    questionPanel.add(myHelper,
              new GridBagConstraints(0, 8, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    questionPanel.add(saveButton,   new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    switchPanel.add(questionListPanel,   "QuestionList");
    questionListPanel.add(newGroupButton,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
//    questionPanel.add(switchPanel,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
//            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    switchPanel.add(groupPanel, "Group");
    switchPanel.add(questionPanel,  "Question");
    groupPanel.add(newQuestionLabel,      new GridBagConstraints(0, 2, 1, 2, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 2, 2), 0, 0));
    groupPanel.add(newQuestionField,         new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 2, 2, 2), 0, 0));
    groupPanel.add(newQuestionButton,       new GridBagConstraints(2, 1, 1, 3, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 2, 2), 0, 0));
    groupPanel.add(deleteGroupButton,       new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    questionListPanel.add(newGroupField,     new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    questionListPanel.add(newGroupLabel,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    groupPanel.add(renameGroupField,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    groupPanel.add(renameGroupLabel,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    groupPanel.add(renameGroupButton,    new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
//    this.add(nav,
//              new GridBagConstraints(1, 0, 1, 7, 0.0, 0.0
//            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
  }

  public void setMessage(Message question) {
    myMessage = question;
    if (question==null) {
// or show an empty panel or something
      return;
    }
    System.err.println("In setmessage: " + question.getName());
    System.err.println("In setmessage: " + question.getType());

    if (question.getName().equals("Question")) {
      // Question stuff
      cardLayout1.show(switchPanel,"Question");
      Property title = question.getProperty("Title");
      Property name = question.getProperty("Name");
      Property id = question.getProperty("Id");
       Property validation = question.getProperty("ValidationCondition");
      Property visprop = question.getProperty("VisibleCondition");
      Property errorMessage = question.getProperty("ErrorMessage");
      errorMessageField.setProperty(errorMessage);
      visibleConditionField.setProperty(validation);
      validationConditionField.setProperty(visprop);
      nameField.setProperty(name);
      idField.setProperty(id);
      titleField.setProperty(title);
      myHelper.load(question);
    }
    if (question.getName().equals("Group")) {
      cardLayout1.show(switchPanel,"Group");
      Property groupName = question.getProperty("Group");
      if (groupName!=null) {
        renameGroupField.setText(groupName.getValue());
      } else {
      renameGroupField.setText("No name");
      }
      // Group stuff
    }
    if (question.getName().equals("QuestionList")) {
      cardLayout1.show(switchPanel,"QuestionList");
      // Group stuff
    }
  }

  void saveButton_actionPerformed(ActionEvent e) {
//    myHelper.apply();
  }

  void newGroupButton_actionPerformed(ActionEvent e) {
    System.err.println("New group: "+myMessage.getName()+" type: "+myMessage.getType());
    Navajo root = myMessage.getRootDoc();
    String id = newGroupField.getText();
    if (id==null ||"".equals(id)) {
      return;
    }
    Message parentMessage = myMessage.getParentMessage();
    System.err.println("perent of New group: "+parentMessage.getName()+" type: "+parentMessage.getType());
    if (parentMessage!=null) {
      try {
        Message newQuestion = createGroup(root, id);
        parentMessage.addMessage(newQuestion);
        myPanel.reload();
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }

    }

  }

  void deleteGroupButton_actionPerformed(ActionEvent e) {
    System.err.println("Deleting group: "+myMessage.getName()+" type: "+myMessage.getType());
    Navajo root = myMessage.getRootDoc();
    Message parentMessage = myMessage.getParentMessage();
    if (parentMessage != null) {
      parentMessage.removeMessage(myMessage);
      myPanel.reload();
    }

  }

  void newQuestionButton_actionPerformed(ActionEvent e) {
    System.err.println("New question: "+myMessage.getName()+" type: "+myMessage.getType());
    Navajo root = myMessage.getRootDoc();
    String id = newQuestionField.getText();
    if (id==null ||"".equals(id)) {
      return;
    }
    Message parentMessage = myMessage.getMessage("Question");
    if (parentMessage == null) {
      parentMessage = NavajoFactory.getInstance().createMessage(root,"Question",Message.MSG_TYPE_ARRAY);
      myMessage.addMessage(parentMessage);
    }

    System.err.println("parent of New question: "+parentMessage.getName()+" type: "+parentMessage.getType());
    if (parentMessage!=null) {
      try {
        Message newQuestion = createQuestion(root, id);
        parentMessage.addMessage(newQuestion);
        System.err.println("\n\nCreated: \n");
       parentMessage.write(System.err);
       System.err.println("\n");
       myPanel.reload();
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }

    }
  }

  private Message createQuestion(Navajo n,String idString) throws NavajoException{
    Message m = NavajoFactory.getInstance().createMessage(n,"Question");
    Property id = NavajoFactory.getInstance().createProperty(n,"Id",Property.STRING_PROPERTY,idString,128,"",Property.DIR_OUT);
    Property name = NavajoFactory.getInstance().createProperty(n,"Name",Property.STRING_PROPERTY,"No name yet",128,"",Property.DIR_OUT);
    Property title = NavajoFactory.getInstance().createProperty(n,"Title",Property.STRING_PROPERTY,"",128,"",Property.DIR_OUT);
    Property visible = NavajoFactory.getInstance().createProperty(n,"VisibleCondition",Property.STRING_PROPERTY,"",128,"",Property.DIR_OUT);
    Property validation = NavajoFactory.getInstance().createProperty(n,"ValidationCondition",Property.STRING_PROPERTY,"",128,"",Property.DIR_OUT);
    Property errorMessage = NavajoFactory.getInstance().createProperty(n,"ErrorMessage",Property.STRING_PROPERTY,"",128,"",Property.DIR_OUT);
    Property value = NavajoFactory.getInstance().createProperty(n,"Value",Property.STRING_PROPERTY,"",128,"",Property.DIR_IN);
    m.addProperty(id);
    m.addProperty(name);
   m.addProperty(title);
    m.addProperty(visible);
    m.addProperty(validation);
    m.addProperty(errorMessage);
    m.addProperty(value);
    return m;
  }
  private Message createGroup(Navajo n,String idString) throws NavajoException{
    Message m = NavajoFactory.getInstance().createMessage(n,"Group");
    Property id = NavajoFactory.getInstance().createProperty(n,"Id",Property.STRING_PROPERTY,idString,128,"",Property.DIR_OUT);
    Property name = NavajoFactory.getInstance().createProperty(n,"Name",Property.STRING_PROPERTY,"No name yet",128,"",Property.DIR_OUT);
    m.addProperty(id);
    m.addProperty(name);
    return m;
  }

  void renameGroupButton_actionPerformed(ActionEvent e) {

  }


}
