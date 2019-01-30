package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.reactive.api.ReactiveFinder;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;
import com.dexels.navajo.reactive.transformer.other.FilterTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.SkipTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.TakeTransformerFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public class TestSingle {
	
	private ReactiveFinder reactiveFinder;

	@Before
	public void setup() {
		reactiveFinder = TestSetup.setup();
	}

	@Test
	public void testSingleSource() throws UnsupportedEncodingException, IOException {
		SingleSourceFactory ssf = new SingleSourceFactory();
		ReactiveParameters parameters = ReactiveParameters.empty(ssf)
				.withConstant("debug", true, Property.BOOLEAN_PROPERTY)
				.withConstant("count", 10, Property.INTEGER_PROPERTY);
		StreamScriptContext context = TestSetup.createContext("Single",Optional.empty());
		ssf.build(parameters)
			.execute(context, Optional.empty(), ImmutableFactory.empty())
//		ssf.build("",parameters, problems, Collections.emptyList(), DataItem.Type.MESSAGE)
//			.execute(context, Optional.empty())
			.map(e->e.message())
			.blockingForEach(e->System.err.println("S: "+e.toFlatString(ImmutableFactory.getInstance())));
	}
	
	@Test
	public void testTake() throws UnsupportedEncodingException, IOException {
		SingleSourceFactory ssf = new SingleSourceFactory();
		ReactiveParameters parameters = ReactiveParameters.empty(ssf)
				.withConstant("debug", true, Property.BOOLEAN_PROPERTY)
				.withConstant("count", 10, Property.INTEGER_PROPERTY);
		StreamScriptContext context = TestSetup.createContext("Single",Optional.empty());
		TakeTransformerFactory takeTransformerFactory = new TakeTransformerFactory();
		
		ReactiveParameters transformerParameter = ReactiveParameters.empty(takeTransformerFactory)
				.withConstant(Operand.ofInteger(5));
//				.withConstant("count", 5, Property.INTEGER_PROPERTY);
		
		List<ReactiveParseProblem> problems = new ArrayList<>();

		ReactiveTransformer takeTransformer = takeTransformerFactory.build(problems,transformerParameter);
		int lastIndex = ssf.build(parameters)
			.execute(context, Optional.empty(), ImmutableFactory.empty())
			.compose(takeTransformer.execute(context, Optional.empty(), ImmutableFactory.empty()))
			.map(e->e.stateMessage())
			.lastOrError()
			.map(msg->(Integer)msg.value("index").get())
			.blockingGet();

		System.err.println("Count: "+lastIndex);
		Assert.assertEquals(4, lastIndex);
//			.blockingForEach(e->System.err.println("S: "+e.toFlatString(ImmutableFactory.getInstance())));
	}

	@Test
	public void testFilter() throws UnsupportedEncodingException, IOException {
		List<ReactiveParseProblem> problems = new ArrayList<>();
		SingleSourceFactory ssf = new SingleSourceFactory();
		ReactiveParameters parameters = ReactiveParameters.empty(ssf)
				.withConstant("debug", true, Property.BOOLEAN_PROPERTY)
				.withConstant("count", 10, Property.INTEGER_PROPERTY);
		StreamScriptContext context = TestSetup.createContext("Single",Optional.empty());
		
		ReactiveTransformerFactory filterFactory = new FilterTransformerFactory();
		ReactiveParameters transformerParameter = ReactiveParameters.empty(filterFactory)
				
				.withExpression(new ContextExpression() {
					
					@Override
					public Optional<String> returnType() {
						return Optional.of("boolean");
					}
					
					@Override
					public boolean isLiteral() {
						return false;
					}
					
					@Override
					public String expression() {
						return "";
					}
					
					@Override
					public Operand apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
							MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage,
							Optional<ImmutableMessage> paramMessage) throws TMLExpressionException {
						try {
							System.err.println(ImmutableFactory.ndJson(paramMessage.get()));
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						int i = ((Integer)paramMessage.get().value("index").get()).intValue();
						boolean isEven = i % 2 == 0;
						System.err.println("Even numer? "+i+" is even: "+isEven);
						return Operand.ofBoolean(isEven);
					}
				});
		ReactiveTransformer filterTransformer = filterFactory.build(problems,transformerParameter);

//		new FilterTransformerFactory().build(
		long lastIndex = ssf.build(parameters)
			.execute(context, Optional.empty(), ImmutableFactory.empty())
			.doOnNext(item->{
				try {
					System.err.println(">>>> "+ImmutableFactory.ndJson(item.message()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			})
			.compose(filterTransformer.execute(context, Optional.empty(), ImmutableFactory.empty()))
			.doOnNext(e->System.err.println("<< "+e.stateMessage()))
			.map(
					e->e.message()
					)
			.count()
			.blockingGet();
		System.err.println("Number of even: "+lastIndex);
		Assert.assertEquals(5, lastIndex);
//			.blockingForEach(e->System.err.println("S: "+e.toFlatString(ImmutableFactory.getInstance())));
	}
	
	@Test
	public void testSkip() throws UnsupportedEncodingException, IOException {
		SingleSourceFactory ssf = new SingleSourceFactory();
		ReactiveParameters parameters = ReactiveParameters.empty(ssf)
				.withConstant("debug", true, Property.BOOLEAN_PROPERTY)
				.withConstant("count", 10, Property.INTEGER_PROPERTY);
		StreamScriptContext context = TestSetup.createContext("Single",Optional.empty());
		SkipTransformerFactory skipTransformerFactory = new SkipTransformerFactory();
		
		ReactiveParameters transformerParameter = ReactiveParameters.empty(skipTransformerFactory)
				.withConstant("count", 5, Property.INTEGER_PROPERTY);

		List<ReactiveParseProblem> problems = new ArrayList<>();
		ReactiveTransformer skipTransformer = skipTransformerFactory.build(problems,transformerParameter);
		
		int lastIndex = ssf.build(parameters)
			.execute(context, Optional.empty(),ImmutableFactory.empty())
			.compose(skipTransformer.execute(context, Optional.empty(),ImmutableFactory.empty()))
			.map(e->e.stateMessage())
			.lastOrError()
			.map(msg->(Integer)msg.value("index").get())
			.blockingGet();
		System.err.println("Count: "+lastIndex);
		Assert.assertEquals(9, lastIndex);
//			.blockingForEach(e->System.err.println("S: "+e.toFlatString(ImmutableFactory.getInstance())));
	}
	
}
