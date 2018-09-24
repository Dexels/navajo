package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.stream.ReactiveScript;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParseException;


public class TestExpressionErrors {

	@Before
	public void setup() {
		TestSetup.setup();
	}
	
	@Test
	public void testSingle() throws UnsupportedEncodingException, IOException {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("expressionerrors.xml")) {
			StreamScriptContext myContext = TestSetup.createContext("Single",Optional.empty());
			try {
				ReactiveScript parsedScript = TestSetup.setup().parse(myContext.getService(), in,"serviceName",Optional.of(Type.EVENT));
				System.err.println("Problems: "+parsedScript.problems());
				parsedScript
					.execute(myContext)
					.map(di->di.event())
					.compose(StreamDocument.inNavajo("Single", Optional.empty(), Optional.empty()))
					.lift(StreamDocument.serialize())
					.blockingForEach(e->System.err.print(new String(e)));
				Assert.fail("Parse should have failed.");
			} catch (ReactiveParseException e) {
				e.printStackTrace();
				int probCount = e.problems().size();
				e.problems().forEach(f->System.err.println("Problems: "+f));
				Assert.assertEquals(3, probCount);
			}
		}
	}
}
