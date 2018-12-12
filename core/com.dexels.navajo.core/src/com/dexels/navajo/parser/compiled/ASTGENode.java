/* Generated By:JJTree&JavaCC: Do not edit this line. ASTGElNode.java */
package com.dexels.navajo.parser.compiled;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.parser.Utils;


@SuppressWarnings({"rawtypes"})
public final class ASTGENode extends SimpleNode {
    public ASTGENode(int id) {
        super(id);
    }
	@Override
	public ContextExpression interpretToLambda(List<String> problems, String expression, Function<String, FunctionClassification> functionClassifier) {
		return lazyBiFunction(problems,expression,(a,b)->interpret(a, b,expression),(a,b)->true,(a,b)->Optional.of(Property.BOOLEAN_PROPERTY),functionClassifier);
	}
	
    public static final Boolean compare(Object a, Object b, String expression)  {

        if (a == null || b == null) {
            throw new TMLExpressionException(
                    "Illegal arguement for ge;. Cannot compare " + a + " >= " + b + ". No null values are allowed: "+expression);
        }

        if (a instanceof Integer && b instanceof Integer)
            return Boolean.valueOf(((Integer) a).intValue() >= ((Integer) b).intValue());
        else if (a instanceof Integer && b instanceof Double)
            return Boolean.valueOf(((Integer) a).intValue() >= ((Double) b).doubleValue());
        else if (a instanceof Double && b instanceof Integer)
            return Boolean.valueOf(((Double) a).intValue() >= ((Integer) b).doubleValue());
        else if (a instanceof Double && b instanceof Double)
            return Boolean.valueOf(((Double) a).doubleValue() >= ((Double) b).doubleValue());
        else if (a instanceof Date)
            return Boolean.valueOf(Utils.compareDates(a, b, ">="));
        else if (a instanceof Money || b instanceof Money)
            return Boolean.valueOf(Utils.getDoubleValue(a) >= Utils.getDoubleValue(b));
        else if (a instanceof Percentage || b instanceof Percentage)
            return Boolean.valueOf(Utils.getDoubleValue(a) >= Utils.getDoubleValue(b));
        else if (a instanceof ClockTime && b instanceof ClockTime)
            return Boolean.valueOf(Utils.compareDates(a, b, ">="));
        else
            throw new TMLExpressionException("Illegal comparison for ge; " + a.getClass().getName() + " " + b.getClass().getName());
    }

	private final Object interpret(Object a, Object b, String expression) throws TMLExpressionException {



        if (a instanceof List) { // Compare all elements in the list.
        		List list = (List) a;
            boolean result = true;

            for (int i = 0; i < list.size(); i++) {
                boolean dum = compare(list.get(i), b, expression).booleanValue();


                if (!(dum))
                    return Boolean.valueOf(false);
                result = result && dum;
            }
            return Boolean.valueOf(result);
        } else {
            return compare(a, b,expression);
        }
    }
}
