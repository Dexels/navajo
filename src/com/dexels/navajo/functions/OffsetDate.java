package com.dexels.navajo.functions;


import java.util.Calendar;

import com.dexels.navajo.parser.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class OffsetDate extends FunctionInterface {

    public OffsetDate() {}

    public String remarks() {
        return "Returns a date with a certain offset.";
    }

    public String usage() {
        return "OffsetDate(<Date>,<int +/- year>,<int +/- month>,<int +/- day>,<int +/- hour(24h)>,<int +/- min>,<int +/- sec>)";
    }

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        java.util.Date date = (java.util.Date) this.getOperands().get(0);
        int year = ((Integer)getOperands().get(1)).intValue();
        int month = ((Integer)getOperands().get(2)).intValue();
        int day = ((Integer)getOperands().get(3)).intValue();

        int hour = ((Integer)getOperands().get(4)).intValue();
        int min = ((Integer)getOperands().get(5)).intValue();
        int sec = ((Integer)getOperands().get(6)).intValue();
        
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        
        if (year!=0) {
			c.add(Calendar.YEAR,year);
		}
        if (month!=0) {
			c.add(Calendar.MONTH,month);
		}
        if (day!=0) {
			c.add(Calendar.DAY_OF_MONTH,day);
		}
        if (hour!=0) {
			c.add(Calendar.HOUR_OF_DAY,day);
		}
        if (min!=0) {
			c.add(Calendar.MINUTE,day);
		}
        if (sec!=0) {
			c.add(Calendar.SECOND,day);
		}

        
        return c.getTime();
    }
}
