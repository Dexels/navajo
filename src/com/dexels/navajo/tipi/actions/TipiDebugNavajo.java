package com.dexels.navajo.tipi.actions;

import java.io.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
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
public class TipiDebugNavajo extends TipiAction {
    public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
        TipiDataComponent evalTipi = null;
        Operand tipi = getEvaluatedParameter("tipipath", event);
        Operand filename = getEvaluatedParameter("filename", event);
        String file = null;
        PrintStream w = null;
        if (tipi != null) {
            evalTipi = (TipiDataComponent) tipi.value;
        }
        if (filename != null) {
            file = (String) filename.value;
        }
        try {
//            if (evalTipi == null) {
                 if (file != null) {
                    w = new PrintStream(file);
                } else {
                    w = System.err;
                }
                    if (myComponent.getNearestNavajo() != null) {
                        Navajo n = myComponent.getNearestNavajo();
                        w.println("********** DEBUG ************* ");
                        w.println("NEAREST NAVAJO: ");
                        try {
                            n.write(w);
                        } catch (NavajoException e) {
                            e.printStackTrace();
                        }
                        w.println("********** END OF DEBUG ****** ");
                    }
                    return;
//                }

     
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (file!=null) {
                w.close();
            }
        }

    }
}
