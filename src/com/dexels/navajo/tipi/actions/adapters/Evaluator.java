package com.dexels.navajo.tipi.actions.adapters;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.internal.TipiAction;

public class Evaluator {
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
