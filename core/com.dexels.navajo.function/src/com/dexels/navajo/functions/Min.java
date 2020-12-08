/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

import java.util.List;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.parser.Utils;


public final class Min extends FunctionInterface {

	@Override
	public String remarks() {
    return "Return the minimum of two given numbers or two dates.";
}

@Override
public String usage() {
    return "Min(number, number), where number can be of type integer or double, or Min(date, date)";
}

    @Override
	public final Object evaluate() throws TMLExpressionException {

        List<?> operands = this.getOperands();

        if (operands.size() == 2) {
            Object a = operands.get(0);
            Object b = operands.get(1);
            return min(a, b);
        } else if (operands.size() == 1) {
            // List as argument.
            Object a = operands.get(0);

            if (!(a instanceof List))
            {
                throw new TMLExpressionException("Invalid number of arguments for Min()");
            }
            List list = (List) a;
            Object currentMin = list.isEmpty() ? null : list.get(0);

            for (int i = 1; i < list.size(); i++) {
                Object b = list.get(i);
                currentMin = min(currentMin, b);
            }
            return currentMin;
        } else
            throw new TMLExpressionException("Invalid number of arguments for Max()");
    }
    private Object min(Object a, Object b)
    {
        if (a instanceof Date)
        {
            if (!(b instanceof Date))
            {
                throw new TMLExpressionException("Cannot compare a date with a non-date");
            }
            else
            {
                if (((Date) a).compareTo((Date) b) < 0)
                {
                    return a;
                }
                else
                {
                    return b;
                }
            }
        }
        else
        {
            if ((b instanceof Date))
            {
                throw new TMLExpressionException("Cannot compare a date with a non-date");
            }
            else
            {
                double value1 = Utils.getDoubleValue(a);
                double value2 = Utils.getDoubleValue(b);
                double min = (value1 < value2) ? value1 : value2;

                if (a instanceof Double || b instanceof Double)
                    return Double.valueOf(min);
                else
                    return Integer.valueOf((int) min);
                
            }
        }
    }
    
    public static void main(String[] args) throws Throwable {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Object a = sdf.parse("2017-11-10");
//        Object a = Math.PI;
        Object b = sdf.parse("2015-11-10");
//        Object b = Integer.valueOf(3);
        Object c = sdf.parse("2019-10-11");
//        Object c = Integer.valueOf(5);
        
        List<Object> l = new ArrayList<Object>();
        l.add(a);
        l.add(b);
        l.add(c);
        Min m = new Min();
        m.reset();
//        m.insertOperand(a);
//        m.insertOperand(b);
        m.insertListOperand(l);
//        System.out.println("Min of " + a + " and " + b + " is " + m.evaluate() );
        System.out.println("Min of " + l + " is " + m.evaluate() );
    }

}
