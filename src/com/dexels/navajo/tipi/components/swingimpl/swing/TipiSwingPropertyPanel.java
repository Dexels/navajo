package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.internal.*;
import javax.swing.event.*;
import java.lang.reflect.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingPropertyPanel
    extends JPanel {
  private Component currentComponent = null;
  private ConditionErrorParser cep = new ConditionErrorParser();
  private int labelWidth = 0;
  private int valign = JLabel.CENTER;
  private int halign = JLabel.LEADING;
//  private int height
  private int propertyWidth = 0;
  private boolean showLabel = true;
  private JLabel myLabel = null;
  BorderLayout borderLayout = new BorderLayout();
  private Map failedPropertyIdMap = null;
  private ResourceBundle res = null;
  public TipiSwingPropertyPanel() {
    try {
      res = ResourceBundle.getBundle(System.getProperty("com.dexels.navajo.propertyMap"));
    }
    catch (MissingResourceException ex) {
      System.err.println("No resourcemap found.");
    }
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void doLayout() {
    super.doLayout();
  }

  public void setPropertyComponent(Component c) {
    if (currentComponent == c) {
      return;
    }
    if (currentComponent != null) {
      remove(currentComponent);
      System.err.println("Removing component: " + currentComponent.getClass());
    }
    currentComponent = c;
    add(currentComponent, BorderLayout.CENTER);
  }

  public void setLabel(final String s) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        if (myLabel == null) {
          myLabel = new JLabel(s);
          myLabel.setOpaque(true);
          add(myLabel, BorderLayout.WEST);
        }
        else {
          myLabel.setText(s);
        }
        if (labelWidth != 0) {
          setLabelIndent(labelWidth);
        }
        myLabel.setHorizontalAlignment(halign);
        myLabel.setVerticalAlignment(valign);
        myLabel.setVisible(showLabel);
      }
    });
  }

  public void showLabel() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        showLabel = true;
        if (myLabel != null) {
          myLabel.setVisible(showLabel);
        }
      }
    });
  }

  public void hideLabel() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        showLabel = false;
        if (myLabel != null) {
          remove(myLabel);
        }
        myLabel = null;
      }
    });
  }

  public void setVerticalLabelAlignment(final int alignment) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        valign = alignment;
        if (myLabel != null) {
          myLabel.setVerticalAlignment(alignment);
        }
      }
    });
  }

  public void update() {
    // Bleh
  }

  public void setHorizontalLabelAlignment(final int alignment) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        halign = alignment;
        if (myLabel != null) {
          myLabel.setHorizontalAlignment(alignment);
        }
      }
    });
  }

  public void checkForConditionErrors(Message msg) {
    if (PropertyControlled.class.isInstance(currentComponent)) {
      PropertyControlled pc = (PropertyControlled) currentComponent;
      String myName = pc.getProperty().getName();
      //System.err.println("Checking for: " + myName);
      ArrayList errors = cep.getFailures(msg);
      failedPropertyIdMap = cep.getFailedPropertyIdMap();
      for (int i = 0; i < errors.size(); i++) {
        final String current = (String) errors.get(i);
        final String id = (String) failedPropertyIdMap.get(current);
//        System.err.println("Failures: " + current);
        if ( (current.indexOf(myName) > -1)) {
          if (Validatable.class.isInstance(currentComponent)) {
            final Validatable f = (Validatable) currentComponent;
            try {
              SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                  f.setValidationState(BaseField.INVALID);
                  f.setToolTipText(cep.getDescription(current));
                  f.addConditionRuleId(id);
                }
              });
            }
            catch (InvocationTargetException ex) {
            }
            catch (InterruptedException ex) {
            }
          }
//          if(IntegerPropertyField.class.isInstance(currentComponent)){  // Mmmm.. shouldn't be like this I guess
//            IntegerPropertyField f = (IntegerPropertyField)currentComponent;
//            f.setValidationState(BaseField.INVALID);
//            f.setToolTipText(cep.getDescription(current));
//            //f.setConditionRuleId(id);
//          }
          return;
        }
      }
    }
  }

  public void resetComponentValidationStateByRule(final String id) {
    //System.err.println("Looking if we should reset: " + id);
    if (failedPropertyIdMap != null && id != null) {
      Iterator it = failedPropertyIdMap.keySet().iterator();
      final PropertyControlled pc = (PropertyControlled) currentComponent;
      String myName = pc.getProperty().getName();
      while (it.hasNext()) {
        String current = (String) it.next();
        if (current.indexOf(myName) > -1) {
          // I am invalid.
          final String current_id = (String) failedPropertyIdMap.get(current);
          SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              if (id.equals(current_id)) {
                if (Validatable.class.isInstance(currentComponent)) {
                  Validatable f = (Validatable) currentComponent;
                  f.setValidationState(BaseField.VALID);
                  f.setToolTipText(getToolTipText(pc.getProperty()));
                }
                if (IntegerPropertyField.class.isInstance(currentComponent)) { // Mmmm.. shouldn't be like this I guess,..
                  IntegerPropertyField f = (IntegerPropertyField) currentComponent;
                  f.setValidationState(BaseField.VALID);
                  f.setToolTipText(getToolTipText(pc.getProperty()));
                }
              }
            }
          });

        }
      }
    }
  }

  public String getToolTipText(Property p) {
    String toolTip = "";
    if (p != null) {
      try {
        if (res != null) {
          toolTip = res.getString(p.getName());
          return toolTip;
        }
        else {
          toolTip = "unknown";
        }
      }
      catch (MissingResourceException e) {
        toolTip = p.getDescription();
        if (toolTip != null && !toolTip.equals("")) {
          return toolTip;
        }
        else {
          toolTip = p.getName();
          return toolTip;
        }
      }
    }
    else {
      toolTip = "unknown";
    }
    return toolTip;
  }

//  public void setSize(int x, int y) {
//    myLabel.setPreferredSize(new Dimension(x,y));
//  }
  public void setLabelIndent(final int lindent) {
//    Thread.currentThread().
    if(!SwingUtilities.isEventDispatchThread() && !"main".equals(Thread.currentThread().getName())) {
      System.err.println("AYAYAY:");
      Thread.currentThread().dumpStack();
    }
//    System.err.println("LABEL INDENT::: "+Thread.currentThread().getName());
        labelWidth = lindent;
        if (myLabel == null) {
          return;
        }
        int height = getPreferredSize().height;
        myLabel.setPreferredSize(new Dimension(lindent, height));
//        revalidate();
        invalidate();
        myLabel.invalidate();
  }

  public int getLabelIndent() {
    return labelWidth;
  }

  public boolean isLabelVisible() {
    if (myLabel != null) {
      return myLabel.isVisible();
    }
    return false;
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout);
  }
}
