package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

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

	public TipiUpdateAllExpressions() {
	}

	protected void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
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
			e.printStackTrace();
		}
	}

}
