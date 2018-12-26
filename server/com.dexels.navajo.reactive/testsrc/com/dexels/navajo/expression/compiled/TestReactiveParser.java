package com.dexels.navajo.expression.compiled;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.reactive.CoreReactiveFinder;
import com.dexels.navajo.reactive.ReactiveStandalone;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;
import com.dexels.navajo.reactive.source.sql.SQLReactiveSourceFactory;
import com.dexels.navajo.reactive.transformer.mergesingle.MergeSingleTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.FilterTransformerFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceTransformerFactory;
import com.dexels.navajo.reactive.transformer.stream.StreamMessageTransformerFactory;

public class TestReactiveParser {
	
	private CoreReactiveFinder finder;

	@Before
	public void setup() {
		finder = new CoreReactiveFinder();

		finder.activate();
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		Reactive.setFinderInstance(finder);
	}

	@Test
	public void testFilter() throws ParseException, IOException {
//		ReactiveBuildContext buildContext = ReactiveBuildContext.of(source->finder.getSourceFactory(source), (transformer,type)->finder.getTransformerFactory(transformer), reducer->finder.getMergerFactory(reducer), finder.transformerFactories(), finder.reactiveMappers(), true);
			
			Navajo n = ReactiveStandalone.runExpression(this.getClass().getResourceAsStream("filter.rr"),"tenant","service","deployment",NavajoFactory.getInstance().createNavajo())
				.map(e->e.event())
				.toObservable()
				.doOnNext(e->System.err.println("<<<<??"+e))
				.compose(StreamDocument.domStreamCollector())
				.blockingFirst();
			
			int size = n.getMessage("Blem").getArraySize();
			System.err.println("size: "+size);
			n.write(System.err);
			Assert.assertEquals(2, size);
	}
	
	@Test
	public void readSingleScript() throws ParseException, IOException {
		ReactiveStandalone.runExpression(this.getClass().getResourceAsStream("single.rr"),"tenant","service","deployment",NavajoFactory.getInstance().createNavajo())
				.map(e->e.event())
				.lift(StreamDocument.serialize())
				.map(e->new String(e))
				.blockingForEach(e->System.err.print(e));
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
}
