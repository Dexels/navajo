package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.ReactiveScriptRunner;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.mappers.Store;
import com.dexels.navajo.reactive.mappers.StoreAsSubMessage;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceTransformer;
import com.dexels.navajo.reactive.transformer.single.SingleMessageTransformer;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class StoreTest {

	public StoreTest() {
		// TODO Auto-generated constructor stub
	}

	@Before
	public void setup() {
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
	}

	public ImmutableMessage createMessage(int suffix) {
		return ImmutableFactory.empty()
				.with("name", "personname_"+suffix, Property.STRING_PROPERTY)
				.with("address", "address_"+suffix, Property.STRING_PROPERTY);
	}
	
	private StreamScriptContext createContext(String serviceName, Optional<ReactiveScriptRunner> runner) {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		return createContext(serviceName, input,runner);
	}
	
	private StreamScriptContext createContext(String serviceName,Navajo input, Optional<ReactiveScriptRunner> runner) {
//		Flowable<NavajoStreamEvent> inStream = Observable.just(input).lift(StreamDocument.domStream()).toFlowable(BackpressureStrategy.BUFFER);
		StreamScriptContext context = new StreamScriptContext("tenant", serviceName, Optional.of("username"), Optional.of("password"), Collections.emptyMap(), Optional.empty(),Optional.of(input), runner);
		return context;
	}
	
	
	@Test
	public void testStore() throws UnsupportedEncodingException, IOException {
		SingleSourceFactory ssf = new SingleSourceFactory();
		ReactiveParameters parameters = ReactiveParameters.empty()
//				.withConstant("debug", true, Property.BOOLEAN_PROPERTY);
				.withConstant("count", 10, Property.INTEGER_PROPERTY);
		StreamScriptContext context = createContext("Single",Optional.empty());
		
		ReactiveParameters transformerParameter = ReactiveParameters.empty();

		Store storeMapper = new Store();
		ReactiveParameters mapperParameter = ReactiveParameters.empty()
				.with("value", (ctx,item,state)->{
					Integer count = (Integer)state.columnValue("count");
					if(count == null) {
						count = 0;
					}
					count++;
					System.err.println("Count: "+count);
					return new Operand(count, Property.INTEGER_PROPERTY);
				})
				.withConstant("name", "count", Property.STRING_PROPERTY);

//		new StoreAsSubMessage().execute(ReactiveParameters.empty()).
		ReactiveTransformer storeTrans = new ReduceTransformer(storeMapper.execute(mapperParameter),transformerParameter);
//		ReactiveTransformer copy =  new SingleMessageTransformer(ReactiveParameters.empty(), joinermapper, xml, path) new StoreAsSubMessage();
		ssf.build("",parameters, Arrays.asList(new ReactiveTransformer[] {storeTrans}), DataItem.Type.MESSAGE)
			.execute(context, Optional.empty())
//			.map(e->e.message())
			.blockingForEach(r->{
				System.err.println(">>>> "+ImmutableFactory.getInstance().describe(r.message())+" state: "+ImmutableFactory.getInstance().describe(r.stateMessage()));
			});
	}
}
