/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actionmanager;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.actions.TipiActionFactory;
import com.dexels.navajo.tipi.tipixml.XMLElement;

import navajo.ExtensionDefinition;

public class OSGiActionManager extends TipiActionManager{

	
	private final static Logger logger = LoggerFactory
			.getLogger(OSGiActionManager.class);

	private static final long serialVersionUID = 1562243077965649520L;
	private final BundleContext bundleContext;

	public OSGiActionManager(BundleContext bc) {
		this.bundleContext = bc;
	}
	@Override
	public void addAction(XMLElement actionDef, ExtensionDefinition ed) {
		// ignoring
	}

	@Override
	public TipiActionFactory getActionFactory(String name) throws TipiException {
		try {
			Collection<ServiceReference<TipiActionFactory>> aa = bundleContext
					.getServiceReferences(TipiActionFactory.class,"(&(type=tipiAction)(name="+name+"))");
			if(aa.isEmpty()) {
				return null;
			}
			ServiceReference<TipiActionFactory> xe = aa.iterator().next();
			return bundleContext.getService(xe);
			
		} catch (InvalidSyntaxException e) {
			logger.error("Error: ",e);
		}

		return null;
	}



}
