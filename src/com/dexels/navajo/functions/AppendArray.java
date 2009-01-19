package com.dexels.navajo.functions;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class AppendArray extends FunctionInterface {

	public String remarks() {
		return "Appends the first array message to the second array message. Returns the second message. Note: Call by reference function";
	}

	public String usage() {
		return "AppendArray(ArrayMessage, ArrayMessage)";
	}

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
