package com.dexels.navajo.tipi.studio.tree;

import javax.swing.tree.TreeNode;
import java.util.*;
import com.dexels.navajo.tipi.tipixml.*;
import javax.swing.ImageIcon;
import tipi.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyridght: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiComponentTreeNode implements TreeNode {
  private XMLElement myElement;
  private TipiComponentTreeNode myParent;
  private int myIndex;
  private ArrayList myChildren = new ArrayList();
  private ImageIcon myIcon;
  private ImageIcon mySelectedIcon;

  public TipiComponentTreeNode() {
  }

  public void setElement(XMLElement e){
    myElement = e;
  }

  public void setParent(TipiComponentTreeNode p){
    myParent = p;
  }

  public void addChild(TipiComponentTreeNode child){
     myChildren.add(child);
  }

  public TreeNode getChildAt(int childIndex) {
    return (TipiComponentTreeNode) myChildren.get(childIndex);
  }

  public int getChildCount() {
    if(myElement != null){
      return myElement.countChildren();
    }else{
      return 0;
    }
  }

  public TreeNode getParent() {
    return myParent;
  }

  public void setIndex(int index){
    myIndex = index;
  }

  public int getIndex(TreeNode node) {
    return myIndex;
  }

  public boolean getAllowsChildren() {
    return true;
  }

  public boolean isLeaf() {
    if(myElement.countChildren() == 0){
      return true;
    }else{
      return false;
    }
  }

  public Enumeration children() {
    return myElement.getChildren().elements();
  }

  public ImageIcon getIcon(){
    return myIcon;
  }

  public ImageIcon getSelectedIcon(){
    return mySelectedIcon;
  }

  public String toString(){
    String myName = myElement.getName();
    if(myName.equals("action")){
      myIcon = new ImageIcon(MainApplication.class.getResource("action.gif"));
      mySelectedIcon = new ImageIcon(MainApplication.class.getResource("action_selected.gif"));
      return myElement.getStringAttribute("type", myElement.getName());
    }
    if(myName.equals("event")){
      myIcon = new ImageIcon(MainApplication.class.getResource("event.gif"));
      mySelectedIcon = new ImageIcon(MainApplication.class.getResource("event_selected.gif"));
      return myElement.getStringAttribute("type", myElement.getName());
    }

    if(myName.equals("param")){
      myIcon = new ImageIcon(MainApplication.class.getResource("param.gif"));
      mySelectedIcon = new ImageIcon(MainApplication.class.getResource("param_selected.gif"));
      String displayString = myElement.getStringAttribute("name") + " : " + myElement.getStringAttribute("value");
      return displayString;
    }
    if(myName.equals("tipi-instance")){
      myIcon = new ImageIcon(MainApplication.class.getResource("container.gif"));
      mySelectedIcon = new ImageIcon(MainApplication.class.getResource("container_selected.gif"));
    }
    if(myName.equals("component-instance")){
      myIcon = new ImageIcon(MainApplication.class.getResource("component.gif"));
      mySelectedIcon = new ImageIcon(MainApplication.class.getResource("component_selected.gif"));
    }
    if(myName.equals("tid")){
      myIcon = new ImageIcon(MainApplication.class.getResource("root.gif"));
      mySelectedIcon = new ImageIcon(MainApplication.class.getResource("root_selected.gif"));
    }


    return myElement.getStringAttribute("id", myElement.getName());
  }

}