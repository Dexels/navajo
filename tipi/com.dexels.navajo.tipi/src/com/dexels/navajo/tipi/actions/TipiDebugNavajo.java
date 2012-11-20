package com.dexels.navajo.tipi.actions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Operand;
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
public class TipiDebugNavajo extends TipiAction {
	private static final long serialVersionUID = -4117223330671763941L;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiDebugNavajo.class);
	
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		// Operand tipi = getEvaluatedParameter("tipipath", event);
		Operand filename = getEvaluatedParameter("filename", event);
		String file = null;
		PrintStream w = null;

		if (filename != null) {
			file = (String) filename.value;
		}
		try {
			// if (evalTipi == null) {
			if (file != null) {
				w = new PrintStream(new FileOutputStream(file));
			} else {
				w = System.err;
			}
			if (getComponent().getNearestNavajo() != null) {
				Navajo n = getComponent().getNearestNavajo();
				w.println("********** DEBUG ************* ");
				w.println("NEAREST NAVAJO: ");
				try {
					n.write(w);
				} catch (NavajoException e) {
					logger.error("Error: ",e);
				}
				w.println("********** END OF DEBUG ****** ");
			}
			return;
			// }

		} catch (IOException e) {
			logger.error("Error: ",e);
		} finally {
			if (file != null) {
				if (w != null) {
					w.close();
				}
			}
		}

	}
}
