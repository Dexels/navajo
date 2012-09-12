package com.dexels.navajo.tipi.components.core.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class PropertyRefParser extends BaseTipiParser {
	private static final long serialVersionUID = -6380517741454351672L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(PropertyRefParser.class);
	
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		// return new PropertyRef(getPropertyByPath(source, expression));
		if (expression.startsWith("!")) {
			try {
				return getAttributePropertyByPath(source,
						expression.substring(1));
			} catch (TipiException e) {
				logger.error("Error: ",e);
				return null;
			}
		} else {
			return getPropertyByPath(source, expression);
		}
	}

}
