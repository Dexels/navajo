package com.dexels.navajo.expression.compiled;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.reactive.ClasspathReactiveScriptEnvironment;
import com.dexels.navajo.reactive.CoreReactiveFinder;
import com.dexels.navajo.reactive.ReactiveScriptEnvironment;
import com.dexels.navajo.reactive.ReactiveStandalone;
import com.dexels.navajo.reactive.api.Reactive;

public class TestReactiveParser {
	
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
//		ReactiveBuildContext buildContext = ReactiveBuildContext.of(source->finder.getSourceFactory(source), (transformer,type)->finder.getTransformerFactory(transformer), reducer->finder.getMergerFactory(reducer), finder.transformerFactories(), finder.reactiveMappers(), true);
			ReactiveScriptEnvironment rse = new ClasspathReactiveScriptEnvironment(TestReactiveParser.class);
			
			Navajo n =  ReactiveStandalone.runBlockingEmpty(rse,"filter",Optional.empty(), Collections.emptyList());
			
//			Navajo n = ReactiveStandalone.runBlockingEmpty(this.getClass().getResourceAsStream("filter.rr"),Optional.empty());
			int size = n.getMessage("Blem").getArraySize();
			System.err.println("size: "+size);
			n.write(System.err);
			Assert.assertEquals(2, size);
	}
	
	@Test
	public void testPipe() throws ParseException, IOException {
//		ReactiveBuildContext buildContext = ReactiveBuildContext.of(source->finder.getSourceFactory(source), (transformer,type)->finder.getTransformerFactory(transformer), reducer->finder.getMergerFactory(reducer), finder.transformerFactories(), finder.reactiveMappers(), true);
			ReactiveScriptEnvironment rse = new ClasspathReactiveScriptEnvironment(TestReactiveParser.class);
			
			Navajo n =  ReactiveStandalone.runBlockingEmpty(rse,"pipe",Optional.empty(), Collections.emptyList());
			n.write(System.err);
//			Navajo n = ReactiveStandalone.runBlockingEmpty(this.getClass().getResourceAsStream("filter.rr"),Optional.empty());
			int size = n.getMessage("Blem").getArraySize();
			System.err.println("size: "+size);
			n.write(System.err);
			Assert.assertEquals(5, size);
	}
	
	@Test
	public void readSingleScript() throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmpty(this.getClass().getResourceAsStream("single.rr"),Optional.empty());
		n.write(System.err);
	}

	@Test
	public void readMultipleScript() throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/multiple.rr");
		n.write(System.err);
	}

	
	
	
	@Test
	public void readJoinScript() throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/join.rr");
		n.write(System.err);
	}


	
	@Test
	public void testReduce( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/reduce.rr");
		n.write(System.err);
	}
	
	@Test
	public void testReduceToList( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/reducetolist.rr");
		n.write(System.err);
	}
	
	@Test
	public void testJoinSpecific( ) throws ParseException, IOException {
			Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/joinspecific.rr");
			n.write(System.err);
			Message m = n.getMessage("Test");
			Assert.assertEquals(3, m.getProperty("someint").getTypedValue());
			Assert.assertEquals("subprop", m.getProperty("sub").getTypedValue());
			Assert.assertEquals("subprop", m.getProperty("moved").getTypedValue());
	}

	@Test
	public void testDelay( ) throws ParseException, IOException {
		long now = System.currentTimeMillis();
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/delay.rr");
		long elapsed = System.currentTimeMillis() - now;
		Assert.assertTrue(elapsed>500);
	}

	@Test
	public void testMethods( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/methods.rr");
		n.write(System.err);
		Assert.assertEquals(1,n.getAllMethods().size());
	}
	
	@Test
	public void testEventStream( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/eventstream.rr");
		n.write(System.err);
		Message m = n.getMessage("Oe");
		int size = m.getArraySize();
		Assert.assertEquals(2, size);
		Integer val = (Integer) m.getMessage(1).getProperty("jet").getTypedValue();
		Assert.assertEquals(1, val.intValue());
	}
	
	@Test
	public void testStream( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/impliciteventstreamparse.rr");
		n.write(System.err);
		Message m = n.getMessage("Oe");
		int size = m.getArraySize();
		Assert.assertEquals(2, size);
		Integer val = (Integer) m.getMessage(1).getProperty("jet").getTypedValue();
		Assert.assertEquals(1, val.intValue());
	}
	
	@Test
	public void testInput() throws ParseException, IOException {
		Navajo input = NavajoFactory.getInstance().createNavajo(ReactiveStandalone.class.getClassLoader().getResourceAsStream("tmlinput.xml"));
		try(InputStream in = ReactiveStandalone.class.getClassLoader().getResourceAsStream("com/dexels/navajo/expression/compiled/input.rr")) {
			Navajo n = ReactiveStandalone.runBlockingInput(in, Optional.empty(),input);
			n.write(System.err);
		}
		
	}


}
