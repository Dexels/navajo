package com.dexels.navajo.tipi.components.swingimpl.tree;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.dexels.navajo.document.Message;

public class MessageTreeNode implements TreeNode {

	private final Message myMessage;
	private final TreeNode myParent;
	private Vector<TreeNode> childList;

	public MessageTreeNode(Message m, TreeNode parent) {
		myParent = parent;
		myMessage = m;
		childList = new Vector<TreeNode>();
		for (Iterator<Message> iter = myMessage.getAllMessages().iterator(); iter
				.hasNext();) {
			Message element = iter.next();
			childList.add(new MessageTreeNode(element, this));
		}
	}

	public Message getMessage() {
		return myMessage;
	}

	@Override
	public String toString() {
		return myMessage.getName();
	}

	@Override
	public Enumeration<TreeNode> children() {
		return childList.elements();
	}

	@Override
	public boolean getAllowsChildren() {
		return childList.size() > 0;
	}

	@Override
	public TreeNode getChildAt(int i) {
		return childList.get(i);
	}

	@Override
	public int getChildCount() {
		return childList.size();
	}

	@Override
	public int getIndex(TreeNode tn) {
		return childList.indexOf(tn);
	}

	@Override
	public TreeNode getParent() {
		return myParent;
	}

	@Override
	public boolean isLeaf() {
		return !getAllowsChildren();
	}

}
