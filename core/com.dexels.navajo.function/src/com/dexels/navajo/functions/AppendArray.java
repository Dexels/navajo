/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class AppendArray extends FunctionInterface {

	@Override
	public String remarks() {
		return "Appends the first array message to the second array message. Returns the second message. Note: Call by reference function";
	}

	@Override
	public String usage() {
		return "AppendArray(ArrayMessage, ArrayMessage)";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		// input (ArrayList, Object).
		if (this.getOperands().size() != 2)
			throw new TMLExpressionException("AppendArray(ArrayMessage, ArrayMessage) expected");
		Object a = this.getOperands().get(0);
		if (!(a instanceof Message)) {
			throw new TMLExpressionException("AppendArray(ArrayMessage, ArrayMessage) expected");
		}
		Object b = this.getOperands().get(1);
		if (!(a instanceof Message)) {
			throw new TMLExpressionException("AppendArray(ArrayMessage, ArrayMessage) expected");
		}
		
		Message source2 = (Message) a;
		Message source = (Message) b;
		List<Message> mmm = source2.getAllMessages();
		for (Message message : mmm) {
			source.addMessage(message);
		}
		return source;
	}

}
