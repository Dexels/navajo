/*
 * Created on October 19, 2012
 *
 */
package com.dexels.navajo.tipi.functions;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.PropertyComponent;

/**
 * @author Marte Koning
 * 
 */
public class HasRequiredPropertyComponent extends AbstractQueryAllComponentsFunction {

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#remarks()
     */
    @Override
	public String remarks() {
        return "Returns whether the given component or one of its children is a required property. Requiredness is determined by the subtype of the property.";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#usage()
     */
    @Override
	public String usage() {
        return "HasRequiredPropertyComponent(TipiComponent source)";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
     */
    @Override
	public Object evaluate() throws TMLExpressionException {
        Object pp = getOperand(0);
        if (pp == null) {
            return null;
        }
        if (!(pp instanceof TipiComponent)) {
            throw new TMLExpressionException(this, "Invalid operand: " + pp.getClass().getName());
        }
        TipiComponent tc = (TipiComponent) pp;
        return queryAllComponents(tc, QUERY_TYPE_OR);
    }

	@Override
	protected Boolean querySingleComponent(TipiComponent tc) {
		if (tc instanceof PropertyComponent)
		{
			PropertyComponent pc = (PropertyComponent) tc;
			Property p = pc.getProperty();
			String required = p.getSubType(Property.SUBTYPE_REQUIRED);
			return required != null && Property.TRUE.equals(required);
		}
		else
		{
			return Boolean.FALSE;
		}
	}
}
