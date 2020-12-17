/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.functions;

import java.nio.charset.Charset;

import org.dexels.utils.Base64;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * @author cbrouwer
 *
 */
public class BasicAuthenticationHeader extends FunctionInterface {

	public BasicAuthenticationHeader() {	
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#remarks()
	 */
	@Override
	public String remarks() {
		return "Returns the HTTP authorization header value for Basic authentication";
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.parser.FunctionInterface#evaluate()
	 */
	@Override
	public Object evaluate() throws TMLExpressionException {
		 if (this.getOperands().size() != 2) {
	            throw new TMLExpressionException("BasicAuthenticationHeader(String, String) expected");
		 }
		 
		 
		String o1 = this.getStringOperand(0);
		String o2 = this.getStringOperand(1);

		String authString =  o1 + ":" + o2;
		byte[] bytes = authString.getBytes(Charset.forName("UTF-8"));
		return "Basic " + Base64.encode(bytes, 0, bytes.length, 0, "");

	}

}
