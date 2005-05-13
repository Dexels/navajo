package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 *
 * $Id$
 *
 $ $Log$
 $ Revision 1.1  2005/05/13 13:02:50  matthijs
 $ init version
 $
 $ Revision 1.4  2004/01/27 13:35:59  arjen
 $ Added some final qualifiers and fixed isAsyncEnabled() bug in
 $ TslCompiler (NOTE: remove previous compiled versions and sources of scripts
 $ that use Asynchronous mappable objects.
 $
 $ Revision 1.3  2002/11/06 09:33:47  arjen
 $ Used Jacobe code beautifier over all source files.
 $ Added log4j support.
 $
 $ Revision 1.2  2002/09/18 14:22:57  arjen
 $ *** empty log message ***
 $
 $ Revision 1.1.1.1  2002/06/05 10:12:27  arjen
 $ Navajo
 $
 $ Revision 1.2  2002/03/11 16:20:43  arjen
 $ *** empty log message ***
 $
 */

public final class Round extends FunctionInterface {

    public Round() {}

    public String remarks() {
        return "With this function a floating point value can be rounded to a given number of digits. Round(2.372, 2) = 2.37";
    }

    public String usage() {
        return "Round(float, integer).";
    }

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object a = this.getOperands().get(0);
        Object b = this.getOperands().get(1);

        try {
            Double d = (Double) a;
            Integer i = (Integer) b;
            double dd = d.doubleValue();
            int digits = i.intValue();

            dd = (int) (dd * Math.pow(10.0, (double) digits))
                    / Math.pow(10.0, (double) digits);
            return new Double(dd);
        } catch (Exception e) {
            throw new TMLExpressionException(this, "Illegal type specified in Round() function: " + e.getMessage());
        }
    }
}
