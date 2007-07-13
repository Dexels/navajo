package com.dexels.navajo.tipi.actions;

import java.io.*;

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
public class TipiDumpNavajo extends TipiAction {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		
		Operand navajo = getEvaluatedParameter("input", event);
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
			Navajo nn = (Navajo) navajo.value;
				w.println("********** DEBUG ************* ");
				w.println("NEAREST NAVAJO: ");
				try {
					nn.write(w);
				} catch (NavajoException e) {
					e.printStackTrace();
				}
				w.println("********** END OF DEBUG ****** ");
			return;
			// }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (file != null) {
				w.close();
			}
		}

	}
}
