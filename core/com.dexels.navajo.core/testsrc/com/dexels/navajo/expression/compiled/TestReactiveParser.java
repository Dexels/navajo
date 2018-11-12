package com.dexels.navajo.expression.compiled;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dexels.navajo.parser.compiled.ASTReactivePipe;
import com.dexels.navajo.parser.compiled.ASTReactiveScriptNode;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.parser.compiled.SimpleNode;
import com.dexels.navajo.parser.compiled.api.ParseMode;

public class TestReactiveParser {

	@Test
	public void readScript() throws ParseException, IOException {
		List<String> problems = new ArrayList<>();
		try(Reader in = new InputStreamReader(this.getClass().getResourceAsStream("examplereactive.rr"))) {
			CompiledParser cp = new CompiledParser(in);
			cp.ReactiveScript();
			SimpleNode sn = (SimpleNode) cp.getJJTree().rootNode();

			ASTReactiveScriptNode n = (ASTReactiveScriptNode) cp.getJJTree().rootNode();
			System.err.println("Count: "+n.jjtGetNumChildren());
			for (int i = 0; i < n.jjtGetNumChildren(); i++) {
				System.err.println("> "+n.jjtGetChild(i).getClass());
					ASTReactivePipe pp = (ASTReactivePipe)n.jjtGetChild(i);
					pp.interpretToLambda(problems, "", ParseMode.DEFAULT);
					System.err.println(">> : "+problems);
//					System.err.println(">< "+pp.jjtGetNumChildren());
//					System.err.println(">><<>> "+n.jjtGetChild(i).getClass());
			}
				
		}
		
		
	}

}
