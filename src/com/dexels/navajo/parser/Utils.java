package com.dexels.navajo.parser;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
import java.util.*;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.Property;


public final class Utils extends Exception {

    public Utils() {}

    public final static int MILLIS_IN_DAY = 24 * 60 * 60 * 1000;

    private final static boolean compare(int a, int b, String c) {

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

    private final static boolean compare(Date a, Date b, String c) {
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

    public final static boolean compareDates(Object a, Object b, String compareChar) throws TMLExpressionException {
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
        } else if (b instanceof ClockTime) {
          return (compare(((ClockTime) a).dateValue(), ((ClockTime) b).dateValue(), compareChar));
        }
        else if (b == null) {
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

    public final static double getDoubleValue(Object o) throws TMLExpressionException {
        if (o instanceof Integer)
            return (double) ((Integer) o).intValue();
        else if (o instanceof Double)
            return ((Double) o).doubleValue();
        else if (o instanceof Money)
          return ((Money) o).doubleValue();
        else if (o instanceof Percentage)
          return ((Percentage) o).doubleValue();

        else
          if (o==null) {
            return 0d;
          }
            throw new TMLExpressionException("Invalid type: " + o.getClass().getName());
    }

    public final static String getStringValue(Object o) throws TMLExpressionException {
        if (o instanceof Integer)
            return (((Integer) o).intValue() + "");
        else if (o instanceof Double)
            return (((Double) o).doubleValue() + "");
        else if (o instanceof String)
            return (String) o;
        else if (o instanceof Boolean)
          return o+"";
        else if (o instanceof java.util.Date)
          return Property.dateFormat1.format(o);
        else if (o instanceof Money)
          return ((Money) o).doubleValue() + "";
        else if (o instanceof Percentage)
          return ((Percentage) o).doubleValue() + "";
        else if (o instanceof ClockTime)
          return ((ClockTime) o).toString();
        else
          throw new TMLExpressionException("Unknown type: " + o.getClass().getName());
    }

    /**
     * Generic method to subtract two objects.
     *
     * @param a
     * @param b
     * @return
     * @throws TMLExpressionException
     */
    public final static Object subtract(Object a, Object b) throws TMLExpressionException {
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
        else if ((a instanceof Money || b instanceof Money)) {
          if (! (a instanceof Money || a instanceof Integer || a instanceof Double))
            throw new TMLExpressionException("Invalid argument for operation: " +
                                             a.getClass());
          if (! (b instanceof Money || b instanceof Integer || b instanceof Double))
            throw new TMLExpressionException("Invalid argument for operation: " +
                                             b.getClass());
          Money arg1 = (a instanceof Money ? (Money) a : new Money(a));
          Money arg2 = (b instanceof Money ? (Money) b : new Money(b));
          return new Money(arg1.doubleValue() - arg2.doubleValue());
        }

        else if ((a instanceof Percentage || b instanceof Percentage)) {
          if (! (a instanceof Percentage || a instanceof Integer || a instanceof Double))
            throw new TMLExpressionException("Invalid argument for operation: " +
                                             a.getClass());
          if (! (b instanceof Percentage || b instanceof Integer || b instanceof Double))
            throw new TMLExpressionException("Invalid argument for operation: " +
                                             b.getClass());
          Percentage arg1 = (a instanceof Percentage ? (Percentage) a : new Percentage(a));
          Percentage arg2 = (b instanceof Percentage ? (Percentage) b : new Percentage(b));
          return new Percentage(arg1.doubleValue() - arg2.doubleValue());
        }



        if (a instanceof Date && b instanceof Date) {
          return new Integer((int) ((((Date) a).getTime() - ((Date) b).getTime())/(double) MILLIS_IN_DAY));
        }
        if (a==null || b==null) {
          return null;
        }
        else {
          throw new TMLExpressionException("Unknown  for subtract" );
        }
    }

    /**
     * Generic method to add two objects.
     *
     * @param a
     * @param b
     * @return
     * @throws TMLExpressionException
     */
    public final static Object add(Object a, Object b) throws TMLExpressionException {
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
        } else if ((a instanceof Money || b instanceof Money)) {
          if (!(a instanceof Money || a instanceof Integer || a instanceof Double))
            throw new TMLExpressionException("Invalid argument for operation: " + a.getClass());
          if (!(b instanceof Money || b instanceof Integer || b instanceof Double))
            throw new TMLExpressionException("Invalid argument for operation: " + b.getClass());
          Money arg1 = (a instanceof Money ? (Money) a : new Money(a));
          Money arg2 = (b instanceof Money ? (Money) b : new Money(b));
          return new Money(arg1.doubleValue() + arg2.doubleValue());
        }
        else if ((a instanceof Percentage || b instanceof Percentage)) {
           if (!(a instanceof Percentage || a instanceof Integer || a instanceof Double))
             throw new TMLExpressionException("Invalid argument for operation: " + a.getClass());
           if (!(b instanceof Percentage || b instanceof Integer || b instanceof Double))
             throw new TMLExpressionException("Invalid argument for operation: " + b.getClass());
           Percentage arg1 = (a instanceof Percentage ? (Percentage) a : new Percentage(a));
           Percentage arg2 = (b instanceof Percentage ? (Percentage) b : new Percentage(b));
           return new Percentage(arg1.doubleValue() + arg2.doubleValue());
         }


        else if ((a instanceof ClockTime && b instanceof DatePattern)) {
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
          System.err.println("ba = " + ba.booleanValue() + ", bb = " + bb.booleanValue());
          return new Integer( (ba.booleanValue() ? 1 : 0 ) + (bb.booleanValue() ? 1 : 0 ));
        }
        else
            throw new TMLExpressionException("Addition: Unknown type");
    }

    private final static boolean isEqual(Object a, Object b) throws TMLExpressionException {
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
        } else if (a instanceof Money && b instanceof Money) {
           return (((Money) a).doubleValue() == ((Money) b).doubleValue());
         } else if (a instanceof Percentage && b instanceof Percentage) {
            return (((Percentage) a).doubleValue() == ((Percentage) b).doubleValue());
         }
         else if (a instanceof ClockTime && b instanceof ClockTime) {
          return Utils.compareDates(a, b, "==");
        } else
          /**
           * CHANGED BY FRANK: WANTED TO COMPARE IF TWO OBJECTS ARE IDENTICAL:
           */
          return a == b;
    }

    /**
     * Generic method to determine whether to object are equal.
     *
     * @param a
     * @param b
     * @return
     * @throws TMLExpressionException
     */
    public final static boolean equals(Object a, Object b) throws TMLExpressionException {
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
