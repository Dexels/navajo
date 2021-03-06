/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.SQLMap;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class JDBCFactory {
	
	public static final Logger logger = LoggerFactory.getLogger(JDBCFactory.class);
	private static volatile int useOSGi = -1;
	
	public static final boolean useOSGi() {
		if ( useOSGi != -1) {
			return ( useOSGi == 1);
		}
		try {
			boolean result = navajoadapters.Version.getDefaultBundleContext()!=null;
			if ( result ) {
				useOSGi = 1; 
			} else {
				useOSGi = 0;
			}
			return result;
		} catch (Throwable t) {
			logger.warn("No OSGi libraries found.");
			useOSGi = 0;
			return false;
		}
	}
	
	public static JDBCMappable getJDBCMap(Access a) throws MappableException, UserException {
			SQLMap sqlMap = new SQLMap();
			sqlMap.load(a);
			return sqlMap;
//		}
	}
}
