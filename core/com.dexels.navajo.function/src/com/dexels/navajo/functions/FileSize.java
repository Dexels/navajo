/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/

package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;

/**
 * @author Jarno Posthumus
 */
public class FileSize extends FunctionInterface {

	public FileSize() {
	}
	
	@Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
		
		if (getOperand(0) == null) {
			return Integer.valueOf(0);
		}
		
		Binary binaryFile = (Binary)getOperand(0);
		
		return Integer.valueOf( (int) binaryFile.getLength());
    }
	
	@Override
	public String remarks() {
	    return "Get the filesize of supplied Binary object in bytes.";
	}
}
