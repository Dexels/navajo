package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.internal.PropertyComponent;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.document.*;
import com.dexels.navajo.echoclient.components.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiProperty extends TipiEchoComponentImpl
    implements PropertyComponent {

  private Property myProperty = null;
  private String myPropertyName = null;
  public TipiProperty() {
  }


  public Property getProperty() {
    return myProperty;
  }

  public void setProperty(Property p) {
    try {
      System.err.println("Setting property: " + p.getFullPropertyName());
    }
    catch (NavajoException ex1) {
      ex1.printStackTrace();
    }
    myProperty = p;
    try {
      ( (EchoPropertyComponent) getContainer()).setProperty(p);
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
  }

  public Object createContainer() {
    EchoPropertyComponent e = new EchoPropertyComponent();
    return e;
  }

  /**
   * getPropertyName
   *
   * @return String
   * @todo Implement this com.dexels.navajo.tipi.internal.PropertyComponent
   *   method
   */
  public String getPropertyName() {
    return myPropertyName;
  }

  /**
   * addTipiEventListener
   *
   * @param listener TipiEventListener
   * @todo Implement this com.dexels.navajo.tipi.internal.PropertyComponent
   *   method
   */
  public void addTipiEventListener(TipiEventListener listener) {
  }

  /**
   * addTipiEvent
   *
   * @param te TipiEvent
   * @todo Implement this com.dexels.navajo.tipi.internal.PropertyComponent
   *   method
   */
//  public void addTipiEvent(TipiEvent te) {
//  }

  protected void setComponentValue(String name, Object object) {
    if ("propertyname".equals(name)) {
      System.err.println("Setting propname to: "+object.toString());
      myPropertyName = object.toString();
    }
  }


}
