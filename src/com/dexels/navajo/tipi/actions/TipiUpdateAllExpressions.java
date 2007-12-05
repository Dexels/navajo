package com.dexels.navajo.tipi.actions;

import java.util.*;

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
			System.err.println("Null evaluation in TipiUpdateExpressions");
		}
		TipiDataComponent toData = (TipiDataComponent) to.value;
		Navajo n = toData.getNearestNavajo();
		ArrayList al;
		try {
			al = n.getAllMessages();
			for (int i = 0; i < al.size(); i++) {
				Message current = (Message)al.get(i);
				current.refreshExpression();
			}

		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
