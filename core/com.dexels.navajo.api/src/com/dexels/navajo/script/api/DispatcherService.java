package com.dexels.navajo.script.api;

import com.dexels.navajo.document.Navajo;

public interface DispatcherService {
	public Navajo dispatch(Navajo in);
	public Navajo generateAbortMessage(String reason);
}
