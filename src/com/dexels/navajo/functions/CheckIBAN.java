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

public final class CheckIBAN extends FunctionInterface {

    public CheckIBAN() {}

    public String remarks() {
        return "Check if the supplied account number is a valid IBAN account";
    }

    public String usage() {
        return "CheckIBAN(String)";
    }

    public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

        Object o = this.getOperands().get(0);

        if (o instanceof String){
        	IBAN iban = new IBAN((String)o);
            return new Boolean(iban.isValid());        
        } else {
            throw new TMLExpressionException("Illegal argument type for function CheckIBAN(): " + o.getClass().getName());
        }
    }

    public static void main(String [] args) {

      String bad = "4511425"; // 4511425
      CheckIBAN e = new CheckIBAN();
      e.reset();
      e.insertOperand((String)bad);
      try {
		System.out.println("Good? " + e.evaluate());
		
		e.reset();
		String good = "GB82WEST12345698765432";
		e.insertOperand((String)good);
	    System.out.println("Good? " + e.evaluate());
	} catch (TMLExpressionException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    }
}
