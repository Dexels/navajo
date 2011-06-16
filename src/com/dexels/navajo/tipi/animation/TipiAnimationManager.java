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
