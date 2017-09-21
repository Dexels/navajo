/* Generated By:JJTree&JavaCC: Do not edit this line. ASTTodayNode.java */
package com.dexels.navajo.parser.compiled;


import java.util.Date;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;


public final class ASTTodayNode extends SimpleNode {
    public ASTTodayNode(int id) {
        super(id);
    }

	@Override
	public ContextExpression interpretToLambda() {
		return new ContextExpression() {

			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg,
					Selection parentSel, String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink, Access access) {
				return new Date();
			}

			@Override
			public boolean isLiteral() {
				return false;
			}
		};
	}
}
