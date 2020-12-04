/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.expression.compiled;

import java.util.stream.Collectors;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class ParameterNamesFunction extends FunctionInterface {

	@Override
	public String remarks() {
		return "";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		return this.getNamedParameters().keySet().stream().sorted().collect(Collectors.joining(","));
	}

}
