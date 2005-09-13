package com.dexels.navajo.functions;

import java.io.*;
import java.net.*;
import java.util.*;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class GetCurrentMessage extends FunctionInterface {

	public String remarks() {
		return "This function will return the current message";
	}

	public String usage() {
		
		return "Used to check if an URL is responding.";
	}

	public Object evaluate() throws TMLExpressionException {
		return getCurrentMessage();
	}
}
