/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.parsers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.internal.TipiReference;


public class CookieRef implements TipiReference {

	
	private final static Logger logger = LoggerFactory
			.getLogger(CookieRef.class);
	private final TipiContext myContext;
	private final String myKey;
	public CookieRef(String key, TipiContext myContext) {
		this.myContext = myContext;
		this.myKey = key;
	}

	public void setValue(Object val) {
		myContext.setCookie(myKey, (String)val);
		logger.info("Current cookie: "+myKey+" will be set to value: "+val);
		}

	public Object getValue() {
		return myContext.getCookie(myKey);
	}
	
	
}
