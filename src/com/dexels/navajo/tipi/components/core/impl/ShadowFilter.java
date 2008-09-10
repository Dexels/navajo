package com.dexels.navajo.tipi.components.core.impl;

import java.beans.*;

// emmm, I think this thing is a bit odd, because it hardly does ANYthing
public class ShadowFilter {
	private String simpleFilter;
	private String messagePath;
	private String shadowNavajoName;
	PropertyChangeSupport propChange = new PropertyChangeSupport(this);

	public String getMessagePath() {
		return messagePath;
	}

	public void setMessagePath(String messagePath) {
		String old = this.messagePath;
		this.messagePath = messagePath;
		propChange.firePropertyChange("messagePath", old, messagePath);

	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		propChange.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		propChange.removePropertyChangeListener(pcl);
	}

	public String getShadowNavajoName() {
		return shadowNavajoName;
	}

	public void setShadowNavajoName(String shadowNavajoName) {
		String old = this.shadowNavajoName;
		this.shadowNavajoName = shadowNavajoName;
		propChange.firePropertyChange("shadowNavajoName", old, shadowNavajoName);
	}

	public String getSimpleFilter() {
		return simpleFilter;
	}

	public void setSimpleFilter(String simpleFilter) {
		String old = this.simpleFilter;
		this.simpleFilter = simpleFilter;
		propChange.firePropertyChange("simpleFilter", old, simpleFilter);
	}
}
