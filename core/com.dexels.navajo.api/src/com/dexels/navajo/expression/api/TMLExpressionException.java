/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.expression.api;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Title: Description: Copyright: Copyright (c) 2001 Company:
 * 
 * @author
 * @version 1.0
 */

public class TMLExpressionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 447452587961773391L;
	private final String message;

	public TMLExpressionException(String s) {
		this.message = "Invalid expression. " + s;
	}

	public TMLExpressionException(String s, Throwable cause) {
		super(cause);
		this.message = "Invalid expression. " + s;
	}

	public TMLExpressionException(FunctionInterface function, String s, Throwable cause) {
		super(cause);
		String usage = function.usage();
		String remarks = function.remarks();
		this.message = s + "\nUsage: " + usage + "\nRemarks: " + remarks;
	}

	public TMLExpressionException(FunctionInterface function, String s) {
		String usage = function.usage();
		String remarks = function.remarks();
		this.message = s + "\nUsage: " + usage + "\nRemarks: " + remarks;
	}

	public TMLExpressionException(List<String> problems,String expression) {
		this(createProblemMessage(problems, expression));
	}

	private static String createProblemMessage(List<String> problems, String expression) {
		StringBuilder sb = new StringBuilder("Found: ");
		sb.append(problems.size());
		sb.append(" problems evaluating expression: ");
		sb.append(expression);
		sb.append("\n");
		sb.append(problems.stream().collect(Collectors.joining("\n")));
		return sb.toString();
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
