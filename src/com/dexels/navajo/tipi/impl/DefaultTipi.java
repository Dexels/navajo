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

  private String autoLoad = null;

  public DefaultTipi() {
  }

  public void autoLoadServices(TipiContext context) throws TipiException {
      if (autoLoad != null && !autoLoad.equals("")) {
        performServiceList(autoLoad, context);
      }
  }

  private void loadServices(String myService) {
    myServices = new ArrayList();
    if (myService != null) {
      if (myService.indexOf(';') >= 0) {
        StringTokenizer st = new StringTokenizer(myService, ";");
        while (st.hasMoreTokens()) {
          String t = st.nextToken();
          myServices.add(t);
          myContext.addTipiInstance(t, this);
        }
      }
      else {
        myServices.add(myService);
        myContext.addTipiInstance(myService, this);
      }
    }
  }

  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
    super.load(definition,instance,context);
    prefix = (String) instance.getAttribute("prefix");
//    String menubar = (String)instance.getAttribute("menubar");
    loadServices((String) definition.getAttribute("service"));
    autoLoad = (String) definition.getAttribute("autoload");

    Vector children = definition.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement)children.get(i);
      if (child.getName().equals("layout")) {
        instantiateWithLayout(child);
      } else {
        if (child.getName().equals("tipi-instance") || child.getName().equals("component-instance")) {
          addAnyInstance(myContext, child, child.getAttribute("constraint"));
        } else {
          System.err.println("Ignoring element: "+child.getName());
        }
      }
    }
//    instantiateWithOutLayout(children);
  }

  private void instantiateWithLayout(XMLElement x)throws TipiException {
    TipiLayout tl = myContext.instantiateLayout(x);
        myLayout = tl;
        tl.createLayout(myContext, this, x, null);
        if (tl instanceof DefaultTipiLayout) {
          DefaultTipiLayout dtl = (DefaultTipiLayout)tl;
          if (getContainer()!=null) {
            getContainer().setLayout(dtl.getLayout());
          }
          Vector children = x.getChildren();
          for (int i = 0; i < children.size(); i++) {
            XMLElement current = (XMLElement)children.get(i);
            addAnyInstance(myContext,current,dtl.parseConstraint((String)current.getAttribute("constraint")));
          }

        }

  }

//  private void instantiateWithOutLayout(Vector children) throws TipiException {
//    for (int i = 0; i < children.size(); i++) {
//      XMLElement child = (XMLElement) children.elementAt(i);
//      if (child.getName().equals("tipi-instance") || child.getName().equals("component-instance")) {
//        addAnyInstance(myContext, child, null);
//      } else {
//        System.err.println("Ignoring element: "+child.getName());
//      }
//    }
// }

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
    }
    return super.getComponentValue(name);

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

//  public void performService(TipiContext context) throws TipiException {
//    if (myServices != null) {
//      for (int i = 0; i < myServices.size(); i++) {
//        performService(context, (String) myServices.get(i));
//      }
//    }
//  }

  public void performService(TipiContext context, String service) throws TipiException {
    if (myNavajo == null) {
      myNavajo = NavajoFactory.getInstance().createNavajo();
    }
    context.performTipiMethod(this, service);
  }

  public void performSyncService(TipiContext context, String service) throws TipiException {
    if (myNavajo == null) {
      myNavajo = NavajoFactory.getInstance().createNavajo();
    }
    context.performSyncTipiMethod(this, service);
  }


  public void loadData(Navajo n, TipiContext tc) throws TipiException {
//    System.err.println("Loading data into tipi: "+getPath());
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
      if(current.getServices().size() > 0){
//        System.err.println("SubTipi '" + current.getName() + "' is listenening to one or more service(s)");
      }
      current.loadData(n, tc);
    }
//    for (int i = 0; i < methodList.size(); i++) {
//      MethodComponent current = (MethodComponent) methodList.get(i);
//      current.loadData(n, tc);
//    }
    performTipiEvent("onLoad",null);
//    if (getContainer()!=null) {
//      getContainer().doLayout();
//    }

  }

  public LayoutManager getContainerLayout(){
   return getContainer().getLayout();
 }

  public boolean loadErrors(Navajo n){
    try {
      return performTipiEvent("onGeneratedErrors", null);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
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
    t.setConstraints(td);
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
  }

  public TipiLayout getLayout() {
    return myLayout;
  }

  public void clearProperties() {
    getContainer().removeAll();
    properties.clear();
  }

  public XMLElement store(){
    XMLElement IamThereforeIcanbeStored = super.store();
    IamThereforeIcanbeStored.setName("tipi-instance");
    return IamThereforeIcanbeStored;
  }


}