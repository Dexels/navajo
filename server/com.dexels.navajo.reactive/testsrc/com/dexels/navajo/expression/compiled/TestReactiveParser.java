package com.dexels.navajo.expression.compiled;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.parser.compiled.api.ParseMode;
import com.dexels.navajo.parser.compiled.api.ReactivePipe;
import com.dexels.navajo.reactive.CoreReactiveFinder;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;
import com.dexels.navajo.reactive.source.sql.SQLReactiveSourceFactory;
import com.dexels.navajo.reactive.transformer.other.FilterTransformerFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceTransformerFactory;
import com.dexels.navajo.reactive.transformer.stream.StreamMessageTransformerFactory;

public class TestReactiveParser {
	
	private CoreReactiveFinder finder;

	@Before
	public void setup() {
		finder = new CoreReactiveFinder();
		finder.addReactiveSourceFactory(new SingleSourceFactory(), "single");
		finder.addReactiveSourceFactory(new SQLReactiveSourceFactory(), "sql");
		finder.addReactiveTransformerFactory(new StreamMessageTransformerFactory(), "Stream");
		finder.addReactiveTransformerFactory(new ReduceTransformerFactory(), "Reduce");
		finder.addReactiveTransformerFactory(new FilterTransformerFactory(), "Filter");
		finder.activate();
		Reactive.setFinderInstance(finder);
	}

	@Test
	public void readScript() throws ParseException, IOException {
		List<String> problems = new ArrayList<>();
		try(Reader in = new InputStreamReader(this.getClass().getResourceAsStream("simple.rr"))) {
			CompiledParser cp = new CompiledParser(in);
			cp.ReactivePipe();
	        ReactivePipe ss = (ReactivePipe) cp.getJJTree().rootNode().interpretToLambda(problems,"",ParseMode.REACTIVE);
	        int transformerCount =ss.transformers.size();
	        ContextExpression contextExpression = ss.transformers.stream().findFirst().get();
			System.err.println("Trans: "+contextExpression);
	        ReactiveSource rsf = (ReactiveSource) ss.source.apply();
	        List<ReactiveTransformerFactory> transfac = ss.transformers.stream().map(e->(ReactiveTransformerFactory)e.apply()).collect(Collectors.toList());
	        System.err.println(ss.source.apply());
		}
	}

	
	@Test
	public void readMoreComplicated() throws ParseException, IOException {
		List<String> problems = new ArrayList<>();
		try(Reader in = new InputStreamReader(this.getClass().getResourceAsStream("subsource.rr"))) {
			CompiledParser cp = new CompiledParser(in);
			cp.ReactivePipe();
	        ReactivePipe ss = (ReactivePipe) cp.getJJTree().rootNode().interpretToLambda(problems,"",ParseMode.REACTIVE);
	        ReactiveSource rsf = (ReactiveSource) ss.source.apply();
	        List<ReactiveTransformer> transfac = ss.transformers
	        		.stream()
	        		.map(e->(ReactiveTransformer)e.apply())
	        		.collect(Collectors.toList());
	        System.err.println(ss.source.apply());
		}
	}

}
