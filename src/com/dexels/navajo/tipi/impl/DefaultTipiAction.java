package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import tipi.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipiAction
    extends TipiAction {
  public void execute(Navajo n, TipiContext context, Object source) throws TipiBreakException,TipiException {
    String path;
    Map params;
    switch (myType) {
      case TYPE_BREAK:
        throw new TipiBreakException(n, context);
      case TYPE_LOAD:
        throw new RuntimeException("Not yet implemented!");
      case TYPE_LOADCONTAINER:
        throw new RuntimeException("Not yet implemented!");
      case TYPE_PERFORMMETHOD:
        performMethod(n, context, source);
        break;
      case TYPE_CALLSERVICE:
        callService(context, source);
        break;
      case TYPE_SETPROPERTYVALUE:
        setPropertyValue(n, context, source);
        break;
      case TYPE_INFO:
        showInfo(n, context, source);
        break;
      case TYPE_SHOWQUESTION:
        showQuestion(n, context, source);
        break;
      case TYPE_EXIT:
        System.exit(0);
        break;
      case TYPE_SETVISIBLE:
        setVisible(context, source);
        break;
      case TYPE_SETENABLED:
        setEnabled(context, source);
        break;
      case TYPE_LOADUI:
        loadUI(context, source);
        break;
      case TYPE_SETVALUE:
        setValue(context, source);
        break;
    }
  }

  private void setValue(TipiContext context, Object source) throws TipiException {
    String path = (String)myParams.get("path");
    String name = (String)myParams.get("name");
    String value = (String)myParams.get("value");
    TipiComponent tc = context.getTipiComponentByPath(path);
    tc.setComponentValue(name,value);
  }

  private void setVisible(TipiContext context, Object source) {
    String componentPath = (String) myParams.get("tipipath");
    String vis = (String) myParams.get("value");
    boolean visible = true;
    if (vis != null) {
      if (vis.equals("false")) {
        visible = false;
      }
    }
    Tipi tscr = context.getTopScreen();
    System.err.println("PATH: " + componentPath);
    Tipi t = tscr.getTipiByPath(componentPath);
    t.getContainer().setVisible(visible);
  }

  private void loadUI(TipiContext context, Object source) {
    String file = (String) myParams.get("file");
    if (file != null) {
      MainApplication.loadXML(file);
    }
  }

  private void setEnabled(TipiContext context, Object source) {
    String componentPath = (String) myParams.get("tipipath");
    String vis = (String) myParams.get("value");
    boolean enabled = true;
    if (vis != null) {
      if (vis.equals("false")) {
        enabled = false;
      }
    }
    Tipi tscr = context.getTopScreen();
    Tipi t = tscr.getTipiByPath(componentPath);
    Container c = t.getContainer();
    if (c != null) {
      System.err.println("This tipi has " + c.getComponentCount() + " subcomponents");
      for (int i = 0; i < c.getComponentCount(); i++) {
        Component current = c.getComponent(i);
        System.err.println("Current class: " + current.getClass());
        current.setEnabled(enabled);
      }
    }
    else {
      System.err.println("Cannot set a NULL container to visible");
    }

  }

  private void performMethod(Navajo n, TipiContext context, Object source) throws TipiBreakException {

    String componentPath = (String) myParams.get("tipipath");
    String method = (String) myParams.get("method");
    Tipi tscr = context.getTopScreen();

    Tipi t = tscr.getTipiByPath(componentPath);
    if (t == null) {
      System.err.println("Can not find tipi for: " + componentPath);
      return;
    }

    try {
      t.performService(context, method);
    }
    catch (TipiException ex) {
      System.err.println("Error preforming method!");
      ex.printStackTrace();
    }

  }

  private void callService(TipiContext context, Object source) throws TipiBreakException {
    String service = (String) myParams.get("service");
    if (service != null) {
      try {
        context.performMethod(service);
      }
      catch (TipiException ex) {
        System.err.println("Error executing call service:");
        ex.printStackTrace();
      }
    }
  }

  private void setPropertyValue(Navajo n, TipiContext context, Object source) throws TipiBreakException {
    String path = (String) myParams.get("path");
    String value = (String) myParams.get("value");
    if (path != null && value != null) {
      Property prop = n.getRootMessage().getPropertyByPath(path);
      prop.setValue(value);
    }
  }

  private void showInfo(Navajo n, TipiContext context, Object source) throws TipiBreakException {
    System.err.println("showInfo!");
    if (Component.class.isInstance(source)) {
      Component c = (Component) source;
      String txt = (String) getParams().get("value");
      JOptionPane.showMessageDialog(c, txt);
    }
    else {
      System.err.println("hmmmmmm....Weird\n\n");
    }
  }

  private void showQuestion(Navajo n, TipiContext context, Object source) throws TipiBreakException {
    System.err.println("showQuestion!");
    String txt = (String) getParams().get("value");
    Component c = (Component) source;
    int response = JOptionPane.showConfirmDialog(c, txt);
    if (response != 0) {
      throw new TipiBreakException(n, source);
    }
  }
}