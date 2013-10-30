package com.dexels.navajo.functions;


import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;




public final class MergeNavajo extends FunctionInterface {

    public MergeNavajo() {}

    @Override
	public String remarks() {
        return "Merges two navajo objects";
    }

    @Override
	public String usage() {
        return "MergeNavajo(slave, master).";
    }

    @Override
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object a = this.getOperands().get(0);
        Object b = this.getOperands().get(1);

        try {
        	Navajo slave = (Navajo) a;
        	Navajo result = slave.copy();
        	Navajo master = (Navajo)b;
        	
        	return result.merge(master);
        } catch (Exception e) {
            throw new TMLExpressionException(this, "Illegal type specified in MergeNavajo() function: " + e.getMessage(),e);
        }
    }
}
