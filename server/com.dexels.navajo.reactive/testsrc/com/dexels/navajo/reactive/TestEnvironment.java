package com.dexels.navajo.reactive;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.parser.Expression;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class TestEnvironment {

	public TestEnvironment() {
	}

	@Before
	public void setup() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		Expression.compileExpressions = true;
//		reactiveScriptParser = new ReactiveScriptParser();
//		MongoSupplier ms = new MongoSupplier();
//		ms.activate();
	}

	@Test @Ignore
	public void testEnv() throws IOException {
		File root = new File("testscripts");
		ReactiveScriptEnvironment env = new ReactiveScriptEnvironment(root);
		env.run(createContext("mongoaggregate"))
			.lift(StreamDocument.serialize())
			.blockingForEach(e->System.err.print(new String(e)));

	}

	public StreamScriptContext createContext(String serviceName) {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		StreamScriptContext context = new StreamScriptContext("tenant", serviceName, "username", "password", Collections.emptyMap());
		Flowable<NavajoStreamEvent> inStream = Observable.just(input).lift(StreamDocument.domStream()).toFlowable(BackpressureStrategy.BUFFER);
		context.setInputFlowable(inStream);
		return context;
	}

}
