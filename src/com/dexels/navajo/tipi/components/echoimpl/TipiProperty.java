package com.dexels.navajo.tipi.components.echoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.actions.PropertyEventListener;
import com.dexels.navajo.tipi.internal.PropertyComponent;

import echopointng.ContainerEx;

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
        ((EchoPropertyComponent) getContainer()).setProperty(p);
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
                tel.performTipiEvent(eventType, m, true);
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
        myPropertyComponent = new EchoPropertyComponent();
        myPropertyComponent.setUseLabelForReadOnlyProperties(false);
        myPropertyComponent.addPropertyEventListener(this);
        // myContainer.add(myPropertyComponent);
        return myPropertyComponent;
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

    /**
     * addTipiEvent
     * 
     * @param te
     *            TipiEvent
     * @todo Implement this com.dexels.navajo.tipi.internal.PropertyComponent
     *       method
     */
    // public void addTipiEvent(TipiEvent te) {
    // }
    protected void setComponentValue(String name, Object object) {
        if ("propertyname".equals(name)) {
            myPropertyName = object.toString();
        }
        if ("label_indent".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getContainer();
            Integer ii = (Integer) object;
            me.setLabelIndent(ii.intValue());
        }
        if ("width".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getContainer();
            Integer ii = (Integer) object;
            me.setValueSize(ii.intValue());
        }
        if ("showlabel".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getContainer();
            me.setLabelVisible(((Boolean)object).booleanValue());
        }
        if ("selectiontype".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getContainer();
            try {
                me.setSelectiontype("" + object);
            } catch (NavajoException e) {
                e.printStackTrace();
            }
        }
        if ("useLabelsForReadOnly".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getContainer();
            boolean val = ((Boolean) object).booleanValue();
            me.setUseLabelForReadOnlyProperties(val);
        }
        if ("alwaysUseLabel".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getContainer();
            boolean val = ((Boolean) object).booleanValue();
            me.setAlwaysUseLabel(val);
        }
        if ("useCheckBoxes".equals(name)) {
            EchoPropertyComponent me = (EchoPropertyComponent) getContainer();
            boolean val = ((Boolean) object).booleanValue();
            me.setUseCheckBoxes(val);
        }
        if ("memoRowCount".equals(name)) {
            myPropertyComponent.setMemoRowCount(((Integer) object).intValue());
        }
        if ("memoColumnCount".equals(name)) {
            myPropertyComponent.setMemoColumnCount(((Integer) object).intValue());
        }
        if ("checkboxGroupColumnCount".equals(name)) {
            myPropertyComponent.setCheckboxGroupColumnCount(((Integer) object).intValue());
        }
        // if("verticalScrolls".equals(name)) {
        // setVerticalScrolls(((Boolean) object).booleanValue());
        // }
        // if("horizontalScrolls".equals(name)) {
        // setHorizontalScrolls(((Boolean) object).booleanValue());
        // }
        if ("capitalization".equals(name)) {
            myPropertyComponent.setCapitalization( (String) object);
         }
        
        super.setComponentValue(name, object);
    }

    public void checkForConditionErrors(Message m) {
        // err.. implement?
    }


}
