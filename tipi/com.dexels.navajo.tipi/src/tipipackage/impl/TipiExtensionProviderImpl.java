/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipipackage.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tipi.TipiExtension;
import tipipackage.TipiExtensionProvider;

public class TipiExtensionProviderImpl implements TipiExtensionProvider {

	private final List<TipiExtension> tipiExtensions = new ArrayList<TipiExtension>();
	
	public void addTipiExtension(TipiExtension te) {
		tipiExtensions.add(te);
	}

	public void removeTipiExtension(TipiExtension te) {
		tipiExtensions.remove(te);
		
	}

	@Override
	public List<TipiExtension> getExtensionList() {
		return Collections.unmodifiableList(tipiExtensions);
	}

}
