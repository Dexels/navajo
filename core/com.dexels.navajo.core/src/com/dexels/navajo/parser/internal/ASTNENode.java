/* Generated By:JJTree&JavaCC: Do not edit this line. ASTNENode.java */

package com.dexels.navajo.parser.internal;

import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.Utils;

public final class ASTNENode extends SimpleNode {
	public ASTNENode(int id) {
		super(id);
	}

	@Override
	public final Object interpret() throws TMLExpressionException {
		// System.out.println("in ASTEQNode()");
		Object a = jjtGetChild(0).interpret();
		// System.out.println("Got first argument");
		Object b = jjtGetChild(1).interpret();

		// System.out.println("Got second argument");

		return Boolean.valueOf(!Utils.equals(a, b));

	}
}