/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class SetMimeType extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		//application/vnd.ms-excel
		Object o = getOperand(0);
		if (!( o instanceof Binary )) {
			throw new TMLExpressionException(this, "Mime type can only be set for binaries.");
		}
		Object mt = getOperand(1);
		if (!(mt instanceof String)) {
			throw new TMLExpressionException(this, "Mime type should be string expression.");
		}
		Binary b = (Binary) o;
		b.setMimeType((String) mt);
		return b;
	}

	@Override
	public String remarks() {
		return "Sets mime-type for a binary object.";
	}

}
