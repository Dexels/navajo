package com.dexels.navajo.functions;

import java.util.regex.Pattern;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class ValidatePhoneNumber extends FunctionInterface {
    
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
        String phoneNumberString = getStringOperand(0);
		if (phoneNumberPattern.matcher(phoneNumberString).matches()) {
			return true;
		} else {
			return false;
		}
    }

}
