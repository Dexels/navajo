package com.dexels.navajo.tipi.actions;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiComponent;
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
public class TipiDisposeChildren extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8311106046281479945L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiDisposeChildren.class);
	
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
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
				logger.debug("Disposing child: "
						+ tipiComponent.getPath());
				myContext.disposeTipiComponent(tipiComponent);

			}
		} catch (Exception ex) {
			logger.error("Error: ",ex);
		}
	}
}
