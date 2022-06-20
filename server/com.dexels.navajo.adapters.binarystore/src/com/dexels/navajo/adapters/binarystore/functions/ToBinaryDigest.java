/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
