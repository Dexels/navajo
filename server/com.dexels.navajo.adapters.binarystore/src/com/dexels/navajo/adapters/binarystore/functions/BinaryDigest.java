package com.dexels.navajo.adapters.binarystore.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;

public class BinaryDigest extends FunctionInterface {

	@Override
	public Object evaluate() {
		Binary b = getBinaryOperand(0);
		return b.getDigest().toString();
	}

	@Override
	public String usage() {
	    return "BinaryDigest(binary)";
	  }
	
	  @Override
	public String remarks() {
	    return "A convertor to query the binary digests from a binary";
	  }
}
