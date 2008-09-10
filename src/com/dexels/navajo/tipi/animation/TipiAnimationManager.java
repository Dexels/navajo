package com.dexels.navajo.tipi.animation;

public class TipiAnimationManager {

	public static boolean isAnimatable(Object start, Object end) {
		if (start == null || end == null) {
			// System.err.println("Null values detected. Not animatable");
			return false;
		}
		if (!start.getClass().equals(end.getClass())) {
			// System.err.println("Differnt types detected. Not animatable: "+
			// start.getClass()+" - "+end.getClass());
			return false;
		}
		if (start instanceof Integer || end instanceof Integer) {
			// System.err.println("Integers detected. Animatable!");
			return true;
		}
		// System.err.println("Other types detected. Not animatable: "+start.
		// getClass()+" - "+end.getClass());
		return false;
	}
}
