package com.dexels.navajo.tipi.components;

//import com.dexels.sportlink.client.swing.components.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;
import java.awt.event.*;
import java.util.*;
import nanoxml.*;

public class BasePropertyComponent extends TipiComponent implements PropertyComponent {
//  private JPanel myPanel = new JPanel();
  JLabel nameLabel = new JLabel();
  private Property myProperty = null;
  Component labelStrut = Box.createHorizontalStrut(100);
  PropertyBox myBox = new PropertyBox();
  PropertyField myField = new PropertyField();
  DatePropertyField myDateField = new DatePropertyField();
  PropertyCheckBox myCheckBox = new PropertyCheckBox();
  private ArrayList myListeners = new ArrayList();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  private int default_label_width = 50;

  private boolean showlabel = false;

  public BasePropertyComponent(Property p) {
    this();
    setProperty(p);
  }

//  public Container getContainer() {
//    return myPanel;
//  }

  public BasePropertyComponent() {
    setContainer(createContainer());
//    getContainer().setBackground(Color.cyan);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public Container createContainer() {
    return new JPanel();
  }

  public void addToContainer(Component c, Object constraints) {
    System.err.println("WARNING! ADDING TO BASEPROPERTYCOMPONENT??!!");
    getContainer().add(c);
  }
  public void setContainerLayout(LayoutManager layout){
    throw new UnsupportedOperationException("Can not set layout of container of class: "+getClass());
  }

  public void setLabelWidth(int width){
//    System.err.println("******************* Setting width: " + width);
    labelStrut = Box.createHorizontalStrut(width);
    getContainer().add(labelStrut,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), width, 0));
  }

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws TipiException{
    // not implemented
    String showLabels = (String)elm.getAttribute("showlabel","true");
   if (showLabels.equals("false")) {
      nameLabel.setVisible(false);
   }
   nameLabel.setBackground(Color.red);
  }

  public void addTipiEvent(TipiEvent te) {
    throw new RuntimeException("Adding a tipi event to a BasePropertyComponent?!");
  }

  public void addPropertyComponent(Component c) {
    getContainer().add(c, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 2, 0, 0), 0, 0));
  }

  public void setProperty(Property p) {
    System.err.println("CREATING PROPERTY COMP for PROPERTY: "+p.toXml(null).toString());
    myProperty = p;
    if (p==null) {
      return;
    }
    String description = p.getDescription();
    if (description==null || "".equals(description)) {
      description = p.getName();
    }

    nameLabel.setText(description);
//    nameLabel.setPreferredSize(new Dimension(200,20));
//      System.err.println("TYPE: "+p.getType());
    if (p.getType().equals("selection")) {
      myBox.loadProperty(p);
//      myBox.setPreferredSize(new Dimension(200,20));
      addPropertyComponent(myBox);
      return;
      }
    if (p.getType().equals("boolean")) {
      myCheckBox.setProperty(p);
//      myCheckBox.setPreferredSize(new Dimension(200,20));
      addPropertyComponent(myCheckBox);
      return;

    }

    if (p.getType().equals("date")) {
      myDateField.setProperty(p);
//      myDateField.setPreferredSize(new Dimension(200,20));
      addPropertyComponent(myDateField);
      return;

    }

    myField.setProperty(p);
//    myField.setPreferredSize(new Dimension(200,20));
    addPropertyComponent(myField);
    return;
  }

  private void jbInit() throws Exception {
    nameLabel.setText("x");
    getContainer().setLayout(gridBagLayout1);
    myBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myBox_actionPerformed(e);
      }
    });
    myBox.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        myBox_focusGained(e);
      }
      public void focusLost(FocusEvent e) {
        myBox_focusLost(e);
      }
    });
    myBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        myBox_itemStateChanged(e);
      }
    });
    myField.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        myField_focusGained(e);
      }
      public void focusLost(FocusEvent e) {
        myField_focusLost(e);
      }
    });
    myField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myField_actionPerformed(e);
      }
    });
    myDateField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myDateField_actionPerformed(e);
      }
    });
    myDateField.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        myDateField_focusGained(e);
      }
      public void focusLost(FocusEvent e) {
        myDateField_focusLost(e);
      }
    });
    myCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myCheckBox_actionPerformed(e);
      }
    });
    myCheckBox.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        myCheckBox_focusGained(e);
      }
      public void focusLost(FocusEvent e) {
        myCheckBox_focusLost(e);
      }
    });
    myCheckBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        myCheckBox_itemStateChanged(e);
      }
    });
    getContainer().add(nameLabel,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 0, 0), 0, 0));
    getContainer().add(labelStrut,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), default_label_width, 0));
  }


  public void addTipiEventListener(TipiEventListener listener) {
    if (listener==null) {
      System.err.println("Oh DEAR!");
    }

    myListeners.add(listener);
  }

  public void fireTipiEvent(int type) {
    for (int i = 0; i < myListeners.size(); i++) {
      TipiEventListener current = (TipiEventListener)myListeners.get(i);

      current.performTipiEvent(type,myProperty.getPath());
    }
  }

  void myBox_actionPerformed(ActionEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONACTIONPERFORMED);
  }

  void myBox_focusGained(FocusEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONFOCUSGAINED);
  }

  void myBox_focusLost(FocusEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONFOCUSLOST);
  }

  void myBox_itemStateChanged(ItemEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONSTATECHANGED);
  }

  void myField_focusGained(FocusEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONFOCUSGAINED);

  }

  void myField_focusLost(FocusEvent e) {
    System.err.println("FOCUS LOST!");
    fireTipiEvent(TipiEvent.TYPE_ONFOCUSLOST);

  }

  void myField_actionPerformed(ActionEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONACTIONPERFORMED);

  }

  void myDateField_actionPerformed(ActionEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONACTIONPERFORMED);

  }

  void myDateField_focusGained(FocusEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONFOCUSGAINED);

  }

  void myDateField_focusLost(FocusEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONFOCUSLOST);

  }

  void myCheckBox_actionPerformed(ActionEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONACTIONPERFORMED);

  }

  void myCheckBox_focusGained(FocusEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONFOCUSGAINED);

  }

  void myCheckBox_focusLost(FocusEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONFOCUSLOST);

  }

  void myCheckBox_itemStateChanged(ItemEvent e) {
    fireTipiEvent(TipiEvent.TYPE_ONSTATECHANGED);
  }


//  public void addTipi(Tipi t, TipiContext context, Map td) {
//    // Not implemented
//  }
//  public void addTipiContainer(TipiContainer t, TipiContext context, Map td) {
//    // Not implemented
//  }










}