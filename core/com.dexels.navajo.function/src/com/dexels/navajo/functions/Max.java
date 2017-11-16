package com.dexels.navajo.functions;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.Utils;

public final class Max extends FunctionInterface {

    @Override
	public String remarks() {
        return "Return the maximum of two given numbers or two given dates.";
    }

    @Override
	public String usage() {
        return "Max(number, number), where number can be of type integer or double, or Max(date, date)";
    }

    @Override
	@SuppressWarnings("rawtypes")
	public final Object evaluate() throws TMLExpressionException {

        List<?> operands = this.getOperands();

        if (operands.size() == 2) {
            Object a = operands.get(0);
            Object b = operands.get(1);
            return max(a, b);
        } else if (operands.size() == 1) {
            // List as argument.
            Object a = operands.get(0);

            if (!(a instanceof List))
                throw new TMLExpressionException("Invalid number of arguments for Max()");
            List list = (List) a;
            Object currentMax = list.isEmpty() ? null : list.get(0);

            for (int i = 1; i < list.size(); i++) {
                Object b = list.get(i);
                currentMax = max(currentMax, b);
            }
            return currentMax;
        } else
            throw new TMLExpressionException("Invalid number of arguments for Max()");
    }
    
    private Object max(Object a, Object b)
    {
        if (a instanceof Date)
        {
            if (!(b instanceof Date))
            {
                throw new TMLExpressionException("Cannot compare a date with a non-date");
            }
            else
            {
                if (((Date) a).compareTo((Date) b) > 1)
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
                double max = (value1 > value2) ? value1 : value2;

                if (a instanceof Double || b instanceof Double)
                    return new Double(max);
                else
                    return new Integer((int) max);
                
            }
        }
    }
    
    public static void main(String[] args) throws Throwable {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Object a = sdf.parse("2017-11-10");
        Object a = Math.PI;
//        Object b = sdf.parse("2015-11-10");
        Object b = new Integer(3);
//        Object c = sdf.parse("2019-10-11");
        Object c = new Integer(5);
        
        List<Object> l = new ArrayList<Object>();
        l.add(a);
        l.add(b);
        l.add(c);
        Max m = new Max();
        m.reset();
//        m.insertOperand(a);
//        m.insertOperand(b);
        m.insertOperand(l);
//        System.out.println("Max of " + a + " and " + b + " is " + m.evaluate() );
        System.out.println("Max of " + l + " is " + m.evaluate() );
    }
}
