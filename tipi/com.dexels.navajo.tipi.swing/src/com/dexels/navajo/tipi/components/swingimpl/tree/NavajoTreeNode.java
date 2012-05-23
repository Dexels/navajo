package com.dexels.navajo.tipi.components.swingimpl.tree;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public class NavajoTreeNode implements TreeNode {

	private final Navajo myNavajo;
	private Vector<TreeNode> childList;

	public NavajoTreeNode(Navajo m) throws NavajoException {
		myNavajo = m;
		childList = new Vector<TreeNode>();
		for (Iterator<Message> iter = myNavajo.getAllMessages().iterator(); iter
				.hasNext();) {
			Message element = iter.next();
			System.err.println("ELEMENT: " + element.getName());
			childList.add(new MessageTreeNode(element, this));
		}
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
		return null;
	}

	public boolean isLeaf() {
		return false;
	}

}
