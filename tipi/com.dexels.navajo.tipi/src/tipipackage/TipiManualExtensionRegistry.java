/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package tipipackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiExtension;

public class TipiManualExtensionRegistry implements ITipiExtensionRegistry, Serializable {

	private static final long serialVersionUID = 3925646413574906584L;
	private final List<TipiExtension> registeredExtensions = new LinkedList<TipiExtension>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiManualExtensionRegistry.class);
	
	@Override
	public void registerTipiExtension(TipiExtension te) {
		registeredExtensions.add(te);
	}

	@Override
	public void loadExtensions(ITipiExtensionContainer context) {
		for (TipiExtension te : registeredExtensions) {
			context.addOptionalInclude(te);
		}
	}

	@Override
	public void debugExtensions() {
		for (TipiExtension te : registeredExtensions) {
			logger.info("Registered extension: " + te.getId());
		}
	}
	
	@Override
	public List<TipiExtension> getExtensionList() {
		return new ArrayList<TipiExtension>(registeredExtensions);
	}

}
