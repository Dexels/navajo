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

public final class FormatStringList extends FunctionInterface {

    public FormatStringList() {}

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object a = this.getOperands().get(0);
        Object b = this.getOperands().get(1);

        if (a instanceof String)
            return (String) a;
        if (!(a instanceof ArrayList))
            throw new TMLExpressionException("FormatStringList: invalid operand: " + a.getClass().getName());
        if (!(b instanceof String))
            throw new TMLExpressionException("FormatStringList: invalid operand: " + a.getClass().getName());
        ArrayList strings = (ArrayList) a;
        String sep = (String) b;
        StringBuffer result = new StringBuffer(20 * strings.size());

        for (int i = 0; i < strings.size(); i++) {
            String el = (String) strings.get(i);

            result.append(el);
            if (i < (strings.size() - 1))
                result.append(sep);
        }
        return result.toString();
    }

    public String usage() {
        return "FormatStringList(list of Strings, separator). Example FormatStringList(\"{\"Navajo\", \"Dexels\"}\", \";\") returns \"Navajo;Dexels\"";
    }

    public String remarks() {
        return "";
    }

    public static void main(String args[]) throws Exception {
        ArrayList a = new ArrayList();

        a.add("Navajo");
        a.add("Dexels");
        FormatStringList l = new FormatStringList();

        l.reset();
        l.insertOperand(a);
        l.insertOperand(";");
        System.out.println(l.evaluate());
    }
}
