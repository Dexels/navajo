/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.animation;

public class TipiAnimationManager {

	public static Class<?> isAnimatable(Object start, Object end) {
		if (start == null || end == null) {
			return null;
		}

		if (start instanceof Integer && end instanceof Integer) {
			return Integer.class;
		}
		if (start instanceof Double && end instanceof Double) {
			return Double.class;
		}
		if (start instanceof Long && end instanceof Long) {
			return Long.class;
		}
		if (!start.getClass().equals(end.getClass())) {
			if (start instanceof Number && end instanceof Number) {
				return Integer.class;
			}
			return null;
		}
		return null;
	}
}
