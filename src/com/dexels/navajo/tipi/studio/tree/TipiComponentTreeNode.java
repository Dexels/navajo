package com.dexels.navajo.tipi.studio.tree;

import javax.swing.tree.TreeNode;
import java.util.*;
import nanoxml.*;
import com.dexels.navajo.document.nanoimpl.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiComponentTreeNode implements TreeNode {
  private XMLElement myElement;
  private TipiComponentTreeNode myParent;
  private int myIndex;
  private ArrayList myChildren = new ArrayList();

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

  public String toString(){
    return myElement.getName();
  }

}