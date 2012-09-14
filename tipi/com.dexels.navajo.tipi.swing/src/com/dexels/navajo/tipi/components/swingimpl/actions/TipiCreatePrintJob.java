package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.print.PrinterJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;


public class TipiCreatePrintJob extends TipiAction {
	private static final long serialVersionUID = -2430232555995628924L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiCreatePrintJob.class);
	
	public TipiCreatePrintJob() {
	}

	protected void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiBreakException,
			com.dexels.navajo.tipi.TipiException {
		/**
		 * @todo Implement this com.dexels.navajo.tipi.internal.TipiAction
		 *       abstract method
		 */
		Operand globalvalue = getEvaluatedParameter("value", event);
		PrinterJob jb = PrinterJob.getPrinterJob();
		// myContext.setGlobalValue(""+globalvalue.value,jb);

		if (jb.printDialog()) {
			myContext.setGlobalValue("" + globalvalue.value, jb);
		} else {
			logger.debug("Breaking on printjob!");
			throw new TipiBreakException(TipiBreakException.USER_BREAK);
		}

		// TipiReference tr = (TipiReference)globalvalue.value;

	}

}
