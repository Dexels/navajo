package com.dexels.navajo.studio.eclipse;

import org.eclipse.core.resources.IProject;

import com.dexels.navajo.document.Navajo;

public interface INavajoActivityListener {
	public void navajoResponse(Navajo n, String scriptName,IProject currentProject);
}
