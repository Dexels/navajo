package com.dexels.navajo.parser.compiled.api;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public class CachedExpressionEvaluator extends DefaultExpressionEvaluator implements ExpressionEvaluator {
    private static final Logger logger = LoggerFactory.getLogger(CachedExpressionEvaluator.class);

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage)
			throws NavajoException {
		ExpressionCache ce = ExpressionCache.getInstance();
		
		Object val;
		String type;
		try {
			val = ce.evaluate(clause, inMessage, parent, null, null, (MappableTreeNode)mappableTreeNode, null,null,immutableMessage,paramMessage);
			type = MappingUtils.determineNavajoType(val);
			return new Operand(val, type, "");
		} catch (TMLExpressionException e) {
		    if (inMessage != null) {
                // Only log if we have useful context
                logger.error("TML parsing issue with expression: {} exception", clause, e );
            }
            throw new TMLExpressionException("TML parsing issue");
		}
	}

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Object mappableTreeNode, Message parent,
			Message currentParam, Selection selection, Object tipiLink, Map<String,Object> params, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) throws NavajoException {
		try {
			ExpressionCache ce = ExpressionCache.getInstance();
			Access access = params == null? null : (Access)params.get(Expression.ACCESS);
			Operand val =ce.evaluate(clause, inMessage, parent, currentParam, selection, (MappableTreeNode)mappableTreeNode, (TipiLink) tipiLink, access,immutableMessage,paramMessage);
//			String type = MappingUtils.determineNavajoType(val);
			if(val==null) {
				throw new TMLExpressionException("Clause resolved to null, shouldnt happen:  expression: "+clause);
			}
			return val;
		} catch (TMLExpressionException e) {
		    if (inMessage != null) {
		        // Only log if we have useful context
		        logger.error("TML parsing issue with expression: {} exception", clause, e );
		    }
			throw new TMLExpressionException(e.getMessage(), e);
		}
	}

	@Override
	public Operand evaluate(String clause, Navajo inMessage, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) throws NavajoException {
		try {
			ExpressionCache ce = ExpressionCache.getInstance();
			return ce.evaluate(clause, inMessage, null, null, null, null, null, null, immutableMessage,paramMessage);
		} catch (TMLExpressionException e) {
		    if (inMessage != null) {
                // Only log if we have useful context
                logger.error("TML parsing issue with expression: {} exception", clause, e );
            }
            throw new TMLExpressionException("TML parsing issue");
		}
	}

}
