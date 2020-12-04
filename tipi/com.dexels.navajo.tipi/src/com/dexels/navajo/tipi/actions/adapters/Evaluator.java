/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actions.adapters;

import java.io.Serializable;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.internal.TipiAction;

public class Evaluator implements Serializable{

	private static final long serialVersionUID = 2091298504248981489L;
	private final TipiAction myAction;

	public Evaluator(TipiAction parentAction) {
		myAction = parentAction;
	}

	public Object evaluate(String e) {
		Operand evaluated = myAction.evaluate(e, myAction.getEvent());
		if (evaluated == null) {
			return null;
		} else {
			return evaluated.value;
		}
	}
}
