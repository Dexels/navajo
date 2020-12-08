/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.tipi.functions;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.tipi.internal.TipiReference;

/**
 * @author frank
 * 
 */
public class ToValue extends FunctionInterface {

	@Override
	public String remarks() {
		return "Dereferences a TipiReference, which is basically a pointer. cool";
	}

	@Override
	public String usage() {
		return "Dereference(Reference)";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		Object pp = getOperand(0);
		if (getOperands().size() != 1) {
			throw new TMLExpressionException(this,
					"Invalid number of operands: " + pp.getClass().getName()
							+ " usage: " + usage());

		}

		if (pp instanceof TipiReference) {
			TipiReference tr = (TipiReference) pp;
			return tr.getValue();

		}
		if (pp instanceof Property) {
			Property tr = (Property) pp;
			return tr.getTypedValue();
		}
		throw new TMLExpressionException(this, "Invalid operand: "
				+ pp.getClass().getName());

	}

}
