package com.dexels.navajo.tipi.actions;

import java.io.File;
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
public class TipiDumpNavajo extends TipiAction {
	private static final long serialVersionUID = -4736771823288001142L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiDumpNavajo.class);
	
	@Override
	public void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {

		Operand navajo = getEvaluatedParameter("input", event);
		Operand filename = getEvaluatedParameter("fileName", event);
		String file = null;
		PrintStream w = null;

		if (filename != null) {
			file = (String) filename.value;
		}
		try {
			// if (evalTipi == null) {
			if (file != null) {
				// TODO Add check for sandbox/GAE mode
				File f = new File(file);
				// myContext.showInfo("Dumping navajo in file: "+f.getAbsolutePath
				// (),"aapje");
				w = createWriter(f);
			} else {
				w = System.err;
			}
			Navajo nn = (Navajo) navajo.value;
			w.println("********** DEBUG ************* ");
			w.println("Supplied navajo: ");
			if (nn == null) {
				logger.warn("Null navajo supplied in dumpnavajo");
			} else {
				try {
					nn.write(w);
				} catch (NavajoException e) {
					logger.error("Error: ",e);
				}
			}
			w.println("********** END OF DEBUG ****** ");
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

	protected PrintStream createWriter(File f) throws IOException {
		return new PrintStream(new FileOutputStream(f));
	}
}
