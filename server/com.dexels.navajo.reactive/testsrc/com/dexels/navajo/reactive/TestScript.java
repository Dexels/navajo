package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.mongo.stream.MongoSupplier;
import com.dexels.navajo.parser.Expression;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class TestScript {

	private ReactiveScriptParser reactiveScriptParser;

	@Before
	public void setup() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		Expression.compileExpressions = true;
		reactiveScriptParser = new ReactiveScriptParser();

		MongoSupplier ms = new MongoSupplier();
		ms.activate();
	}
	
	public StreamScriptContext createContext(String serviceName) {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		StreamScriptContext context = new StreamScriptContext("tenant", serviceName, "username", "password", Collections.emptyMap());
		Flowable<NavajoStreamEvent> inStream = Observable.just(input).lift(StreamDocument.domStream()).toFlowable(BackpressureStrategy.BUFFER);
		context.setInputFlowable(inStream);
		return context;
	}
	
	@Test @Ignore
	public void testSQL() {
		SQL.query("dummy", "KNVB", "select * from organization where rownum < 500")
			.flatMap(msg->StreamDocument.replicationMessageToStreamEvents("Organization", msg, true))
			.compose(StreamDocument.inArray("Organization"))
			.compose(StreamDocument.inNavajo("ProcessGetOrg", "", ""))
			.lift(StreamDocument.serialize())
		
		.blockingForEach(e->System.err.print(new String(e)));
	}
	@Test @Ignore
	public void testSimpleScript() throws IOException {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("simplereactive.xml")) {
			StreamScriptContext myContext = createContext("SimpleReactiveSql");
			reactiveScriptParser.parse(myContext.service, in).execute(myContext)
				.lift(StreamDocument.serialize())
				.blockingForEach(e->System.err.print(new String(e)));
		}
	}
	
	@Test @Ignore
	public void testScript() throws IOException {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("reactive.xml")) {
			StreamScriptContext myContext = createContext("AdvancedReactiveSql");
			reactiveScriptParser.parse(myContext.service, in).execute(myContext)
				.lift(StreamDocument.serialize())
				.blockingForEach(e->System.err.print(new String(e)));
		}
	}
	
	@Test @Ignore
	public void testMongoScript() throws IOException {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("reactivemongo.xml")) {
			StreamScriptContext myContext = createContext("SimpleReactiveMongoDb");
			reactiveScriptParser.parse(myContext.service, in).execute(myContext)
				.lift(StreamDocument.serialize())
				.blockingForEach(e->System.err.print(new String(e)));
//				.scan(0L, (a,l)->a+l.length)
//				.blockingForEach(e->System.err.println("Bytes: "+e));
		}
	}
}
