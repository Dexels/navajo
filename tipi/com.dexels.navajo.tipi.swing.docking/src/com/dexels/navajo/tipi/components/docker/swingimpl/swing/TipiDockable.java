/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.docker.swingimpl.swing;

import java.net.URL;

import com.javadocking.dockable.Dockable;

public interface TipiDockable {
	public void setIcon(URL icon);
	public void setTitle(String title);
	public URL getIcon();
	public String getTitle();
	public String getDescription();
	public void setDescription(String description);
	public Dockable getDockable();
	public void setDockable(Dockable dockable);
	public void setCloseable(boolean closeable);
	public boolean getCloseable();
}
