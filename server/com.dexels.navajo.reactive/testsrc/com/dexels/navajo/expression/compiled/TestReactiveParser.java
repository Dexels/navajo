package com.dexels.navajo.expression.compiled;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
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
		ReactiveStandalone.runExpression(this.getClass().getResourceAsStream("multiple.rr"),"tenant","service","deployment",NavajoFactory.getInstance().createNavajo())
				.map(e->e.event())
				.lift(StreamDocument.serialize())
				.map(e->new String(e))
				.blockingForEach(e->System.err.print(e));
	}

	
	
	@Test
	public void readJoinScript() throws ParseException, IOException {
			ReactiveStandalone.runExpression(this.getClass().getResourceAsStream("join.rr"),"tenant","service","deployment",NavajoFactory.getInstance().createNavajo())
				.map(e->e.event())
				.compose(StreamDocument.inNavajo("service", Optional.empty(), Optional.empty()))
				.lift(StreamDocument.serialize())
				.map(e->new String(e))
				.blockingForEach(e->System.err.print(e));
	}


	
	@Test
	public void testReduce( ) throws ParseException, IOException {
		try(InputStream in = this.getClass().getResourceAsStream("reduce.rr")) {
			Navajo n = ReactiveStandalone.runBlockingEmpty(in);
			n.write(System.err);
		};
	}

}
