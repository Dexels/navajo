/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.lobobrowser.util;

import org.w3c.dom.*;

public class Nodes {
	public static Node getCommonAncestor(Node node1, Node node2) {
		if(node1 == null || node2 == null) {
			return null;
		}
		Node checkNode = node1;
		while(!isSameOrAncestorOf(checkNode, node2)) {
			checkNode = checkNode.getParentNode();
			if(checkNode == null) {
				return null;
			}
		}
		return checkNode;
	}
	
	public static boolean isSameOrAncestorOf(Node node, Node child) {
		if(child.isSameNode(node)) {
			return true;
		}
		Node parent = child.getParentNode();
		if(parent == null) {
			return false;
		}
		return isSameOrAncestorOf(node, parent);
	}
}
