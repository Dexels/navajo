package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;
import java.util.*;


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

        if (this.getOperands().size() != 2)
          throw new TMLExpressionException("usage: FormatDate(date, format)");

        java.util.Date date = (java.util.Date) this.getOperands().get(0);
        if (date == null)
          return "";

        String format = (String) this.getOperands().get(1);
        if (format == null)
          throw new TMLExpressionException("FormatDate: format cannot be null");

        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);

        return formatter.format(date);
    }

    public String usage() {
        return "FormatDate(date, format)";
    }

    public String remarks() {
        return "";
    }

    public static void main(String args[]) {
        Calendar c = Calendar.getInstance();

        c.set(1949, 3, 2);
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MM-dd-yyyy");

        System.out.println(formatter.format(c.getTime()));

    }
}
