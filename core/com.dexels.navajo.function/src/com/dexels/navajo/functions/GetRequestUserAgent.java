/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;

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
           return getStringOperand(0);
       }
      
        return DEFAULT_USER_AGENT;
    }


}
