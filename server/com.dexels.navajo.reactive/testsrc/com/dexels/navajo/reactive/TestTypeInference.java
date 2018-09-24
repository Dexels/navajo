package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;

public class TestTypeInference {
	private ReactiveScriptParser reactiveScriptParser;

	@Before
	public void setup() {
		reactiveScriptParser = TestSetup.setup();
	}

	@Test
	public void testSingleSource() throws UnsupportedEncodingException, IOException {
		SingleSourceFactory ssf = new SingleSourceFactory();
		ReactiveParameters parameters = ReactiveParameters.empty()
				.withConstant("debug", true, Property.BOOLEAN_PROPERTY)
				.withConstant("count", 10, Property.INTEGER_PROPERTY);
		List<ReactiveParseProblem> problems = new ArrayList<>();
		StreamScriptContext context = TestSetup.createContext("Single",Optional.empty());
		ssf.build("",parameters, problems, Collections.emptyList(), DataItem.Type.MESSAGE)
			.execute(context, Optional.empty())
			.map(e->e.message())
			.blockingForEach(e->System.err.println("S: "+e.toFlatString(ImmutableFactory.getInstance())));
	}
}
