/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


import java.util.Calendar;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;


/**
 * <p>Title: <h3>SportLink Services</h3><br></p>
 * <p>Description: Web Services for the SportLink Project<br><br></p>
 * <p>Copyright: Copyright (c) 2002<br></p>
 * <p>Company: Dexels.com<br></p>
 * @author not attributable
 * @version $Id$
 */

public class GetWeekDayDate extends FunctionInterface {

  @Override
public String remarks() {
    return "This function return the a calendar date of the first given weekday in the past. Optionally a third parameter can be added containing a date. This is the reference date used";
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
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    Object o = this.getOperand(0);
    Object f = this.getOperand(1);
    Object reference = null;
    
    int weekday = -1;
    boolean direction;
    if (getOperands().size()>2) {
        reference = this.getOperand(2);
	}
    if (!(o instanceof Integer)) {
    	if (o instanceof String) {
			weekday = parseWeekday((String)o);
		} else {
	    	throw new TMLExpressionException("Invalid operand type, Integer expected");

		}
    } else {
    	   weekday = ((Integer) o).intValue();
    }
    if (f instanceof Boolean) {
    	direction = ((Boolean)f).booleanValue();
    } else {
    	if (f instanceof String) {
    		String s = (String)f;
    		if (s.startsWith("forward")) {
				direction = false;
			} else if(s.startsWith("back")) {
				direction = true;
			} else {
				throw new TMLExpressionException("For direction operand: Enter 'forwards' or 'backwards'");
			}
		} else {
			throw new TMLExpressionException("Invalid operand type, Boolean OR String: 'forwards'/'backwards' expexted");
		}
		}
    if (reference!=null && !(reference instanceof java.util.Date))
        throw new TMLExpressionException("Invalid operand type, Date expected");

//     Boolean past = (Boolean) f;
    Calendar today = Calendar.getInstance();
    if (reference!=null) {
    	java.util.Date d = (java.util.Date)reference;
		today.setTime(d);
	}
    
    if (today.get(Calendar.DAY_OF_WEEK) == weekday)
         return reset(today, direction);

    int factor = (direction ? -1 : 1);

    for (int i = 1; i <= 8; i++) {
       today.add(Calendar.DAY_OF_WEEK, factor);
       if (today.get(Calendar.DAY_OF_WEEK) == weekday) {
         return reset(today, direction);
       }
    }

    return null;
  }

  private int parseWeekday(String weekday) throws TMLExpressionException {
	  String w = weekday.toLowerCase();
	  if (w.startsWith("sat")) {
		return Calendar.SATURDAY;
	}
	  if (w.startsWith("sun")) {
			return Calendar.SUNDAY;
		}
	  if (w.startsWith("mon")) {
			return Calendar.MONDAY;
		}
	  if (w.startsWith("tue")) {
			return Calendar.TUESDAY;
		}
	  if (w.startsWith("wed")) {
			return Calendar.WEDNESDAY;
		}
	  if (w.startsWith("thu")) {
			return Calendar.THURSDAY;
		}
	  if (w.startsWith("fri")) {
			return Calendar.FRIDAY;
		}
	  throw new  com.dexels.navajo.expression.api.TMLExpressionException("Illegal weekday: "+weekday);
	  
}

@Override
public String usage() {
   return "GetWeekdayDate(Integer weekday / String weekday, Boolean past / String 'forward'/'back', Date referenceDate): Date";
  }

  public static void main (String [] args) throws Exception {
    GetWeekDayDate f = new GetWeekDayDate();
    f.reset();
    f.insertStringOperand("sun");
    f.insertStringOperand("forwards");
    Object o = f.evaluate();
    System.err.println("f = " + o);

  
      f.reset();
    f.insertStringOperand("sat");
    f.insertBooleanOperand(false);
    f.insertOperand(Operand.ofDynamic(o));
    Object o2 = f.evaluate();
    System.err.println("f = " + o2);
  
  }

}
