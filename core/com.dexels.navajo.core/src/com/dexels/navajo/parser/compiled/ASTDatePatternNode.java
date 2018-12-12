/* Generated By:JJTree&JavaCC: Do not edit this line. ASTDatePatternNode.java */
package com.dexels.navajo.parser.compiled;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.parser.DatePattern;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public final class ASTDatePatternNode extends SimpleNode {
    public ASTDatePatternNode(int id) {
        super(id);
    }

	@Override
	public ContextExpression interpretToLambda(List<String> problems, String expression, Function<String, FunctionClassification> functionClassifier) {
		ContextExpression y = jjtGetChild(0).interpretToLambda(problems,expression,functionClassifier);
		checkOrAdd("Year (item 0) field should be an integer",problems,y.returnType(),Property.INTEGER_PROPERTY);
		ContextExpression m = jjtGetChild(1).interpretToLambda(problems,expression,functionClassifier);
		checkOrAdd("Month (item 1) field should be an integer",problems,y.returnType(),Property.INTEGER_PROPERTY);
		ContextExpression d = jjtGetChild(2).interpretToLambda(problems,expression,functionClassifier);
		checkOrAdd("Day (item 2) field should be an integer",problems,y.returnType(),Property.INTEGER_PROPERTY);
		ContextExpression h = jjtGetChild(3).interpretToLambda(problems,expression,functionClassifier);
		checkOrAdd("Hour (item 3) field should be an integer",problems,y.returnType(),Property.INTEGER_PROPERTY);
		ContextExpression min = jjtGetChild(4).interpretToLambda(problems,expression,functionClassifier);
		checkOrAdd("Minute (item 4) field should be an integer",problems,y.returnType(),Property.INTEGER_PROPERTY);
		ContextExpression s = jjtGetChild(5).interpretToLambda(problems,expression,functionClassifier);
		checkOrAdd("Second (item 5) field should be an integer",problems,y.returnType(),Property.INTEGER_PROPERTY);
		final boolean isLiteral = y.isLiteral() && m.isLiteral() && d.isLiteral() && h.isLiteral() && h.isLiteral() && s.isLiteral();
		
		return new ContextExpression() {
			
			@Override
			public boolean isLiteral() {
				return isLiteral;
			}
			
			@Override
			public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
					 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) throws TMLExpressionException {
		        int yearT = ((Integer) y.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink, access, immutableMessage,paramMessage)).intValue();
		        int monthT = ((Integer)m.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink, access, immutableMessage,paramMessage)).intValue();
		        int dayT = ((Integer) d.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink, access, immutableMessage,paramMessage)).intValue();
		        int hourT = ((Integer) h.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink, access, immutableMessage,paramMessage)).intValue();
		        int minT = ((Integer) min.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink, access, immutableMessage,paramMessage)).intValue();
		        int secT = ((Integer) s.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink, access, immutableMessage,paramMessage)).intValue();
		        return new DatePattern(yearT, monthT, dayT, hourT, minT, secT, true);
			}

			@Override
			public Optional<String> returnType() {
				return Optional.of(Property.DATE_PATTERN_PROPERTY);
			}
			
			@Override
			public String expression() {
				return expression;
			}
		};
	}

}
