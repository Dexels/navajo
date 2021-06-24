/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.io.IOException;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.server.DispatcherFactory;

public class GetContextResource extends FunctionInterface {

	@Override
	public String remarks() {
		return "GetContextResource gets the contents of a file in the <context>/resources folder";
	}

	@Override
	public String usage() {
		return "GetContextResource(filename)";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		java.io.File contextRoot = DispatcherFactory.getInstance().getNavajoConfig().getContextRoot();
		java.io.File res = new java.io.File(contextRoot,"resources");
		// input (ArrayList, Object).
		if (this.getOperands().size() != 1)
			throw new TMLExpressionException("GetContextResource(filename) expected");
		Object a = this.getOperands().get(0);
		if (!(a instanceof String)) {
			throw new TMLExpressionException("GetContextResource(filename) expected");
		}
		java.io.File result = new java.io.File(res,(String)a);
		// TODO: Verify the path does not venture outside the resources folder (Or at LEAST the context).
		// Security might be compromised if supplied with path like: ../../../root/somethingsecret
		try {
			return new Binary(result);
		} catch (IOException e) {
			throw new TMLExpressionException("Error constructing binary", e);
		}
	}

}
