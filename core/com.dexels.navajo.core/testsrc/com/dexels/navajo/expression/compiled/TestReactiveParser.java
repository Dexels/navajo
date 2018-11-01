package com.dexels.navajo.expression.compiled;

import java.io.InputStreamReader;
import java.io.Reader;

import org.junit.Test;

import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;

public class TestReactiveParser {

	@Test
	public void readScript() throws ParseException {
		Reader in = new InputStreamReader(this.getClass().getResourceAsStream("examplereactive.rr"));
		CompiledParser cp = new CompiledParser(in);
		cp.ReactiveElement();
		
	}

}
