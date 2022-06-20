/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class IsArrayMessage extends FunctionInterface {

	@Override
	public String remarks() {
		return "Function to check whether the input message is an array message or not. Returns a boolean.";
	}

	@Override
	public String usage() {
		return "IsArrayMessage( Message )";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		if (this.getOperands().size() != 1)
			throw new TMLExpressionException("IsArrayMessage( Message name ) expected");

		Object a = this.getOperands().get(0);

		if (!(a instanceof String)) {
			throw new TMLExpressionException("IsArrayMessage( Message name ) expected");
		}

		String messageName = (String) a;

	    Navajo doc      = getNavajo();
	    Message message = doc.getMessage( messageName );

		return Boolean.valueOf(message.isArrayMessage());
	}

}
