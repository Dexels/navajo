/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions.util;


import java.security.AccessControlException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.TMLExpressionException;

import navajocore.Version;


public class FunctionFactoryFactory {

	private static  FunctionFactoryInterface instance = null;
	private static Object semaphore = new Object();

	private static final Logger logger = LoggerFactory
			.getLogger(FunctionFactoryFactory.class);

	private FunctionFactoryFactory() {}

	@SuppressWarnings("unchecked")
	public static FunctionFactoryInterface getInstance() {
		if ( instance != null ) {
			return instance;
		}

		synchronized (semaphore) {

			if ( instance != null ) {
				return instance;
			}

			String func = null;

			try {
				func = System.getProperty("NavajoFunctionFactory");
			} catch (AccessControlException e1) {
				// can't read property. Whatever, func remains null.
			}

			try {
				if(Version.osgiActive()) {
					logger.debug("OSGi environment detected!");
					func = "com.dexels.navajo.functions.util.OsgiFunctionFactory";
				} else {
					logger.debug("no OSGi environment detected!");

				}
			} catch (Throwable t) {
				logger.debug("NO OSGi environment detection failed!");
			}

			if ( func != null ) {
				try {
					Class<? extends FunctionFactoryInterface> c = (Class<? extends FunctionFactoryInterface>) Class.forName(func);
					instance = c.getDeclaredConstructor().newInstance();
				} catch (Exception e) {
					throw new TMLExpressionException("Error resolving function", e);
				}

			} else {
				instance = new JarFunctionFactory();
			}
			instance.init();
		}

		return instance;

	}

}
