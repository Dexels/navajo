/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.DatePattern;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.NavajoType;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.types.StopwatchTime;
import com.dexels.navajo.expression.api.TMLExpressionException;

public final class Utils extends Exception {

    private static final long serialVersionUID = -5520295170789410974L;

    private static final int MILLIS_IN_DAY = 24 * 60 * 60 * 1000;

    private Utils() {}

    private static final boolean compare(int a, int b, String c) {

        if (c.equals(">")) {
            return (a > b);
        } else if (c.equals("<")) {
            return (a < b);
        } else if (c.equals("==")) {
            return (a == b);
        } else if (c.equals("!=")) {
            return (a != b);
        } else if (c.equals(">=")) {
            return (a >= b);
        } else if (c.equals("<=")) {
            return (a <= b);
        } else {
            return false;
        }
    }

    private static final boolean compare(Date a, Date b, String c) {

        if (c.equals(">")) {
            return (a.after(b));
        } else if (c.equals("<")) {
            return (a.before(b));
        } else if (c.equals("==")) {
            return (a.equals(b));
        } else if (c.equals("!=")) {
            return (!a.equals(b));
        } else if (c.equals(">=")) {
            return (a.after(b) || a.equals(b));
        } else if (c.equals("<=")) {
            return (a.before(b) || a.equals(b));
        } else {
            return false;
        }
    }

    public static final <T extends Comparable<T>> boolean compare(T a, T b, String c) {

        int compareResult = a.compareTo(b);

        if (c.equals(">")) {
            return compareResult > 0;
        } else if (c.equals("<")) {
            return compareResult < 0;
        } else if (c.equals("==")) {
            return compareResult == 0;
        } else if (c.equals("!=")) {
            return compareResult != 0;
        } else if (c.equals(">=")) {
            return compareResult >= 0;
        } else if (c.equals("<=")) {
            return compareResult <= 0;
        } else {
            return false;
        }
    }

    public static final boolean compareDates(Object a, Object b, String compareChar) {

        if (b instanceof Integer) {
            int offset = ((Integer) b).intValue();
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            Date today = new Date();

            cal.setTime(today);
            cal.add(Calendar.YEAR, -offset);
            cal2.setTime((Date) a);

            today = cal.getTime();
            if (compareChar.equals("==")) {
                return (compare(cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR), compareChar));
            } else {
                return (compare(today, (Date) a, compareChar));
            }
        } else if (b instanceof Date) {
            return (compare((Date) a, (Date) b, compareChar));
        } else if (b instanceof ClockTime) {
            return (compare(((ClockTime) a).dateValue(), ((ClockTime) b).dateValue(), compareChar));
        } else if (b == null) {
            if (compareChar.equals("==")) {
                return a == null;
            } else {
                return a != null;
            }
        }

        throw new TMLExpressionException("Invalid date comparison (a =" + a + ", b = " + b + ")");
    }

    public static final double getDoubleValue(Object o) {

        if (o instanceof Integer) {
            return (Integer) o;
        } else if (o instanceof Long) {
            return (Long) o;
        } else if (o instanceof Double) {
            return (Double) o;
        } else if (o instanceof Money) {
            return ((Money) o).doubleValue();
        } else if (o instanceof Percentage) {
            return ((Percentage) o).doubleValue();
        } else if (o == null) {
            return 0.0;
        }

        throw new TMLExpressionException("Invalid type: " + o.getClass().getName());
    }

    private static final String getStringValue(Object o) {

        if (o instanceof Date) {
            return new SimpleDateFormat(Property.DATE_FORMAT1).format(o);
        } else if (o instanceof Money) {
            return String.valueOf(((Money) o).doubleValue());
        } else if (o instanceof Percentage) {
            return String.valueOf(((Percentage) o).doubleValue());
        } else if (o instanceof Selection) {
            return ((Selection) o).getValue();
        } else if (o != null) {
            return o.toString();
        }

        throw new TMLExpressionException("Unknown type, can't determine type of null value.");
    }

    /**
     * Generic method to subtract two objects.
     */
    public static final Object subtract(Object a, Object b, String expression) {

        if (a instanceof Integer) {
            int inta = (Integer) a;
            if (b instanceof Integer) {
                return inta - (Integer) b;
            } else if (b instanceof Long) {
                return inta - (Long) b;
            } else if (b instanceof Double) {
                return inta - (Double) b;
            }
        } else if (a instanceof Long) {
            long longa = (Long) a;
            if (b instanceof Integer) {
                return longa - (Integer) b;
            } else if (b instanceof Long) {
                return longa - (Long) b;
            } else if (b instanceof Double) {
                return longa - (Double) b;
            }
        } else if (a instanceof Double) {
            double doublea = (Double) a;
            if (b instanceof Integer) {
                return doublea - (Integer) b;
            } else if (b instanceof Long) {
                return doublea - (Long) b;
            } else if (b instanceof Double) {
                return doublea - (Double) b;
            }
        } else if ((a instanceof String) || (b instanceof String)) {
            throw new TMLExpressionException("Subtraction not defined for Strings");
        } else if ((a instanceof Money || b instanceof Money)) {
            if (!(a instanceof Money || a instanceof Integer || a instanceof Double))
                throw new TMLExpressionException("Invalid argument for operation: " + a.getClass()
                        + ", expression: " + expression);
            if (!(b instanceof Money || b instanceof Integer || b instanceof Double))
                throw new TMLExpressionException("Invalid argument for operation: " + b.getClass()
                        + ", expression: " + expression);

            Money arg1 = (a instanceof Money ? (Money) a : new Money(a));
            Money arg2 = (b instanceof Money ? (Money) b : new Money(b));

            return new Money(arg1.doubleValue() - arg2.doubleValue());
        } else if ((a instanceof Percentage || b instanceof Percentage)) {
            if (!(a instanceof Percentage || a instanceof Integer || a instanceof Double))
                throw new TMLExpressionException("Invalid argument for operation: " + a.getClass()
                        + ", expression: " + expression);
            if (!(b instanceof Percentage || b instanceof Integer || b instanceof Double))
                throw new TMLExpressionException("Invalid argument for operation: " + b.getClass()
                + ", expression: " + expression);

            Percentage arg1 = (a instanceof Percentage ? (Percentage) a : new Percentage(a));
            Percentage arg2 = (b instanceof Percentage ? (Percentage) b : new Percentage(b));

            return new Percentage(arg1.doubleValue() - arg2.doubleValue());
        } else if (a instanceof Date && b instanceof Date) {
            // Correct dates for daylight savings time.
            Calendar ca = Calendar.getInstance();
            ca.setTime((Date) a);
            ca.add(Calendar.MILLISECOND, ca.get(Calendar.DST_OFFSET));

            Calendar cb = Calendar.getInstance();
            cb.setTime((Date) b);
            cb.add(Calendar.MILLISECOND, cb.get(Calendar.DST_OFFSET));

            return Integer.valueOf(
                    (int) ((ca.getTimeInMillis() - cb.getTimeInMillis()) / (double) MILLIS_IN_DAY));
        } else if ((a instanceof DatePattern || a instanceof Date)
                && (b instanceof DatePattern || b instanceof Date)) {
            DatePattern dp1 = null;
            DatePattern dp2 = null;

            if (a instanceof Date) {
                dp1 = DatePattern.parseDatePattern((Date) a);
            } else {
                dp1 = (DatePattern) a;
            }

            if (b instanceof Date) {
                dp2 = DatePattern.parseDatePattern((Date) b);
            } else {
                dp2 = (DatePattern) b;
            }

            dp1.subtract(dp2);
            return dp1.getDate();
        } else if ((a instanceof ClockTime || a instanceof Date || a instanceof StopwatchTime)
                && (b instanceof ClockTime || b instanceof Date || b instanceof StopwatchTime)) {
            long myMillis = (a instanceof ClockTime ? ((ClockTime) a).dateValue().getTime()
                    : (a instanceof Date ? ((Date) a).getTime() : ((StopwatchTime) a).getMillis()));
            long otherMillis = (b instanceof ClockTime ? ((ClockTime) b).dateValue().getTime()
                    : (b instanceof Date ? ((Date) b).getTime() : ((StopwatchTime) b).getMillis()));

            return new StopwatchTime((int) (myMillis - otherMillis));
        } else if (a == null || b == null) {
            return null;
        }

        throw new TMLExpressionException("Subtraction: Unknown type. " + " expression: " + expression);
    }

    /**
     * Generic method to add two objects.
     */
    public static final Object add(Object a, Object b, String expression) {

        if ((a == null) && (b == null)) {
            return null;
        } else if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        }

        if (a instanceof Integer) {
            int inta = (Integer) a;
            if (b instanceof Integer) {
                return inta + (Integer) b;
            } else if (b instanceof Long) {
                return inta + (Long) b;
            } else if (b instanceof Double) {
                return inta + (Double) b;
            }
        } else if (a instanceof Long) {
            long longa = (Long) a;
            if (b instanceof Integer) {
                return longa + (Integer) b;
            } else if (b instanceof Long) {
                return longa + (Long) b;
            } else if (b instanceof Double) {
                return longa + (Double) b;
            }
        } else if (a instanceof Double) {
            double doublea = (Double) a;
            if (b instanceof Integer) {
                return doublea + (Integer) b;
            } else if (b instanceof Long) {
                return doublea + (Long) b;
            } else if (b instanceof Double) {
                return doublea + (Double) b;
            }
        } else if ((a instanceof String) || (b instanceof String)) {
            String sA = Utils.getStringValue(a);
            String sB = Utils.getStringValue(b);

            return sA + sB;
        } else if ((a instanceof DatePattern || a instanceof Date)
                && (b instanceof DatePattern || b instanceof Date)) {
            DatePattern dp1 = null;
            DatePattern dp2 = null;

            if (a instanceof Date) {
                dp1 = DatePattern.parseDatePattern((Date) a);
            } else {
                dp1 = (DatePattern) a;
            }

            if (b instanceof Date) {
                dp2 = DatePattern.parseDatePattern((Date) b);
            } else {
                dp2 = (DatePattern) b;
            }

            dp1.add(dp2);
            return dp1.getDate();
        } else if ((a instanceof Money || b instanceof Money)) {
            if (!(a instanceof Money || a instanceof Integer || a instanceof Double))
                throw new TMLExpressionException("Invalid argument for operation: " + a.getClass()
                        + ", expression: " + expression);
            if (!(b instanceof Money || b instanceof Integer || b instanceof Double))
                throw new TMLExpressionException("Invalid argument for operation: " + b.getClass()
                        + ", expression: " + expression);
            Money arg1 = (a instanceof Money ? (Money) a : new Money(a));
            Money arg2 = (b instanceof Money ? (Money) b : new Money(b));

            return new Money(arg1.doubleValue() + arg2.doubleValue());
        } else if ((a instanceof Percentage || b instanceof Percentage)) {
            if (!(a instanceof Percentage || a instanceof Integer || a instanceof Double))
                throw new TMLExpressionException("Invalid argument for operation: " + a.getClass()
                        + ", expression: " + expression);
            if (!(b instanceof Percentage || b instanceof Integer || b instanceof Double))
                throw new TMLExpressionException("Invalid argument for operation: " + b.getClass()
                        + ", expression: " + expression);
            Percentage arg1 = (a instanceof Percentage ? (Percentage) a : new Percentage(a));
            Percentage arg2 = (b instanceof Percentage ? (Percentage) b : new Percentage(b));

            return new Percentage(arg1.doubleValue() + arg2.doubleValue());
        } else if ((a instanceof ClockTime && b instanceof DatePattern)) {
            DatePattern dp1 = DatePattern.parseDatePattern(((ClockTime) a).dateValue());
            DatePattern dp2 = (DatePattern) b;
            dp1.add(dp2);

            return new ClockTime(dp1.getDate());
        } else if ((b instanceof ClockTime && a instanceof DatePattern)) {
            DatePattern dp1 = DatePattern.parseDatePattern(((ClockTime) b).dateValue());
            DatePattern dp2 = (DatePattern) a;
            dp1.add(dp2);

            return new ClockTime(dp1.getDate());
        } else if ((a instanceof ClockTime && b instanceof ClockTime)) {
            DatePattern dp1 = DatePattern.parseDatePattern(((ClockTime) a).dateValue());
            DatePattern dp2 = DatePattern.parseDatePattern(((ClockTime) b).dateValue());
            dp1.add(dp2);

            return new ClockTime(dp1.getDate());
        } else if ((a instanceof Boolean && b instanceof Boolean)) {
            Boolean ba = (Boolean) a;
            Boolean bb = (Boolean) b;

            return Integer.valueOf((ba.booleanValue() ? 1 : 0) + (bb.booleanValue() ? 1 : 0));
        }

        throw new TMLExpressionException("Addition: Unknown type. " + " expression: " + expression);
    }

    /**
     * Fix money == null issue
     */
    private static Object getActualValue(Object a) {

        if (a == null) {
            return null;
        } else {
            if (a instanceof NavajoType) {
                NavajoType n = (NavajoType) a;
                if (n.isEmpty()) {
                    return null;
                } else {
                    return a;
                }
            }
        }

        return a;
    }

    private static final boolean isEqual(Object a, Object b) throws TMLExpressionException {

        Object aValue = getActualValue(a);
        Object bValue = getActualValue(b);

        if ((aValue == null) && (bValue == null)) {
            return true;
        } else if ((aValue == null) || (bValue == null)) {
            return false;
        } else if (aValue instanceof Integer && bValue instanceof Integer) {
            return (((Integer) aValue).intValue() == ((Integer) bValue).intValue());
        } else if (aValue instanceof Integer && bValue instanceof Double) {
            return (((Integer) aValue).intValue() == ((Double) bValue).doubleValue());
        } else if (aValue instanceof Double && bValue instanceof Integer) {
            return (((Integer) bValue).intValue() == ((Double) aValue).doubleValue());
        } else if (aValue instanceof Double && bValue instanceof Double) {
            return (((Double) bValue).doubleValue() == ((Double) aValue).doubleValue());
        } else if (aValue instanceof Boolean && bValue instanceof Boolean) {
            return (((Boolean) aValue).booleanValue() == ((Boolean) bValue).booleanValue());
        } else if (aValue instanceof String || bValue instanceof String) {
            String sA = Utils.getStringValue(aValue);
            String sB = Utils.getStringValue(bValue);
            return (sA.equals(sB));
        } else if (aValue instanceof Date) {
            return Utils.compareDates(aValue, bValue, "==");
        } else if (aValue instanceof Money && bValue instanceof Money) {
            return (((Money) aValue).doubleValue() == ((Money) bValue).doubleValue());
        } else if (aValue instanceof Percentage && bValue instanceof Percentage) {
            return (((Percentage) aValue).doubleValue() == ((Percentage) bValue).doubleValue());
        } else if (aValue instanceof ClockTime && bValue instanceof ClockTime) {
            return Utils.compareDates(aValue, bValue, "==");
        } else if (aValue instanceof Binary && bValue instanceof Binary) {
            return ((Binary) aValue).isEqual((Binary) bValue);
        } else {
            /**
             * CHANGED BY FRANK: WANTED TO COMPARE IF TWO OBJECTS ARE IDENTICAL:
             */
            return aValue == bValue;
        }
    }

    /**
     * Generic method to determine whether two objects are equal.
     */
    public static final boolean equals(Object a, Object b, String expression)
            throws TMLExpressionException {

        if (a instanceof List) {
            List<?> list = (List<?>) a;

            if (list.size() == 0) {
                return false;
            }

            for (int i = 0; i < list.size(); i++) {
                if (!isEqual(list.get(i), b)) {
                    return false;
                }
            }

            return true;
        } else {
            return isEqual(a, b);
        }
    }

}
