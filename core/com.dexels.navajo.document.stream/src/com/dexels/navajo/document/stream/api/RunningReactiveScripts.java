/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.api;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public interface RunningReactiveScripts {

	public void submit(StreamScriptContext context);

	public List<String> services();

	public void completed(StreamScriptContext context);

	public void cancel(String uuid);

	public Collection<StreamScriptContext> contexts();

	public JsonNode asJson();
	
	public void complete(String uuid);

}