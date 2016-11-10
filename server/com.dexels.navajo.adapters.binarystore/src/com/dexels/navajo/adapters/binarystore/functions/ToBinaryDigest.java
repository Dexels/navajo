package com.dexels.navajo.adapters.binarystore.functions;

import com.dexels.navajo.document.types.BinaryDigest;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class ToBinaryDigest extends FunctionInterface {

	public ToBinaryDigest() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		String parse = (String) getOperand(0);
		return new BinaryDigest(parse);
	}

	public String usage() {
	    return "ToBinaryDigest(digestString)";
	  }
	  @Override
	public String remarks() {
	    return "A convertor to parse binary digests to a binary_digest object";
	  }
}
