package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.ReactiveScript;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.expression.compiled.TestReactiveParser;
import com.dexels.navajo.parser.compiled.ASTReactiveScriptNode;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.reactive.api.CompiledReactiveScript;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactiveFinder;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactivePipe;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;

public class TestStream {
	private ReactiveFinder resolver;
	private CoreReactiveFinder finder;

	
	@Before
	public void setup() {
		finder = new CoreReactiveFinder();
		Reactive.setFinderInstance(finder);
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		Reactive.setFinderInstance(finder);
	}

	@Test
	public void testFilter() throws ParseException, IOException {
//		ReactiveScriptEnvironment rse = new ClasspathReactiveScriptEnvironment(TestReactiveParser.class);
		ASTReactiveScriptNode sc = ReactiveStandalone.parseExpression(getClass().getResourceAsStream("stream.rr"));
		int children = sc.jjtGetNumChildren();
		sc.dump("");
		Assert.assertEquals(2, children);
		
	}
	
}
