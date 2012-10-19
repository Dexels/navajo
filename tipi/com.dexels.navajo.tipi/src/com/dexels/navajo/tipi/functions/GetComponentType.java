/*
 * Created on October 19, 2012
 *
 */
package com.dexels.navajo.tipi.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.tipi.TipiComponent;

/**
 * @author Erik Versteeg
 * 
 */
public class GetComponentType extends FunctionInterface {

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#remarks()
     */
    public String remarks() {
        return "Returns the type of a TipiComponent. ";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.dexels.navajo.parser.FunctionInterface#usage()
     */
    public String usage() {
        return "GetComponentType(TipiComponent source)";
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
        return tc.getComponentType();
    }
}
