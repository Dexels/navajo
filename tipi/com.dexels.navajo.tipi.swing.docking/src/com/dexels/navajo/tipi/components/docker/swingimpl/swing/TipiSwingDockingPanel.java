/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.docker.swingimpl.swing;

import java.net.URL;

import javax.swing.JPanel;

import com.javadocking.dockable.Dockable;

public class TipiSwingDockingPanel extends JPanel implements TipiDockable{

	private static final long serialVersionUID = -4033427231601365932L;
	private URL myIcon;
	private String title;
	private String description = "";
	private Dockable myDockable;
	private boolean closeable = false;
	
	@Override
	public URL getIcon() {
		return myIcon;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setIcon(URL icon) {
		myIcon = icon;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
		
	}

	@Override
	public Dockable getDockable() {
		return myDockable;
	}

	@Override
	public void setDockable(Dockable dockable) {
		myDockable = dockable;
	}
	
	@Override
	public void setCloseable(boolean closeable){
		this.closeable = closeable;
	}
	
	@Override
	public boolean getCloseable(){
		return closeable;
	}

}
