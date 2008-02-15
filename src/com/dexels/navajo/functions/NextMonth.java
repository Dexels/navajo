package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;
import java.util.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class NextMonth extends FunctionInterface {

    public NextMonth() {}

    public String remarks() {
        return "Get the next month as a date object, where supplied integer is an offset for the current year.";
    }

    public String usage() {
        return "NextMonth(Integer)";
    }

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

        java.util.Date datum = new java.util.Date();
        Calendar c = Calendar.getInstance();

        c.setTime(datum);

        Integer arg = (Integer) this.getOperands().get(0);
        int offset = arg.intValue();
        c.set(c.get(Calendar.YEAR) + offset, c.get(Calendar.MONTH) + 1, 1);

        java.text.SimpleDateFormat formatter =
                new java.text.SimpleDateFormat("yyyy-MM-dd");

        // return formatter.format(c.getTime());
        return c.getTime();
    }
}
