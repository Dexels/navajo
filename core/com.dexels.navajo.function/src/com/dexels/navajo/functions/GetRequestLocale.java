package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;

public class GetRequestLocale  extends FunctionInterface {

    private static final Object DEFAULT_LOCALE = "nl";

    @Override
    public String remarks() {
        return "Returns the locale of the Navajo request with NL as default, optionally overriding the default";
    }

    @Override
    public Object evaluate() {
       String loc = getAccess().getInDoc().getHeader().getHeaderAttribute("locale");
       if (loc != null) {
           return loc;
       }
       if ((getOperands().size() > 0)) {
           return getStringOperand(0);
       }
      
       return DEFAULT_LOCALE;
    }


}
