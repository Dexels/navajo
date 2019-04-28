package com.dexels.navajo.expression.compiled;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.dexels.navajo.reactive.api.ReactivePipe;
import com.fasterxml.aalto.AsyncByteArrayFeeder;

public class TestReactiveParser {
	
	
	private static final Logger logger = LoggerFactory.getLogger(TestReactiveParser.class);

	@Before
	public void setup() {
		CoreReactiveFinder finder = new CoreReactiveFinder();
		Reactive.setFinderInstance(finder);
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
	}

	@Test
	public void testFilter() throws ParseException, IOException {
		ReactiveScriptEnvironment rse = new ClasspathReactiveScriptEnvironment(TestReactiveParser.class);
		Navajo n =  ReactiveStandalone.runBlockingEmpty(rse,"filter");
		int size = n.getMessage("Blem").getArraySize();
		logger.info("size: {}",size);
		n.write(System.err);
		Assert.assertEquals(2, size);
	}
	
	@Test
	public void testPipe() throws ParseException, IOException {
			ReactiveScriptEnvironment rse = new ClasspathReactiveScriptEnvironment(TestReactiveParser.class);
			
			Navajo n =  ReactiveStandalone.runBlockingEmpty(rse,"pipe");
			n.write(System.err);
			int size = n.getMessage("Item").getArraySize();
			logger.info("size: {}",size);
			n.write(System.err);
			Assert.assertEquals(5, size);
	}
	
	@Test
	public void testPipeParser() throws ParseException, IOException {
			CompiledReactiveScript rs = ReactiveStandalone.compileReactiveScript(TestReactiveParser.class.getResourceAsStream("pipe.rr"));
			Optional<ReactivePipe> foundPipe = rs.pipes.stream().findFirst();
			Assert.assertTrue(foundPipe.isPresent());
			if(foundPipe.isPresent()) {
				int size = foundPipe.get().transformers.size();
				logger.info("size: {}",size);
				Assert.assertEquals(2, size);
			}
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
		Assert.assertEquals(10, i);
	}

	@Test
	public void readJoinSimpleScript() throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/joinsimple.rr");
		n.write(System.err);
		Assert.assertEquals("outer", n.getProperty("Test/outer").getTypedValue());
		Assert.assertEquals("inner", n.getProperty("Test/inner").getTypedValue());
		Assert.assertEquals("inner", n.getProperty("Test/innername").getTypedValue());
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
		ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/delay.rr");
		long elapsed = System.currentTimeMillis() - now;
		Assert.assertTrue(elapsed>500);
	}

	@Test
	public void testMethods( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/methods.rr");
		Assert.assertEquals(1,n.getAllMethods().size());
	}
	
	@Test
	public void testAddressSubMessage( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/addresssubmessage.rr");
		String val = (String) n.getMessage("Test/bla").getProperty("prop2").getTypedValue();
		Assert.assertEquals("prop2value",val);
	}
	
	@Test
	public void testEventStream( ) throws ParseException, IOException {
		AsyncByteArrayFeeder a;
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/eventstream.rr");
		Message m = n.getMessage("Oe");
		int size = m.getArraySize();
		Assert.assertEquals(2, size);
		Integer val = (Integer) m.getMessage(1).getProperty("jet").getTypedValue();
		Assert.assertEquals(1, val.intValue());
	}
	
	@Test
	public void testStream( ) throws ParseException, IOException {
		Navajo n = ReactiveStandalone.runBlockingEmptyFromClassPath("com/dexels/navajo/expression/compiled/impliciteventstreamparse.rr");
		Message m = n.getMessage("Oe");
		int size = m.getArraySize();
		Assert.assertEquals(2, size);
		Integer val = (Integer) m.getMessage(1).getProperty("jet").getTypedValue();
		Assert.assertEquals(1, val.intValue());
	}
	
	@Test
	public void testInput() throws ParseException, IOException {
		Navajo input = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("tmldatainput.xml"));
		try(InputStream in = ReactiveStandalone.class.getClassLoader().getResourceAsStream("com/dexels/navajo/expression/compiled/input.rr")) {
			Navajo n = ReactiveStandalone.runBlockingInput(in, input);
			n.write(System.err);
			Assert.assertEquals(2, n.getMessage("Doubled").getArraySize());
			Assert.assertEquals(2, n.getMessage("Doubled").getMessage(1).getProperty("multipliedbytwo").getTypedValue());
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
