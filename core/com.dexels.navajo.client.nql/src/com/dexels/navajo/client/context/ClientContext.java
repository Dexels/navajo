/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.context;

import java.util.Map;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public interface ClientContext {

	public void reset();

	public void callService(String service, String tenant) throws ClientException;

	public Map<String, Navajo> getNavajos();

	public String getServiceName(Navajo n);

	public void callService(String service, String tenant, String username, String password, Navajo input)
			throws ClientException;

	public boolean hasNavajo(String name);

	public Navajo getNavajo(String name);

	public void setUsername(String username);

	public void setPassword(String password);

	public Property parsePropertyPath(String path);
	
}