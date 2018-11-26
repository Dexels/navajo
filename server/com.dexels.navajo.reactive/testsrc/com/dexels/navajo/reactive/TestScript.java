package com.dexels.navajo.reactive;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.StreamDocument;
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
import com.dexels.navajo.reactive.transformer.other.IntervalTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.TakeTransformerFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceTransformerFactory;
import com.dexels.navajo.reactive.transformer.single.SingleMessageTransformerFactory;
import com.dexels.navajo.reactive.transformer.stream.StreamMessageTransformerFactory;

public class TestScript {

	private ReactiveScriptParser reactiveScriptParser;
	private ReactiveScriptEnvironment env;


	@Before
	public void setup() {
//		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		File root = new File("testscripts");
		env = new ReactiveScriptEnvironment(root);
		ReactiveFinder finder = new CoreReactiveFinder();
		reactiveScriptParser = new ReactiveScriptParser();
		reactiveScriptParser.setReactiveFinder(finder);
//		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		Expression.compileExpressions = true;
//		finder.a
		finder.addReactiveSourceFactory(new SQLReactiveSourceFactory(),"sql");
		finder.addReactiveSourceFactory(new SingleSourceFactory(),"single");
		finder.addReactiveSourceFactory(new InputStreamSourceFactory(),"inputstream");
		finder.addReactiveTransformerFactory(new CSVTransformerFactory(),"csv");
		finder.addReactiveTransformerFactory(new FileStoreTransformerFactory(),"filestore");
		finder.addReactiveTransformerFactory(new MergeSingleTransformerFactory(),"mergeSingle");
		finder.addReactiveTransformerFactory(new CallTransformerFactory(),"call");
		finder.addReactiveTransformerFactory(new StreamMessageTransformerFactory(),"stream");
		finder.addReactiveTransformerFactory(new SingleMessageTransformerFactory(),"single");
		finder.addReactiveTransformerFactory(new ReduceTransformerFactory(),"reduce");
		finder.addReactiveTransformerFactory(new TakeTransformerFactory(),"take");
		finder.addReactiveTransformerFactory(new IntervalTransformerFactory(),"interval");
		env.setReactiveScriptParser(reactiveScriptParser);
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
	}

	public StreamScriptContext createContext(String serviceName, Optional<ReactiveScriptRunner> runner) {
//		Flowable<NavajoStreamEvent> inStream = Observable.just(input).lift(StreamDocument.domStream()).toFlowable(BackpressureStrategy.BUFFER);
		StreamScriptContext context = new StreamScriptContext("tenant", serviceName
				, Optional.of("username")
				, Optional.of("password")
				, NavajoFactory.getInstance().createNavajo()
				, Collections.emptyMap(), Optional.empty(),runner,Collections.emptyList(), Optional.empty(),Optional.empty());
		return context;
	}
	
	@Test
	public void testSQL() {
		long count = SQL.query("dummy", "KNVB", "select * from organization where rownum < 500")
			.flatMap(msg->StreamDocument.replicationMessageToStreamEvents("Organization", msg, true))
			.compose(StreamDocument.inArray("Organization"))
			.compose(StreamDocument.inNavajo("ProcessGetOrg", Optional.empty(), Optional.empty()))
			.map(e->e.type())
			.doOnNext(e->System.err.println("Type: "+e))
//			.lift(StreamDocument.serialize())
			.count()
			.blockingGet();
		System.err.println("count: "+count);
		Assert.assertEquals(1002, count);
//		.blockingForEach(e->System.err.print(new String(e)));
	}
	@Test
	public void testSimpleScript() throws IOException {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("simplereactive.xml")) {
			StreamScriptContext myContext = createContext("SimpleReactiveSql",Optional.empty());
			reactiveScriptParser.parse(myContext.getService(), in, "serviceName",Optional.of(Type.EVENT))
				.execute(myContext)
				.map(di->di.event())
				.lift(StreamDocument.serialize())
				.blockingForEach(e->System.err.print(new String(e)));
		}
	}

}


