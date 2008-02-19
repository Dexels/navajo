package com.dexels.navajo.tipi.components.swingimpl.tree;

import java.util.*;

import javax.swing.tree.*;

import com.dexels.navajo.document.*;

public class MessageTreeNode implements TreeNode {

	private final Message myMessage;
	private final TreeNode myParent;
	private Vector<TreeNode> childList;

	public MessageTreeNode(Message m, TreeNode parent) {
		myParent = parent;
		myMessage = m;
		childList = new Vector<TreeNode>();
		for (Iterator<Message> iter = myMessage.getAllMessages().iterator(); iter.hasNext();) {
			Message element = iter.next();
			childList.add(new MessageTreeNode(element,this));
		}
	}

	public Message getMessage() {
		return myMessage;
	}
	public String toString() {
		return myMessage.getName();
	}
	public Enumeration<TreeNode> children() {
		return childList.elements();
	}

	public boolean getAllowsChildren() {
		return childList.size() > 0;
	}

	public TreeNode getChildAt(int i) {
		return childList.get(i);
	}

	public int getChildCount() {
		return childList.size();
	}

	public int getIndex(TreeNode tn) {
		return childList.indexOf(tn);
	}

	public TreeNode getParent() {
		return myParent;
	}

	public boolean isLeaf() {
		return !getAllowsChildren();
	}

}
