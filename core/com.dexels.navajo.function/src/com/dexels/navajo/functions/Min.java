/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.functions;

import java.util.Date;
import java.util.List;

import com.dexels.navajo.document.types.ClockTime;
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
        return "Min(number, number), where number can be of type integer, long, or double, or Min(date, date)";
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
            if (!(a instanceof List)) {
                throw new TMLExpressionException("Invalid number of arguments for Min()");
            }

            List<?> list = (List<?>) a;
            Object currentMin = list.isEmpty() ? null : list.get(0);

            for (int i = 1; i < list.size(); i++) {
                Object b = list.get(i);
                currentMin = min(currentMin, b);
            }

            return currentMin;
        }

        throw new TMLExpressionException("Invalid number of arguments for Max()");
    }

    private Object min(Object a, Object b) {

        if (a instanceof Date || a instanceof ClockTime) {
            if (b == null) {
                return a;
            } else if (!(b instanceof Date || b instanceof ClockTime)) {
                throw new TMLExpressionException(
                        "Cannot compare a date/clocktime with a non-date/clocktime");
            } else {
                Date aValue = a instanceof ClockTime ? ((ClockTime) a).dateValue() : (Date) a;
                Date bValue = b instanceof ClockTime ? ((ClockTime) b).dateValue() : (Date) b;
                if (aValue.compareTo(bValue) < 0) {
                    return a;
                } else {
                    return b;
                }
            }
        } else if (b instanceof Date || b instanceof ClockTime) {
            if (a == null) {
                return b;
            } else {
                throw new TMLExpressionException(
                        "Cannot compare a date/clocktime with a non-date/clocktime");
            }
        } else {
            double value1 = Utils.getDoubleValue(a);
            double value2 = Utils.getDoubleValue(b);
            double min = (value1 < value2) ? value1 : value2;

            if (a instanceof Double || b instanceof Double) {
                return Double.valueOf(min);
            } else {
                return Integer.valueOf((int) min);
            }
        }
    }

}
