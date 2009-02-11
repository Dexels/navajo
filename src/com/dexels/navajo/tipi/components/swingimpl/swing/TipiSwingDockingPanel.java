package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.net.URL;

import javax.swing.JPanel;

import com.javadocking.dockable.Dockable;

public class TipiSwingDockingPanel extends JPanel implements TipiDockable{
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
	
	public void setCloseable(boolean closeable){
		this.closeable = closeable;
	}
	
	public boolean getCloseable(){
		return closeable;
	}

}
