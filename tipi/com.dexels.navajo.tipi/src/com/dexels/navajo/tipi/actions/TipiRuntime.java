package com.dexels.navajo.tipi.actions;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/**
 * @todo Refactor, move to NavajoSwingTipi
 * @deprecated
 */

@Deprecated
public class TipiRuntime extends TipiAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -560275660312305221L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiRuntime.class);
	
	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		String txt = getParameter("command").getValue();
		Operand o = null;
		try {
			o = evaluate(txt, event);
		} catch (Exception ex) {
			logger.error("Error evaluating[" + txt
					+ "] inserting as plain text only");
			logger.error("Error: ",ex);
		}
		String command = "rundll32 url,FileProtocolHandler ";
		if (o != null) {
			command = command + (String) o.value;
		} else {
			command = command + txt;
		}
		try {
			Runtime rt = Runtime.getRuntime();
			Process ps = rt.exec(command);
			logger.info("Command exited: " + ps.exitValue());
		} catch (IOException ioe) {
			logger.error("Execution failed! stack:",ioe);
		}
	}
}
