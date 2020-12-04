/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.core.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
@Deprecated
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
		propChange
				.firePropertyChange("shadowNavajoName", old, shadowNavajoName);
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
