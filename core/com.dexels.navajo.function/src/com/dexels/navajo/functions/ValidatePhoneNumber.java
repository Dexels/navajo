package com.dexels.navajo.functions;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class ValidatePhoneNumber extends FunctionInterface {
    
	private final static Logger logger = LoggerFactory.getLogger(ValidatePhoneNumber.class);
	// Only exclude unwanted chars. The length is not a part of the check.
	private static final Pattern phoneNumberPattern = Pattern.compile("^([0-9\\(\\)\\/\\+ \\-]*)$");
    
    public ValidatePhoneNumber() {}
    
    @Override
	public String remarks() {
        return "Check if the supplied phone number is valid";
    }

    @Override
	public String usage() {
        return "ValidatePhoneNumber(String|Long)";
    }
    @Override
	public boolean isPure() {
    		return false;
    }

	@Override
    public Object evaluate() throws TMLExpressionException {
        Object o1 = this.getOperands().get(0);
        
        // No need to check if everything is missing
        if (o1 == null || o1.equals("")) {
            throw new TMLExpressionException("Phonenumber argument is missing or null ValidatePhoneNumber()");
        }
        
		if (phoneNumberPattern.matcher((CharSequence) o1).matches()) {
			return true;
		} else {
			return false;
		}
    }

    public static void main(String [] args) {

        ValidatePhoneNumber e1 = new ValidatePhoneNumber();
        e1.reset();
        e1.insertOperand("+31 (0)20 490 4977");
        ValidatePhoneNumber e2 = new ValidatePhoneNumber();
        e2.reset();
        e2.insertOperand("020-4904977");
        try {
            System.out.println(e1.getOperand(0) + " - " + e1.evaluate());
            System.out.println(e2.getOperand(0) + " - " + e2.evaluate());
        } catch (TMLExpressionException ee) {
        	logger.error("Error: ", ee);
        }
      }
}
