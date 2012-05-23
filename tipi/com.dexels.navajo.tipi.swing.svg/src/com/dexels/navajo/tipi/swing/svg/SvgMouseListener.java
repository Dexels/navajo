package com.dexels.navajo.tipi.swing.svg;

public interface SvgMouseListener {
	public void onClick(String targetId);
	public void onMouseUp(String targetId);
	public void onMouseDown(String targetId);
	public void onMouseOver(String targetId);	
	public void onMouseOut(String targetId);
	public void onMouseMove(String targetId);
	public void onActivate(String targetId);

}
