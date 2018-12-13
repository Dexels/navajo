package com.dexels.navajo.expression.compiled;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.parser.compiled.api.ReactivePipeNode;
import com.dexels.navajo.reactive.CoreReactiveFinder;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactivePipe;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;
import com.dexels.navajo.reactive.source.sql.SQLReactiveSourceFactory;
import com.dexels.navajo.reactive.transformer.other.FilterTransformerFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceTransformerFactory;
import com.dexels.navajo.reactive.transformer.stream.StreamMessageTransformerFactory;

import io.reactivex.Flowable;

public class TestReactiveParser {
	
	private CoreReactiveFinder finder;

	@Before
	public void setup() {
		finder = new CoreReactiveFinder();
		finder.addReactiveSourceFactory(new SingleSourceFactory(), "single");
		finder.addReactiveSourceFactory(new SQLReactiveSourceFactory(), "sql");
		finder.addReactiveTransformerFactory(new StreamMessageTransformerFactory(), "stream");
		finder.addReactiveTransformerFactory(new ReduceTransformerFactory(), "reduce");
		finder.addReactiveTransformerFactory(new FilterTransformerFactory(), "filter");
		finder.activate();
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		Reactive.setFinderInstance(finder);
	}

	@Test
	public void testFilter() throws ParseException, IOException {
//		ReactiveBuildContext buildContext = ReactiveBuildContext.of(source->finder.getSourceFactory(source), (transformer,type)->finder.getTransformerFactory(transformer), reducer->finder.getMergerFactory(reducer), finder.transformerFactories(), finder.reactiveMappers(), true);
			
			Navajo n = runExpression(this.getClass().getResourceAsStream("filter.rr"),"tenant","service","deployment",NavajoFactory.getInstance().createNavajo())
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
//		ReactiveBuildContext buildContext = ReactiveBuildContext.of(source->finder.getSourceFactory(source), (transformer,type)->finder.getTransformerFactory(transformer), reducer->finder.getMergerFactory(reducer), finder.transformerFactories(), finder.reactiveMappers(), true);
			runExpression(this.getClass().getResourceAsStream("single.rr"),"tenant","service","deployment",NavajoFactory.getInstance().createNavajo())
				.map(e->e.event())
				.lift(StreamDocument.serialize())
				.map(e->new String(e))
				.blockingForEach(e->System.err.print(e));
	}


	private static Navajo runBlockingEmpty(InputStream inExpression) throws ParseException, IOException {
		return runExpression(inExpression, "tenant","service","deployment",NavajoFactory.getInstance().createNavajo())
				.map(e->e.event())
				.toObservable()
				.compose(StreamDocument.domStreamCollector())
				.blockingFirst();
	}

	private static Flowable<DataItem> runExpression(InputStream inExpression, String tenant, String service, String deployment, Navajo input) throws ParseException, IOException {
		StreamScriptContext context = new StreamScriptContext(tenant, service, deployment).withInputNavajo(input);
		try(Reader in = new InputStreamReader(inExpression)) {
 
			CompiledParser cp = new CompiledParser(in);
			cp.ReactivePipe();
			List<String> problems = new ArrayList<>();
			ReactivePipeNode src = (ReactivePipeNode) cp.getJJTree().rootNode().interpretToLambda(problems,"",Reactive.finderInstance().functionClassifier());
			System.err.println("Sourcetype: "+src);
			ReactivePipe pipe = (ReactivePipe) src.apply().value;
			
			Flowable<DataItem> flow = pipe.execute(context, Optional.empty(), ImmutableFactory.empty());
			return flow;
		}

	}

	
	@Test
	public void readMoreComplicated() throws ParseException, IOException {
		List<String> problems = new ArrayList<>();
		try(Reader in = new InputStreamReader(this.getClass().getResourceAsStream("subsource.rr"))) {
			CompiledParser cp = new CompiledParser(in);
			cp.ReactivePipe();
			ReactivePipeNode src = (ReactivePipeNode) cp.getJJTree().rootNode().interpretToLambda(problems,"",Reactive.finderInstance().functionClassifier());
			System.err.println("Sourcetype: "+src);
			ReactivePipe pipe = (ReactivePipe) src.apply().value;
			
			StreamScriptContext context = new StreamScriptContext("TENANT", "someservice", "somedeployment").withInputNavajo(NavajoFactory.getInstance().createNavajo());
			pipe.execute(context, Optional.empty(), ImmutableFactory.empty())
				.blockingForEach(e->System.err.println(e.event()));

		}
	}
	
	@Test
	public void testReduce( ) throws ParseException, IOException {
		try(InputStream in = this.getClass().getResourceAsStream("reduce.rr")) {
			Navajo n = runBlockingEmpty(in);
			n.write(System.err);
		};
	}

}
