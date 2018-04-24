package com.dexels.navajo.adapters.binarystore.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class BinaryDigest extends FunctionInterface {

	public BinaryDigest() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		Binary b = (Binary) getOperand(0);
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
