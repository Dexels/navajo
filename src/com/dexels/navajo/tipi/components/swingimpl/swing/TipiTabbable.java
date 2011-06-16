package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Color;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;

public interface TipiTabbable {
	public Icon getTabIcon();

	public Color getTabBackgroundColor();

	public Color getTabForegroundColor();

	public String getTabTooltip();

	public void addPropertyChangeListener(PropertyChangeListener pcl);

	public void removePropertyChangeListener(PropertyChangeListener pcl);

	public void setIndex(int nextIndex);
}
