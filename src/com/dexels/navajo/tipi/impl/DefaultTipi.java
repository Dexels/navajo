package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;
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
//    if (menubar!=null) {
//      XMLElement xe = context.getTipiMenubarDefinition(menubar);
//      TipiMenubar tm = context.createTipiMenubar();
//      tm.load(xe,context);
//      context.getTopLevel().setTipiMenubar(tm);
//    }

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
//      if (child.getName().equals("event")) {
//        TipiEvent te = new TipiEvent();
//        te.load(this, child, context);
//        addTipiEvent(te);
//      }
      addAnyInstance(context,child,child.getAttribute("constraint"));

    }
    String autoLoad = (String) definition.getAttribute("autoload");
    if (autoLoad != null) {
      performServiceList(autoLoad, context);
    }
  }

  public void showPopup(MouseEvent e) {
    ((JPopupMenu)myPopupMenu.getContainer()).show(getContainer(), e.getX(), e.getY());
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

  public Object getComponentValue(String name){
    if(".".equals(name)){
      return getNavajo();
    }else{
      return super.getComponentValue(name);
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
//    methodList.add(m);
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
      myNavajo = NavajoFactory.getInstance().createNavajo();
    }
    context.performTipiMethod(this, service);
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {

//    System.err.println("Loading data into tipi>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>: "+getName());
//    try {
//      n.write(System.err);
//    }
//    catch (NavajoException ex) {
//      ex.printStackTrace();
//    }
//    System.err.println("My props: "+properties);

    if (n == null) {
      throw new TipiException("Loading with null Navajo! ");
    }
    for (int i = 0; i < properties.size(); i++) {
      BasePropertyComponent current = (BasePropertyComponent) properties.get(i);
      Property p;
      if (prefix != null) {
//        System.err.println("WITH Prefix, looking for: "+prefix + "/" + (String) propertyNames.get(i));
        p = n.getProperty(prefix + "/" + (String) propertyNames.get(i));
      }
      else {
//        System.err.println("WITHOUT Prefix, looking for: "+(String) propertyNames.get(i));
        p = n.getProperty( (String) propertyNames.get(i));
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
//    for (int i = 0; i < methodList.size(); i++) {
//      MethodComponent current = (MethodComponent) methodList.get(i);
//      current.loadData(n, tc);
//    }
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
    TipiComponent tc = getTipiComponentByPath(path);
    if (tc==null) {
      System.err.println("Could not find tipi!");
      return null;
    }
    if (!Tipi.class.isInstance(tc)) {
      System.err.println("Found a component while looking for a tipi, but not a Tipi");
      Thread.dumpStack();
      return null;
    }
    return (Tipi)tc;
//
//    if (path.indexOf("/") == 0) {
//      path = path.substring(1);
//    }
//    int s = path.indexOf("/");
//    if (s == -1) {
//      if (path.equals("result")) {
//        System.out.println("I am here");
//      }
//      return getTipi(path);
//    }
//    else {
//      String name = path.substring(0, s);
//      String rest = path.substring(s);
//      Tipi t = getTipi(name);
//      if (t == null) {
//        throw new NullPointerException("Did not find Tipi: " + name + " list: " + tipiList);
//      }
//      return t.getTipiByPath(rest);
//    }
  }

  public TipiLayout getLayout() {
    return myLayout;
  }

  public void clearProperties() {
    getContainer().removeAll();
    properties.clear();
  }

}