package com.dexels.navajo.tipi.actions;

import java.io.*;

import com.dexels.navajo.document.*;
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
		Operand filename = getEvaluatedParameter("fileName", event);
		String file = null;
		PrintStream w = null;
		
		if (filename != null) {
			file = (String) filename.value;
		}
		try {
			// if (evalTipi == null) {
			if (file != null) {
				File f = new File(file);
		//		 myContext.showInfo("Dumping navajo in file: "+f.getAbsolutePath(),"aapje");
				w = new PrintStream(new FileOutputStream(f));
			} else {
				w = System.err;
			}
			Navajo nn = (Navajo) navajo.value;
				w.println("********** DEBUG ************* ");
				w.println("Supplied navajo: ");
				if (nn==null) {
					System.err.println("Null navajo supplied in dumpnavajo");
				} else {
					try {
						nn.write(w);
					} catch (NavajoException e) {
						e.printStackTrace();
					}
				}
				w.println("********** END OF DEBUG ****** ");
			return;
			// }

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (file != null) {
				if(w!=null) {
					w.close();
				}
			}
		}

	}
}
