/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler.meta;

import java.io.File;

import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.server.DispatcherFactory;

public class InheritDependency extends Dependency {

	public InheritDependency(long timestamp, String id) {
		super(timestamp, id);
	}
	
	@Override
	public final boolean recompileOnDirty() {
		return true;
	}

	@Override
	public final long getCurrentTimeStamp() {
		return getScriptTimeStamp(getId());
	}

	public final static long getScriptTimeStamp(String id) {
		// Try to find included script.
		String scriptPath =  DispatcherFactory.getInstance().getNavajoConfig().getScriptPath();
		File f = new File(scriptPath, id + ".xml");
		if ( f.exists() ) {
			return f.lastModified();
		} else {
			return -1;
		}
	}
}
