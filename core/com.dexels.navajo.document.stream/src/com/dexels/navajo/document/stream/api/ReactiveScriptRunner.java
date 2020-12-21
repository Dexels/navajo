/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.dexels.navajo.document.stream.ReactiveScript;

public interface ReactiveScriptRunner {
	public ReactiveScript build(String service, boolean debug) throws IOException;
	public boolean acceptsScript(String service);
	public Optional<String> deployment();
	public Optional<InputStream> sourceForService(String service);
	public ReactiveScript compiledScript(String service) throws IOException;
}
