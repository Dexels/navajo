package com.dexels.navajo.tipi.impl;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class DefaultTipi
    extends TipiComponent
    implements Tipi {
//  private String myService = "";
  //private Navajo myNavajo = null;
  private ArrayList tipiList = new ArrayList();
  private ArrayList methodList = new ArrayList();
  private Map tipiMap = new HashMap();
//  private String myId = null;
//  private TipiLayout myLayout = null;
  private ArrayList myServices = null;
  protected String prefix;
//  protected String myName;
  protected TipiPopupMenu myPopupMenu = null;
  private String autoLoad = null;
  private String autoLoadDestination = null;
  public DefaultTipi() {
  }

  public void autoLoadServices(TipiContext context) throws TipiException {
    String autoDest;
    if (autoLoadDestination == null) {
      autoDest = "*";
    }
    else {
      autoDest = autoLoadDestination;
    }
    if (autoLoad != null && !autoLoad.equals("")) {
//        System.err.println("Performing servicelist for: "+getPath());
      performServiceList(autoLoad, autoDest, context);
    }
  }

  private void loadServices(String myService) {
    myServices = new ArrayList();
    if (myService != null) {
      //myContext.clearTipiAllInstances();
      if (myService.indexOf(';') >= 0) {
        StringTokenizer st = new StringTokenizer(myService, ";");
        while (st.hasMoreTokens()) {
          String t = st.nextToken();
          myServices.add(t);
          myContext.addTipiInstance(t, this);
//          System.err.println("Adding tipi with service: "+myService+" my id: "+myId);
        }
      }
      else {
        myServices.add(myService);
        myContext.addTipiInstance(myService, this);
//        System.err.println("Adding tipi with service: "+myService+" my id: "+myId);
      }
    }
  }

  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
    super.load(definition, instance, context);
//    System.err.println("Loading class: "+instance.getAttribute("id"));
    prefix = (String) instance.getAttribute("prefix");
//    String menubar = (String)instance.getAttribute("menubar");
    loadServices( (String) definition.getAttribute("service"));
    autoLoad = (String) definition.getAttribute("autoload");
    autoLoadDestination = (String) definition.getAttribute("autoloadDestination");
    String constraint = (String) instance.getAttribute("constraint");
    if (constraint==null) {
      constraint = (String) definition.getAttribute("constraint");
    }
    if (constraint!=null) {
      setConstraints(constraint);
    }
    System.err.println("MY CONSTRIANT::::::::::::::::: "+constraint);

    Vector children = null;
    if (instance.getAttribute("class") != null) {
//      System.err.println("Instantiating from instance");
      children = instance.getChildren();
    }
    else {
//      System.err.println("Instantiating from definition");
      children = definition.getChildren();
    }
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.get(i);
      if (child.getName().equals("layout")) {
        instantiateWithLayout(child);
      }
      else {
        if (child.getName().equals("tipi-instance") || child.getName().equals("component-instance")) {
          addAnyInstance(myContext, child, child.getAttribute("constraint"));
        }
//        else {
//          System.err.println("Ignored: "+child.getName());
//        }
      }
    }
//    instantiateWithOutLayout(children);
  }

  private void instantiateWithLayout(XMLElement x) throws TipiException {
    TipiLayout tl = myContext.instantiateLayout(x);
    setLayout(tl);
    tl.createLayout();
    tl.initializeLayout(x);
//    if (tl instanceof DefaultTipiLayout) {
//      DefaultTipiLayout dtl = (DefaultTipiLayout) tl;
      if (getContainer() != null) {
        setContainerLayout(tl.getLayout());
//            getContainer().setLayout(dtl.getLayout());
      }
      tl.loadLayout(this, null);

//          Vector children = x.getChildren();
//
//          for (int i = 0; i < children.size(); i++) {
//            XMLElement current = (XMLElement)children.get(i);
//            addAnyInstance(myContext,current,dtl.parseConstraint((String)current.getAttribute("constraint")));
//    }
//
//        }
//
  }

  public void showPopup(MouseEvent e) {
    ( (JPopupMenu) myPopupMenu.getContainer()).show(getContainer(), e.getX(), e.getY());
  }

  public String getName() {
    return myName;
  }

  public TipiComponent addAnyInstance(TipiContext context, XMLElement instance, Object constraints) throws TipiException {
//    System.err.println("Adding to: "+getPath()+" instance. name: "+instance.getAttribute("name")+" id: "+instance.getAttribute("id"));
    if (instance.getName().equals("tipi-instance")) {
      return (TipiComponent) addTipiInstance(context, constraints, instance);
    }
    if (instance.getName().equals("component-instance")) {
      return addComponentInstance(context, instance, constraints);
    }
    return null;
  }

  public Object getComponentValue(String name) {
    if (".".equals(name)) {
      return getNavajo();
    }
    return super.getComponentValue(name);
  }

  public void refreshLayout() {
    ArrayList elementList = new ArrayList();
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = (TipiComponent)getChildAt(i);
      if (current.isVisibleElement()) {
        removeFromContainer(current.getContainer());
      }
      elementList.add(current);
    }
    for (int i = 0; i < elementList.size(); i++) {
      TipiComponent current = (TipiComponent)elementList.get(i);
      if (current.isVisibleElement()) {
        addToContainer(current.getContainer(),current.getConstraints());
      }
    }
    getContainer().repaint();
    if (JComponent.class.isInstance(getContainer())) {
      ((JComponent)getContainer()).revalidate();
    }

  }

  public void replaceLayout(TipiLayout tl) {
    ArrayList elementList = new ArrayList();
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = (TipiComponent)getChildAt(i);
      if (current.isVisibleElement()) {
        removeFromContainer(current.getContainer());
      }
      elementList.add(current);
    }
    setLayout(tl);
    setContainerLayout(tl.getLayout());
    for (int i = 0; i < elementList.size(); i++) {
      TipiComponent current = (TipiComponent)elementList.get(i);
      Object o = tl.createDefaultConstraint(i);
      current.setConstraints(o);
      addToContainer(current.getContainer(),o);
    }
    getContainer().repaint();
    if (JComponent.class.isInstance(getContainer())) {
      ((JComponent)getContainer()).revalidate();
    }
  }


  public void performServiceList(String list, String tipiPath, TipiContext context) throws TipiException {
//    System.err.println("Performing service list for path: "+getPath()+" with indicated path of: "+tipiPath);
    if (list.indexOf(";") < 0) {
      performService(context, tipiPath, list);
      return;
    }
    StringTokenizer st = new StringTokenizer(list, ";");
    while (st.hasMoreTokens()) {
      performService(context, tipiPath, st.nextToken());
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

  public void addService(String service) {
    myServices.add(service);
  }

  public void removeService(String service) {
    myServices.remove(service);
  }

  public void performService(TipiContext context, String service) throws TipiException {
    performService(context, "*", service);
  }

  public void performService(TipiContext context, String tipiPath, String service) throws TipiException {
    /** @todo Tempory HACK!!! */
    tipiPath = "*";
//    System.err.println("Performing service: "+getPath());
    if (myNavajo == null) {
      myNavajo = NavajoFactory.getInstance().createNavajo();
    }
    context.performTipiMethod(this, myNavajo, tipiPath, service);
  }
  public void loadData(Navajo n, TipiContext tc) throws TipiException {
    if (n != null) {
    }
    if (n == null) {
      throw new TipiException("Loading with null Navajo! ");
    }
    for (int i = 0; i < properties.size(); i++) {
      BasePropertyComponent current = (BasePropertyComponent) properties.get(i);
      Property p;
      System.err.println("Checking property: "+current.getPropertyName());
      if (prefix != null) {
        System.err.println("DEPRECATED:::::: WITH Prefix, looking for: "+prefix + "/" + current.getPropertyName());
        p = n.getProperty(prefix + "/" + current.getPropertyName());
        current.setProperty(p);
      }
      else {
        System.err.println("WITHOUT Prefix, looking for: "+current.getPropertyName());
        p = n.getProperty( current.getPropertyName());
        if (p!=null) {
          System.err.println("Loading property. Type: "+p.getType()+" value: "+p.getValue());
          current.setProperty(p);
          System.err.println(">> "+current.getContainer().isVisible());
          System.err.println(">>> "+current.getContainer().getBounds());
        }

      }
    }
    if (n == null) {
      System.err.println("NULL NAVAJO!");
      return;
    }
    myNavajo = n;
//    if (getLayout() != null) {
//      if (getLayout().needReCreate()) {
//        getLayout().reCreateLayout(tc, this, n);
//      }
//      getLayout().loadLayout(this, n);
//    }
    for (int i = 0; i < getTipiCount(); i++) {
      Tipi current = getTipi(i);
      if (current.getServices().size() > 0) {
//        System.err.println("SubTipi '" + current.getName() + "' is listenening to one or more service(s)");
      }
      current.loadData(n, tc);
    }
//    for (int i = 0; i < methodList.size(); i++) {
//      MethodComponent current = (MethodComponent) methodList.get(i);
//      current.loadData(n, tc);
//    }
    performTipiEvent("onLoad", null);
    if (getContainer()!=null) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          getContainer().doLayout();
          if (JComponent.class.isInstance(getContainer())) {
            ((JComponent)getContainer()).revalidate();
            ((JComponent)getContainer()).repaint();
          }

        }
      });
    }
  }

  public LayoutManager getContainerLayout() {
    return getContainer().getLayout();
  }

  public boolean loadErrors(Navajo n) {
    try {
      return performTipiEvent("onGeneratedErrors", null);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  private TipiComponent addComponentInstance(TipiContext context, XMLElement inst, Object constraints) throws TipiException {
    TipiComponent ti = (TipiComponent) (context.instantiateComponent(inst));
//    System.err.println("Adding to instance: "+inst.getStringAttribute("id","Name: "+inst.getStringAttribute("name")));
    ti.setConstraints(constraints);
    addComponent(ti, context, constraints);
    if (ti instanceof DefaultTipi) {
      ( (DefaultTipi) ti).autoLoadServices(context);
    }
    return ti;
  }

  protected Tipi addTipiInstance(TipiContext context, Object constraints, XMLElement inst) throws TipiException {
//    Tipi t = (Tipi)(context.instantiateComponent(inst));
//    System.err.println("Adding tipi");
//    addTipi(ti, context, constraints, inst);
    Tipi t = (Tipi) addComponentInstance(context, inst, constraints);
    tipiList.add(t);
    tipiMap.put(t.getId(), t);
    t.setConstraints(constraints);
    return t;
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
    if (tc == null) {
      System.err.println("Could not find tipi!");
      return null;
    }
    if (!Tipi.class.isInstance(tc)) {
      System.err.println("Found a component while looking for a tipi, but not a Tipi");
      Thread.dumpStack();
      return null;
    }
    return (Tipi) tc;
  }

//  public TipiLayout getLayout() {
//    return myLayout;
//  }

  public void clearProperties() {
    getContainer().removeAll();
    properties.clear();
  }

  public XMLElement store() {
    XMLElement IamThereforeIcanbeStored = super.store();
    IamThereforeIcanbeStored.setName("tipi-instance");
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < myServices.size(); i++) {
      sb.append(myServices.get(i));
      if ((i+1)<myServices.size()) {
        sb.append(";");
      }
    }
    IamThereforeIcanbeStored.setAttribute("service",sb.toString());
    return IamThereforeIcanbeStored;
  }

  public void tipiLoaded() {
  }

  public void childDisposed() {
  }

}