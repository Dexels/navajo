package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class ElfProef extends FunctionInterface {

    public ElfProef() {}

    public String remarks() {
        return "Check if the supplied account number is a valid account (Dutch banks only).";
    }

    public String usage() {
        return "ElfProef(String|Integer)";
    }

    public final boolean elfProef(String nummer) {

        boolean result = false;

        if (nummer.length() != 9)
            result = false;

        int total = 0;

        for (int i = 0; i < (nummer.length()); i++) {
            int digit = Integer.parseInt(nummer.charAt(i) + "");

            digit = (9 - i) * digit;
            total += digit;
        }

        if (total % 11 == 0)
            result = true;
        else
            result = false;
        return result;
    }

    public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

        Object o = this.getOperands().get(0);

        if (o instanceof String)
            return new Boolean(elfProef((String) o));
        else if (o instanceof Integer)
            return new Boolean(elfProef(((Integer) o).intValue() + ""));
        else {
            throw new TMLExpressionException("Illegal argument type for function ElfProef(): " + o.getClass().getName());
        }
    }

    public static void main(String [] args) {

      String n = "307617769";
      ElfProef e = new ElfProef();
      System.out.println(e.elfProef(n));
    }
}
