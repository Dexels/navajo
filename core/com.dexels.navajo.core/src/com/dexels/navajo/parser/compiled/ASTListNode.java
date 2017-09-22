/* Generated By:JJTree&JavaCC: Do not edit this line. ASTListNode.java */

package com.dexels.navajo.parser.compiled;


import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public final class ASTListNode extends SimpleNode {

    int args = 0;

    public ASTListNode(int id) {
        super(id);
    }

	@Override
	public ContextExpression interpretToLambda() {
		final List<ContextExpression> exprs = new ArrayList<>();
		boolean onlyImmutable = true;
		for (int i = 0; i < jjtGetNumChildren(); i++) {
			ContextExpression lmb = jjtGetChild(i).interpretToLambda();
			exprs.add(lmb);
			if(!onlyImmutable && !lmb.isLiteral()) {
				onlyImmutable = false;
			}
		} 
		final boolean onlyImm = onlyImmutable;
		return new ContextExpression() {
			
			@Override
			public boolean isLiteral() {
				return onlyImm;
			}
			
			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink, Access access) throws TMLExpressionException {
				List<Object> result = new ArrayList<>();
				for (ContextExpression contextExpression : exprs) {
					result.add(contextExpression.apply(doc, parentMsg, parentParamMsg, parentSel, selectionOption, mapNode, tipiLink, access));
				}
				return result;
			}
		};
	}
}