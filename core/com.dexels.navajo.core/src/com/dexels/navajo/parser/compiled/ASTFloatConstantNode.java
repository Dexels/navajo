/* Generated By:JJTree&JavaCC: Do not edit this line. ASTFloatConstantNode.java */
package com.dexels.navajo.parser.compiled;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public final class ASTFloatConstantNode extends SimpleNode {

    double val;

    public ASTFloatConstantNode(int id) {
        super(id);
    }

	@Override
	public ContextExpression interpretToLambda() {
		return new ContextExpression() {
			
			@Override
			public boolean isLiteral() {
				return true;
			}
			
			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					String option, String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink) {
		        return new Double(val);
			}
		};
	}

}
