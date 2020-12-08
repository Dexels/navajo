/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
