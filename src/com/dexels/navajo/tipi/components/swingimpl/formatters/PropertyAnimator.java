package com.dexels.navajo.tipi.components.swingimpl.formatters;

import org.jdesktop.animation.timing.*;
import org.jdesktop.animation.timing.interpolation.*;

import com.dexels.navajo.document.*;

public class PropertyAnimator {

	private Animator myAnimator;
	private Property myProperty;
	private Object initial;
	private Object myTarget;
//
//	public PropertyAnimator(Property p, int duration, Object target) {
//		animateProperty(p, duration, target);
//	}



	public void animateProperty(Property p, int duration, Object target) {
		myProperty = p;
		myAnimator = new Animator(duration);
		myAnimator.setInterpolator(new SplineInterpolator(0f,0.4f,0.8f,1f));
		myAnimator.setAcceleration(0.3f);
		myAnimator.setDeceleration(0.3f);
	    initial = p.getTypedValue();
	    myTarget = target;
	    myAnimator.addTarget(new TimingTarget(){

	    
	    	public void begin() {
	    	}

	    	public void end() {
	    	}

	    	public void repeat() {
	    	}

	    	public void timingEvent(float e) {
	    		interpolate(initial, myTarget, e);
	    	}});
	    
	    myAnimator.start();
	}
	
	public void interpolate(Object start, Object end, float fraction) {
		Integer s = (Integer)start;
		Integer e = (Integer)end;
		int diff = e-s;
		myProperty.setAnyValue(new Integer((int) (s+diff*fraction)));
	}

}
