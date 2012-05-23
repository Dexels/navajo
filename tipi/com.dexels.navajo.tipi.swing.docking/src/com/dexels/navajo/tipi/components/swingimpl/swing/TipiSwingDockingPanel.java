package com.dexels.navajo.tipi.components.swingimpl.swing;

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
	
	public URL getIcon() {
		return myIcon;
	}

	public String getTitle() {
		return title;
	}

	public void setIcon(URL icon) {
		myIcon = icon;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
		
	}

	public Dockable getDockable() {
		return myDockable;
	}

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
