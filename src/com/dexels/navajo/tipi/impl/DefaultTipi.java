package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
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

public class DefaultTipi extends DefaultTipiContainer implements Tipi{

  private String myService = "";
  private Navajo myNavajo = null;
  private ArrayList tipiList = new ArrayList();
  private ArrayList methodList = new ArrayList();
  private Map tipiMap = new HashMap();
  private String myId = null;
  private TipiLayout myLayout = null;
  private DefaultMethodToolBar myToolbar = null;
  protected ArrayList myEventList = new ArrayList();
  public DefaultTipi() {
  }

  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
    setContext(context);
//    boolean isDefault = false;
//    XMLElement defaultElm = null;
    Container c ;
//    TipiPanel myPanel = new TipiPanel();
//
    String type = (String)definition.getAttribute("type");
    String b = (String) instance.getAttribute("border");
    String scrollable = (String) instance.getAttribute("scrollable");
    if ("desktop".equals(type)) {
       c = new JDesktopPane();
    } else {
       c = new TipiPanel();
       if("true".equals(scrollable)){
         ((TipiPanel)c).setScrollable(true);
       }
       if(b != null && b.equals("true")){
         ((TipiPanel)c).addBorder();
      }
    }

    String showMethodBar = (String)definition.getAttribute("methodbar");
    if ("true".equals(showMethodBar)) {
      TipiPanel outer = new TipiPanel();
      outer.setLayout(new BorderLayout());
      if (c==null) {
        outer.add(c,BorderLayout.CENTER);
      } else {
        outer.add(c,BorderLayout.CENTER);
      }

      setOuterContainer(outer);
      outer.setBackground(Color.green);
      myToolbar = new DefaultMethodToolBar();
      outer.add(myToolbar,BorderLayout.SOUTH);
//      myToolbar.setBackground(Color.red);
//      myToolbar.revalidate();
//      myToolbar.load(this);
    }
    //c.setBackground(Color.red);
    setContainer(c);
    System.err.println("----------> DefaultTipi load called calling parent and then looping through children");
    super.load(definition,instance,context);
    myService = (String)definition.getAttribute("service");
//    String tipiMethod = (String) elm.getAttribute("service");
    if (myService!=null) {
      context.addTipiInstance(myService,this);
    }
    Vector children = definition.getChildren();
    for (int i = 0; i < children.size(); i++) {
      XMLElement child = (XMLElement) children.elementAt(i);
      System.err.println("LOOPING THROUGH CHILDREN: "+child.toString());
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
      if (child.getName().equals("tipi-instance") && !type.equals("tabbed")) {
        Tipi t = (Tipi)context.instantiateClass(this,child);
        addTipi(t,context,null,child);
      }


    }
    String autoLoad = (String)definition.getAttribute("autoload");
    if (autoLoad!=null && "true".equals(autoLoad)) {
      performService(context);
    }
  }


  public void addTipiEvent(TipiEvent te) {
    myEventList.add(te);
  }

  public Navajo getNavajo() {
    return myNavajo;
  }

  public String getName() {
    return myName;
  }

  public Tipi getTipi(int i) {
    return (Tipi)tipiList.get(i);
  }
  public String getService() {
    return myService;
  }

  public void addMethod(MethodComponent m) {
    methodList.add(m);
  }
  public void performService(TipiContext context) throws TipiException{
    performService(context,myService);
  }

  public void performService(TipiContext context, String service) throws TipiException {
    if (myNavajo==null) {
      myNavajo = new Navajo();
    }
    context.performTipiMethod(this,service);
  }


  public void loadData(Navajo n, TipiContext tc) throws TipiException {
//    System.err.println("LOADING NAVAJO:  "+n.toXml());
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
  public void performEvent(TipiEvent te) {
    System.err.println("PERFORMING EVENT!\n\n");
    te.performAction(getNavajo(),te.getSource(),getContext());
  }

  public void performAllEvents(int type) {
    System.err.println("LOADING ALL EVENTS...");
    for (int i = 0; i < myEventList.size(); i++) {
      TipiEvent te = (TipiEvent)myEventList.get(i);
      System.err.println("::: Examining event of type: "+te.getType()+" looking for: "+type);
      if (te.getType()==type) {
        performEvent(te);
      }
    }
  }

  public void addTipi(Tipi t, TipiContext context, Map td, XMLElement definition) {
    if (t==null) {
      throw new NullPointerException("HOly cow!");
    }
    String id = t.getId();
    System.err.println("Tipi added. My type: "+getClass()+" and my name: "+getName()+"my id: "+getId());
    System.err.println("Tipi added. type: "+t.getClass()+" and name: "+t.getName()+" id: "+id );
    tipiList.add(t);
    tipiMap.put(id,t);
    String vis = (String)definition.getAttribute("visible", "true");
    boolean visible;
    if(vis.equals("false")){
      visible = false;
    }else{
      visible = true;
    }
    t.getContainer().setVisible(visible);

    System.err.println("Container: " + t.getName() + " , visible: " + visible);
//    addComponent(t, context, td);
  }


  public String getId() {
    return myId;
  }

  public void setId(String id) {
    myId = id;
  }

  public Tipi getTipi(String name) {
    return (Tipi)tipiMap.get(name);
  }
//  public TipiContainer getTipiContainer(String name) {
//    return (TipiContainer)containerMap.get(name);
//  }
  public void addProperty(String parm1, TipiComponent parm2, TipiContext parm3, Map td) {
    throw new RuntimeException("Can not add property to tipi!");
  }
  public int getTipiCount() {
    return tipiList.size();
  }

  public Tipi getTipiByPath(String path) {
    System.err.println("Looking in: "+getClass()+" my name is: "+getName());
    System.err.println("getTipiByPath (Screen: ): "+path);
    int s = path.indexOf("/");
    if (s==-1) {
      return getTipi(path);
    }
    if (s==0) {
      return getTipiByPath(path.substring(1));
    }
    String name = path.substring(0,s);
    String rest = path.substring(s);
    System.err.println("Name: "+name);
    System.err.println("Rest: "+rest);
    Tipi t = getTipi(name);
    if (t==null) {
      throw new NullPointerException("Did not find Tipi: "+name+" list: "+tipiList);
//      return null;
    }
    return t.getTipiByPath(rest);
  }

  public TipiLayout getLayout() {
    return myLayout;
  }

}