package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;

public class GetRequestLocale  extends FunctionInterface {

    @Override
    public String remarks() {
        return "Returns the locale of the Navajo request";
    }

    @Override
    public Object evaluate() {
       return  getAccess().getInDoc().getHeader().getHeaderAttribute("locale");
       
    }


}
