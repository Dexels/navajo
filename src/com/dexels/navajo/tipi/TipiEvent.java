package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.tipixml.*;
import java.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.impl.*;
import javax.swing.tree.TreeNode;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiEvent implements TreeNode {

  private String myEventName;
  private String myEventService;
  private String mySource;
  private ArrayList myActions;
  private Navajo myNavajo;
  private TipiComponent myComponent;

  public TipiEvent() {
  }

  public void load(TipiComponent tc, XMLElement elm, TipiContext context) throws TipiException{
    myComponent = tc;
    myActions = new ArrayList();
    if (elm.getName().equals("event")) {
      String stringType = (String) elm.getAttribute("type");
      myEventName = stringType;
      myEventService = (String) elm.getAttribute("service");
      mySource = (String) elm.getAttribute("listen");
      //myCondition = (String) elm.getAttribute("condition");
      Vector temp = elm.getChildren();
      parseActions(temp, context, null);
    }
  }

  private void parseActions(Vector v, TipiContext context, TipiCondition c){
    try{
      for (int i = 0; i < v.size(); i++) {
        XMLElement current = (XMLElement) v.elementAt(i);
        if (current.getName().equals("action")) {
          TipiAction action = context.instantiateTipiAction(current, myComponent, this);
          action.setCondition(c);
          myActions.add(action);
        }
        if (current.getName().equals("condition")) {
          TipiCondition con = context.instantiateTipiCondition(current, myComponent, this);
          parseActions(current.getChildren(), context, con);
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }

  }

  public void appendAction(TipiAction a){
    myActions.add(a);
  }

  public void removeAction(TipiAction a){
    myActions.remove(a);
  }

  public void moveActionUp(TipiAction action){
    int index_old = myActions.indexOf(action);
    if(index_old > 0){
      myActions.remove(action);
      myActions.add(index_old - 1, action);
    }
  }

  public void moveActionDown(TipiAction action){
    int index_old = myActions.indexOf(action);
    if(index_old < myActions.size()-1){
      myActions.remove(action);
      myActions.add(index_old + 1, action);
    }
  }


  public void performAction(Object source, TipiContext context, Object event) throws TipiException {
    if (source!=null) {
//      System.err.println("Performing event. Source: "+source.toString()+" class: "+source.getClass());
    } else {
//      System.err.println("Performing event. Called with null source!");
      Thread.currentThread().dumpStack();
    }

        for (int i = 0; i < myActions.size(); i++) {
          TipiAction current = (TipiAction) myActions.get(i);
//          System.err.println("PERFORMING: "+current.store());
          try {
            current.executeAction();
          }
          catch (TipiBreakException ex) {
            System.err.println("Break encountered!");
            return;
          }
    }
  }

  public int getActionCount() {
    return myActions.size();
  }

  public TipiAction getAction(int index) {
    return (TipiAction)myActions.get(index);
  }

  public boolean isTrigger(String name, String service) {
    //System.err.println(">>>>> Checking for TRIGGER: " + name + " service_compare: " + service + "?=" + myEventService);
    if(name != null){
      if(service == null || myEventService == null || myEventService.equals("")){
        return name.equals(myEventName);
      }else{
        return (service.equals(myEventService)  && name.equals(myEventName));
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

  public XMLElement store(){
//    throw new RuntimeException("Todo: check and reimplement");
    XMLElement s = new CaseSensitiveXMLElement();
    s.setName("event");
    s.setAttribute("type", myEventName);
    for(int i=0;i<myActions.size();i++){
      TipiAction current = (TipiAction)myActions.get(i);
      TipiCondition tc = current.getCondition();
      if(tc != null){
        XMLElement condition = tc.store();
        condition.addChild(current.store());
        s.addChild(condition);
      }else{
        s.addChild(current.store());
      }
    }
    return s;
  }
  public TreeNode getChildAt(int index) {
    return (TreeNode)myActions.get(index);
  }
  public int getChildCount() {
    return myActions.size();
  }
  public TreeNode getParent() {
    return myComponent;
  }
  public int getIndex(TreeNode kiddo) {
    return myActions.indexOf(kiddo);
  }
  public boolean getAllowsChildren() {
    return true;
  }
  public boolean isLeaf() {
    return false;
  }
  public Enumeration children() {
    Vector victor = new Vector(myActions);
    return victor.elements();
  }

}