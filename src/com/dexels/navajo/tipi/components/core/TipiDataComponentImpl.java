package com.dexels.navajo.tipi.components.core;

import java.util.*;
//import javax.swing.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiDataComponentImpl
    extends TipiComponentImpl
    implements TipiDataComponent {
  private ArrayList myServices = null;
  protected String prefix;
  private String autoLoad = null;
  private String autoLoadDestination = null;
  public TipiDataComponentImpl() {
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
    if (constraint == null) {
      constraint = (String) definition.getAttribute("constraint");
    }
    if (constraint != null) {
      setConstraints(constraint);
    }
//    System.err.println("MY CONSTRIANT::::::::::::::::: " + constraint);
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
          addComponentInstance(myContext, child, child.getAttribute("constraint"));
        }
      }
    }
  }

  private void instantiateWithLayout(XMLElement x) throws TipiException {
    TipiLayout tl = myContext.instantiateLayout(x);
    if (tl==null) {
      throw new RuntimeException("Trying to instantiate with layout, but the layout == null");
    }
    setLayout(tl);
    tl.createLayout();
    tl.initializeLayout(x);
    if (getContainer() != null) {
      setContainerLayout(tl.getLayout());
    }
    tl.loadLayout(this, null);
  }

  public String getName() {
    return myName;
  }

  protected Object getComponentValue(String name) {
    if (".".equals(name)) {
      return getNavajo();
    }
    return super.getComponentValue(name);
  }

  public void replaceLayout(TipiLayout tl) {
    ArrayList elementList = new ArrayList();
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent current = (TipiComponent) getTipiComponent(i);
      if (current.isVisibleElement()) {
        removeFromContainer(current.getContainer());
      }
      elementList.add(current);
    }
    setLayout(tl);
    setContainerLayout(tl.getLayout());
    for (int i = 0; i < elementList.size(); i++) {
      TipiComponent current = (TipiComponent) elementList.get(i);
      Object o = tl.createDefaultConstraint(i);
      current.setConstraints(o);
      addToContainer(current.getContainer(), o);
    }
  }

  public void performServiceList(String list, String tipiPath, TipiContext context) throws TipiException {
    if (list.indexOf(";") < 0) {
      performService(context, tipiPath, list);
      return;
    }
    StringTokenizer st = new StringTokenizer(list, ";");
    while (st.hasMoreTokens()) {
      performService(context, tipiPath, st.nextToken());
    }
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
    if (n == null) {
      throw new TipiException("Loading with null Navajo! ");
    }
//    System.err.println("\n\nLoading tipi: "+getPath()+" with service: "+n.getHeader().getRPCName()+"\n");
    System.err.println("# of properties: "+properties.size());
    for (int i = 0; i < properties.size(); i++) {
      PropertyComponent current = (PropertyComponent) properties.get(i);
      Property p;
      if (prefix != null) {
        System.err.println("DEPRECATED:::::: WITH Prefix, looking for: " + prefix + "/" + current.getPropertyName());
        p = n.getProperty(prefix + "/" + current.getPropertyName());
        current.setProperty(p);
      }
      else {
        p = n.getProperty(current.getPropertyName());
        if (p != null) {
          current.setProperty(p);
        }
      }
    }
    if (n == null) {
      System.err.println("NULL NAVAJO!");
      return;
    }
    myNavajo = n;
    /** @todo Maybe it is not a good idea that it is recursive. */
    /** @todo Also, the children get loaded, but no onLoad event is fired. Bit strange. */
    for (int i = 0; i < getChildCount(); i++) {
      TipiComponent tcomp = getTipiComponent(i);
      if (TipiDataComponent.class.isInstance(tcomp)) {
        TipiDataComponent current = (TipiDataComponent) tcomp;
        current.loadData(n, tc);
      }
    }
    performTipiEvent("onLoad", null, true);
    doLayout();
  }

  protected void doLayout() {
  }

  public boolean loadErrors(Navajo n) {
    try {
      return performTipiEvent("onGeneratedErrors", null, true);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  public void refreshLayout() {
    // do nothing.
  }

  public TipiDataComponent getTipiByPath(String path) {
    TipiComponent tc = getTipiComponentByPath(path);
    if (tc == null) {
      System.err.println("Could not find tipi!");
      return null;
    }
    if (!TipiDataComponent.class.isInstance(tc)) {
      System.err.println("Found a component while looking for a tipi, but not a Tipi");
      Thread.dumpStack();
      return null;
    }
    return (TipiDataComponent) tc;
  }

  public void clearProperties() {
    /** @todo Beware. Removed this.... */
//    getContainer().removeAll();
    properties.clear();
  }

  public XMLElement store() {
    XMLElement IamThereforeIcanbeStored = super.store();
    IamThereforeIcanbeStored.setName("tipi-instance");
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < myServices.size(); i++) {
      sb.append(myServices.get(i));
      if ( (i + 1) < myServices.size()) {
        sb.append(";");
      }
    }
    IamThereforeIcanbeStored.setAttribute("service", sb.toString());
    return IamThereforeIcanbeStored;
  }

  public void tipiLoaded() {
  }

  public void childDisposed() {
  }
}
