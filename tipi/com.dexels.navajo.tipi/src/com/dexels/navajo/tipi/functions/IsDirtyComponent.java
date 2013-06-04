/*
 * Created on October 19, 2012
 *
 */
package com.dexels.navajo.tipi.functions;

import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.PropertyComponent;

/**
 * @author Marte Koning
 * 
 */
public class IsDirtyComponent extends AbstractQueryAllComponentsFunction {

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#remarks()
     */
    public String remarks() {
        return "Returns whether the given component or one of its children is dirty. Properties can be dirty if the value changed since instantiation or the last call to clearDirty.";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#usage()
     */
    public String usage() {
        return "IsDirtyComponent(TipiComponent source)";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
     */
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
			return pc.isDirty();
		}
		else
		{
			return Boolean.FALSE;
		}
	}
}
