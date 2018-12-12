package com.dexels.navajo.reactive;

import java.io.File;
import java.util.Collections;
import java.util.Optional;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.api.ReactiveScriptRunner;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.reactive.api.ReactiveFinder;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;
import com.dexels.navajo.reactive.source.sql.SQLReactiveSourceFactory;
import com.dexels.navajo.reactive.source.test.EventStreamSourceFactory;
import com.dexels.navajo.reactive.stored.InputStreamSourceFactory;
import com.dexels.navajo.reactive.transformer.call.CallTransformerFactory;
import com.dexels.navajo.reactive.transformer.csv.CSVTransformerFactory;
import com.dexels.navajo.reactive.transformer.filestore.FileStoreTransformerFactory;
import com.dexels.navajo.reactive.transformer.mergesingle.MergeSingleTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.FilterTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.FlattenEventStreamFactory;
import com.dexels.navajo.reactive.transformer.other.IntervalTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.SkipTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.TakeTransformerFactory;
import com.dexels.navajo.reactive.transformer.parseevents.ParseEventStreamFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceTransformerFactory;
import com.dexels.navajo.reactive.transformer.single.SingleMessageTransformerFactory;
import com.dexels.navajo.reactive.transformer.stream.StreamMessageTransformerFactory;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

public class TestSetup {

	public static ReactiveFinder setup() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
//		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		File root = new File("testscripts");
//		env = new ReactiveScriptEnvironment(root);
//		ReactiveScriptParser reactiveScriptParser = new ReactiveScriptParser();
		ReactiveFinder finder = new CoreReactiveFinder();
//		reactiveScriptParser = new ReactiveScriptParser();
//		reactiveScriptParser.setReactiveFinder(finder);
//		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		Expression.compileExpressions = true;
//		finder.a
		finder.addReactiveSourceFactory(new SQLReactiveSourceFactory(),"sql");
		finder.addReactiveSourceFactory(new SingleSourceFactory(),"single");
		finder.addReactiveSourceFactory(new InputStreamSourceFactory(),"inputstream");
		finder.addReactiveSourceFactory(new EventStreamSourceFactory(),"eventstream");
		finder.addReactiveTransformerFactory(new CSVTransformerFactory(),"csv");
		finder.addReactiveTransformerFactory(new FileStoreTransformerFactory(),"filestore");
		finder.addReactiveTransformerFactory(new MergeSingleTransformerFactory(),"mergeSingle");
		finder.addReactiveTransformerFactory(new CallTransformerFactory(),"call");
		finder.addReactiveTransformerFactory(new StreamMessageTransformerFactory(),"stream");
		finder.addReactiveTransformerFactory(new SingleMessageTransformerFactory(),"single");
		finder.addReactiveTransformerFactory(new ReduceTransformerFactory(),"reduce");
		finder.addReactiveTransformerFactory(new FilterTransformerFactory(),"filter");
		finder.addReactiveTransformerFactory(new TakeTransformerFactory(),"take");
		finder.addReactiveTransformerFactory(new SkipTransformerFactory(),"skip");
		finder.addReactiveTransformerFactory(new ParseEventStreamFactory(),"streamtoimmutable");
		finder.addReactiveTransformerFactory(new FlattenEventStreamFactory(),"flatten");
		finder.addReactiveTransformerFactory(new IntervalTransformerFactory(),"interval");
//		env.setReactiveScriptParser(reactiveScriptParser);
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		return finder;
	}
	
	public static StreamScriptContext createContext(String serviceName, Optional<ReactiveScriptRunner> runner) {
//		Flowable<NavajoStreamEvent> inStream = Observable.just(input).lift(StreamDocument.domStream()).toFlowable(BackpressureStrategy.BUFFER);
		StreamScriptContext context = new StreamScriptContext("tenant", serviceName
				, Optional.of("username")
				, Optional.of("password")
				, NavajoFactory.getInstance().createNavajo()
				, Collections.emptyMap()
				, Optional.empty()
				, runner,Collections.emptyList(), Optional.empty(),Optional.empty());
		return context;
	}
	
}
