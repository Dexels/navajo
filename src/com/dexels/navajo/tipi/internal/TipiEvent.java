package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.studio.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiEvent
    implements TipiExecutable {
  private String myEventName;
  private String myEventService;
  private String mySource;
//  private ArrayList myActions;
  private Navajo myNavajo;
  private TipiComponent myComponent;
//  private TipiActionBlock myTopActionBlock = null;
  private final ArrayList myExecutables = new ArrayList();
  public TipiEvent() {
  }

  public void load(TipiComponent tc, XMLElement elm, TipiContext context) throws TipiException {
    myComponent = tc;
//    myActions = new ArrayList();
    if (elm.getName().equals("event")) {
      String stringType = (String) elm.getAttribute("type");
      myEventName = stringType;
      myEventService = (String) elm.getAttribute("service");
      mySource = (String) elm.getAttribute("listen");
      //myCondition = (String) elm.getAttribute("condition");
      Vector temp = elm.getChildren();
      for (int i = 0; i < temp.size(); i++) {
        XMLElement current = (XMLElement) temp.get(i);
        if (current.getName().equals("condition")) {
          TipiActionBlock ta = context.instantiateDefaultTipiActionBlock(myComponent, this);
          ta.loadConditionStyle(current, myComponent, this);
          myExecutables.add(ta);
        }
        if (current.getName().equals("block")) {
          TipiActionBlock ta = context.instantiateDefaultTipiActionBlock(myComponent, this);
          ta.load(current, myComponent, this);
          ta.setTipiActionBlockParent(null);
          myExecutables.add(ta);
        }
        if (current.getName().equals("action")) {
          TipiAction ta = context.instantiateTipiAction(current, myComponent, this);
          myExecutables.add(ta);
        }
      }
    }
  }

  public boolean isSync() {
    return false;
  }

  public void appendExecutable(TipiExecutable a) {
    myExecutables.add(a);
  }

  public void removeExecutable(TipiExecutable a) {
    myExecutables.remove(a);
  }

  public void moveExecutableUp(TipiAction action) {
    int index_old = myExecutables.indexOf(action);
    if (index_old > 0) {
      myExecutables.remove(action);
      myExecutables.add(index_old - 1, action);
    }
  }

  public void moveExecutableDown(TipiAction action) {
    int index_old = myExecutables.indexOf(action);
    if (index_old < myExecutables.size() - 1) {
      myExecutables.remove(action);
      myExecutables.add(index_old + 1, action);
    }
  }

  public TipiContext getContext() {
    if (myComponent == null) {
      throw new RuntimeException("Event without component is not allowed");
    }
    return myComponent.getContext();
  }

  public void asyncPerformAction(final TipiEventListener listener, final Object event) {
    final TipiEvent te = this;


//    SwingWorker workThread = new SwingWorker() {
//      public Object construct() {
//        try {
//          performAction(listener, event);
//          myComponent.getContext().threadEnded(te, Thread.currentThread());
//        }
//        catch (TipiException ex) {
//          ex.printStackTrace();
//        }
//        return null;
//      }
//    };
//    myComponent.getContext().threadStarted(this, workThread);
//    workThread.start();
    myComponent.getContext().performAction(te);
  }
  public TipiComponent getComponent() {
    return myComponent;
  }

  public void performAction() throws TipiException {
    performAction(myComponent, null);
  }

  public void performAction(TipiEventListener listener) throws TipiException {
    performAction(listener, null);
  }

  public void performAction(TipiEventListener listener, Object event) throws TipiException {
    listener.eventStarted(this, event);
    try {
      getContext().performedEvent(myComponent, this);
    }
    catch (BlockActivityException ex1) {
      System.err.println("Blocked exception");
      return;
    }
    try {
      for (int i = 0; i < myExecutables.size(); i++) {
        TipiExecutable current = (TipiExecutable) myExecutables.get(i);
        current.performAction();
      }
    }
    catch (TipiBreakException ex) {
      System.err.println("Break encountered in event");
    }
    listener.eventFinished(this, event);
  }

  public boolean isTrigger(String name, String service) {
//    System.err.println("NAME: "+name+" myName: "+myEventName);
//    System.err.println(">>>>> Checking for TRIGGER: " + name + " service_compare: " + service + "?=" + myEventService);
    if (name != null) {
      if (service == null || myEventService == null || myEventService.equals("")) {
        return name.equals(myEventName);
      }
      else {
        return (service.equals(myEventService) && name.equals(myEventName));
      }
    }
    System.err.println("Name not specified!!");
    return false;
  }

  public void setNavajo(Navajo n) {
    myNavajo = n;
  }

  public String getEventName() {
    return myEventName;
  }

  public String getSource() {
    return mySource;
  }

  public XMLElement store() {
//    throw new RuntimeException("Todo: check and reimplement");
    XMLElement s = new CaseSensitiveXMLElement();
    s.setName("event");
    s.setAttribute("type", myEventName);
    for (int i = 0; i < myExecutables.size(); i++) {
      TipiExecutable current = (TipiExecutable) myExecutables.get(i);
      s.addChild(current.store());
    }
    return s;
  }

  public int getExecutableChildCount() {
    return myExecutables.size();
  }

  public TipiExecutable getExecutableChild(int index) {
    return (TipiExecutable) myExecutables.get(index);
  }
}
