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
    extends TipiComponent
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

    setContainer(createContainer());
    String showMethodBar = (String) definition.getAttribute("methodbar");
    if ("true".equals(showMethodBar)) {
      throw new UnsupportedOperationException("No methodbar stuff yet.");
    }

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
//      System.err.println("LOOPING THROUGH CHILDREN: "+child.toString());
      if (child.getName().equals("layout")) {
        TipiLayout tl = context.instantiateLayout(child);
        tl.createLayout(context, this, child, null);
        myLayout = tl;
      }
      if (child.getName().equals("event")) {
        TipiEvent te = new TipiEvent();
        te.load(child, context);
        addTipiEvent(te);
      }
      if (child.getName().equals("tipi-instance")) {
        addTipiInstance(context, null, child);
//        Tipi t = (Tipi)context.instantiateClass(child);
//        addTipi(t,context,null,child);                            // Map is not passed through
      }

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

  public void addPropertyInstance(TipiContext context, XMLElement instance, Map columnAttributes) throws TipiException {
    BasePropertyComponent pc = new BasePropertyComponent();
    pc.addTipiEventListener(this);
    String propertyName = (String) instance.getAttribute("name");
    String lw = (String) columnAttributes.get("labelwidth");
    int label_width = 10;
    if (lw != null) {
      label_width = Integer.parseInt(lw);
    }
    pc.setLabelWidth(label_width);
    pc.load(instance, instance, context);
    addProperty(propertyName, pc, context, columnAttributes);
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

//  public void performTipiEvent(int type, String source){
//    System.err.println("----------> Performing: " + source + ", type: "+ type + "myEventList: " + myEventList.size());
//    for(int i=0;i<myEventList.size();i++){
//      TipiEvent te = (TipiEvent)myEventList.get(i);
//      if(te.getType() == type){
//        te.performAction(getNavajo(), getNavajo().getRootMessage().getPropertyByPath(source), getContext());
//      }
//    }
//  }

//  public void addTipiEvent(TipiEvent te) {
//    myEventList.add(te);
//  }

//  public Navajo getNavajo() {
//    return myNavajo;
//  }





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
    //System.err.println("PerformService n=" + n.toXml().toString());
    if (myNavajo == null) {
      myNavajo = new Navajo();
    }
    context.performTipiMethod(this, service);
  }

  public void loadData(Navajo n, TipiContext tc) throws TipiException {
    //System.err.println("LOADING NAVAJO:  "+n.toXml());
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
//    super.loadData(n, tc);
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
    performAllEvents(TipiEvent.TYPE_ONLOAD);
  }


  public Tipi addTipiInstance(TipiContext context, Map constraints, XMLElement inst) throws TipiException {
//    Tipi ti = (Tipi) (context.instantiateClass(inst));
    System.err.println("ADDDING TIPI INSTANCE:: ");
    Tipi ti = (Tipi)(context.instantiateComponent(inst));
//    ti.instantiateComponent();
//    addComponent(ti,context,constraints);
    System.err.println("NEARLY ADDING TIPI!");
    addTipi(ti, context, constraints, inst);
    return ti;
  }

  private void addTipi(Tipi t, TipiContext context, Map td, XMLElement definition) {
    System.err.println("Adding tipi!");
    if (t == null) {
      throw new NullPointerException("Holy cow!");
    }
    String id = t.getId();

    // This is actually USEFUL debug info!
    //System.err.println("Tipi added. My type: "+getClass()+" and my name: "+getName()+"my id: "+getId());
    //System.err.println("Tipi added. type: "+t.getClass()+" and name: "+t.getName()+" id: "+id );

    tipiList.add(t);
    tipiMap.put(id, t);
    String vis = (String) definition.getAttribute("visible", "true");
    String border = (String) definition.getAttribute("border", "false");
    if (JPanel.class.isInstance(t.getContainer()) && "true".equals(border)) {
      System.err.println("Creating border for: " + t.getName());
      ( (JPanel) t.getContainer()).setBorder(new EtchedBorder());
    }
    boolean visible;
    if (vis.equals("false")) {
      visible = false;
    }
    else {
      visible = true;
    }
    t.getContainer().setVisible(visible);

    System.err.println("Container: " + id);
    addComponent(t, context, td);
  }
//
//  public String getId() {
//    return myId;
//  }
//
//  public void setId(String id) {
//    myId = id;
//  }

  public Tipi getTipi(String name) {
    Tipi t = (Tipi) tipiMap.get(name);
    System.err.println("Getting tipi. My name: " + myName + " my id: " + myId + " looking for: " + name + " found? " + t == null);
    return t;
  }

//  public void addProperty(String parm1, TipiComponent parm2, TipiContext parm3, Map td) {
//    throw new RuntimeException("Can not add property to tipi!");
//  }
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