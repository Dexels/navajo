package com.dexels.navajo.tipi.swing.svg;

public interface SvgAnimationListener {
	public void onAnimationStarted(String animationId, String targetId);
	public void onAnimationEnded(String animationId, String targetId);
}
