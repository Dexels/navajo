package com.dexels.navajo.expression.compiled;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.ReactiveScript;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.reactive.ClasspathReactiveScriptEnvironment;
import com.dexels.navajo.reactive.CoreReactiveFinder;
import com.dexels.navajo.reactive.ReactiveScriptEnvironment;
import com.dexels.navajo.reactive.ReactiveStandalone;
import com.dexels.navajo.reactive.api.CompiledReactiveScript;
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
		ReactiveScriptEnvironment rse = new ClasspathReactiveScriptEnvironment(TestReactiveParser.class);
		Navajo n =  ReactiveStandalone.runBlockingEmpty(rse,"filter");
		int size = n.getMessage("Blem").getArraySize();
		System.err.println("size: "+size);
		n.write(System.err);
		Assert.assertEquals(2, size);
	}
	
	@Test
	public void testPipe() throws ParseException, IOException {
//		ReactiveBuildContext buildContext = ReactiveBuildContext.of(source->finder.getSourceFactory(source), (transformer,type)->finder.getTransformerFactory(transformer), reducer->finder.getMergerFactory(reducer), finder.transformerFactories(), finder.reactiveMappers(), true);
			ReactiveScriptEnvironment rse = new ClasspathReactiveScriptEnvironment(TestReactiveParser.class);
			
			Navajo n =  ReactiveStandalone.runBlockingEmpty(rse,"pipe");
			n.write(System.err);
//			Navajo n = ReactiveStandalone.runBlockingEmpty(this.getClass().getResourceAsStream("filter.rr"),Optional.empty());
			int size = n.getMessage("Result").getArraySize();
			System.err.println("size: "+size);
			n.write(System.err);
			Assert.assertEquals(5, size);
	}
	
	@Test
	public void testPipeParser() throws ParseException, IOException {
			ReactiveScriptEnvironment rse = new ClasspathReactiveScriptEnvironment(TestReactiveParser.class);
			CompiledReactiveScript rs = ReactiveStandalone.compileReactiveScript(TestReactiveParser.class.getResourceAsStream("pipe.rr"));
			int size = rs.pipes.stream().findFirst().get().transformers.size();
			System.err.println("size: "+size);
			Assert.assertEquals(3, size);
	}
	
	@Test
	public void readSingleScript() throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmpty(this.getClass().getResourceAsStream("single.rr"));
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
	public void readJoinSimpleScript() throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/joinsimple.rr");
		n.write(System.err);
		Assert.assertEquals("outer", n.getProperty("Test/outer").getTypedValue());
		Assert.assertEquals("inner", n.getProperty("Test/inner").getTypedValue());
		Assert.assertEquals("inner", n.getProperty("Test/innername").getTypedValue());
//		Assert.assertEquals(10, i);
	}
	

	
	@Test
	public void testReduce( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/reduce.rr");
		int i = (Integer) n.getProperty("/Bla/sum").getTypedValue();
		Assert.assertEquals(56, i);
		n.write(System.err);
	}
	
	@Test
	public void testReduceSimple( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/reducesimple.rr");
		int i = (Integer) n.getProperty("/Bla/sum").getTypedValue();
		Assert.assertEquals(15, i);
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
	public void testAddressSubMessage( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/addresssubmessage.rr");
		n.write(System.err);
		String val = (String) n.getMessage("Test/bla").getProperty("prop2").getTypedValue();
		
		Assert.assertEquals("prop2value",val);
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
			Navajo n = ReactiveStandalone.runBlockingInput(in, input);
			n.write(System.err);
		}
		
	}

	@Test
	public void testTypeCheck() throws ParseException, IOException {
		ReactiveStandalone.compileReactiveScript(getClass().getResourceAsStream("testtypecheck.rr")).typecheck();
	}

	@Test
	public void testCSV( ) throws ParseException, IOException {
		ReactiveScriptEnvironment rse = new ClasspathReactiveScriptEnvironment(TestReactiveParser.class);
		ReactiveScript compiledScript = rse.compiledScript("csv");
		Assert.assertTrue(compiledScript.binaryMimeType().isPresent());
		Assert.assertEquals("text/csv",compiledScript.binaryMimeType().get());
		byte[] n = ReactiveStandalone.runStream(rse,"csv")
				.map(e->e.data())
				.reduce(new ByteArrayOutputStream(),(str,b)->{
					str.write(b);
					return str;
				})
				.map(e->e.toByteArray())
				.blockingGet();

		String resultString = new String(n);
		System.err.println(resultString);
		Assert.assertTrue(n.length>40);
	}
}
