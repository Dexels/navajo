package com.dexels.navajo.tipi.components.swingimpl.tree;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public class NavajoTreeNode implements TreeNode {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoTreeNode.class);
	private final Navajo myNavajo;
	private Vector<TreeNode> childList;

	public NavajoTreeNode(Navajo m) throws NavajoException {
		myNavajo = m;
		childList = new Vector<TreeNode>();
		for (Iterator<Message> iter = myNavajo.getAllMessages().iterator(); iter
				.hasNext();) {
			Message element = iter.next();
			logger.debug("ELEMENT: " + element.getName());
			childList.add(new MessageTreeNode(element, this));
		}
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
		return null;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

}
