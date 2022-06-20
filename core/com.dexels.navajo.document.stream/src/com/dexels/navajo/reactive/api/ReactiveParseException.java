/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.api;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.dexels.navajo.document.stream.ReactiveParseProblem;

public class ReactiveParseException extends RuntimeException {

	private static final long serialVersionUID = 6115559675419129641L;
	private List<ReactiveParseProblem> problems;

	public ReactiveParseException() {
		this.problems = Collections.emptyList();
	}

	public ReactiveParseException(String message) {
		super(message);
		this.problems = Collections.emptyList();
	}
	public ReactiveParseException(String message, List<ReactiveParseProblem> problems) {
		this(message+"\n"+problems.stream().map(e->e.toString()).collect(Collectors.joining("\n")));
		this.problems = problems;
	}

	public ReactiveParseException(Throwable cause) {
		super(cause);
		this.problems = Collections.emptyList();
	}

	public ReactiveParseException(String message, Throwable cause, List<ReactiveParseProblem> problems) {
//		String total = message+problems.stream().map(e->e.toString()).collect(Collectors.joining("\n"));
		this(message+"\n"+problems.stream().map(e->e.toString()).collect(Collectors.joining("\n")), cause);
		this.problems = problems;
	}

	public ReactiveParseException(String message, Throwable cause) {
		super(message, cause);
		this.problems = Collections.emptyList();
	}

	public ReactiveParseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.problems = Collections.emptyList();
	}

	public List<ReactiveParseProblem> problems() {
		return this.problems;
	}

}
