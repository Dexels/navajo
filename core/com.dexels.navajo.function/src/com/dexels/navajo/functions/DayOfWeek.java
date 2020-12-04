/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.Calendar;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * <p>
 * Title:
 * <h3>SportLink Services</h3><br>
 * </p>
 * <p>
 * Description: Web Services for the SportLink Project<br>
 * <br>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002<br>
 * </p>
 * <p>
 * Company: Dexels.com<br>
 * </p>
 * 
 * @author not attributable
 * @version $Id: Weekday.java,v 1.1 2012/07/06 10:26:58 eversteeg Exp $
 */

public final class DayOfWeek extends FunctionInterface {

    @Override
	public String remarks() {
        return "This function return a calendar date of the first given weekday in the past";
    }

    private final java.util.Date reset(Calendar c, boolean past) {
        if (past) {
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
        } else {
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MILLISECOND, 99);
        }
        return c.getTime();
    }

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object o = this.getOperand(0);
        Object f = this.getOperand(1);
        if (!(o instanceof Integer)) throw new TMLExpressionException(
                    "Invalid operand type, Integer expected");
        if (!(f instanceof Boolean)) throw new TMLExpressionException(
                    "Invalid operand type, Boolean expected");

        Integer weekday = (Integer) o;
        Boolean past = (Boolean) f;
        Calendar today = Calendar.getInstance();

        if (today.get(Calendar.DAY_OF_WEEK) == weekday.intValue())
            return reset(today, past.booleanValue());

        int factor = (past.booleanValue() ? -1 : 1);

        for (int i = 1; i <= 8; i++) {
            today.add(Calendar.DAY_OF_WEEK, factor);
            if (today.get(Calendar.DAY_OF_WEEK) == weekday.intValue()) {
                return reset(today, past.booleanValue());
            }
        }

        return null;
    }

    @Override
	public String usage() {
        return "FirstDayOfWeek(Integer weekday, Boolean past): Date";
    }

    public static void main(String[] args) throws Exception {
        DayOfWeek f = new DayOfWeek();
        f.reset();
        f.insertIntegerOperand(Integer.valueOf(6));
        f.insertBooleanOperand(new Boolean(false));
        Object o = f.evaluate();
        System.err.println("f = " + o);
    }

}
