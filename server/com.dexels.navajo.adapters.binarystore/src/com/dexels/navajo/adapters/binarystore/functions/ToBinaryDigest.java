package com.dexels.navajo.adapters.binarystore.functions;

import com.dexels.navajo.document.types.BinaryDigest;
import com.dexels.navajo.expression.api.FunctionInterface;

public class ToBinaryDigest extends FunctionInterface {

	@Override
	public Object evaluate() {
		String parse = getStringOperand(0);
		return new BinaryDigest(parse);
	}

	@Override
	public String usage() {
	    return "ToBinaryDigest(digestString)";
	  }
	@Override
	public String remarks() {
	    return "A convertor to parse binary digests to a binary_digest object";
	  }
}
