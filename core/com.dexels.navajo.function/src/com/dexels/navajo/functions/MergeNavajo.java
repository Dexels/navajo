/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;




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
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
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
