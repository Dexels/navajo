package com.dexels.navajo.tipi.components;

import com.dexels.navajo.document.*;
import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.awt.event.*;
import java.util.*;
import com.dexels.navajo.parser.Operand;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.server.*;

public class BasePropertyComponent extends SwingTipiComponent implements PropertyComponent {
  private Property myProperty = null;

  PropertyBox myBox = null;
  MultipleSelectionPropertyCheckboxGroup myMultiple = null;
  MultipleSelectionPropertyList myMultipleList =null;
  TextPropertyField myField = null;
  DatePropertyField myDateField = null;
  PropertyCheckBox myCheckBox = null;
  IntegerPropertyField myIntField = null;
  private ArrayList myListeners = new ArrayList();
  private int default_label_width = 50;
  private int default_property_width = 50;
  private boolean hardEnabled = false;
  private boolean myVisibleState = true;
  private boolean myEnableState = true;
  private boolean use_checkbox = false;
  private Component currentPropertyComponent = null;
  private String myCapitalization = "off";
  private String myPropertyName = null;
  public BasePropertyComponent(Property p) {
    this();
    setProperty(p);
  }
  public BasePropertyComponent() {
    initContainer();
  }

  public Container createContainer() {
    PropertyPanel p =  new PropertyPanel();
//    p.setVisible(false);
    addTipiEventListener(this);
    return p;
  }

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
    myEventList.add(te);
  }

public boolean performTipiEvent(String type, Object event) throws TipiException {
  boolean hasEventType = false;
  if (event != null) {
  }
  for (int i = 0; i < myEventList.size(); i++) {
    TipiEvent te = (TipiEvent) myEventList.get(i);
    if(te.getEventName().equals(type)){
      hasEventType = true;
      te.performAction(this,getContext(), event);
    }
  }
  return hasEventType;
}


  public void addPropertyComponent(Component c) {
    currentPropertyComponent = c;
    ((PropertyPanel)getContainer()).setPropertyComponent(c);
  }

  public void setLabelVisible(boolean state){
    if(state){
      ((PropertyPanel)getContainer()).showLabel();
    }else{
      ((PropertyPanel)getContainer()).hideLabel();

    }
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
    constructPropertyComponent(p);
    System.err.println("SETTING PROPERTY: "+p.getValue());
    System.err.println("Size: "+p.getLength());
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ((PropertyPanel)getContainer()).setVisible(myVisibleState);
        if(hardEnabled){
          setEnabled(myEnableState);
        }
        getContainer().doLayout();
      }
    });
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
    if (p.getType().equals("integer")) {
      createIntegerField(p);
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
    myIntField = new IntegerPropertyField();

    myIntField.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        myField_focusGained(e);
      }

      public void focusLost(FocusEvent e) {
        myField_focusLost(e);
      }
    });
    myIntField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        myField_actionPerformed(e);
      }
    });
    myIntField.setProperty(p);
    addPropertyComponent(myIntField);
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
      myField = new TextPropertyField();
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
    myField.setCapitalizationMode(myCapitalization);
    myField.setProperty(p);
    addPropertyComponent(myField);
  }

  public void addTipiEventListener(TipiEventListener listener) {
    if (listener == null) {
    }

    myListeners.add(listener);
  }


  public void setEnabled(boolean value){
    if(myProperty != null){
      if (myProperty.getType().equals("selection") && !"+".equals(myProperty.getCardinality())) {
        myBox.setEnabled(value);
        return;
      }
      if (myProperty.getType().equals("selection") && "+".equals(myProperty.getCardinality())) {
        if(use_checkbox){
          myMultiple.setEnabled(value);;
        }else{
          myMultipleList.setEnabled(value);
        }
        return;
      }
      if (myProperty.getType().equals("boolean")) {
        myCheckBox.setEnabled(value);
        return;
      }
      if (myProperty.getType().equals("date")) {
        myDateField.setEnabled(value);
        myDateField.setEditable(value);
        return;
      }
      if (myProperty.getType().equals("integer")) {
        myIntField.setEnabled(value);
        myIntField.setEditable(value);
        return;
      }
      myField.setEnabled(value);
      myField.setEditable(value);
      return;
    }else{
    }
  }


  public void fireTipiEvent(String type) {
    if (myProperty == null) {
      System.err.println("Trying to fire event from null property!");
      return;
    }
    if(Validatable.class.isInstance(currentPropertyComponent) && type.equals("onFocusGained")){
     Validatable v = (Validatable)currentPropertyComponent;
     ArrayList rules = v.getConditionRuleIds();
     if(rules != null){
       for(int i=0;i<rules.size();i++){
         String id = (String) rules.get(i);
         myContext.resetConditionRuleById(id);
       }
     }
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
    fireTipiEvent("onActionPerformed");
  }

  void myBox_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  void myBox_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  void myBox_itemStateChanged(ItemEvent e) {
    fireTipiEvent("onStateChanged");
  }

  void myField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
    System.err.println("Field focus gained!");
  }

  void myField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");

  }

  void myField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");

  }

  void myDateField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");

  }

  void myDateField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");

  }

  void myDateField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");

  }

  void myCheckBox_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");

  }

  void myCheckBox_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");

  }

  void myCheckBox_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");

  }

  void myCheckBox_itemStateChanged(ItemEvent e) {
    fireTipiEvent("onStateChanged");
  }
  public void setComponentValue(String name, Object object) {
    if ("propertyname".equals(name)) {
      myPropertyName = ((String)object);
    }
    if ("use_checkbox".equals(name)) {
      use_checkbox = ((Boolean)object).booleanValue();
    }
    if ("showlabel".equals(name)) {
      setLabelVisible(((Boolean)object).booleanValue());
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
    if("enabled".equals(name)){
      hardEnabled = true;
      myEnableState = ((Boolean)object).booleanValue();
      this.setEnabled(myEnableState);
    }
    if("visible".equals(name)){
      myVisibleState = ((Boolean)object).booleanValue();
      ((PropertyPanel)getContainer()).setVisible(myVisibleState);
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
      int lindent = ((Integer)object).intValue();
      ((PropertyPanel)getContainer()).setLabelIndent(lindent);
    }
    if ("capitalization".equals(name)) {
      if (myField==null) {
        myCapitalization = (String)object;
      }

    }

    if("propertyValue".equals(name)){
      System.err.println("Setting propertyValue to: " + object.toString());
      // Buggy as hell
      Operand o = null;
      try {
        o = Expression.evaluate( (String) object, this.getNearestNavajo(), null, null, null, myContext);
      }
      catch (Exception ex) {
        System.err.println("Kledder!");
      }
      if (o != null) {
        if (myProperty.getType().equals(Property.FLOAT_PROPERTY))
          myProperty.setValue( (Double) o.value);
        else if (myProperty.getType().equals(Property.INTEGER_PROPERTY))
          myProperty.setValue( (Integer) o.value);
        else if (myProperty.getType().equals(Property.DATE_PROPERTY))
          myProperty.setValue( (java.util.Date) o.value);
        else if (myProperty.getType().equals(Property.BOOLEAN_PROPERTY))
          myProperty.setValue( (Double) o.value);
        else {
          myProperty.setValue(o.value.toString());
        }
        this.constructPropertyComponent(myProperty);
      }
    }
    super.setComponentValue(name, object);
  }


  public String getPropertyName() {
    if (myPropertyName!=null) {
      return myPropertyName;
    }
    return getId();


  }
}