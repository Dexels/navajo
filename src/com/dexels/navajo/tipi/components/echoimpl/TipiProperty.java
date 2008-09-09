package com.dexels.navajo.tipi.components.echoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.LayoutData;
import nextapp.echo2.app.Style;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.echoclient.components.EchoPropertyComponent;
import com.dexels.navajo.echoclient.components.PropertyEventListener;
import com.dexels.navajo.echoclient.components.Styles;

import com.dexels.navajo.tipi.TipiEventListener;

import com.dexels.navajo.tipi.internal.PropertyComponent;

import echopointng.ContainerEx;
import echopointng.GroupBox;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiProperty extends TipiEchoComponentImpl implements PropertyComponent, PropertyEventListener {

    private Property myProperty = null;

    private String myPropertyName = null;

    private EchoPropertyComponent myPropertyComponent;

    // private ContainerEx myContainer;
    private final ArrayList myListeners = new ArrayList();

    public TipiProperty() {
    }

    public Property getProperty() {
        return myProperty;
    }

    public void setProperty(Property p) {
        myProperty = p;
        ((EchoPropertyComponent) getActualComponent()).setProperty(p);
    }

    public void propertyEventFired(Property p, String eventType) {
        try {
            Map m = new HashMap();
            m.put("propertyName", myProperty.getFullPropertyName());
            m.put("propertyValue", myProperty.getTypedValue());
            m.put("propertyType", myProperty.getType());
            m.put("propertyLength", new Integer(myProperty.getLength()));
            for (int i = 0; i < myListeners.size(); i++) {
                TipiEventListener tel = (TipiEventListener) myListeners.get(i);
                tel.performTipiEvent(eventType, m, false);
            }
            if (p == null) {
                return;
            }
            if (p != myProperty) {
                System.err.println("Mysterious anomaly: Property of event is not the loaded property");
                return;
            }
            System.err.println("PRoperty event firing: "+eventType+" prop: "+p.getFullPropertyName());
            performTipiEvent(eventType, m, false);
            // }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object createContainer() {
        // myContainer = new ContainerEx();
    	ContainerEx gb = new ContainerEx();

    	myPropertyComponent = new EchoPropertyComponent();
        myPropertyComponent.setUseLabelForReadOnlyProperties(false);
        myPropertyComponent.addPropertyEventListener(this);
        gb.add(myPropertyComponent);
        return gb;
    }

    public Object getActualComponent() {
        return myPropertyComponent;
    }

    public void setUseLabelForReadOnlyProperties(boolean b) {
        myPropertyComponent.setUseLabelForReadOnlyProperties(b);
    }

    /**
     * getPropertyName
     * 
     * @return String
     * @todo Implement this com.dexels.navajo.tipi.internal.PropertyComponent
     *       method
     */
    public String getPropertyName() {
        return myPropertyName;
    }

    /**
     * addTipiEventListener
     * 
     * @param listener
     *            TipiEventListener
     * @todo Implement this com.dexels.navajo.tipi.internal.PropertyComponent
     *       method
     */
    public void addTipiEventListener(TipiEventListener listener) {
        if (listener == null) {
        }
        myListeners.add(listener);
    }

    
    
    public Object getComponentValue(String name) {
  	  if (myProperty!=null) {
  		  System.err.println("myProperty:"+myProperty.getName()+" value: "+myProperty.getValue());
  	  }
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
//      if ("use_checkbox".equals(name)) {
//        return new Boolean("checkbox".equals(selectionType));
//      }
//      if ("selectiontype".equals(name)) {
//        return selectionType;
//      }
//      if ("showlabel".equals(name)) {
//        return new Boolean(isLabelVisible());
//      }
//      if ("label_valign".equals(name)) {
//        return vAlign;
//      }
//      if ("enabled".equals(name)) {
//          if (myEnableState!=null) {
//              return new Boolean(myEnableState.booleanValue());
//          } else {
//              return new Boolean( ( (GenericPropertyComponent) getContainer()).isEnabled());
//          }
//        }
//      if ("visible".equals(name)) {
//        return new Boolean( ( (GenericPropertyComponent) getContainer()).isVisible());
//      }
//      if ("label_halign".equals(name)) {
//        return hAlign;
//      }
//      if ("label_indent".equals(name)) {
//        return new Integer( ( (GenericPropertyComponent) getContainer()).getLabelIndent());
//      }
//      if ("capitalization".equals(name)) {
//        return myCapitalization;
//      }
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
          if (myProperty.getType().equals(Property.BINARY_PROPERTY)) {
        	  return myProperty.getTypedValue();
          }
          return "" + myProperty.getTypedValue();
        }
      }
      return super.getComponentValue(name);
    }
    
    
    /**
     * addTipiEvent
     * 
     * @param te
     *            TipiEvent
     * @todo Implement this com.dexels.navajo.tipi.internal.PropertyComponent
     *       method
     */
    // public void addTipiEvent(TipiEvent te) {
    // }	  System.err.println("Getting PROPERTY VALUE: "+name);

    protected void setComponentValue(String name, Object object) {
        if ("propertyname".equals(name)) {
            myPropertyName = object.toString();
        	return;
        }
        if ("label_indent".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getActualComponent();
            Integer ii = (Integer) object;
            me.setLabelIndent(ii.intValue());
        	return;
        }
        if ("width".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getActualComponent();
            Integer ii = (Integer) object;
            me.setValueSize(ii.intValue());
        	return;
        	   
        }
        if ("showlabel".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getActualComponent();
            me.setLabelVisible(((Boolean)object).booleanValue());
        	return;

        }
        if ("allowDateOverlay".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getActualComponent();
            me.setAllowDateOverlay(((Boolean)object).booleanValue());
        	return;

        }
        if ("selectiontype".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getActualComponent();
              me.setSelectiontype("" + object);
            
        	return;
        }
        if ("useLabelsForReadOnly".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getActualComponent();
            boolean val = ((Boolean) object).booleanValue();
            me.setUseLabelForReadOnlyProperties(val);
        	return;
        }
        if ("alwaysUseLabel".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getActualComponent();
            boolean val = ((Boolean) object).booleanValue();
            me.setAlwaysUseLabel(val);
        	return;
        }
        if ("selectionmode".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getActualComponent();
            boolean val = ((Boolean) object).booleanValue();
            me.setUseCheckBoxes(val);
        	return;
        }
        if ("memoRowCount".equals(name)) {
            myPropertyComponent.setMemoRowCount(((Integer) object).intValue());
        	return;
        }
        if ("memoColumnCount".equals(name)) {
            myPropertyComponent.setMemoColumnCount(((Integer) object).intValue());
        	return;
        }
        if ("checkboxGroupColumnCount".equals(name)) {
            myPropertyComponent.setCheckboxGroupColumnCount(((Integer) object).intValue());
        	return;
        }
        if ("visibleRowCount".equals(name)) {
            myPropertyComponent.setMultiRowCount(((Integer) object).intValue());
        	return;
        }
        if ("maxImageHeight".equals(name)) {
            myPropertyComponent.setMaxImageHeight(((Integer) object).intValue());
        	return;
        }
        if ("maxImageWidth".equals(name)) {
            myPropertyComponent.setMaxImageWidth(((Integer) object).intValue());
        	return;
        }        
        // if("verticalScrolls".equals(name)) {
        // setVerticalScrolls(((Boolean) object).booleanValue());
        // }
        // if("horizontalScrolls".equals(name)) {
        // setHorizontalScrolls(((Boolean) object).booleanValue());
        // }
        if ("capitalization".equals(name)) {
            myPropertyComponent.setCapitalization( (String) object);
        	return;
        	   
        }

        if ("propertyValue".equals(name)) {
            myPropertyComponent.setPropertyValue( object);
        	return;
        }
        
        if ("border".equals(name)) {
        	Component parent = myPropertyComponent.getParent();
        	LayoutData ld = parent.getLayoutData();
        	parent.remove(myPropertyComponent);
        	GroupBox gb = new GroupBox(""+object);
        	Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(gb.getClass(), "Default");
            gb.setStyle(ss);
            
        	gb.add(myPropertyComponent);
        	parent.add(gb);
        	if(ld!=null) {
        		gb.setLayoutData(ld);
        	}
        	myPropertyComponent.setPropertyValue( object);
        	setContainer(gb);
        	return;
         }
        
        if ("allowStringLineWrap".equals(name)) {
        	myPropertyComponent.setAllowLineWrap(((Boolean)object).booleanValue());
        	return;
        }
        if ("visible".equals(name)) {
        	myPropertyComponent.setVisible(((Boolean)object).booleanValue());
        	return;
        }

        super.setComponentValue(name, object);
    }

    public void checkForConditionErrors(Message m) {
        // err.. implement?
    }
//
//	public void processStyles() {
//		// TODO Auto-generated method stub
//		super.processStyles();
//
//	}


}
