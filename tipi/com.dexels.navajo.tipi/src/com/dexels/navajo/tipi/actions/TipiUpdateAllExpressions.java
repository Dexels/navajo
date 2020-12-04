/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiDataComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
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

public class TipiUpdateAllExpressions extends TipiAction {

	private static final long serialVersionUID = 3122064689511943689L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiUpdateAllExpressions.class);
	
	public TipiUpdateAllExpressions() {
	}

	@Override
	protected void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiBreakException,
			com.dexels.navajo.tipi.TipiException {
		Operand to = getEvaluatedParameter("path", event);
		if (to == null) {
			throw new TipiException("Null evaluation in TipiUpdateExpressions");
		}
		TipiDataComponent toData = (TipiDataComponent) to.value;
		doRefresh(toData);

	}

	private void doRefresh(TipiDataComponent toData) {
		Navajo n = toData.getNearestNavajo();
		try {
			n.refreshExpression();
		} catch (NavajoException e) {
			logger.error("Error: ",e);
		}
	}

}
