package com.dexels.navajo.tipi.components.swingimpl.questioneditor;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import com.dexels.navajo.document.*;
//import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
//import com.dexels.navajo.tipi.tipixml.*;
import javax.swing.event.*;
//import com.dexels.navajo.document.nanoimpl.*;
import java.util.*;
import com.dexels.navajo.document.nanoimpl.*;
//import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PropertyElementHelper extends JPanel {

  private TipiProperty myExamplePanel = new TipiProperty();

  private Property myProperty = null;
  private ExampleDocumentListener myDocListner = new ExampleDocumentListener();
  private SelectionPropertyPanel mySelectionPanel = new SelectionPropertyPanel();
//  private XMLElement myNode;
//  private Message myMessage = null;


  public PropertyElementHelper() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    // Kind of a hack, TipiComponents should not really be abused like this.
    myExamplePanel.initContainer();

/** @todo Problem there. Removed it from tipiproperty */
//    myExamplePanel.setVerticalScrolls(false);
    mySelectionPanel.setPropertyHelper(this);
    typeBox.setModel(new DefaultComboBoxModel(Property.VALID_DATA_TYPES));
    directionCombo.setModel(new DefaultComboBoxModel(Property.VALID_DIRECTIONS));
    examplePanel.add( (Component) myExamplePanel.getContainer(),
                     BorderLayout.CENTER);

    nameField.getDocument().addDocumentListener(myDocListner);
    descriptionField.getDocument().addDocumentListener(myDocListner);
    valueField.getDocument().addDocumentListener(myDocListner);
    typeBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myProperty.setType(""+typeBox.getSelectedItem());
        myProperty.setValue("");
        updateExample(myProperty);
      }
    });
    directionCombo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myProperty.setDirection(""+directionCombo.getSelectedItem());
        updateExample(myProperty);

      }
    });

    selectionPanel.add(mySelectionPanel,BorderLayout.CENTER);

  }

  JLabel nameLabel = new JLabel();
  JTextField nameField = new JTextField();
  JLabel descriptionLabel = new JLabel();
  JTextField descriptionField = new JTextField();
  ButtonGroup selectionGroup = new ButtonGroup();
  JPanel examplePanel = new JPanel();
  TitledBorder titledBorder1;
  JRadioButton selectionButton = new JRadioButton();
  JRadioButton valueButton = new JRadioButton();
  JPanel switchPanel = new JPanel();
  JPanel selectionPanel = new JPanel();
  JPanel valuePanel = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JComboBox typeBox = new JComboBox();
  JLabel typeLabel = new JLabel();
  CardLayout cardLayout = new CardLayout();
  JLabel valueLabel = new JLabel();
  JTextField valueField = new JTextField();
  JSpinner lengthSpinner = new JSpinner();
  JLabel lengthLabel = new JLabel();
  JLabel directionLabel = new JLabel();
  JComboBox directionCombo = new JComboBox();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  private final void jbInit() throws Exception {
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.
        white, new Color(148, 145, 140)), "Example:");
    nameLabel.setText("Name:");
    this.setLayout(gridBagLayout1);
    nameField.setText("");
    descriptionLabel.setText("Description");
    descriptionField.setText("");
    examplePanel.setBorder(titledBorder1);
    examplePanel.setLayout(borderLayout1);
    selectionButton.setText("Selection");
    selectionButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selectionButton_actionPerformed(e);
      }
    });
    valueButton.setText("Value:");
    valueButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        valueButton_actionPerformed(e);
      }
    });
    switchPanel.setLayout(cardLayout);
    typeLabel.setText("Type:");
    valuePanel.setLayout(gridBagLayout2);
    valueLabel.setText("Value:");
    valueField.setText("");
    switchPanel.setBorder(BorderFactory.createEtchedBorder());
    lengthLabel.setText("Length:");
    directionLabel.setRequestFocusEnabled(true);
    directionLabel.setText("Direction:");
    selectionPanel.setLayout(borderLayout2);
    valueField.getDocument().addDocumentListener(new DocumentListener(){
      public void changedUpdate(DocumentEvent de) {
        myProperty.setValue(valueField.getText());
        updateExample(myProperty);
      }

      public void insertUpdate(DocumentEvent de) {
        myProperty.setValue(valueField.getText());
        updateExample(myProperty);
      }

      public void removeUpdate(DocumentEvent de) {
        myProperty.setValue(valueField.getText());
        updateExample(myProperty);
      }
    });
//    this.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
//                                               , GridBagConstraints.WEST,
//                                               GridBagConstraints.NONE,
//                                               new Insets(2, 2, 2, 2), 0, 0));
//    this.add(nameField, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
//                                               , GridBagConstraints.WEST,
//                                               GridBagConstraints.HORIZONTAL,
//                                               new Insets(2, 2, 2, 2), 0, 0));
//    this.add(descriptionLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
//        , GridBagConstraints.WEST, GridBagConstraints.NONE,
//        new Insets(2, 2, 2, 2), 0, 0));
//    this.add(descriptionField, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
//        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
//        new Insets(2, 2, 2, 2), 0, 0));
    this.add(examplePanel, new GridBagConstraints(0, 5, 3, 1, 0.0, 1.0
                                                  , GridBagConstraints.NORTH,
                                                  GridBagConstraints.HORIZONTAL,
                                                  new Insets(2, 2, 2, 2),0 , 10));
    this.add(selectionButton, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(2, 2, 2, 2), 0, 0));
    this.add(valueButton, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(2, 2, 2, 2), 0, 0));
    this.add(switchPanel, new GridBagConstraints(0, 4, 3, 1, 1.0, 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(2, 2, 2, 2), 0, 0));
    selectionGroup.add(selectionButton);
    selectionGroup.add(valueButton);
    switchPanel.add(valuePanel, "value");
    switchPanel.add(selectionPanel, "selection");
//    this.add(directionLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
//        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
//        new Insets(2, 2, 2, 2), 0, 0));
//    this.add(directionCombo, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
//        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
//        new Insets(2, 2, 2, 2), 0, 0));
    valuePanel.add(typeLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 2, 2, 2), 0, 0));
    valuePanel.add(typeBox, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(2, 2, 2, 2), 0, 0));
    valuePanel.add(valueLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.NONE,
        new Insets(2, 2, 2, 2), 0, 0));
    valuePanel.add(valueField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
        new Insets(2, 2, 2, 2), 0, 0));
//    valuePanel.add(lengthLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
//        , GridBagConstraints.WEST, GridBagConstraints.NONE,
//        new Insets(2, 2, 2, 2), 0, 0));
//    valuePanel.add(lengthSpinner, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
//        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
//        new Insets(2, 2, 2, 2), 0, 0));
  }

  void selectionButton_actionPerformed(ActionEvent e) {
    valueSelectionUpdate();
  }

  void valueButton_actionPerformed(ActionEvent e) {
    valueSelectionUpdate();
  }

  private final void valueSelectionUpdate() {
//    System.err.println("Val. update");
    if (selectionButton.isSelected()) {
      myProperty.setType(Property.SELECTION_PROPERTY);
      cardLayout.show(switchPanel, "selection");
    }
    else {
      // switch to value
      myProperty.setType(""+typeBox.getSelectedItem());
      cardLayout.show(switchPanel, "value");

    }
    updateExample(myProperty);
  }


//  public void apply() {
//    applyToNode();
//  }
  public void load(Message question) {
    /** @todo Fix cloning */

//    myMessage = question;
    Property valueProperty = question.getProperty("Value");
    myProperty = valueProperty;
//    myNode = xe;
//    String name = myNode.getStringAttribute(Property.PROPERTY_NAME);
//    String name =
//    nameField.setText(valueProperty.);
//    descriptionField.setText(v Property.PROPERTY_DESCRIPTION));
//    String type = xe.getStringAttribute(Property.PROPERTY_TYPE);
      String type = valueProperty.getType();
    if (Property.SELECTION_PROPERTY.equals(type)) {
      // switch to selection
      cardLayout.show(switchPanel, "selection");
      selectionButton.setSelected(true);
 /** @todo Was: getLastElement() */
      mySelectionPanel.load(myProperty);
    }
    else {
      // switch to value
      mySelectionPanel.clear();
      cardLayout.show(switchPanel, "value");
//      String length = xe.getStringAttribute(Property.PROPERTY_LENGTH);
//      if (length==null) {
//        length="0";
//      }
//      lengthSpinner.setValue(new Integer(length));
      valueButton.setSelected(true);
      typeBox.setSelectedItem(valueProperty.getType());
      valueField.setText(myProperty.getValue());
    }

    directionCombo.setSelectedItem(myProperty.getDirection());
    updateExample(myProperty);

  }

 void updateExample(Property xe) {
    Property clone = (Property) xe.clone();
    myExamplePanel.setProperty(clone);
      ((JComponent)myExamplePanel.getContainer()).revalidate();

  }

  class ExampleDocumentListener
      implements DocumentListener {
    public void changedUpdate(DocumentEvent de) {
      updateExample(myProperty);
//      System.err.println("changedUpdate update");
    }

    public void insertUpdate(DocumentEvent de) {
      updateExample(myProperty);
//      System.err.println("insertUpdate update");
    }

    public void removeUpdate(DocumentEvent de) {
      updateExample(myProperty);
//      System.err.println("Remove update");
    }
  }
}
