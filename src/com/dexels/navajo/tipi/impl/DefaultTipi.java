package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class DefaultTipi extends DefaultTipiContainer implements Tipi, TipiEventListener{

//  private String myService = "";
  //private Navajo myNavajo = null;
  private ArrayList tipiList = new ArrayList();
  private ArrayList methodList = new ArrayList();
  private Map tipiMap = new HashMap();
  private String myId = null;
  private TipiLayout myLayout = null;
  private DefaultMethodToolBar myToolbar = null;
  private ArrayList myServices = null;

  public DefaultTipi() {
  }

  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
    setContext(context);
    Container c ;
    String myService;
    String type = (String)definition.getAttribute("type");

    setContainer(createContainer());
    String showMethodBar = (String)definition.getAttribute("methodbar");
    if ("true".equals(showMethodBar)) {
      throw new UnsupportedOperationException("No methodbar stuff yet.");
    }
    super.load(definition,instance,context);
    myService = (String)definition.getAttribute("service");
    myServices = new ArrayList();
    if (myService!=null) {
      if(myService.indexOf(';')>=0) {
        StringTokenizer st = new StringTokenizer(myService,";");
        while (st.hasMoreTokens()) {
          String t = st.nextToken();
          myServices.add(t);
          context.addTipiInstance(t,this);
        }
      } else {
        myServices.add(myService);
        context.addTipiInstance(myService,this);
      }
    }
    Vector children = definition.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
//      System.err.println("LOOPING THROUGH CHILDREN: "+child.toString());
      if (child.getName().equals("layout")) {
        TipiLayout tl = context.instantiateLayout(child);
        tl.createLayout(context,this,child,null);
        myLayout = tl;
      }
      if (child.getName().equals("event")) {
        TipiEvent te = new TipiEvent();
        te.load(child,context);
        addTipiEvent(te);
      }
      if (child.getName().equals("tipi-instance")) {
        Tipi t = (Tipi)context.instantiateClass(this,child);
        addTipi(t,context,null,child);                            // Map is not passed through
      }


    }
    String autoLoad = (String)definition.getAttribute("autoload");
    if (autoLoad!=null) {
      performServiceList(autoLoad,context);
    }
  }

  public void performServiceList(String list, TipiContext context) throws TipiException {
    if (list.indexOf(";")<0) {
      performService(new Navajo(), context, list);
      return;
    }
    StringTokenizer st = new StringTokenizer(list,";");
    while(st.hasMoreTokens()) {
      performService(new Navajo(),context,st.nextToken());
    }
  }

  public void setContainerLayout(LayoutManager layout){

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

  public String getName() {
    return myName;
  }

  public Tipi getTipi(int i) {
    return (Tipi)tipiList.get(i);
  }
  public ArrayList getServices() {
    return myServices;
  }

  public void addMethod(MethodComponent m) {
    methodList.add(m);
  }
  public void performService(TipiContext context) throws TipiException{
    if (myServices!=null) {
      for (int i = 0; i < myServices.size(); i++) {
        performService(new Navajo(), context,(String)myServices.get(i));
      }
    }
  }

  public void performService(Navajo n, TipiContext context, String service) throws TipiException {
    myNavajo = n;
    System.err.println("PerformService n=" + n.toXml().toString());
    if (myNavajo==null) {
      myNavajo = new Navajo();
    }
    context.performTipiMethod(this,service);
  }


  public void loadData(Navajo n, TipiContext tc) throws TipiException {
   //System.err.println("LOADING NAVAJO:  "+n.toXml());
    super.loadData(n,tc);
    if (n==null) {
      System.err.println("NULL NAVAJO!");
      return;
    }

    myNavajo = n;
    if (getLayout()!=null) {
      if (getLayout().needReCreate()) {
        getLayout().reCreateLayout(tc,this,n);
      }
    }

    if (myToolbar!=null) {
      myToolbar.load(this,n,tc);
    }

    for (int i = 0; i < getTipiCount(); i++) {
      Tipi current = getTipi(i);
      current.loadData(n,tc);
    }
    for (int i = 0; i < methodList.size(); i++) {
      MethodComponent current = (MethodComponent)methodList.get(i);
      current.loadData(n,tc);
    }
    performAllEvents(TipiEvent.TYPE_ONLOAD);
  }

//  public void addComponent(TipiComponent c, TipiContext context, Map td){
//      getContainer().add(c.getContainer(), td);
//  }
//  public void performEvent(TipiEvent te) {
//    System.err.println("PERFORMING EVENT!\n\n");
//    te.performAction(getNavajo(),te.getSource(),getContext());
//  }
//
//  public void performAllEvents(int type) {
//    System.err.println("LOADING ALL EVENTS...");
//    for (int i = 0; i < myEventList.size(); i++) {
//      TipiEvent te = (TipiEvent)myEventList.get(i);
//      System.err.println("::: Examining event of type: "+te.getType()+" looking for: "+type);
//      if (te.getType()==type) {
//        performEvent(te);
//      }
//    }
//  }

  public void addTipi(Tipi t, TipiContext context, Map td, XMLElement definition) {
    if (t==null) {
      throw new NullPointerException("HOly cow!");
    }
    String id = t.getId();

    // This is actually USEFUL debug info!
    //System.err.println("Tipi added. My type: "+getClass()+" and my name: "+getName()+"my id: "+getId());
    //System.err.println("Tipi added. type: "+t.getClass()+" and name: "+t.getName()+" id: "+id );

    tipiList.add(t);
    tipiMap.put(id,t);
    String vis = (String)definition.getAttribute("visible", "true");
    String border = (String)definition.getAttribute("border", "false");
    if(JPanel.class.isInstance(t.getContainer()) && "true".equals(border)){
      System.err.println("Creating border for: " + t.getName());
      ((JPanel)t.getContainer()).setBorder(new EtchedBorder());
    }
    boolean visible;
    if(vis.equals("false")){
      visible = false;
    }else{
      visible = true;
    }
    t.getContainer().setVisible(visible);

//    System.err.println("Container: " + t.getName() + " , visible: " + visible);
    addComponent(t, context, td);
  }


  public String getId() {
    return myId;
  }

  public void setId(String id) {
    myId = id;
  }

  public Tipi getTipi(String name) {
    System.err.println("Getting tipi: " + name);
    System.err.println("CAST: " + tipiMap.get(name));
    System.err.println("AFTER: " + (tipiMap.get(name) instanceof Tipi));
    return (Tipi)tipiMap.get(name);
  }

  public void addProperty(String parm1, TipiComponent parm2, TipiContext parm3, Map td) {
    throw new RuntimeException("Can not add property to tipi!");
  }
  public int getTipiCount() {
    return tipiList.size();
  }

  public Tipi getTipiByPath(String path) {
    System.err.println("IN GETTIPIBYPATH(), I AM : " + this);
    System.err.println("Looking in: "+getClass()+" my name is: "+getName());
    System.err.println("getTipiByPath (Screen: ): "+path);

    if (path.indexOf("/") == 0)
      path = path.substring(1);

    int s = path.indexOf("/");
    if (s==-1) {
      System.err.println("Returning getTipi(" +path+")");
      if (path.equals("result")) {
        System.out.println("I am here");
      }
      return getTipi(path);
    } else {
      String name = path.substring(0, s);
      String rest = path.substring(s);
      System.err.println("Name: " + name);
      System.err.println("Rest: " + rest);
      Tipi t = getTipi(name);
      System.err.println("Tipi: " + t);
      System.err.println("Tipi name: " + t.getName());
      if (t == null) {
        throw new NullPointerException("Did not find Tipi: " + name + " list: " + tipiList);
//      return null;
      }
      return t.getTipiByPath(rest);
    }
  }

  public TipiLayout getLayout() {
    return myLayout;
  }

  public void clearProperties(){
    getContainer().removeAll();
    properties.clear();
  }

}