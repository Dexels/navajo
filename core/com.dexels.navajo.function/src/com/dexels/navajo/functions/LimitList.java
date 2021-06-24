/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class LimitList extends FunctionInterface {
    @Override
    public String remarks() {
        return "Return a list with at most x elements";
    }

    @Override
    public String usage() {
        return "LimitList(list, number)";
    }

    @Override
    public Object evaluate() throws TMLExpressionException {
        Object o = this.getOperands().get(0);
        Object o2 = this.getOperands().get(1);
        if (o == null || !(o instanceof List)) {
            throw new TMLExpressionException(this, "LimitList expects a List as first argument");
        }
        if (o2 == null || !(o2 instanceof Integer)) {
            throw new TMLExpressionException(this, "LimitList expects an integer as second argument");
        }
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) o;
        int limit = (Integer) o2;
        return list.subList(0, Math.min(list.size(),  limit));
    }

    public static void main(String[] args) throws TMLExpressionException {
        List<String> testlist = new ArrayList<>();
        testlist.add("aap");
        testlist.add("noot");
        testlist.add("mies");
        testlist.add("wim");
        testlist.add("zus");
        LimitList id = new LimitList();
        id.reset();
        id.insertListOperand(testlist);
        id.insertIntegerOperand(3);
        System.err.println(id.evaluate());
        
        id.reset();
        id.insertListOperand(testlist);
        id.insertIntegerOperand(15);
        System.err.println(id.evaluate());
    }
}
