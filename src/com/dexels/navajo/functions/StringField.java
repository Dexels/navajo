package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;

import java.util.StringTokenizer;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public class StringField extends FunctionInterface {

    public StringField() {// Hallo
    }

    public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object a = this.getOperands().get(0);
        Object b = this.getOperands().get(1);
        Object c = this.getOperands().get(2);

        if (!(a instanceof String) || !(b instanceof String)
                || !(c instanceof Integer))
            throw new TMLExpressionException("StringField(): invalid operand. Usage: " + usage());
        String text = (String) a;
        String seperator = (String) b;
        int field = ((Integer) c).intValue();
        StringTokenizer tokens = new StringTokenizer(text, seperator);
        String result = "";

        for (int i = 0; i < field; i++) {
            result = tokens.nextToken();
        }
        return result.trim();
    }

    public String usage() {
        return "StringField(string, seperator, index)";
    }

    public String remarks() {
        return "This function returns a specified string field given a seperator and an initial string. Eg. StringField('aap, noot, mies', ',', 2) = 'noot'.";
    }
}
