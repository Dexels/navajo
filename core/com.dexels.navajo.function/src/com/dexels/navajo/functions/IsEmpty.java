/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.List;

import com.dexels.navajo.document.types.NavajoType;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class IsEmpty extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {

		Object arg = this.getOperands().get(0);
		
		if (arg == null) {
			return Boolean.TRUE;
		}
		
		if (arg instanceof NavajoType) {
			NavajoType n = (NavajoType)arg;
			return (n.isEmpty());
		}
		
		if ( arg instanceof String) {
			return (((String) arg).trim().equals(""));
		}
		
		if ( arg instanceof List ) {
			return (((List<?>) arg).size() == 0);
		}
		
		return Boolean.FALSE;

	}

	@Override
	public String remarks() {
		return "Determines whether a given Navajo Object is empty or null";
	}

}
