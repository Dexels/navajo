/* Generated By:JJTree&JavaCC: Do not edit this line. ASTTipiNode.java */

package com.dexels.navajo.parser.compiled;

import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.api.ContextExpression;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public final class ASTTipiNode extends SimpleNode {

  String val = "";
//  TipiLink tipiLink;

  public ASTTipiNode(int id) {
    super(id);
  }

@Override
public ContextExpression interpretToLambda(List<String> problems, String expression) {
	return new ContextExpression() {
		
		@Override
		public boolean isLiteral() {
			return false;
		}
		
		@Override
		public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
				 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) throws TMLExpressionException {
		      try {
				return tipiLink.evaluateExpression(val);
			} catch (Exception e) {
				throw new TMLExpressionException("Error evaluating tipiLink: "+val, e);
			}
		}

		@Override
		public Optional<String> returnType() {
			return Optional.empty();
		}
		
		@Override
		public String expression() {
			return expression;
		}
	};
}

}
