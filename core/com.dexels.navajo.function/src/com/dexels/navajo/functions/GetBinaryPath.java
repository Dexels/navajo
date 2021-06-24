/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class GetBinaryPath extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		
		Binary b = (Binary) getOperand(0);
		if ( b != null ) {
			return b.getFile().getAbsolutePath();
		} else {
			return null;
		}
	}
 
	@Override
	public String remarks() {
		return "Returns the path to a binary";
	}

	@Override
	public String usage() {
		return "GetBinaryPath([binary])";
	}

}
