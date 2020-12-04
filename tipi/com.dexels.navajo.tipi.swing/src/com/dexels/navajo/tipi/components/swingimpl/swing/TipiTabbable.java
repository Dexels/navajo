/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Color;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;

public interface TipiTabbable {
	public Icon getTabIcon();

	public Color getTabBackgroundColor();

	public Color getTabForegroundColor();

	public String getTabTooltip();

	public String getTabText();

	public void addPropertyChangeListener(PropertyChangeListener pcl);

	public void removePropertyChangeListener(PropertyChangeListener pcl);

	public void setIndex(int nextIndex);
}
