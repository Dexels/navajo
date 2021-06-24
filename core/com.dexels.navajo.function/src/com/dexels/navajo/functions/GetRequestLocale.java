/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
