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
public class TipiDisposeChildren extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		try {
			// String pathVal = getParameter("path").getValue();
			// TipiComponent tp = (TipiComponent) evaluate(pathVal,
			// event).value;
			Operand evaluatedParameter = getEvaluatedParameter("path", event);
			if (evaluatedParameter == null || evaluatedParameter.value == null) {
				throw new TipiException("Could not locate path!");
			}

			TipiComponent tp = (TipiComponent) evaluatedParameter.value;
			List<TipiComponent> children = new LinkedList<TipiComponent>();
			for (int i = 0; i < tp.getChildCount(); i++) {
				children.add(tp.getTipiComponent(i));
			}
			for (TipiComponent tipiComponent : children) {
				myContext.disposeTipiComponent(tipiComponent);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
