package com.dexels.navajo.tipi.studio.tree;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeModelListener;
import java.util.*;
import com.dexels.navajo.tipi.tipixml.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiComponentTreeModel implements TreeModel {
  private XMLElement myElement;
  private TipiComponentTreeNode root;
  private ArrayList myListeners = new ArrayList();

  public TipiComponentTreeModel() {
  }

  public TipiComponentTreeModel(XMLElement e) {
    myElement = e;
    root = new TipiComponentTreeNode();
    root.setElement(myElement);
    parseTree(myElement, root);
  }

  public void setElement(XMLElement e){
    myElement = e;
    root = new TipiComponentTreeNode();
    root.setElement(myElement);
    parseTree(myElement, root);
  }

  private void parseTree(XMLElement e, TipiComponentTreeNode parent){
    //System.err.println("Parsing tree");
    Vector kids = e.getChildren();
    int child_index = 0;
    for(int i=0;i<kids.size();i++){
      XMLElement kid = (XMLElement)kids.elementAt(i);
      String kidsName = kid.getName();
      if(!kidsName.equals("td") && !kidsName.equals("tr") && !kidsName.equals("layout")){
        TipiComponentTreeNode node = new TipiComponentTreeNode();
        node.setElement(kid);
        node.setParent(parent);
        node.setIndex(child_index);
        parent.addChild(node);
        child_index++;
        parseTree(kid, node);
      }else{
        parseTree(kid, parent);
      }
    }
  }

  public Object getRoot() {
    return root;
  }
  public Object getChild(Object parent, int index) {
    TipiComponentTreeNode n = (TipiComponentTreeNode)parent;
    return n.getChildAt(index);
  }
  public int getChildCount(Object parent) {
    TipiComponentTreeNode n = (TipiComponentTreeNode)parent;
    return n.getChildCount();
  }
  public boolean isLeaf(Object node) {
    TipiComponentTreeNode n = (TipiComponentTreeNode)node;
    return n.isLeaf();
  }
  public void valueForPathChanged(TreePath path, Object newValue) {
    System.err.println("valueForPathChanged called!");
  }
  public int getIndexOfChild(Object parent, Object child) {
    TipiComponentTreeNode n = (TipiComponentTreeNode)parent;
    return n.getIndex((TipiComponentTreeNode)child);
  }
  public void addTreeModelListener(TreeModelListener l) {
    myListeners.add(l);
  }
  public void removeTreeModelListener(TreeModelListener l) {
    myListeners.remove(l);
  }

}