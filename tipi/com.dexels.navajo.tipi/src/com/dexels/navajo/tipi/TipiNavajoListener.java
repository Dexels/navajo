package com.dexels.navajo.tipi;

import com.dexels.navajo.document.Navajo;

public interface TipiNavajoListener {
	public void navajoSent(Navajo n, String service);

	public void navajoReceived(Navajo n, String service);
}
