/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.core;

public abstract class TipiHeadlessDataComponentImpl extends
		TipiDataComponentImpl {

	private static final long serialVersionUID = 2009309562917964579L;

	@Override
	public void runSyncInEventThread(Runnable r) {
		r.run();
	}

	@Override
	public void runAsyncInEventThread(Runnable r) {
		r.run();
	}

	@Override
	public Object createContainer() {
		return null;
	}
}
