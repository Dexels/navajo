/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public final class WeekDay extends FunctionInterface {

  private final static Locale locale = new Locale("en_US");

  public WeekDay() {
  }

  @Override
public String remarks() {
    return "Return a three letter string with the weekday; If no argument is "
        +  "provided return the weekday for today; Only english locale is "
        +  "supported now; The result is return in upper case";
  }

  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

	Object o = null;
	
	try {
		o = getOperand(0);
	} catch (Exception e) {
		o = new java.util.Date();
	}
	
    java.util.Date day = null;

    if (o == null ) {
      // take today
      day = Calendar.getInstance(locale).getTime();
    }
    else if (o instanceof java.util.Date) {
      day = (java.util.Date) o;
    } else if (o instanceof String) {
      java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
//      java.util.Date date = null;

      try {
          day = format.parse((String) o);
      } catch (Exception e) {
          throw new TMLExpressionException("Invalid date format: " + (String) o);
      }
    } else {
      throw new TMLExpressionException("Invalid date: " + o);
    }

    return new SimpleDateFormat("EEE", locale).format(day).toUpperCase();
  }

  @Override
public String usage() {
    return "WeekDay([ Date | Date string ]): String";
  }

  public static void main(String [] args) throws Exception {

    // Tests.
    WeekDay wd = new WeekDay();

    wd.reset();
    wd.insertOperand(Operand.NULL);
    System.out.println("result = " + wd.evaluate().toString());

    wd.reset();
    wd.insertStringOperand("2013-07-16");
    System.out.println("result = " + wd.evaluate().toString());

    wd.reset();
    wd.insertDateOperand(new java.util.Date(System.currentTimeMillis()));
    System.out.println("result = " + wd.evaluate().toString());

  }

}
