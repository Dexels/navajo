package com.dexels.navajo.reactive;

import java.util.Collections;
import java.util.Optional;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.api.ReactiveScriptRunner;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;
import com.dexels.navajo.reactive.source.sql.SQLReactiveSourceFactory;
import com.dexels.navajo.reactive.stored.InputStreamSourceFactory;
import com.dexels.navajo.reactive.transformer.call.CallTransformerFactory;
import com.dexels.navajo.reactive.transformer.csv.CSVTransformerFactory;
import com.dexels.navajo.reactive.transformer.filestore.FileStoreTransformerFactory;
import com.dexels.navajo.reactive.transformer.mergesingle.MergeSingleTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.FilterTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.SkipTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.TakeTransformerFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceTransformerFactory;
import com.dexels.navajo.reactive.transformer.single.SingleMessageTransformerFactory;
import com.dexels.navajo.reactive.transformer.stream.StreamMessageTransformerFactory;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

public class TestSetup {

	public static ReactiveScriptParser setup() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		Expression.compileExpressions = true;
		ReactiveScriptParser reactiveScriptParser = new ReactiveScriptParser();
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
		return reactiveScriptParser;
	}
	

	public static StreamScriptContext createContext(String serviceName, Optional<ReactiveScriptRunner> runner) {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		return createContext(serviceName, input,runner);
	}
	
	public static StreamScriptContext createContext(String serviceName,Navajo input, Optional<ReactiveScriptRunner> runner) {
//		Flowable<NavajoStreamEvent> inStream = Observable.just(input).lift(StreamDocument.domStream()).toFlowable(BackpressureStrategy.BUFFER);
		StreamScriptContext context = new StreamScriptContext("tenant", serviceName
				, Optional.of("username")
				, Optional.of("password")
				, NavajoFactory.getInstance().createNavajo()
				, Collections.emptyMap()
				, Optional.empty()
				, input,runner, Collections.emptyList(),Optional.empty());
		return context;
	}
	
}
