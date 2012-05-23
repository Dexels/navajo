package com.dexels.navajo.tipi.components.swingimpl.swing;

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
