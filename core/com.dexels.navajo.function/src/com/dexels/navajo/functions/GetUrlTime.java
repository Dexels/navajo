/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.expression.api.TMLExpressionException;

public final class GetUrlTime extends GetUrlBase {

	@Override
	public String remarks() {
		return "Check the date of an url. This is not the modification date, this is the time the header was sent. Effectively the current time of the webserver.";
	}

	@Override
	public String usage() {		
		return "Check the  date of a webserver.";
	}

	@Override
	public final Object evaluate() throws TMLExpressionException {
	      // input (ArrayList, Object).
        if (this.getOperands().size() != 1)
            throw new TMLExpressionException("GetUrlTime(String) expected");
        Object a = this.getOperands().get(0);
        if (a==null) {
			return null;
		}
        if (!(a instanceof String))
            throw new TMLExpressionException("GetUrlTime(String) expected");

        URL u;
		try {
			u = new URL((String)a);
		} catch (MalformedURLException e) {
			throw new TMLExpressionException("CheckUrl: bad url: "+a);
		}

		return getUrlTime(u);
	}
	

}
