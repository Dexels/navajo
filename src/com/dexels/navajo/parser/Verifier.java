package com.dexels.navajo.parser;


import com.dexels.navajo.util.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class Verifier {

    public static void tokens(String clause) {

        // SimpleCharStream stream = new SimpleCharStream(new java.io.StringBufferInputStream(clause));
        SimpleCharStream stream = new SimpleCharStream(new java.io.StringReader(clause));
        TMLParserTokenManager m = new TMLParserTokenManager(stream);
        Token t = null;
        String tokenName = "";

        do {
            t = m.getNextToken();
            tokenName = m.tokenImage[t.kind];

        } while (!tokenName.equals("<EOF>"));

    }

    public static boolean verifyExpression(String clause) {

        if (clause.equals(""))
            return true;

        try {
            // TMLParser parser = new TMLParser(new java.io.StringBufferInputStream(clause));
            TMLParser parser = new TMLParser(new java.io.StringReader(clause));

            parser.Expression();
        } catch (Throwable e) {
            System.out.println(e);
            return false;
        }
        if (!clause.startsWith("'")) {
            if ((clause.indexOf("<") != -1) || (clause.indexOf("=") != -1)
                    || (clause.indexOf(">") != -1)
                    || (clause.indexOf("?") != -1))
                return false;
        }
        return true;
    }

    public static boolean verifyCondition(String clause) {
        if (clause.equals(""))
            return true;

        try {
            // TMLParser parser = new TMLParser(new java.io.StringBufferInputStream(clause));
            TMLParser parser = new TMLParser(new java.io.StringReader(clause));

            parser.Expression();
        } catch (Throwable e) {
            System.out.println(e);
            return false;
        }

        if ((clause.indexOf("<") != -1) || (clause.indexOf("=") != -1)
                || (clause.indexOf(">") != -1) || (clause.indexOf("?") != -1))
            return true;
        else
            return false;
    }

    public static void main(String args[]) throws Exception {
        // tokens("$value");
        boolean result = verifyExpression("'* ? / \" ='");


        Operand o = Expression.evaluate("'group_id = '", null);

    }
}
