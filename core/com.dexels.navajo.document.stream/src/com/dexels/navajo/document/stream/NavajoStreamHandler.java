/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.stream.api.Method;
import com.dexels.navajo.document.stream.api.NavajoHead;
import com.dexels.navajo.document.stream.api.Prop;

public interface NavajoStreamHandler {
	public void messageDone(Map<String,String> attributes, List<Prop> properties);
	public void messageStarted(Map<String,String> attributes);
	public void messageDefinitionStarted(Map<String,String> attributes);
	public void messageDefinition(Map<String,String> attributes, List<Prop> properties);
	public void arrayStarted(Map<String,String> attributes);
	public void arrayElementStarted();
	public void arrayElement(List<Prop> properties);
	public void arrayDone(String name);
	public void navajoStart(NavajoHead head);
	public void navajoDone(List<Method> methods);
	public void binaryStarted(String name, int length, Optional<String> description, Optional<String> direction, Optional<String> subtype);
	public void binaryContent(String name);
	public void binaryDone();
}
