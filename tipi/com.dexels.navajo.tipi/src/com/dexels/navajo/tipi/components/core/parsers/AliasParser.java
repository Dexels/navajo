/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.core.parsers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiComponent;
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

public class AliasParser extends BaseTipiParser {

	private static final long serialVersionUID = -8640653767113050463L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(AliasParser.class);
	
	private String findAlias(TipiComponent current, String expression) {
		if (current == null) {
			return null;
		}
		String alias = current.getAlias(expression);
		if (alias != null) {
			return alias;
		}
		return findAlias(current.getTipiParent(), expression);

	}

	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		String alias = findAlias(source, expression);
		if (alias == null) {
			throw new RuntimeException("Error. Can not find alias: "
					+ expression);
		}
		try {
			Operand evaluate = source.getContext().evaluate(alias, source, event);
			return evaluate.value;
		} catch (Exception e) {
			logger.error("Error: ",e);

		}
		return null;
	}

}
