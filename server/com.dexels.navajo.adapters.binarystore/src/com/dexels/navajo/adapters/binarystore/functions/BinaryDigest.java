/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
