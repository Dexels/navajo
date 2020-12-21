/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.descriptionprovider;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class NullDescriptionProvider extends BaseDescriptionProvider {

	@Override
	public void updateProperty(Navajo in, Property element, String locale, String tenant) {
		// leave all properties alone
	}

	@Override
	public void flushCache() {
	}

	@Override
	public int getCacheSize() {
		return 0;
	}

	@Override
	public void flushUserCache(String user) {
	}

	@Override
	public void deletePropertyContext(String locale, String context) {
	}

	@Override
	public void updateDescription(String locale, String name, String description, String context, String username) {
	}

	@Override
	public void updatePropertyDescription(PropertyDescription pd, String rpcUser) {
	}

}
