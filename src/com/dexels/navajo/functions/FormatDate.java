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

public class FormatDate extends FunctionInterface {

    public FormatDate() {}

    public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        java.util.Date date = (java.util.Date) this.getOperands().get(0);
        String format = (String) this.getOperands().get(1);

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
