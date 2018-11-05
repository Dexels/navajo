package com.dexels.navajo.expression.compiled;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.parser.compiled.ASTReactiveElementNode;
import com.dexels.navajo.parser.compiled.ASTReactiveScriptNode;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.Node;
import com.dexels.navajo.parser.compiled.ParseException;

public class TestReactiveParser {

	@Test @Ignore
	public void readScript() throws ParseException, IOException {
		List<String> problems = new ArrayList<>();
		try(Reader in = new InputStreamReader(this.getClass().getResourceAsStream("examplereactive.rr"))) {
			CompiledParser cp = new CompiledParser(in);
			cp.ReactiveScript();
			ASTReactiveScriptNode n = (ASTReactiveScriptNode) cp.getJJTree().rootNode();
			for (int i = 0; i < n.jjtGetNumChildren(); i++) {
				Node nn = n.jjtGetChild(i);
				System.err.println("Node: "+nn.getClass());
				System.err.println(">>>> "+nn.interpretToLambda(problems, ""));
				
				
			} ;
			System.err.println("Problems: "+problems);
			System.err.println(n);
		}
		
		
	}

}
