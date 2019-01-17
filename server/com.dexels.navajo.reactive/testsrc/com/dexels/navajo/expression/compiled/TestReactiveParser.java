package com.dexels.navajo.expression.compiled;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.parser.compiled.ASTPipeline;
import com.dexels.navajo.parser.compiled.ASTReactivePipe;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.reactive.ClasspathReactiveScriptEnvironment;
import com.dexels.navajo.reactive.CoreReactiveFinder;
import com.dexels.navajo.reactive.ReactiveScriptEnvironment;
import com.dexels.navajo.reactive.ReactiveStandalone;
import com.dexels.navajo.reactive.api.CompiledReactiveScript;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactivePipe;

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
	public void testPipeParser() throws ParseException, IOException {
			ReactiveScriptEnvironment rse = new ClasspathReactiveScriptEnvironment(TestReactiveParser.class);
			CompiledReactiveScript rs = ReactiveStandalone.compileReactiveScript(TestReactiveParser.class.getResourceAsStream("pipe.rr"), Optional.empty());
			int size = rs.pipes.stream().findFirst().get().transformers.size();
			System.err.println("size: "+size);
			Assert.assertEquals(3, size);
	}
	
	@Test
	public void readSingleScript() throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmpty(this.getClass().getResourceAsStream("single.rr"),Optional.empty());
		n.write(System.err);
	}

	@Test
	public void readMultipleScript() throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/multiple.rr");
		int firstSize = n.getMessage("FirstMessage").getArraySize();
		int secondSize = n.getMessage("SecondMessage").getArraySize();
		Assert.assertEquals(5, firstSize);
		Assert.assertEquals(2, secondSize);
		n.write(System.err);
	}

	@Test
	public void readJoinScript() throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/join.rr");
		int i = (Integer) n.getProperty("/Test/sum").getTypedValue();
		n.write(System.err);
		Assert.assertEquals(10, i);
	}


	
	@Test
	public void testReduce( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/reduce.rr");
		int i = (Integer) n.getProperty("/Bla/sum").getTypedValue();
		Assert.assertEquals(105, i);
		n.write(System.err);
	}
	
	@Test
	public void testReduceToList( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/reducetolist.rr");
		Message m = n.getMessage("Test/SubMessage");
		int i = m.getArraySize();
		Assert.assertEquals(30, i);
		
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

	@Test
	public void testCallLocal( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/calllocal.rr");
	}
	
	@Test
	public void testTypeCheck() throws ParseException, IOException {
		ReactiveStandalone.compileReactiveScript(getClass().getResourceAsStream("testtypecheck.rr"), Optional.empty()).typecheck();
	}


	@Test
	public void testTransformerCount() throws ParseException {
//		String exp = "eventsource(classpath='tmlinput.xml')->streamtoimmutable(path='/Bla')->stream(messageName='Oe',isArray=true)";
		String exp = "->single(count=100)->filter([id]%3==0)->filter([id]%2==0)->filter([id]%2==0)";
		CompiledParser cp = new CompiledParser(new StringReader(exp));
		cp.Expression();
		ASTReactivePipe rootNode = (ASTReactivePipe) cp.getJJTree().rootNode();
		ASTPipeline rp = (ASTPipeline) rootNode.jjtGetChild(0);
//		System.err.println("rootNode: "+rp.getClass());
		List<String> problems = new ArrayList<>();
		rootNode.interpretToLambda(problems, "",fn->FunctionClassification.REACTIVE_SOURCE);
//		List<ReactivePipeNode> pipes
//		System.err.println("Args: "+rootNode.args);
//		ReactivePipe src = (ReactivePipe) rootNode.interpretToLambda(problems,"",Reactive.finderInstance().functionClassifier()).apply().value;
//		System.err.println("Sourcetype: "+src.source);
//		int transformers = src.transformers.size();
//		System.err.println("Transformercount: "+transformers);
//		List<ReactivePipe> pp = src.stream().map(e->((ReactivePipe)e.apply().value)).collect(Collectors.toList());

	}
}
