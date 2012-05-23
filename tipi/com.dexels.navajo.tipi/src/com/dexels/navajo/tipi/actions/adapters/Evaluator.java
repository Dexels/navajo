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
