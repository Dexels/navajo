package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;
import java.util.*;
import com.dexels.navajo.document.types.ClockTime;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class FormatDate extends FunctionInterface {

    public FormatDate() {}

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

        if (this.getOperands().size() < 2 || this.getOperands().size() > 3)
          throw new TMLExpressionException(this.usage());

        java.util.Date date = null;
        
        if (getOperands().get(0) instanceof ClockTime) {
        	date = ((ClockTime) getOperands().get(0)).dateValue();
        } else if (getOperands().get(0) instanceof Date) {
        	date = (java.util.Date) getOperands().get(0);
        } else {
        	throw new TMLExpressionException(this, "Type mismatch");
        }


        String format = (String) this.getOperands().get(1);
        if (format == null)
          throw new TMLExpressionException("FormatDate: format cannot be null");

        java.text.SimpleDateFormat formatter = null;

        if (this.getOperands().size() > 2) {
          final String loc = (String)this.getOperands().get(2);
          if (loc != null && loc.length() > 0) {
            final Locale l = new Locale(loc);
            if (l == null) {
              throw new TMLExpressionException(
                  "FormatDate: unavailable or invalid locale: '" + loc + "'");
            }
            formatter = new java.text.SimpleDateFormat(format, l);
          }
        }
        if (formatter == null) {
          formatter = new java.text.SimpleDateFormat(format);
        }

        return formatter.format(date);
    }

    public String usage() {
        return "FormatDate(date, format[, locale])";
    }

    public String remarks() {
        return "formats a date according to a format string plus an optional locale";
    }

    public static void main(String args[]) {
        Calendar c = Calendar.getInstance();

        {
          c.set(1949, 3, 2);
          java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
              "MM-dd-yyyy");

          System.out.println(formatter.format(c.getTime()));
        }
        {
          c.set(1958, 3, 27);
          java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
              "dd-MMM-yy", new Locale("nl"));

          System.out.println(formatter.format(c.getTime()));
        }

    }
}
