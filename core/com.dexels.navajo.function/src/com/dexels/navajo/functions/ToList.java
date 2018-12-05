package com.dexels.navajo.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public final class ToList extends FunctionInterface {
    
    @SuppressWarnings("unchecked")
    @Override
    public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object elem1 = null;
        Object elem2 = null;
        Object transformer = null;
        
        if (getOperands().size() > 0) {
            elem1 = this.getOperands().get(0);
        }
        if (getOperands().size() > 1) {
            elem2 = this.getOperands().get(1);
        }
        if (getOperands().size() > 2) {
            transformer = this.getOperands().get(2);
        }
       
       
        List<Object> result = new ArrayList<>();
        if (elem1 == null || "".equals(elem1))
            return result;
        
        if (elem1 instanceof List) {
            result.addAll((Collection<? extends Object>) elem1);
            if (elem2 != null) {
                if (elem2 instanceof List) {
                    result.addAll((Collection<? extends Object>) elem2);
                } else {
                    result.add(elem2);
                }
            }
        } else {
            result.add(elem1);
        }
        
        if (transformer == null || !(transformer instanceof String)) {
            return result;
        }
        String converterString = (String) transformer;
        if ("string".equalsIgnoreCase(converterString)) {
            List<Object> convertedresult = new ArrayList<>();
            for (Object elem : result) {
                convertedresult.add(elem.toString());
            }
            result = convertedresult;
        } else if ("integer".equalsIgnoreCase(converterString)) {
            List<Object> convertedresult = new ArrayList<>();
            for (Object elem : result) {
                try {
                    convertedresult.add(Integer.parseInt((String) elem));
                } catch (Throwable t) {
                    throw new TMLExpressionException("Converting to integer failed for: " + elem);
                }
            }
            result = convertedresult;
        }
        return result;

    }

    @Override
    public String usage() {
        return "ToList(Object,[ 'newelem'], [converter])";
    }

    @Override
    public String remarks() {
        return "Converts a single item to a new list, or add an element to an existing list. The third argument allows for conversion of all elements in the new list";
    }

    public static void main(String[] args) {
        System.err.println(new java.util.Date().getTime() + "");
    }
}
