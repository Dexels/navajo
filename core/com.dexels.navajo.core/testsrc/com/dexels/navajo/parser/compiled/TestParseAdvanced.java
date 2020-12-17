/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.parser.compiled;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class TestParseAdvanced {
	@Test
	public void parseKv() throws ParseException {
		Reader sr = new InputStreamReader(TestParseAdvanced.class.getResourceAsStream("teststreamdef.rr"));
		CompiledParser cp = new CompiledParser(sr);
		cp.KeyValue();
		List<String> problems = new ArrayList<>();
		Node n = cp.getJJTree().rootNode();
		System.err.println("n:"+n);
//        ContextExpression ss = cp.getJJTree().rootNode().interpretToLambda(problems,sr.toString(),fn->FunctionClassification.DEFAULT);
//        if(!problems.isEmpty()) {
//    			throw new TMLExpressionException(problems,"problem");
//        }
//        ss.apply();
	}
}
