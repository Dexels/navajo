package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;

public class GetRequestUserAgent  extends FunctionInterface {

    private static final Object DEFAULT_USER_AGENT = "";

    @Override
    public String remarks() {
        return "Returns the User-Agent header value of the Navajo request with empty string as default, optionaly overriding the default";
    }

    @Override
    public Object evaluate() {
        String userAgent = getAccess().getInDoc().getHeader().getHeaderAttribute("user_agent");
        if (userAgent != null) {
            return userAgent;
       }
       if ((getOperands().size() > 0)) {
           return getOperand(0).toString();
       }
      
        return DEFAULT_USER_AGENT;
    }


}
