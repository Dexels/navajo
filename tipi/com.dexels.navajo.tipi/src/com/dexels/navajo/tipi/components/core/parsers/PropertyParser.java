/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.core.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class PropertyParser extends BaseTipiParser {
	private static final long serialVersionUID = 8160207631197417713L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(PropertyParser.class);
	
	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return getPropertyValue(source, expression);
	}

	private Object getPropertyValue(TipiComponent source, String expression) {
		if (expression.startsWith("!")) {
			try {
				return getAttributePropertyValueByPath(source,
						expression.substring(1));
			} catch (TipiException e) {
				logger.error("Error: ",e);
				return null;
			}
		} else {
			Property p = getPropertyByPath(source, expression);
			if (p != null) {
				return p.getTypedValue();
			}
		}
		return null;
	}



}
