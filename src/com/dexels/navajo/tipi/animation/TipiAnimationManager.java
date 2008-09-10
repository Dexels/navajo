package com.dexels.navajo.tipi.animation;

public class TipiAnimationManager {

	public static boolean isAnimatable(Object start, Object end) {
		if (start == null || end == null) {
			return false;
		}
		if (!start.getClass().equals(end.getClass())) {
			return false;
		}
		if (start instanceof Integer || end instanceof Integer) {
			return true;
		}
		return false;
	}
}
