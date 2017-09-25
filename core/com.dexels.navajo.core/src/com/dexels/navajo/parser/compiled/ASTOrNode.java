/* Generated By:JJTree&JavaCC: Do not edit this line. ASTOrNode.java */
package com.dexels.navajo.parser.compiled;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.api.ContextExpression;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public final class ASTOrNode extends SimpleNode {
    public ASTOrNode(int id) {
        super(id);
    }
	@Override
	public ContextExpression interpretToLambda() {
		ContextExpression expA = jjtGetChild(0).interpretToLambda();
		ContextExpression expB = jjtGetChild(1).interpretToLambda();
		return new ContextExpression() {
			
			@Override
			public boolean isLiteral() {
				return expA.isLiteral() && expB.isLiteral();
			}
			
			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink, Access access) throws TMLExpressionException {
		        Object a = expA.apply(doc, parentMsg, parentParamMsg, parentSel, selectionOption, mapNode,tipiLink,access);
		        Boolean ba = (Boolean) a;
		        if(a==null) {
		        		ba = Boolean.FALSE;
		        }
		        if (ba.booleanValue())
		            return Boolean.TRUE;

		        Object b = expB.apply(doc, parentMsg, parentParamMsg, parentSel, selectionOption, mapNode,tipiLink,access);
		        Boolean bb = (Boolean) b;
		        if(b==null) {
		        		b = Boolean.FALSE;
		        }

		        return Boolean.valueOf(ba.booleanValue() || bb.booleanValue());
			}
		};
    }
}
