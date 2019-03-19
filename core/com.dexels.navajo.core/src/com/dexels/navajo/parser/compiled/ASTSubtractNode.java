/* Generated By:JJTree&JavaCC: Do not edit this line. ASTSubtractNode.java */
package com.dexels.navajo.parser.compiled;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.parser.Utils;

@SuppressWarnings({"unchecked","rawtypes"})
public final class ASTSubtractNode extends SimpleNode {
    ASTSubtractNode(int id) {
        super(id);
    }
    
	@Override
	public ContextExpression interpretToLambda(List<String> problems, String expression, Function<String, FunctionClassification> functionClassifier) {
		return lazyBiFunction(problems,expression,(a,b)->interpret(a, b),equalOrEmptyTypesOrDateWithDatePattern(),(a,b)->Optional.empty(),functionClassifier);
	}
	
    private BiFunction<Optional<String>, Optional<String>, Boolean> equalOrEmptyTypesOrDateWithDatePattern() {
    		return (a,b)->{
    			boolean res = equalOrEmptyTypes().apply(a, b);
    			if(res) {
    				return true;
    			}
    			if(a.isPresent() && b.isPresent()) {
    				if(a.get().equals(Property.DATE_PROPERTY) && b.get().equals(Property.DATE_PATTERN_PROPERTY)) {
    					return true;
    				}
    			}
    			return res;
    		};
    }


	private final Operand interpret(Operand ao, Operand bo) {
		Object a = ao.value;
		Object b = bo.value;
        if (!(a instanceof ArrayList || b instanceof ArrayList)) {
            return Operand.ofDynamic(Utils.subtract(a, b));
        } else if ((a instanceof ArrayList) && !(b instanceof ArrayList)) {
            ArrayList list = (ArrayList) a;
            ArrayList result = new ArrayList();

            for (int i = 0; i < list.size(); i++) {
                Object val = list.get(i);
                Object rel = Utils.subtract(val, b);

                result.add(rel);
            }
            return Operand.ofList(result);
        } else if ((b instanceof ArrayList) && !(a instanceof ArrayList)) {
            ArrayList list = (ArrayList) b;
            ArrayList result = new ArrayList();

            for (int i = 0; i < list.size(); i++) {
                Object val = list.get(i);
                Object rel = Utils.subtract(a, val);

                result.add(rel);
            }
            return Operand.ofList(result);
        } else if (a instanceof ArrayList && b instanceof ArrayList) {
            ArrayList list1 = (ArrayList) a;
            ArrayList list2 = (ArrayList) b;

            if (list1.size() != list2.size())
                throw new TMLExpressionException("Can only add lists of equals length");
            ArrayList result = new ArrayList();

            for (int i = 0; i < list1.size(); i++) {
                Object val1 = list1.get(i);
                Object val2 = list2.get(i);
                Object rel = Utils.subtract(val1, val2);

                result.add(rel);
            }
            return Operand.ofList(result);
        } else
            throw new TMLExpressionException("Unknown type");
    }
}
