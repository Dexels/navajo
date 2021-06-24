/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author unascribed
 * @version $Id$
 */

public final class CapString extends FunctionInterface {

	public static final String vcIdent = "$Id$";

	public CapString() {
	}
    @Override
	public boolean isPure() {
    		return false;
    }

	@Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

		if (getOperands().size() != 2) {
			throw new TMLExpressionException(this, "Invalid function call");
		}
		String s = getStringOperand(0);
		Integer size = getIntegerOperand(1);

		if (s == null || s.length() == 0) {
			return "";
		} else {
			if (s.length() > size) {
				return s.substring(0, size);
			} else {
				return s;
			}
		}

	}

	@Override
	public String usage() {
		return "CapString(string, size)";
	}

	@Override
	public String remarks() {
		return "CapString a string";
	}
}
