package com.dexels.navajo.tipi.components;

//import com.dexels.sportlink.client.swing.components.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.awt.event.*;
import java.util.*;

public class BasePropertyComponent
    extends SwingTipiComponent
    implements PropertyComponent {
//  private JPanel myPanel = new JPanel();
//  JLabel nameLabel = new JLabel();
  private Property myProperty = null;
//  Component labelStrut = Box.createHorizontalStrut(100);
//  Component propertyStrut = Box.createHorizontalStrut(100);

  PropertyBox myBox = null;
  MultipleSelectionPropertyCheckboxGroup myMultiple = null;
  MultipleSelectionPropertyList myMultipleList =null;
  PropertyField myField = null;
  DatePropertyField myDateField = null;
  PropertyCheckBox myCheckBox = null;

  private ArrayList myListeners = new ArrayList();
//  GridBagLayout gridBagLayout1 = new GridBagLayout();
  private int default_label_width = 50;
  private int default_property_width = 50;

//  private boolean showlabel = false;
  private boolean use_checkbox = false;
  private Component currentPropertyComponent = null;


  public BasePropertyComponent(Property p) {
    this();
    setProperty(p);
  }
  public BasePropertyComponent() {
    initContainer();
//    try {
//      jbInit();
//    }
//    catch (Exception e) {
//      e.printStackTrace();
//    }
  }

  public Container createContainer() {
    PropertyPanel p =  new PropertyPanel();
    p.setVisible(false);
    return p;
  }


//  public void addToContainer(Component c, Object constraints) {
//    System.err.println("WARNING! ADDING TO BASEPROPERTYCOMPONENT??!!");
//    getContainer().add(c);
//  }
  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
  }
  public void removeFromContainer(Component c) {
    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
  }

  public void setContainerLayout(LayoutManager layout) {
    throw new UnsupportedOperationException("Can not set layout of container of class: " + getClass());
  }

  public void setLabelWidth(int width) {
  }
  public void setPropertyWidth(int width) {
  }

  public void addTipiEvent(TipiEvent te) {
    throw new RuntimeException("Adding a tipi event to a BasePropertyComponent?!");
  }

  public void addPropertyComponent(Component c) {
    currentPropertyComponent = c;
    ((PropertyPanel)getContainer()).setPropertyComponent(c);
  }

  public void setLabelVisible(boolean state){
    if(state){
//      labelStrut.setSize(default_label_width, 0);
    }else{
//      getContainer().remove(labelStrut);
      ((PropertyPanel)getContainer()).hideLabel();

    }
//    nameLabel.setVisible(state);
  }

  public void setProperty(Property p) {
    myProperty = p;
    if (p == null) {
      return;
    }
    String description = p.getDescription();
    if (description == null || "".equals(description)) {
      description = p.getName();
    }
    ((PropertyPanel)getContainer()).setLabel(description);

//    nameLabel.setText(description);
    constructPropertyComponent(p);
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ((PropertyPanel)getContainer()).setVisible(true);
      }
    });
//    getContainer().doLayout();
  }

  private void constructPropertyComponent(Property p) {
    if (p.getType().equals("selection")) {
      if (!"+".equals(p.getCardinality())) {
        createPropertyBox(p);
        return;
      } else {
        if (use_checkbox) {
          createPropertyCheckboxList(p);
        } else {
          createPropertyList(p);
        }
        return;
      }
    }
    if (p.getType().equals("boolean")) {
      createPropertyCheckbox(p);
      return;

    }

    if (p.getType().equals("date")) {
      createPropertyDateField(p);
      return;

    }
    createPropertyField(p);
    return;
  }

  private void createPropertyBox(Property p) {
    if (myBox==null) {
      myBox = new PropertyBox();
    }
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

    myBox.loadProperty(p);
    addPropertyComponent(myBox);
  }

  private void createPropertyList(Property p) {
    if (myMultipleList==null) {
      myMultipleList = new MultipleSelectionPropertyList();
    }
    myMultipleList.setProperty(p);
    addPropertyComponent(myMultipleList);
    myMultipleList.revalidate();
    myMultipleList.repaint();
//    myMultipleList.revalidate();
  }

  private void createPropertyCheckboxList(Property p) {
    if (myMultiple==null) {
      myMultiple = new MultipleSelectionPropertyCheckboxGroup();
    }
    myMultiple.setProperty(p);
    addPropertyComponent(myMultiple);
  }

  private void createPropertyCheckbox(Property p) {
    if (myCheckBox==null) {
      myCheckBox = new PropertyCheckBox();
    }
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
    myCheckBox.setProperty(p);
    addPropertyComponent(myCheckBox);
  }

  private void createIntegerField(Property p) {
    JFormattedTextField jf = new JFormattedTextField();
  }

  private void createPropertyDateField(Property p) {
    if (myDateField==null) {
      myDateField = new DatePropertyField();
    }
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
    myDateField.setProperty(p);
    addPropertyComponent(myDateField);
  }
  private void createPropertyField(Property p) {
    if (myField==null) {
      myField = new PropertyField();
    }
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
    myField.setProperty(p);
    addPropertyComponent(myField);
  }

  public void addTipiEventListener(TipiEventListener listener) {
    if (listener == null) {
      System.err.println("Oh DEAR!");
    }

    myListeners.add(listener);
  }

//
//  private void setIndent(int size) {
//    labelStrut.setSize(size,0);
//    propertyStrut.setSize(size,0);
//  }
//
  public void setEnabled(boolean value){
    //System.err.println("SetEnabled called in BasePropertyComponent");
    if(myProperty != null){
      if (myProperty.getType().equals("selection") && !"+".equals(myProperty.getCardinality())) {
        myBox.setEnabled(value);
        return;
      }
      if (myProperty.getType().equals("selection") && "+".equals(myProperty.getCardinality())) {
        myMultiple.setEnabled(value);
        return;
      }
      if (myProperty.getType().equals("boolean")) {
        myCheckBox.setEnabled(value);
        return;
      }
      if (myProperty.getType().equals("date")) {
        myDateField.setEnabled(value);
        return;
      }
      myField.setEnabled(value);
      return;
    }else{
      System.err.println("Whoops I have no Property.. how is this possible??");
    }
  }



  public void fireTipiEvent(int type) {
    if (myProperty == null) {
      System.err.println("Trying to fire event d=from null property!");
      return;
    }

    try {
      for (int i = 0; i < myListeners.size(); i++) {
        TipiEventListener current = (TipiEventListener) myListeners.get(i);
        current.performTipiEvent(type, myProperty.getFullPropertyName());
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
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
  public void setComponentValue(String name, Object object) {
    /**@todo Override this com.dexels.navajo.tipi.TipiComponent method*/
//    System.err.println("Setting: "+name+" to : "+object);
    if ("use_checkbox".equals(name)) {
      use_checkbox = "true".equals(object);
    }
    if ("showlabel".equals(name)) {
//      nameLabel.setVisible("true".equals(object));
    }
    if ("label_valign".equals(name)) {
      int valign = JLabel.CENTER;
      if ("top".equals(object)) {
        valign = JLabel.TOP;
      }
      if ("bottom".equals(object)) {
        valign = JLabel.BOTTOM;
      }
      if ("center".equals(object)) {
        valign = JLabel.CENTER;
      }
      ((PropertyPanel)getContainer()).setVerticalLabelAlignment(valign);
    }
    if ("label_halign".equals(name)) {
      int halign = JLabel.LEADING;
      if ("left".equals(object)) {
        halign = JLabel.LEFT;
      }
      if ("right".equals(object)) {
        halign = JLabel.RIGHT;
      }
      if ("leading".equals(object)) {
        halign = JLabel.LEADING;
      }
      if ("center".equals(object)) {
        halign = JLabel.CENTER;
      }
      if ("trailing".equals(object)) {
        halign = JLabel.TRAILING;
      }
      ((PropertyPanel)getContainer()).setHorizontalLabelAlignment(halign);
    }
    if ("label_indent".equals(name)) {
      int lindent = Integer.parseInt(""+object);
      ((PropertyPanel)getContainer()).setLabelIndent(lindent);
    }
    super.setComponentValue(name, object);
  }
}