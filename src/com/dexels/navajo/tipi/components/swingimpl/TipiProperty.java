package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiProperty
    extends TipiSwingComponentImpl
    implements PropertyComponent, PropertyValidatable {
  private Property myProperty = null;
  PropertyBox myBox = null;
  BaseLabel myBinaryLabel = null;
  MultipleSelectionPropertyCheckboxGroup myMultiple = null;
  MultipleSelectionPropertyList myMultipleList = null;
  TextPropertyField myField = null;
  DatePropertyField myDateField = null;
  PropertyCheckBox myCheckBox = null;
  IntegerPropertyField myIntField = null;
  FloatPropertyField myFloatField = null;
  PropertyPasswordField myPasswordField = null;
  ClockTimeField myClockTimeField = null;
  MoneyPropertyField myMoneyField = null;
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
  public TipiProperty(Property p) {
    setProperty(p);
  }

  public TipiProperty() {
//    initContainer();
    super();
  }

  public Object createContainer() {
    TipiSwingPropertyPanel p = new TipiSwingPropertyPanel();
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    addTipiEventListener(this);
    return p;
  }


  public Property getProperty() {
    return myProperty;
  }
//
//  public void addToContainer(Object c, Object constraints) {
//    throw new UnsupportedOperationException("Can not add to container of class: " + getClass());
//  }
//
//  public void removeFromContainer(Object c) {
//    getSwingContainer().remove( (Component) c);
//  }
  public void setLabelWidth(final int width) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        ( (TipiSwingPropertyPanel) getContainer()).setLabelIndent(width);
      }
    });
  }

  public void setPropertyWidth(int width) {
  }

  public void addTipiEvent(TipiEvent te) {
    myEventList.add(te);
  }

  public void addPropertyComponent(Component c) {
    ( (TipiSwingPropertyPanel) getContainer()).setPropertyComponent(c);
    currentPropertyComponent = c;
  }

  public boolean isLoading() {
    return setPropFlag;
  }

  public void setLoading(boolean b) {
    setPropFlag = b;
  }

  public void setLabelVisible(final boolean state) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        if (state) {
          ( (TipiSwingPropertyPanel) getContainer()).showLabel();
        }
        else {
          ( (TipiSwingPropertyPanel) getContainer()).hideLabel();
        }
      }
    });
  }

  public boolean isLabelVisible() {
    return ( (TipiSwingPropertyPanel) getContainer()).isLabelVisible();
  }

  public void setProperty(final Property p) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        myProperty = p;
        if (p == null) {
          return;
        }
        currentType = p.getType();
        setPropFlag = true;
        String description = p.getDescription();
        if (description == null || "".equals(description)) {
          description = p.getName();
        }
        ( (TipiSwingPropertyPanel) getContainer()).setLabel(description);
        constructPropertyComponent(p);
        ( (TipiSwingPropertyPanel) getContainer()).setVisible(myVisibleState);
        if (hardEnabled) {
          setEnabled(myEnableState);
        }
//        getSwingContainer().doLayout();
        setPropFlag = false;
        getSwingContainer().invalidate();
      }
    });
  }

  public void resetComponentValidationStateByRule(final String id) {
    TipiSwingPropertyPanel tpp = (TipiSwingPropertyPanel)getContainer();
    tpp.resetComponentValidationStateByRule(id);
  }
  public void checkForConditionErrors(Message msg) {
    TipiSwingPropertyPanel tpp = (TipiSwingPropertyPanel)getContainer();
    tpp.checkForConditionErrors(msg);
  }

  private void constructPropertyComponent(Property p) {
    if (p.getType().equals("selection")) {
      if (!"+".equals(p.getCardinality())) {
        createPropertyBox(p);
        return;
      }
      else {
        if (use_checkbox) {
          createPropertyCheckboxList(p);
        }
        else {
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
    if (p.getType().equals("money")) {
      createMoneyPropertyField(p);
      return;
    }
    if (p.getType().equals("clocktime")) {
      createClockTimeField(p);
      return;
    }
    createPropertyField(p);
    return;
  }

  private void createBinaryComponent(Property p) {
    //Test case image..
    byte[] data = (byte[]) p.getTypedValue();
    ImageIcon img = new ImageIcon(data);
    myBinaryLabel = new BaseLabel();
    myBinaryLabel.setIcon(img);
    addPropertyComponent(myBinaryLabel);
  }

  private void createPropertyBox(Property p) {
    if (myBox == null) {
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
    if (myMultipleList == null) {
      myMultipleList = new MultipleSelectionPropertyList();
    }
    addPropertyComponent(myMultipleList);
    myMultipleList.setProperty(p);
  }

  private void createPropertyCheckboxList(Property p) {
    if (myMultiple == null) {
      myMultiple = new MultipleSelectionPropertyCheckboxGroup();
    }
    addPropertyComponent(myMultiple);
    myMultiple.setProperty(p);
  }

  private void createPropertyCheckbox(Property p) {
    if (myCheckBox == null) {
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
    if (myDateField == null) {
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
    if (myField == null) {
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
    if (myPasswordField == null) {
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

  private void createMoneyPropertyField(Property p) {
    if (myMoneyField == null) {
      myMoneyField = new MoneyPropertyField();
      myMoneyField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myMoneyField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myMoneyField_focusLost(e);
        }
      });
      myMoneyField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myMoneyField_actionPerformed(e);
        }
      });
    }
    myMoneyField.setProperty(p);
    addPropertyComponent(myMoneyField);
  }

  private void createClockTimeField(Property p) {
    if (myClockTimeField == null) {
      myClockTimeField = new ClockTimeField();
      myClockTimeField.addFocusListener(new java.awt.event.FocusAdapter() {
        public void focusGained(FocusEvent e) {
          myClockTimeField_focusGained(e);
        }

        public void focusLost(FocusEvent e) {
          myClockTimeField_focusLost(e);
        }
      });
      myClockTimeField.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          myClockTimeField_actionPerformed(e);
        }
      });
    }
    myClockTimeField.setProperty(p);
    addPropertyComponent(myClockTimeField);
  }


  public void addTipiEventListener(TipiEventListener listener) {
    if (listener == null) {
    }
    myListeners.add(listener);
  }

  public void setEnabled(boolean value) {
    if (myProperty != null) {
      if (myProperty.getType().equals("selection") && !"+".equals(myProperty.getCardinality())) {
        myBox.setEnabled(value);
        return;
      }
      if (myProperty.getType().equals("selection") && "+".equals(myProperty.getCardinality())) {
        if (use_checkbox) {
          myMultiple.setEnabled(value); ;
        }
        else {
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
    }
    else {
    }
  }

  public void fireTipiEvent(String type) {
    if (myProperty == null) {
      System.err.println("Trying to fire event from null property!");
      return;
    }
    if (Validatable.class.isInstance(currentPropertyComponent) && type.equals("onFocusGained")) {
      Validatable v = (Validatable) currentPropertyComponent;
      ArrayList rules = v.getConditionRuleIds();
      if (rules != null) {
        for (int i = 0; i < rules.size(); i++) {
          String id = (String) rules.get(i);
          myContext.resetConditionRuleById(id);
        }
      }
    }
    else {
//      System.err.println(">>>>>>>>>>>>>>> NOT VALIDATABLE: " + currentPropertyComponent.getClass());
    }
    try {
      for (int i = 0; i < myListeners.size(); i++) {
        TipiEventListener current = (TipiEventListener) myListeners.get(i);
        Map m = new HashMap();
        m.put("propertyName",myProperty.getFullPropertyName());
        current.performTipiEvent(type, m, false);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void myBox_actionPerformed(ActionEvent e) {
//    System.err.println("AP -->"  + e.getActionCommand() + "previous: " + PREVIOUS_SELECTION_INDEX + " current: " + myBox.getSelectedIndex() + ", propFlag: " + setPropFlag);
    if (!setPropFlag) {
      if (e.getActionCommand().equals("comboBoxChanged")) {
//        System.err.println("STARTING COMBO ONCHANGED EVENT");
        fireTipiEvent("onValueChanged");
//      System.err.println("onValueChanged!!");
        PREVIOUS_SELECTION_INDEX = myBox.getSelectedIndex();
//        System.err.println("ENDING COMBO ONCHANGED EVENT");
      }
      else {
        fireTipiEvent("onActionPerformed");
      }
    }
  }

  void myBox_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  void myBox_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  void myBox_itemStateChanged(ItemEvent e) {
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

  void myClockTimeField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  void myClockTimeField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  void myClockTimeField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");
  }
  void myMoneyField_focusGained(FocusEvent e) {
    fireTipiEvent("onFocusGained");
  }

  void myMoneyField_focusLost(FocusEvent e) {
    fireTipiEvent("onFocusLost");
  }

  void myMoneyField_actionPerformed(ActionEvent e) {
    fireTipiEvent("onActionPerformed");
  }



  public void setComponentValue(final String name, final Object object) {
    final TipiComponent me = this;
    runSyncInEventThread(new Runnable() {
      public void run() {
        if ("propertyname".equals(name)) {
          myPropertyName = ( (String) object);
        }
        if ("use_checkbox".equals(name)) {
          use_checkbox = ( (Boolean) object).booleanValue();
        }
        if ("showlabel".equals(name)) {
          setLabelVisible( ( (Boolean) object).booleanValue());
        }
        if ("label_valign".equals(name)) {
          int valign = JLabel.CENTER;
          vAlign = (String) object;
          if ("top".equals(object)) {
            valign = JLabel.TOP;
          }
          if ("bottom".equals(object)) {
            valign = JLabel.BOTTOM;
          }
          if ("center".equals(object)) {
            valign = JLabel.CENTER;
          }
          final int val = valign;
          ( (TipiSwingPropertyPanel) getContainer()).setVerticalLabelAlignment(val);
        }
        if ("enabled".equals(name)) {
          myEnableState = ( (Boolean) object).booleanValue();
          hardEnabled = myEnableState;
          setEnabled(myEnableState);
        }
        if ("visible".equals(name)) {
          myVisibleState = ( (Boolean) object).booleanValue();
          ( (TipiSwingPropertyPanel) getContainer()).setVisible(myVisibleState);
        }
        if ("visibleRowCount".equals(name)) {
          if (myMultipleList == null) {
            myMultipleList = new MultipleSelectionPropertyList();
          }
          myMultipleList.setVisibleRowCount( ( (Integer) object).intValue());
        }
        if ("label_halign".equals(name)) {
          int halign = JLabel.LEADING;
          hAlign = (String) object;
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
          final int hal = halign;
          ( (TipiSwingPropertyPanel) getContainer()).setHorizontalLabelAlignment(hal);
        }
        if ("label_indent".equals(name)) {
          final int lindent = ( (Integer) object).intValue();
          ( (TipiSwingPropertyPanel) getContainer()).setLabelIndent(lindent);
        }
        if ("capitalization".equals(name)) {
          if (myField == null) {
            myCapitalization = (String) object;
          }
        }
        if ("propertyValue".equals(name)) {
          // Buggy as hell
//          Operand o = myContext.evaluate( (String) object, me);

//          if (o != null) {
            if (myProperty.getType().equals(Property.FLOAT_PROPERTY)) {
              myProperty.setValue( (Double) object);
            }
            else if (myProperty.getType().equals(Property.INTEGER_PROPERTY)) {
              myProperty.setValue( (Integer) object);
            }
            else if (myProperty.getType().equals(Property.DATE_PROPERTY)) {
              myProperty.setValue( (java.util.Date) object);
            }
            else if (myProperty.getType().equals(Property.BOOLEAN_PROPERTY)) {
              myProperty.setValue( (Double) object);
            }
            else {
              myProperty.setValue(object.toString());
            }
            constructPropertyComponent(myProperty);
          }
//        }
      }
    });
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
    if ("enabled".equals(name)) {
      return new Boolean(hardEnabled);
    }
    if ("visible".equals(name)) {
      return new Boolean( ( (TipiSwingPropertyPanel) getContainer()).isVisible());
    }
    if ("label_halign".equals(name)) {
      return vAlign;
    }
    if ("label_indent".equals(name)) {
      return new Integer( ( (TipiSwingPropertyPanel) getContainer()).getLabelIndent());
    }
    if ("capitalization".equals(name)) {
      return myCapitalization;
    }
    if ("propertyValue".equals(name)) {
      if (myProperty != null) {
        return myProperty.getTypedValue();
      }
    }
    return super.getComponentValue(name);
  }

  public String getPropertyName() {
    if (myPropertyName != null) {
      return myPropertyName;
    }
    return getId();
  }
}
