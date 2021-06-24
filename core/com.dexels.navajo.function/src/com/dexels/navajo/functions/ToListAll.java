/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.expression.api.FunctionInterface;

public final class ToListAll extends FunctionInterface {
    
    @SuppressWarnings("unchecked")
    @Override
    public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        List<Object> result = new ArrayList<>();
        
        for (int i=0;i<getOperands().size();i++) {
            Object elem = this.getOperands().get(i);
            if (elem == null) continue;
            
            if (elem instanceof List) {
                List<Object> listElem = (List<Object>) elem;
                result.addAll(listElem);
            } else {
                result.add(elem);

            }
        }
        return result;


    }

    @Override
    public String usage() {
        return "ToList(elems)";
    }

    @Override
    public String remarks() {
        return "Converts all paremeters to a new list";
    }

}
