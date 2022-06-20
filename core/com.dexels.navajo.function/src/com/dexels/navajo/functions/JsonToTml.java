/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.io.Reader;
import java.io.StringReader;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.json.JSONTMLFactory;
import com.dexels.navajo.expression.api.FunctionInterface;

public class JsonToTml extends FunctionInterface {

@Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object s = this.getOperands().get(0);
        Object m = null;
		
        if (getOperands().size() != 1) {
        	m = this.getOperands().get(1);
        }

        if (s == null) {
          return null;
        }

        if (s instanceof String) {
			Navajo n;
			try {
	        	Reader r = new StringReader((String) s);
				if (m == null) {
		        	n = JSONTMLFactory.getInstance().parse(r);
				} else {
		        	n = JSONTMLFactory.getInstance().parse(r, m.toString());
				}
				return n;
			} catch (Exception e) {
				return null;
			}
        }
        return null;
    }

    @Override
	public String usage() {
        return "JsonToTml(Object, messageName)";
    }

    @Override
	public String remarks() {
        return "Returns a tml representation of the supplied json object.";
    }
}
