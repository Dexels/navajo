/* Generated By:JJTree&JavaCC: Do not edit this line. ASTOptionNode.java */
package com.dexels.navajo.parser.compiled;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.api.ContextExpression;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public final class ASTOptionNode extends SimpleNode {

    String option = "";

    public ASTOptionNode(int id) {
        super(id);
    }

	@Override
	public ContextExpression interpretToLambda() {
		return new ContextExpression() {
			
			@Override
			public boolean isLiteral() {
				return false;
			}
			
			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink, Access access) throws TMLExpressionException {
				return ASTOptionNode.this.option;
			}
		};
	}
}
