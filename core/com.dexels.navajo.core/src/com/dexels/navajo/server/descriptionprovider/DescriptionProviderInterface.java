/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.descriptionprovider;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.script.api.Access;

public interface DescriptionProviderInterface {

	public void updatePropertyDescriptions(Navajo in, Navajo out, Access access) throws NavajoException;
//	public Message dumpCacheMessage(Navajo n) throws NavajoException;
	public int getCacheSize();
	public void flushCache();
	public void flushUserCache(String user);
	public void updatePropertyDescription(PropertyDescription pd, String rpcUser);
	public void updateDescription(String locale, String name, String description, String context, String username);
	public void deletePropertyContext(String locale, String context);
	public void setDescriptionConfigMessage(Message descriptionMessage);
	
}
