package com.dexels.navajo.tipi.components.swingimpl.formatters;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.interpolation.SplineInterpolator;

import com.dexels.navajo.document.Property;

public class PropertyAnimator {

	private Animator myAnimator;
	private Property myProperty;
	private Object initial;
	private Object myTarget;

	//
	// public PropertyAnimator(Property p, int duration, Object target) {
	// animateProperty(p, duration, target);
	// }

	public void animateProperty(Property p, int duration, Object target,
			final Class<?> animationClass) {
		myProperty = p;
		myAnimator = new Animator(duration);
		myAnimator.setInterpolator(new SplineInterpolator(0f, 0.4f, 0.8f, 1f));
		myAnimator.setAcceleration(0.3f);
		myAnimator.setDeceleration(0.3f);
		initial = p.getTypedValue();
		myTarget = target;
		myAnimator.addTarget(new TimingTarget() {

			public void begin() {
			}

			public void end() {
				interpolate(initial, myTarget, 1, animationClass);
			}

			public void repeat() {
			}

			public void timingEvent(float e) {
				interpolate(initial, myTarget, e, animationClass);
			}
		});

		myAnimator.start();
	}

	public void interpolate(Object start, Object end, float fraction,
			Class<?> animationClass) {
		Number startN = (Number) start;
		Number endN = (Number) end;
		if (animationClass.equals(Integer.class)) {
			// Integer s = (Integer)start;
			// Integer e = (Integer)end;
			// int diff = e-s;
			double diff = endN.doubleValue() - startN.doubleValue();
			int res = new Integer(
					(int) (startN.doubleValue() + diff * fraction));
			myProperty.setAnyValue(res);
		}
		if (animationClass.equals(Double.class)) {
			double diff = endN.doubleValue() - startN.doubleValue();
			double res = new Double((startN.doubleValue() + diff * fraction));
			myProperty.setAnyValue(res);
		}
	}

}
