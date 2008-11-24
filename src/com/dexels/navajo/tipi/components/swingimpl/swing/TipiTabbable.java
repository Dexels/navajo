package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.beans.*;

import javax.swing.*;

public interface TipiTabbable  {
	public Icon getTabIcon();
	public Color getTabBackgroundColor();
	public Color getTabForegroundColor();
	public String getTabTooltip();
	
	public void addPropertyChangeListener(PropertyChangeListener pcl);
	public void removePropertyChangeListener(PropertyChangeListener pcl);
	public void setIndex(int nextIndex);
}
