package com.dexels.navajo.tipi.components;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swing.*;

public class BasePropertyComponent extends TipiComponent implements PropertyComponent {
  private Property myProperty = null;

  PropertyBox myBox = null;
  BaseLabel myBinaryLabel = null;
  MultipleSelectionPropertyCheckboxGroup myMultiple = null;
  MultipleSelectionPropertyList myMultipleList =null;
  TextPropertyField myField = null;
  DatePropertyField myDateField = null;
  PropertyCheckBox myCheckBox = null;
  IntegerPropertyField myIntField = null;
  FloatPropertyField myFloatField = null;
  PropertyPasswordField myPasswordField = null;
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
  private int PREVIOUS_SELECTION_INDEX = -1;
  private boolean setPropFlag = false;
  private String vAlign = null;
  private String hAlign = null;

  private boolean isLoading = false;
  private String currentType = "";

  public BasePropertyComponent(Property p) {
    setProperty(p);
  }
  public BasePropertyComponent() {
//    initContainer();
    super();
  }

  public Container createContainer() {
      PropertyPanel p = new PropertyPanel();
//    p.setVisible(false);
      TipiHelper th = new SwingTipiHelper();
      th.initHelper(this);
      addHelper(th);
      addTipiEventListener(this);
      return p;
  }

  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
  }
  public void removeFromContainer(Component c) {
    getContainer().remove(c);
    //getContainer().removeNotify();
    //getContainer().repaint();
    //getContainer().validate();
    //throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
  }

//  public void setContainerLayout(LayoutManager layout) {
//    throw new UnsupportedOperationException("Can not set layout of container of class: " + getClass());
//  }

  public void setLabelWidth(int width) {
  }
  public void setPropertyWidth(int width) {
  }

  public void addTipiEvent(TipiEvent te) {
    myEventList.add(te);
  }

  public void addPropertyComponent(Component c) {
    ((PropertyPanel)getContainer()).setPropertyComponent(c);
    currentPropertyComponent = c;
  }

  public boolean isLoading() {
    return setPropFlag;
  }

  public void setLoading(boolean b) {
    setPropFlag = b;
  }

  public void setLabelVisible(boolean state){
    if(state){
      ((PropertyPanel)getContainer()).showLabel();
    }else{
      ((PropertyPanel)getContainer()).hideLabel();
    }
  }
  public boolean isLabelVisible(){
    return ((PropertyPanel)getContainer()).isLabelVisible();
  }

  public void setProperty(Property p) {
    myProperty = p;
    if (p == null) {
      return;
    }
//    System.err.println("--> Setting property: " + p.getName()+" == "+p.getValue()+" == "+p.getType());
    currentType = p.getType();
    setPropFlag = true;
    String description = p.getDescription();
    if (description == null || "".equals(description)) {
      description = p.getName();
    }
    ((PropertyPanel)getContainer()).setLabel(description);
    constructPropertyComponent(p);
//    System.err.println("SETTING PROPERTY: "+p.getValue());
//    System.err.println("Size: "+p.getLength());
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ((PropertyPanel)getContainer()).setVisible(myVisibleState);
        if(hardEnabled){
          setEnabled(myEnableState);
        }
        getContainer().doLayout();
      }
    });
    setPropFlag = false;
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
    if (p.getType().equals("binary")) {
      createBinaryComponent(p);
      return;
    }
    if (p.getType().equals("password")) {
      createPropertyPasswordField(p);
      return;
    }
    if (p.getType().equals("float")) {
     createPropertyFloatField(p);
     return;
   }

    createPropertyField(p);

    return;
  }

  private void createBinaryComponent(Property p){
    //Test case image..
    byte[] data = (byte[])p.getTypedValue();
    ImageIcon img = new ImageIcon(data);
    myBinaryLabel = new BaseLabel();
    myBinaryLabel.setIcon(img);
    addPropertyComponent(myBinaryLabel);
  }

  private void createPropertyBox(Property p) {

   if (myBox==null) {
     myBox = new PropertyBox();

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
   }
   addPropertyComponent(myBox);
    myBox.loadProperty(p);

  }

  private void createPropertyList(Property p) {

    if (myMultipleList==null) {
      myMultipleList = new MultipleSelectionPropertyList();
//      myMultipleList.setPreferredSize(new Dimension(200,200));

     }
     addPropertyComponent(myMultipleList);
    myMultipleList.setProperty(p);

  }

  private void createPropertyCheckboxList(Property p) {

    if (myMultiple==null) {
      myMultiple = new MultipleSelectionPropertyCheckboxGroup();
    }

    addPropertyComponent(myMultiple);
    myMultiple.setProperty(p);

  }

  private void createPropertyCheckbox(Property p) {

    if (myCheckBox==null) {
      myCheckBox = new PropertyCheckBox();
      addPropertyComponent(myCheckBox);
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
    }

    myCheckBox.setProperty(p);

  }

  private void createIntegerField(Property p) {

    if (myIntField == null) {
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
    }
    addPropertyComponent(myIntField);
    myIntField.setProperty(p);

  }

  private void createPropertyFloatField(Property p) {

    if (myFloatField == null) {
      myFloatField = new FloatPropertyField();
      myFloatField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myField_focusLost(e);
        }
      });
      myFloatField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myField_actionPerformed(e);
        }
      });
    }
    addPropertyComponent(myFloatField);
    myFloatField.setProperty(p);

  }

  private void createPropertyDateField(Property p) {

    if (myDateField==null) {
      myDateField = new DatePropertyField();
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
    }
    addPropertyComponent(myDateField);
    myDateField.setProperty(p);

  }

  private void createPropertyField(Property p) {

    if (myField==null) {
      myField = new TextPropertyField();
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
    }

    myField.setProperty(p);
     addPropertyComponent(myField);

  }
  private void createPropertyPasswordField(Property p) {

    if (myPasswordField==null) {
      myPasswordField = new PropertyPasswordField();
      myPasswordField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myPasswordField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myPasswordField_focusLost(e);
        }
      });
      myPasswordField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myPasswordField_actionPerformed(e);
        }
      });
    }

    myPasswordField.setProperty(p);
     addPropertyComponent(myPasswordField);

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

//    System.err.println("AP -->"  + e.getActionCommand() + "previous: " + PREVIOUS_SELECTION_INDEX + " current: " + myBox.getSelectedIndex() + ", propFlag: " + setPropFlag);
    if (!setPropFlag) {
      if(e.getActionCommand().equals("comboBoxChanged")){
        fireTipiEvent("onValueChanged");
//      System.err.println("onValueChanged!!");
        PREVIOUS_SELECTION_INDEX = myBox.getSelectedIndex();
      }else{
        fireTipiEvent("onActionPerformed");
      }
    }

  }

  void myBox_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  void myBox_focusLost(FocusEvent e) {
//    if (this.getPropertyName().equals("ContributionCode")) {
//      System.err.println("#EVENT LISTENERS: " + this.myListeners.size());
//      System.err.println("#FOCUS LISTENERS: " + myBox.getFocusListeners().length);
//      for (int i = 0; i < myListeners.size(); i++) {
//        System.err.println("EVENT LISTENER: " + this.myListeners.get(i).getClass().getName());
//      }
//    }

    fireTipiEvent("onFocusLost");
  }

  void myBox_itemStateChanged(ItemEvent e) {
//    System.err.println("SC --> Property: " + this.getPropertyName() + ", Previous index: " + PREVIOUS_SELECTION_INDEX + ", new index: " + myBox.getSelectedIndex() + ", ITEM: " + myBox.getSelectedItem().toString() + ", mouseFlag: " + mouseFlag);
//    if(myBox.getSelectedIndex() != PREVIOUS_SELECTION_INDEX && mouseFlag){
//      fireTipiEvent("onValueChanged");
//      PREVIOUS_SELECTION_INDEX = myBox.getSelectedIndex();
//      mouseFlag = false;
//      System.err.println("Fired onValueChanged!");
//    }
    if (!setPropFlag) {
      fireTipiEvent("onStateChanged");
    }
  }

  void myField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  void myField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");

  }

  void myField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");

  }
  void myPasswordField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  void myPasswordField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");

  }

  void myPasswordField_actionPerformed(ActionEvent e) {
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
      vAlign = (String)object;
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

    if ("visibleRowCount".equals(name)) {
      if (myMultipleList==null) {
        myMultipleList = new MultipleSelectionPropertyList();
      }
      myMultipleList.setVisibleRowCount(((Integer)object).intValue());

    }


    if ("label_halign".equals(name)) {
      int halign = JLabel.LEADING;
      hAlign = (String)object;
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
//      System.err.println("Setting propertyValue to: " + object.toString());
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

  public Object getComponentValue(String name) {
    if ("propertyname".equals(name)) {
      return myPropertyName;
    }
    if ("use_checkbox".equals(name)) {
      return new Boolean(use_checkbox);
    }
    if ("showlabel".equals(name)) {
      return new Boolean(isLabelVisible());
    }
    if ("label_valign".equals(name)) {
      return vAlign;
    }
    if("enabled".equals(name)){
      return new Boolean(hardEnabled);
    }
    if("visible".equals(name)){
      return new Boolean(((PropertyPanel)getContainer()).isVisible());
    }

    if ("label_halign".equals(name)) {
      return vAlign;
    }
    if ("label_indent".equals(name)) {
      return new Integer(((PropertyPanel)getContainer()).getLabelIndent());
    }
    if ("capitalization".equals(name)) {
      return myCapitalization;
    }
    if("propertyValue".equals(name)){
//      System.err.println("Setting propertyValue to: " + object.toString());
      // Buggy as hell
      if(myProperty != null){
        return myProperty.getTypedValue();
      }
   }
    return super.getComponentValue(name);
  }

  public String getPropertyName() {
    if (myPropertyName!=null) {
      return myPropertyName;
    }
    return getId();


  }
}