/* Generated By:JJTree&JavaCC: Do not edit this line. ASTOptionNode.java */
package com.dexels.navajo.parser.compiled;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public final class ASTOptionNode extends SimpleNode {

    String option = "";
    
	private final static Logger logger = LoggerFactory.getLogger(ASTOptionNode.class);


    public ASTOptionNode(int id) {
        super(id);
    }

	@Override
	public ContextExpression interpretToLambda(List<String> problems, String expression, Function<String, FunctionClassification> functionClassifier) {
		return new ContextExpression() {
			
			@Override
			public boolean isLiteral() {
				return false;
			}
			
			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) throws TMLExpressionException {
				return ASTOptionNode.this.option;
			}

			@Override
			public Optional<String> returnType() {
				logger.warn("Sketchy type resolution of option. Assuming string.");
				return Optional.of(Property.INTEGER_PROPERTY);
			}
			
			@Override
			public String expression() {
				return expression;
			}
		};
	}
}
