package com.dexels.navajo.adapters.binarystore.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class BinaryDigest extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		Binary b = getBinaryOperand(0);
		return b.getDigest().toString();
	}

	public String usage() {
	    return "BinaryDigest(binary)";
	  }
	
	  @Override
	public String remarks() {
	    return "A convertor to query the binary digests from a binary";
	  }
}
