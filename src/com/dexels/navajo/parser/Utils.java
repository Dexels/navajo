
package com.dexels.navajo.parser;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
import java.util.*;


public class Utils extends Exception {

    public Utils() {}

    private static boolean compare(int a, int b, String c) {

        if (c.equals(">"))
            return (a > b);
        else if (c.equals("<"))
            return (a < b);
        else if (c.equals("=="))
            return (a == b);
        else if (c.equals("!="))
            return (a != b);
        else if (c.equals(">="))
            return (a >= b);
        else if (c.equals("<="))
            return (a <= b);
        else
            return false;
    }

    private static boolean compare(Date a, Date b, String c) {
        if (c.equals(">"))
            return (a.after(b));
        else if (c.equals("<"))
            return (a.before(b));
        else if (c.equals("=="))
            return (a.equals(b));
        else if (c.equals("!="))
            return (!a.equals(b));
        else if (c.equals(">="))
            return (a.after(b) || a.equals(b));
        else if (c.equals("<="))
            return (a.before(b) || a.equals(b));
        else
            return false;
    }

    public static boolean compareDates(Object a, Object b, String compareChar) throws TMLExpressionException {
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
                // return (compare(today.getYear(), ((Date) a).getYear(), compareChar));
                return (compare(cal.get(cal.YEAR), cal2.get(cal2.YEAR), compareChar));
            } else
                return (compare(today, (Date) a, compareChar));
        } else if (b instanceof Date) {
            return (compare((Date) a, (Date) b, compareChar));
        } else if (b == null) {
            if (compareChar.equals("==")) {
              if (a == null)
                  return true;
              else
                  return false;
            } else {
              if (a == null)
                return false;
              else
                return true;
            }
        } else
            throw new TMLExpressionException("Invalid date comparison (a =" + a + ", b = " + b + ")");
    }

    public static double getDoubleValue(Object o) throws TMLExpressionException {
        if (o instanceof Integer)
            return (double) ((Integer) o).intValue();
        else if (o instanceof Double)
            return ((Double) o).doubleValue();
        else
            throw new TMLExpressionException("Invalid type: " + o.getClass().getName());
    }

    public static String getStringValue(Object o) throws TMLExpressionException {
        if (o instanceof Integer)
            return (((Integer) o).intValue() + "");
        else if (o instanceof Double)
            return (((Double) o).doubleValue() + "");
        else if (o instanceof String)
            return (String) o;
        else throw new TMLExpressionException("Unknown type: " + o.getClass().getName());
    }

    public static Object subtract(Object a, Object b) throws TMLExpressionException {
        if ((a instanceof Integer) && (b instanceof Integer))
            return new Integer(((Integer) a).intValue() - ((Integer) b).intValue());
        else if ((a instanceof String) || (b instanceof String)) {
            throw new TMLExpressionException("Subtraction not defined for Strings");
        } else if (a instanceof Double && b instanceof Integer)
            return new Double(((Double) a).doubleValue() - ((Integer) b).intValue());
        else if (a instanceof Integer && b instanceof Double)
            return new Double(((Integer) a).intValue() - ((Double) b).doubleValue());
        else if (a instanceof Double && b instanceof Double)
            return new Double(((Double) a).doubleValue() - ((Double) b).doubleValue());
        else
            throw new TMLExpressionException("Unknown type");
    }

    public static Object add(Object a, Object b) throws TMLExpressionException {
        if ((a == null) && (b == null))
            return null;
        else if (a == null)
            return b;
        else if (b == null)
            return a;
        else if ((a instanceof Integer) && (b instanceof Integer))
            return new Integer(((Integer) a).intValue() + ((Integer) b).intValue());
        else if ((a instanceof String) || (b instanceof String)) {
            String sA = Utils.getStringValue(a);
            String sB = Utils.getStringValue(b);

            return sA + sB;
        } else if (a instanceof Double && b instanceof Integer)
            return new Double(((Double) a).doubleValue() + ((Integer) b).intValue());
        else if (a instanceof Integer && b instanceof Double)
            return new Double(((Integer) a).intValue() + ((Double) b).doubleValue());
        else if (a instanceof Double && b instanceof Double)
            return new Double(((Double) a).doubleValue() + ((Double) b).doubleValue());
        else if ((a instanceof DatePattern || a instanceof Date)
                && (b instanceof DatePattern || b instanceof Date)) {
            DatePattern dp1 = null;
            DatePattern dp2 = null;

            if (a instanceof Date)
                dp1 = DatePattern.parseDatePattern((Date) a);
            else
                dp1 = (DatePattern) a;
            if (b instanceof Date)
                dp2 = DatePattern.parseDatePattern((Date) b);
            else
                dp2 = (DatePattern) b;
            dp1.add(dp2);
            return dp1.getDate();
        } else
            throw new TMLExpressionException("Addition: Unknown type");
    }

    private static boolean isEqual(Object a, Object b) throws TMLExpressionException {
        if ((a == null) && (b == null))
            return true;
        else if ((a == null) || (b == null))
            return false;
        else if (a instanceof Integer && b instanceof Integer)
            return (((Integer) a).intValue() == ((Integer) b).intValue());
        else if (a instanceof Integer && b instanceof Double)
            return (((Integer) a).intValue() == ((Double) b).doubleValue());
        else if (a instanceof Double && b instanceof Integer)
            return (((Integer) b).intValue() == ((Double) a).doubleValue());
        else if (a instanceof Double && b instanceof Double)
            return (((Double) b).doubleValue() == ((Double) a).doubleValue());
        else if (a instanceof Boolean && b instanceof Boolean)
            return (((Boolean) a).booleanValue() == ((Boolean) b).booleanValue());
        else if (a instanceof String || b instanceof String) {
            String sA = Utils.getStringValue(a);
            String sB = Utils.getStringValue(b);

            return (sA.equals(sB));
        } else if (a instanceof Date) {
            return Utils.compareDates(a, b, "==");
        } else
            throw new TMLExpressionException("Invalid operands for comparison: " + a + "/" + b);
    }

    public static boolean equals(Object a, Object b) throws TMLExpressionException {
        if (a instanceof ArrayList) {
            boolean result = true;
            ArrayList list = (ArrayList) a;

            for (int i = 0; i < list.size(); i++) {
                boolean dum = isEqual(list.get(i), b);

                if (dum == false)
                    return false;
                result = result && dum;
            }
            return result;
        } else {
            return isEqual(a, b);
        }

    }
}
