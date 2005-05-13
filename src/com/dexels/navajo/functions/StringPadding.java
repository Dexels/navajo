package com.dexels.navajo.functions;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.dexels.navajo.parser.*;

/**
 * @author: Matthijs Philip.
 * @company: Dexels BV
 * @created: 13-05-2005
 *  
 */

public class StringPadding extends FunctionInterface {

    public String remarks() {
        return "This function ads whitespace (spaces) to a string until it has the specified size";
    }

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    	
    	if (this.getOperands().size() < 2) {
    		throw new TMLExpressionException(this, "Wrong number of arguments");
    	}
    	if (!(getOperand(0) instanceof String)) {
    		throw new TMLExpressionException(this, "Wrong argument type: " + getOperand(0));
    	}
        String object = (String) getOperand(0);
        if (!(getOperand(1) instanceof Integer)) {
    		throw new TMLExpressionException(this, "Wrong argument type: " + getOperand(1));
    	}
        int padSize = ((Integer) getOperand(1)).intValue();
        String padChar = " ";
        boolean padFront = false;

        if (this.getOperands().size() > 2) {
        	if (!(getOperand(2) instanceof String)) {
        		throw new TMLExpressionException(this, "Wrong argument type: " + getOperand(2));
        	}
            padChar = (String) getOperand(2);
        }

        if (this.getOperands().size() > 3) {
        	if (!(getOperand(3) instanceof Boolean) || getOperand(3) == null) {
        		throw new TMLExpressionException(this, "Wrong argument type: " + getOperand(3));
        	}
            padFront = ((Boolean) getOperand(3)).booleanValue();
            System.err.println("padFront = " + padFront);
        }

        while (object.length() < padSize) {
            if (padFront) {
                object = padChar + object;
            } else {
                object = object + padChar;
            }
        }

        return object;
    }

    public String usage() {
        return "StringPadding( String Object, Integer PadToSize, [String PaddingChar (default (' ')], [boolean Post/Pre (default 0)] )";
    }

    public static void main(String[] args) throws Exception {
        StringPadding f = new StringPadding();
        f.reset();        
        f.insertOperand("Voetbal");
        f.insertOperand(new Integer(12));
        f.insertOperand("l");
        f.insertOperand("1");
        Object o = f.evaluate();
        String apenoot = o.toString();
        System.out.println("o.length : " + apenoot.length());
        System.out.println("o = " + o + ", type = " + o.getClass().getName());
    }
}
