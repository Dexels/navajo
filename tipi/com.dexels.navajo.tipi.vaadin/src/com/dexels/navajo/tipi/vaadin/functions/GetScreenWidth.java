/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class GetScreenWidth extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		return 1024;
	}

	@Override
	public String remarks() {
		return null;
	}

	@Override
	public String usage() {
		return(" ");
	}

}
