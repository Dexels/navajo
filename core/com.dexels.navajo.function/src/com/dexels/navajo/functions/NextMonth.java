/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


import java.util.Calendar;

import com.dexels.navajo.expression.api.FunctionInterface;


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

    @Override
	public String remarks() {
        return "Get the next month as a date object, where supplied integer is an offset for the current year.";
    }

    @Override
	public String usage() {
        return "NextMonth(Integer)";
    }

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

        java.util.Date datum = new java.util.Date();
        Calendar c = Calendar.getInstance();

        c.setTime(datum);

        Integer arg = (Integer) this.getOperands().get(0);
        int offset = arg.intValue();
        c.set(c.get(Calendar.YEAR) + offset, c.get(Calendar.MONTH) + 1, 1);

//        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

        // return formatter.format(c.getTime());
        return c.getTime();
    }
}
