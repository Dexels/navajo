package com.dexels.navajo.tipi.validation;

import com.dexels.navajo.tipi.TipiComponent;

public class TipiValidationManager {
	public boolean validateComponentTree(TipiComponent tc) {
		TipiValidationDecorator tvd = tc.getContext()
				.getTipiValidationDecorator();
		if (tvd == null) {
			return true;
		}
		boolean childrenValid = true;
		for (TipiComponent element : tc.getChildren()) {
			childrenValid = childrenValid && validateComponentTree(element);
		}
		if (tc instanceof TipiValidatableComponent) {
			// TipiValidatableComponent tvc = (TipiValidatableComponent)tc;

		}
		boolean valid = false;
		// tvd.setValidation(valid, null, null);

		return valid;
	}
}
