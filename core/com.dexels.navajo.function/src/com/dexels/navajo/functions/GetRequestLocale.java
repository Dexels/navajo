package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;

public class GetRequestLocale  extends FunctionInterface {

    @Override
    public String remarks() {
        return "Returns the locale of the Navajo request, with an optional default";
    }

    @Override
    public Object evaluate() {
       String loc = getAccess().getInDoc().getHeader().getHeaderAttribute("locale");
       if (loc != null || getOperands().size() < 1) {
           return loc;
       }
       return getOperand(0);
       
    }


}
