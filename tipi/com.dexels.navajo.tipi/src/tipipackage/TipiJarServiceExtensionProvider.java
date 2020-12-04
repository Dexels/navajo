/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipipackage;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiExtension;

public class TipiJarServiceExtensionProvider extends TipiManualExtensionRegistry {

	private static final long serialVersionUID = 5065605262245351282L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiJarServiceExtensionProvider.class);
	
	public TipiJarServiceExtensionProvider() {
		List<TipiExtension> ll = listExtensions();
		for (TipiExtension tipiExtension : ll) {
			tipiExtension.loadDescriptor();
			registerTipiExtension(tipiExtension);
		}
	}

	private List<TipiExtension> listExtensions() {
		List<TipiExtension> extensionList = new LinkedList<TipiExtension>();
		try {
		    
			Iterator<TipiExtension> iter = java.util.ServiceLoader.load(TipiExtension.class).iterator();
			while (iter.hasNext()) {
				TipiExtension tipiExtension = iter.next();
				extensionList.add(tipiExtension);
//				tipiExtension.loadDescriptor();
			}
		} catch (Throwable e) {
			logger.error("Error: ",e);
		}
		return extensionList;
	}

}
