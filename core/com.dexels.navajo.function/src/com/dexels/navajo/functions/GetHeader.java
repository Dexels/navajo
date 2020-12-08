/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class GetHeader extends FunctionInterface {

	public GetHeader() {

	}

	@Override
	public String remarks() {
		return "Returns a header attribute from the request";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {

		String result;

		if ( inMessage != null ) {
			Header h = inMessage.getHeader();
			result = h.getHeaderAttribute(getStringOperand(0));
		} else {
			return "empty header";
		}
		
		return result;
	}
}
