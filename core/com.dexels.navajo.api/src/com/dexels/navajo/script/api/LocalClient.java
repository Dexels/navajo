/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.script.api;

import com.dexels.navajo.document.Navajo;

public interface LocalClient {
	public Navajo call(String instance, Navajo n) throws FatalException;
	public Navajo call(Navajo n) throws FatalException;
	public Navajo generateAbortMessage(String reason) throws FatalException;
	public Navajo handleCallback(String instance, Navajo n, String callback);
	public Navajo handleInternal(String instance, Navajo in, Object cert, ClientInfo clientInfo) throws FatalException;
	public Navajo handleInternal(String instance, Navajo in, boolean skipAuth) throws FatalException;
	public boolean isSpecialWebservice(String name);
	public String getApplicationId();
}
