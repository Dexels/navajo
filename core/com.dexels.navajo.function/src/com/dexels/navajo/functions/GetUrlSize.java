/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.expression.api.TMLExpressionException;

public final class GetUrlSize extends GetUrlBase {

	@Override
	public String remarks() {
		return "Get the length of URL content";
	}

	@Override
	public String usage() {		
		return "Get the length of URL content";
	}

	@Override
	public final Object evaluate() throws TMLExpressionException {
	      // input (ArrayList, Object).
        if (this.getOperands().size() != 1)
            throw new TMLExpressionException("GetUrlSize(String) expected");
        Object a = this.getOperands().get(0);
        if (a==null) {
			return null;
		}
        if (!(a instanceof String))
            throw new TMLExpressionException("GetUrlSize(String) expected");

        URL u;
		try {
			u = new URL((String)a);
		} catch (MalformedURLException e) {
			throw new TMLExpressionException("GetUrlSize: bad url: "+a);
		}

		return Integer.valueOf(getUrlLength(u));
	}


}
