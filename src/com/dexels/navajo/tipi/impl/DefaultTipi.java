package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class DefaultTipi
    extends SwingTipiComponent
    implements Tipi, TipiEventListener {

//  private String myService = "";
  //private Navajo myNavajo = null;
  private ArrayList tipiList = new ArrayList();
  private ArrayList methodList = new ArrayList();
  private Map tipiMap = new HashMap();

//  private String myId = null;
  private TipiLayout myLayout = null;
  private DefaultMethodToolBar myToolbar = null;
  private ArrayList myServices = null;
  protected String prefix;
//  protected String myName;
  protected TipiPopupMenu myPopupMenu = null;

  public DefaultTipi() {
  }

  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
    super.load(definition,instance,context);
    setContext(context);
    Container c;
    String myService;
    String type = (String) definition.getAttribute("type");

/** @todo REMEMBER THIS ONE... I THINK IT SHOULD BE REMOVED */
//    setContainer(createContainer());

    prefix = (String) instance.getAttribute("prefix");
    myName = (String) definition.getAttribute("name");
    String popup = (String) definition.getAttribute("popup");
    if (popup != null) {
      myPopupMenu = context.instantiateTipiPopupMenu(popup);
      getContainer().addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          if (e.isPopupTrigger()) {
            showPopup(e);
          }
        }

        public void mouseReleased(MouseEvent e) {
          if (e.isPopupTrigger()) {
            showPopup(e);
          }
        }
      });

    }
    String menubar = (String)instance.getAttribute("menubar");
    if (menubar!=null) {
      System.err.println("\n\nYES! I've found a menubar!");
      XMLElement xe = context.getTipiMenubarDefinition(menubar);
System.err.println(">>>>>>>.."+xe);
      TipiMenubar tm = context.createTipiMenubar();
      tm.load(xe,context);
      context.getTopLevel().setTipiMenubar(tm);
    }

//    super.load(definition, instance, context);
    myService = (String) definition.getAttribute("service");
    myServices = new ArrayList();
    if (myService != null) {
      if (myService.indexOf(';') >= 0) {
        StringTokenizer st = new StringTokenizer(myService, ";");
        while (st.hasMoreTokens()) {
          String t = st.nextToken();
          myServices.add(t);
          context.addTipiInstance(t, this);
        }
      }
      else {
        myServices.add(myService);
        context.addTipiInstance(myService, this);
      }
    }
    Vector children = definition.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      if (child.getName().equals("layout")) {
        TipiLayout tl = context.instantiateLayout(child);

        tl.createLayout(context, this, child, null);
        myLayout = tl;

      }
      if (child.getName().equals("event")) {
        TipiEvent te = new TipiEvent();
        te.load(this, child, context);
        addTipiEvent(te);
      }
      addAnyInstance(context,child,child.getAttribute("constraint"));

    }
    String autoLoad = (String) definition.getAttribute("autoload");
    if (autoLoad != null) {
      performServiceList(autoLoad, context);
    }
  }

  public void showPopup(MouseEvent e) {
    myPopupMenu.show(getContainer(), e.getX(), e.getY());
  }
  public String getName() {
    return myName;
  }

  public void addAnyInstance(TipiContext context, XMLElement instance, Object constraints) throws TipiException {
    if (instance.getName().equals("tipi-instance")) {
      addTipiInstance(context, constraints, instance);
    }
    if (instance.getName().equals("component-instance")) {
      addComponentInstance(context, instance,constraints);
    }
  }

  public void performServiceList(String list, TipiContext context) throws TipiException {
    if (list.indexOf(";") < 0) {
      performService(context, list);
      return;
    }
    StringTokenizer st = new StringTokenizer(list, ";");
    while (st.hasMoreTokens()) {
      performService(context, st.nextToken());
    }
  }

  public void setContainerLayout(LayoutManager layout) {

    getContainer().setLayout(layout);
  }


  public Tipi getTipi(int i) {
    return (Tipi) tipiList.get(i);
  }

  public ArrayList getServices() {
    return myServices;
  }

  public void addMethod(MethodComponent m) {
    methodList.add(m);
  }

  public void performService(TipiContext context) throws TipiException {
    if (myServices != null) {
      for (int i = 0; i < myServices.size(); i++) {
        performService(context, (String) myServices.get(i));
      }
    }
  }

  public void performService(TipiContext context, String service) throws TipiException {
    System.err.println("PerformService n=" + service);
    if (myNavajo == null) {
      myNavajo = new Navajo();
    }
    context.performTipiMethod(this, service);
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {
//    System.err.println("LOADING NAVAJO:  "+n.toXml());
    if (n == null) {
      return;
    }
    for (int i = 0; i < properties.size(); i++) {
      BasePropertyComponent current = (BasePropertyComponent) properties.get(i);
      Property p;
      if (prefix != null) {
        p = n.getRootMessage().getPropertyByPath(prefix + "/" + (String) propertyNames.get(i));
      }
      else {
        p = n.getRootMessage().getPropertyByPath( (String) propertyNames.get(i));
      }
      current.setProperty(p);
    }
    if (n == null) {
      System.err.println("NULL NAVAJO!");
      return;
    }

    myNavajo = n;
    if (getLayout() != null) {
      if (getLayout().needReCreate()) {
        getLayout().reCreateLayout(tc, this, n);
      }
    }

    if (myToolbar != null) {
      myToolbar.load(this, n, tc);
    }

    for (int i = 0; i < getTipiCount(); i++) {
      Tipi current = getTipi(i);
      current.loadData(n, tc);
    }
    for (int i = 0; i < methodList.size(); i++) {
      MethodComponent current = (MethodComponent) methodList.get(i);
      current.loadData(n, tc);
    }
    performAllEvents(TipiEvent.TYPE_ONLOAD,null);
  }

  public LayoutManager getContainerLayout(){
   return getContainer().getLayout();
 }


  protected Tipi addTipiInstance(TipiContext context, Object constraints, XMLElement inst) throws TipiException {
    Tipi ti = (Tipi)(context.instantiateComponent(inst));
    addTipi(ti, context, constraints, inst);
    return ti;
  }

  private void addTipi(Tipi t, TipiContext context, Object td, XMLElement definition) {
//    String id = t.getId();
    tipiList.add(t);
    tipiMap.put(t.getId(), t);
//    String vis = (String) definition.getAttribute("visible", "true");
//    String border = (String) definition.getAttribute("border", "false");
//    if (JPanel.class.isInstance(t.getContainer()) && "true".equals(border)) {
//      System.err.println("Creating border for: " + t.getName());
//      ( (JPanel) t.getContainer()).setBorder(new EtchedBorder());
//    }
//    boolean visible;
//    if (vis.equals("false")) {
//      visible = false;
//    }
//    else {
//      visible = true;
//    }
//    t.getContainer().setVisible(visible);

    addComponent((TipiComponent)t, context, td);
  }

  public Tipi getTipi(String name) {
    Tipi t = (Tipi) tipiMap.get(name);
//    System.err.println("Getting tipi. My name: " + myName + " my id: " + myId + " looking for: " + name + " found? " + t == null);
    return t;
  }
  public int getTipiCount() {
    return tipiList.size();
  }




  public Tipi getTipiByPath(String path) {
    if (path.indexOf("/") == 0) {
      path = path.substring(1);
    }
    int s = path.indexOf("/");
    if (s == -1) {
      if (path.equals("result")) {
        System.out.println("I am here");
      }
      return getTipi(path);
    }
    else {
      String name = path.substring(0, s);
      String rest = path.substring(s);
      Tipi t = getTipi(name);
      if (t == null) {
        throw new NullPointerException("Did not find Tipi: " + name + " list: " + tipiList);
      }
      return t.getTipiByPath(rest);
    }
  }

  public TipiLayout getLayout() {
    return myLayout;
  }

  public void clearProperties() {
    getContainer().removeAll();
    properties.clear();
  }

}