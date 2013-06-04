package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.PropertyComponent;
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
public final class TipiClearDirty extends TipiAction {

	private static final long serialVersionUID = 472131866710083014L;

	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		// Set<String> ss = getParameterNames();

		Object p = getEvaluatedParameterValue("rootComponent", event);
		if (p == null) {
			throw new TipiException(
					"TipiClearDirty: rootComponent missing ");

		}
		if (!(p instanceof TipiComponent)) {
			throw new TipiException(
					"TipiClearDirty: rootComponent wrong type");
		}
		TipiComponent tc = (TipiComponent) p;
		processAllComponents(tc);
		
	}
	
	private void processAllComponents(TipiComponent root)
	{
		if (root.getChildCount() == 0)
		{
			if (root instanceof PropertyComponent)
			{
				((PropertyComponent) root).setDirty(Boolean.FALSE);
			}
		}
		else
		{
			for (TipiComponent child : root.getChildren())
			{
				processAllComponents(child);
			}
		}

	}
}
