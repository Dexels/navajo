package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import com.dexels.navajo.document.*;
import java.util.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */
public class MessageTreeNode
    implements TreeNode {
  private final Message myMessage;
  private final String myLabelProp;
  private final ArrayList myChildren = new ArrayList();
  private final MessageTreeNode myParent;
  public MessageTreeNode(Message m, String labelProperty,
                         MessageTreeNode parent) {
    System.err.println("Creating node with messagename: "+m.getName()+" and type: "+m.getType());
    myMessage = m;
    myLabelProp = labelProperty;
    myParent = parent;
  }

  public String getNextMessageName(String currentName) {
    if (currentName.equals("QuestionList")) {
      return "Group";
    }
    if (currentName.equals("Group")) {
      return "Question";
    }

   return "Question";
  }
  /**
  * Constructor for the root node
  */


  public MessageTreeNode(Message m, String labelProperty) {
    System.err.println("Creating node with messagename: "+m.getName()+" and type: "+m.getType());
    myMessage = m;
    myLabelProp = labelProperty;
    myParent = null;

//    Message questionMessage = m.getMessage(getNextMessageName(m.getName()));
    if (m==null) {
      return;
    }
    System.err.println("ArrayList: " + m.getArraySize());
    for (int i = 0; i < m.getArraySize(); i++) {
      Message current = m.getMessage(i);
//      Message child = m.getMessage(myMessage.getName());
      System.err.println("CREATING TREENODE, index: " + i + " i am: " +
                         toString());
      MessageTreeNode mtn = new MessageTreeNode(current, myLabelProp, this);
      myChildren.add(mtn);
      mtn.build();
    }
  }

  public Message getMessage() {
    return myMessage;
  }

  public void build() {
      Message children = myMessage.getMessage(getNextMessageName(myMessage.getName()));
//    Message children = myMessage.getMessage("Question");
    if (children == null) {
      return;
    }
    for (int i = 0; i < children.getArraySize(); i++) {
      Message m = children.getMessage(i);
      System.err.println("looping. Index: " + i);
      System.err.println("CREATING TREENODE, index: " + i + " i am: " +
                         toString());
      MessageTreeNode mtn = new MessageTreeNode(m, myLabelProp, this);
      myChildren.add(mtn);
      mtn.build();
    }
  }

  public TreeNode getChildAt(int childIndex) {
    return (TreeNode) myChildren.get(childIndex);
  }

  public int getChildCount() {
    return myChildren.size();
  }

  public TreeNode getParent() {
    return myParent;
  }

  public int getIndex(TreeNode node) {
    return myChildren.indexOf(node);
  }

  public boolean getAllowsChildren() {
    return true;
  }

  public boolean isLeaf() {
    return myChildren.isEmpty();
  }

  public Enumeration children() {
    return new Vector(myChildren).elements();
  }

  @Override
public String toString() {
    if (myMessage == null) {
      return "..";
    }
    if (myMessage.getName().equals("Group")) {
      if (myMessage.isArrayMessage()) {
        return "root";
      }
      else {
        if (myMessage.getMessage("Name")!=null) {
          return myMessage.getProperty("Name").getValue();
        }
      }
    }

    Property p = myMessage.getProperty(myLabelProp);
    if (p != null) {
      return p.getValue();
    }
    else {
      return "-";
    }
  }
}
