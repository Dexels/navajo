package com.dexels.navajo.functions;


import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;




public final class MergeNavajo extends FunctionInterface {

    public MergeNavajo() {}

    public String remarks() {
        return "Merges two navajo objects";
    }

    public String usage() {
        return "MergeNavajo(slave, master).";
    }

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object a = this.getOperands().get(0);
        Object b = this.getOperands().get(1);

        try {
        	Navajo slave = (Navajo) a;
        	Navajo result = slave.copy();
        	Navajo master = (Navajo)b;
        	
        	return result.merge(master);
        } catch (Exception e) {
        	e.printStackTrace();
            throw new TMLExpressionException(this, "Illegal type specified in MergeNavajo() function: " + e.getMessage(),e);
        }
    }
}
