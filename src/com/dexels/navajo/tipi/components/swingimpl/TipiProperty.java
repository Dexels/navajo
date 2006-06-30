package com.dexels.navajo.tipi.components.swingimpl;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import com.dexels.navajo.document.*;
//import com.dexels.navajo.parser.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.document.types.*;

public class TipiProperty
    extends TipiSwingComponentImpl
    implements PropertyComponent, PropertyValidatable, PropertyEventListener {
  private Property myProperty = null;
  private ArrayList myListeners = new ArrayList();
//  private int default_label_width = 50;
//  private int default_property_width = 50;
//  private boolean hardEnabled = false;
  private boolean myVisibleState = true;
  private Boolean myEnableState = null;
  private String selectionType = "default";
//  private Component currentPropertyComponent = null;
  private String myCapitalization = "off";
  private String myPropertyName = null;
//  private int PREVIOUS_SELECTION_INDEX = -1;
  private boolean setPropFlag = false;
  private String vAlign = null;
  private String hAlign = null;
  private boolean isLoading = false;
  private String currentType = "";
  private boolean showDatePicker = false;
  private boolean verticalScrolls = true;
  private boolean horizontalScrolls = false;
     
  public TipiProperty(Property p) {
    setProperty(p);
  }

  public TipiProperty() {
//    initContainer();
    super();
  }

  public Object createContainer() {
    GenericPropertyComponent p = new GenericPropertyComponent();
    p.addPropertyEventListener(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    addTipiEventListener(this);
    p.addPropertyKeyListener(new KeyListener() {
        public void keyTyped(KeyEvent e) {
            Map m = getEventMap(e);
            m.put("mode", "typed");
            try {
                performTipiEvent("onKey", m, true);
            } catch (TipiException e1) {
                e1.printStackTrace();
            }
        }

        public void keyPressed(KeyEvent e) {
            Map m = getEventMap(e);
            m.put("mode", "typed");
            try {
                performTipiEvent("onKey", m, true);
            } catch (TipiException e1) {
                e1.printStackTrace();
            }
        }

        public void keyReleased(KeyEvent e) {
            Map m = getEventMap(e);
            m.put("mode", "released");
            try {
                performTipiEvent("onKey", m, true);
            } catch (TipiException e1) {
                e1.printStackTrace();
            }
        }
        
        public Map getEventMap(KeyEvent e) {
            Map hm = new HashMap();
            hm.put("code", new Integer(e.getKeyCode()));
            hm.put("modifiers", e.getKeyModifiersText(e.getModifiers()));
            hm.put("key", e.getKeyText(e.getKeyCode()));
            return hm;
        }
    });
    return p;
  }

  public Property getProperty() {
    return myProperty;
  }

  /* 
   * Unsure about this method. I do think it should 
   */
//  public void validate() {
//      aya
//      ((GenericPropertyComponent)getContainer()).();
//  }
//
//  public void addToContainer(Object c, Object constraints) {
//    throw new UnsupportedOperationException("Can not add to container of class: " + getClass());
//  }
//
//  public void removeFromContainer(Object c) {
//    getSwingContainer().remove( (Component) c);
//  }
  public void setLabelWidth(final int width) {
    runASyncInEventThread(new Runnable() {
      public void run() {
         ( (GenericPropertyComponent) getContainer()).setLabelIndent(width);
      }
    });
  }

  public void setPropertyWidth(int width) {
  }

  public void addTipiEvent(TipiEvent te) {
    myEventList.add(te);
  }


  public boolean isLoading() {
    return setPropFlag;
  }

  public void setLoading(boolean b) {
    setPropFlag = b;
  }

  public void setLabelVisible(final boolean state) {
    runASyncInEventThread(new Runnable() {
      public void run() {
        if (state) {
           ( (GenericPropertyComponent) getContainer()).showLabel();
        }
        else {
           ( (GenericPropertyComponent) getContainer()).hideLabel();
        }
      }
    });
  }

  public boolean isLabelVisible() {
    return ( (GenericPropertyComponent) getContainer()).isLabelVisible();
  }

  public void setProperty(final Property p) {
    runASyncInEventThread(new Runnable() {
      public void run() {
        myProperty = p;
        if (p == null) {
          return;
        }
        currentType = p.getType();
        setPropFlag = true;
          ( (GenericPropertyComponent) getContainer()).setProperty(p);
         if (myEnableState!=null) {
             ( (GenericPropertyComponent) getContainer()).setHardEnabled(myEnableState.booleanValue());
        }
      }

     });
  }

  public void resetComponentValidationStateByRule(final String id) {
    GenericPropertyComponent tpp = (GenericPropertyComponent) getContainer();
    tpp.resetComponentValidationStateByRule(id);
  }

  public void checkForConditionErrors(Message msg) {
    GenericPropertyComponent tpp = (GenericPropertyComponent) getContainer();
    tpp.checkForConditionErrors(msg);
  }

  public void addTipiEventListener(TipiEventListener listener) {
    if (listener == null) {
    }
    myListeners.add(listener);
  }

  public void setEnabled(boolean value) {
     ( (GenericPropertyComponent) getContainer()).setEnabled(value);
 }

  public void setMaxImageWidth(int w) {
      ( (GenericPropertyComponent) getContainer()).setMaxImageWidth(w);
  }

  public void setMaxImageHeight(int h) {
      ( (GenericPropertyComponent) getContainer()).setMaxImageHeight(h);
  }

  public void setComponentValue(final String name, final Object object) {
    final TipiComponent me = this;
    runASyncInEventThread(new Runnable() {
      public void run() {
        if ("propertyname".equals(name)) {
          myPropertyName = ( (String) object);
        }
        if ("use_checkbox".equals(name)) {
          selectionType = "checkbox";
          ( (GenericPropertyComponent) getContainer()).setSelectionType("checkbox");
        }
        if ("selectiontype".equals(name)) {
           ( (GenericPropertyComponent) getContainer()).setSelectionType( (String) object);
//          selectionType =( (String) object);
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
          ( (GenericPropertyComponent) getContainer()).setVerticalLabelAlignment(val);
        }
        if ("enabled".equals(name)) {
            myEnableState = ( (Boolean) object);
           ( (GenericPropertyComponent) getContainer()).setEnabled(myEnableState.booleanValue());
        }
        if ("visible".equals(name)) {
          myVisibleState = ( (Boolean) object).booleanValue();
          ( (GenericPropertyComponent) getContainer()).setVisible( ( (Boolean) object).booleanValue());
        }
        if ("visibleRowCount".equals(name)) {
           ( (GenericPropertyComponent) getContainer()).setVisibleRows( ( (Integer) object).intValue());
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
          ( (GenericPropertyComponent) getContainer()).setHorizontalLabelAlignment(hal);
        }
        if ("label_indent".equals(name)) {
          final int lindent = ( (Integer) object).intValue();
          ( (GenericPropertyComponent) getContainer()).setLabelIndent(lindent);
        }
        if ("capitalization".equals(name)) {
           ( (GenericPropertyComponent) getContainer()).setCapitalization( (String) object);
        }
        if ("showdatepicker".equals(name)) {
           ( (GenericPropertyComponent) getContainer()).setShowDatePicker( ( (Boolean) object).booleanValue());
        }
        if("alwaysUseLabel".equals("name") ) {
            ( (GenericPropertyComponent) getContainer()).setAlwaysUseLabel( ( (Boolean) object).booleanValue());
        } 
        if("maxImageWidth".equals("name") ) {
            setMaxImageWidth( ( (Integer) object).intValue());
        } 
        if("maxImageHeight".equals("name") ) {
            setMaxImageHeight( ( (Integer) object).intValue());
        } 

        
        if ("propertyValue".equals(name)) {
          if (myProperty == null) {
            throw new IllegalStateException("Can not set the value of a TipiProperty when it has not been loaded! Component: " + me.getPath());
          }
          myProperty.setAnyValue(object);
          ( (GenericPropertyComponent) getContainer()).constructPropertyComponent(myProperty);
        }
//        private int checkboxGroupColumnCount = 0;
//        private int memoColumnCount = 0;
//        private int memoRowCount = 0;
//          
        if("memoRowCount".equals(name)) {
            ( (GenericPropertyComponent) getContainer()).setMemoRowCount( ( (Integer) object).intValue());            
        }
        if("memoColumnCount".equals(name)) {
            ( (GenericPropertyComponent) getContainer()).setMemoColumnCount( ( (Integer) object).intValue());            
        }
        if("checkboxGroupColumnCount".equals(name)) {
            ( (GenericPropertyComponent) getContainer()).setCheckboxGroupColumnCount( ( (Integer) object).intValue());            
        }
        if("verticalScrolls".equals(name)) {
            setVerticalScrolls(((Boolean) object).booleanValue());
        }
        if("horizontalScrolls".equals(name)) {
            setHorizontalScrolls(((Boolean) object).booleanValue());
        }
      }
    });
    super.setComponentValue(name, object);
  }

  public Object getComponentValue(String name) {
    if ("propertyname".equals(name)) {
      if (myProperty != null && myProperty.getType().equals(Property.SELECTION_PROPERTY)) {
        try {
          Selection s = myProperty.getSelected();
          if (s != null) {
            return s.getName();
          }
          else {
            return myPropertyName;
          }
        }
        catch (Exception e) {
          return myPropertyName;
        }
      }
      return myPropertyName;
    }
    if ("use_checkbox".equals(name)) {
      return new Boolean("checkbox".equals(selectionType));
    }
    if ("selectiontype".equals(name)) {
      return selectionType;
    }
    if ("showlabel".equals(name)) {
      return new Boolean(isLabelVisible());
    }
    if ("label_valign".equals(name)) {
      return vAlign;
    }
    if ("enabled".equals(name)) {
        if (myEnableState!=null) {
            return new Boolean(myEnableState.booleanValue());
        } else {
            return new Boolean( ( (GenericPropertyComponent) getContainer()).isEnabled());
        }
      }
    if ("visible".equals(name)) {
      return new Boolean( ( (GenericPropertyComponent) getContainer()).isVisible());
    }
    if ("label_halign".equals(name)) {
      return hAlign;
    }
    if ("label_indent".equals(name)) {
      return new Integer( ( (GenericPropertyComponent) getContainer()).getLabelIndent());
    }
    if ("capitalization".equals(name)) {
      return myCapitalization;
    }
    if ("propertyValue".equals(name)) {
      if (myProperty != null) {
        if (myProperty.getType().equals(Property.SELECTION_PROPERTY)) {
          try {
            Selection s = myProperty.getSelected();
            if (s != null) {
              return s.getValue();
            }
            else {
              return "" + myProperty.getTypedValue();
            }
          }
          catch (Exception e) {
            return "" + myProperty.getTypedValue();
          }
        }
        return "" + myProperty.getTypedValue();
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

  public void propertyEventFired(Property p, String eventType, Validatable v) {
    if (p == null) {
      System.err.println("Trying to fire event from null property!");
      return;
    }
    if (p != myProperty) {
      System.err.println("Mysterious anomaly: Property of event is not the loaded property");
      return;
    }
    if (eventType.equals("onFocusGained") && v != null) {
//      Validatable v = (Validatable) currentPropertyComponent;
      ArrayList rules = v.getConditionRuleIds();
      if (rules != null) {
        for (int i = 0; i < rules.size(); i++) {
          String id = (String) rules.get(i);
          myContext.resetConditionRuleById(id);
        }
      }
    }
    try {
      Map m = new HashMap();
      m.put("propertyName", myProperty.getFullPropertyName());
      m.put("propertyValue", myProperty.getTypedValue());
      m.put("propertyType", myProperty.getType());
      m.put("propertyLength", new Integer(myProperty.getLength()));
//      PropertyImpl p = (PropertyImpl)myProperty;
      for (int i = 0; i < myListeners.size(); i++) {
        TipiEventListener current = (TipiEventListener) myListeners.get(i);
        current.performTipiEvent(eventType, m, false);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setVerticalScrolls(boolean b) {
     ( (GenericPropertyComponent) getContainer()).setVerticalScrolls(b);
  }

  public void setHorizontalScrolls(boolean b) {
     ( (GenericPropertyComponent) getContainer()).setHorizontalScrolls(b);
  }

  public void updateProperty() {
      ( (GenericPropertyComponent) getContainer()).updateProperty();
  }

  protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
      runSyncInEventThread(new Runnable() {
        public void run() {
            updateProperty();
        }
      });
    }

}
