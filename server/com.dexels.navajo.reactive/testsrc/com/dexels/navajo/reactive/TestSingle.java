package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.ReactiveScriptRunner;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;
import com.dexels.navajo.reactive.source.sql.SQLReactiveSourceFactory;
import com.dexels.navajo.reactive.stored.InputStreamSourceFactory;
import com.dexels.navajo.reactive.transformer.call.CallTransformerFactory;
import com.dexels.navajo.reactive.transformer.csv.CSVTransformerFactory;
import com.dexels.navajo.reactive.transformer.filestore.FileStoreTransformerFactory;
import com.dexels.navajo.reactive.transformer.mergesingle.MergeSingleTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.FilterTransformer;
import com.dexels.navajo.reactive.transformer.other.FilterTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.SkipTransformer;
import com.dexels.navajo.reactive.transformer.other.SkipTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.TakeTransformer;
import com.dexels.navajo.reactive.transformer.other.TakeTransformerFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceTransformerFactory;
import com.dexels.navajo.reactive.transformer.single.SingleMessageTransformerFactory;
import com.dexels.navajo.reactive.transformer.stream.StreamMessageTransformerFactory;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

public class TestSingle {
	
	private ReactiveScriptParser reactiveScriptParser;

	@Before
	public void setup() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		Expression.compileExpressions = true;
		reactiveScriptParser = new ReactiveScriptParser();
		reactiveScriptParser.addReactiveSourceFactory(new SQLReactiveSourceFactory(),"sql");
		reactiveScriptParser.addReactiveSourceFactory(new SingleSourceFactory(),"single");
		reactiveScriptParser.addReactiveSourceFactory(new InputStreamSourceFactory(),"inputstream");
		reactiveScriptParser.addReactiveTransformerFactory(new CSVTransformerFactory(),"csv");
		reactiveScriptParser.addReactiveTransformerFactory(new FileStoreTransformerFactory(),"filestore");
		reactiveScriptParser.addReactiveTransformerFactory(new MergeSingleTransformerFactory(),"mergeSingle");
		reactiveScriptParser.addReactiveTransformerFactory(new CallTransformerFactory(),"call");
		reactiveScriptParser.addReactiveTransformerFactory(new StreamMessageTransformerFactory(),"stream");
		reactiveScriptParser.addReactiveTransformerFactory(new SingleMessageTransformerFactory(),"single");
		reactiveScriptParser.addReactiveTransformerFactory(new ReduceTransformerFactory(),"reduce");
		reactiveScriptParser.addReactiveTransformerFactory(new FilterTransformerFactory(),"filter");
		reactiveScriptParser.addReactiveTransformerFactory(new TakeTransformerFactory(),"take");
		reactiveScriptParser.addReactiveTransformerFactory(new SkipTransformerFactory(),"skip");
	}

	private StreamScriptContext createContext(String serviceName, Optional<ReactiveScriptRunner> runner) {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		return createContext(serviceName, input,runner);
	}
	
	private StreamScriptContext createContext(String serviceName,Navajo input, Optional<ReactiveScriptRunner> runner) {
//		Flowable<NavajoStreamEvent> inStream = Observable.just(input).lift(StreamDocument.domStream()).toFlowable(BackpressureStrategy.BUFFER);
		StreamScriptContext context = new StreamScriptContext("tenant", serviceName, Optional.of("username"), Optional.of("password"), Collections.emptyMap(), Optional.empty(), Optional.of(input),runner);
		return context;
	}
	
	

	@Test
	public void testSingleSource() throws UnsupportedEncodingException, IOException {
		SingleSourceFactory ssf = new SingleSourceFactory();
		ReactiveParameters parameters = ReactiveParameters.empty()
				.withConstant("debug", true, Property.BOOLEAN_PROPERTY)
				.withConstant("count", 10, Property.INTEGER_PROPERTY);
		List<ReactiveParseProblem> problems = new ArrayList<>();
		StreamScriptContext context = createContext("Single",Optional.empty());
		ssf.build("",parameters, problems, Collections.emptyList(), DataItem.Type.MESSAGE)
			.execute(context, Optional.empty())
			.map(e->e.message())
			.blockingForEach(e->System.err.println("S: "+e.toFlatString(ImmutableFactory.getInstance())));
	}
	
	@Test
	public void testTake() throws UnsupportedEncodingException, IOException {
		SingleSourceFactory ssf = new SingleSourceFactory();
		ReactiveParameters parameters = ReactiveParameters.empty()
				.withConstant("debug", true, Property.BOOLEAN_PROPERTY)
				.withConstant("count", 10, Property.INTEGER_PROPERTY);
		StreamScriptContext context = createContext("Single",Optional.empty());
		
		ReactiveParameters transformerParameter = ReactiveParameters.empty()
				.withConstant("count", 5, Property.INTEGER_PROPERTY);
		List<ReactiveParseProblem> problems = new ArrayList<>();

		ReactiveTransformer takeTransformer = new TakeTransformerFactory().build(problems,transformerParameter);
		int lastIndex = ssf.build("",parameters, problems,Arrays.asList(new ReactiveTransformer[] {takeTransformer}), DataItem.Type.MESSAGE)
			.execute(context, Optional.empty())
			.map(e->e.message())
			.lastOrError()
			.map(msg->(Integer)msg.columnValue("index"))
			.blockingGet();
		System.err.println("Count: "+lastIndex);
		Assert.assertEquals(4, lastIndex);
//			.blockingForEach(e->System.err.println("S: "+e.toFlatString(ImmutableFactory.getInstance())));
	}

	@Test
	public void testFilter() throws UnsupportedEncodingException, IOException {
		SingleSourceFactory ssf = new SingleSourceFactory();
		ReactiveParameters parameters = ReactiveParameters.empty()
				.withConstant("debug", true, Property.BOOLEAN_PROPERTY)
				.withConstant("count", 10, Property.INTEGER_PROPERTY);
		StreamScriptContext context = createContext("Single",Optional.empty());
		
		ReactiveParameters transformerParameter = ReactiveParameters.empty()
				.with("filter", (cxt,msg,param)->{
					int i = ((Integer)msg.get().columnValue("index")).intValue();
					boolean isEven = i % 2 == 0;
					System.err.println("Even numer? "+i+" is even: "+isEven);
					return new Operand( isEven,Property.BOOLEAN_PROPERTY);
				});

//		new FilterTransformerFactory().build(
		List<ReactiveParseProblem> problems = new ArrayList<>();
		ReactiveTransformer filterTransformer = new FilterTransformerFactory().build(problems,transformerParameter);
		long lastIndex = ssf.build("",parameters, problems, Arrays.asList(new ReactiveTransformer[] {filterTransformer}), DataItem.Type.MESSAGE)
			.execute(context, Optional.empty())
			.map(e->e.message())
			.count()
			.blockingGet();
		System.err.println("Number of even: "+lastIndex);
		Assert.assertEquals(5, lastIndex);
//			.blockingForEach(e->System.err.println("S: "+e.toFlatString(ImmutableFactory.getInstance())));
	}
	
	@Test
	public void testSkip() throws UnsupportedEncodingException, IOException {
		SingleSourceFactory ssf = new SingleSourceFactory();
		ReactiveParameters parameters = ReactiveParameters.empty()
				.withConstant("debug", true, Property.BOOLEAN_PROPERTY)
				.withConstant("count", 10, Property.INTEGER_PROPERTY);
		StreamScriptContext context = createContext("Single",Optional.empty());
		
		ReactiveParameters transformerParameter = ReactiveParameters.empty()
				.withConstant("count", 5, Property.INTEGER_PROPERTY);

		List<ReactiveParseProblem> problems = new ArrayList<>();
		ReactiveTransformer skipTransformer = new SkipTransformerFactory().build(problems,transformerParameter);
		
		int lastIndex = ssf.build("",parameters, problems, Arrays.asList(new ReactiveTransformer[] {skipTransformer}), DataItem.Type.MESSAGE)
			.execute(context, Optional.empty())
			.map(e->e.message())
			.lastOrError()
			.map(msg->(Integer)msg.columnValue("index"))
			.blockingGet();
		System.err.println("Count: "+lastIndex);
		Assert.assertEquals(9, lastIndex);
//			.blockingForEach(e->System.err.println("S: "+e.toFlatString(ImmutableFactory.getInstance())));
	}
	
	@Test
	public void testSingle() throws UnsupportedEncodingException, IOException {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("single.xml")) {
			StreamScriptContext myContext = createContext("Single",Optional.empty());
			List<ReactiveParseProblem> problems = new ArrayList<>();
			reactiveScriptParser.parse(myContext.service, in,"serviceName",problems)
				.execute(myContext)
				.map(di->di.event())
				.compose(StreamDocument.inNavajo("Single", Optional.empty(), Optional.empty()))
				.lift(StreamDocument.serialize())
				.blockingForEach(e->System.err.print(new String(e)));
		}
	}
	
	@Test
	public void testStore() throws UnsupportedEncodingException, IOException {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("teststore.xml")) {
			StreamScriptContext myContext = createContext("storeTestScript",Optional.empty());
			List<ReactiveParseProblem> problems = new ArrayList<>();
			reactiveScriptParser.parse(myContext.service, in,"storeTestScript",problems)
				.execute(myContext)
				.map(di->di.event())
				.compose(StreamDocument.inNavajo("Single", Optional.empty(), Optional.empty()))
				.lift(StreamDocument.serialize())
				.blockingForEach(e->System.err.print(new String(e)));
		}
	}
	
	@Test
	public void testReduce() throws UnsupportedEncodingException, IOException {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("testreduce.xml")) {
			StreamScriptContext myContext = createContext("storeTestScript",Optional.empty());
			List<ReactiveParseProblem> problems = new ArrayList<>();
			reactiveScriptParser.parse(myContext.service, in,"storeTestScript",problems)
				.execute(myContext)
				.map(di->di.event())
				.compose(StreamDocument.inNavajo("Single", Optional.empty(), Optional.empty()))
				.lift(StreamDocument.serialize())
				.blockingForEach(e->System.err.print(new String(e)));
		}
	}
	
	@Test
	public void testInputStream() throws UnsupportedEncodingException, IOException {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("inputstream.xml")) {
			StreamScriptContext myContext = createContext("storeTestScript",Optional.empty());
			List<ReactiveParseProblem> problems = new ArrayList<>();
			Navajo n = reactiveScriptParser.parse(myContext.service, in,"storeTestScript",problems)
				.execute(myContext)
				.map(di->di.event())
				.compose(StreamDocument.inNavajo("Single", Optional.empty(), Optional.empty()))
				.lift(StreamDocument.collectFlowable())
				.firstOrError()
				.blockingGet();
			String firstName = (String) n.getMessage("Person").getProperty("firstname").getTypedValue();
			Assert.assertEquals("Bertrand", firstName);
		}
	}
	
}
