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

public class TipiUpdateExpressions extends TipiAction {
	public TipiUpdateExpressions() {
	}

	protected void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
		Operand to = getEvaluatedParameter("path", event);
		if(to!=null) {
			TipiDataComponent toData = (TipiDataComponent) to.value;
			Navajo n = toData.getNearestNavajo();
			if (n != null) {
				try {
					n.refreshExpression();
					return;
				} catch (NavajoException ex) {
					throw new TipiException(ex);
				}
			}
		}
		Operand navajo = getEvaluatedParameter("navajo", event);
		
		if(navajo==null) {
			throw new TipiException("supply either path or navajo");
		}
		
		Navajo n = (Navajo) navajo.value;
		if (n != null) {
			try {
				n.refreshExpression();
				return;
			} catch (NavajoException ex) {
				throw new TipiException(ex);
			}
		}
	}

}
