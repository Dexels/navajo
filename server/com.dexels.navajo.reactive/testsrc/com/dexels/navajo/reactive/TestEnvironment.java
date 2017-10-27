package com.dexels.navajo.reactive;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;
import com.dexels.navajo.reactive.transformer.stream.StreamMessageTransformerFactory;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class TestEnvironment {

	private ReactiveScriptEnvironment env;

	public TestEnvironment() {
	}

	@Before
	public void setup() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		Expression.compileExpressions = true;
		File root = new File("testscripts");
		env = new ReactiveScriptEnvironment(root);
		ReactiveScriptParser rsp = new ReactiveScriptParser();
		rsp.addReactiveSourceFactory(new SingleSourceFactory(), "single");
		rsp.addReactiveTransformerFactory(new StreamMessageTransformerFactory(), "stream");
		env.setReactiveScriptParser(rsp);
//		rsp.addReactiveSourceFactory("", settings);
	}

	@Test 
	public void testEnv() throws IOException {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("singlesimple.xml")) {
			env.installScript("singlesimple", in);
		}
		env.run("singlesimple").execute(createContext("singlesimple"))
			.map(di->di.event())
			.lift(StreamDocument.serialize())
			.blockingForEach(e->System.err.print(new String(e)));

	}

	public StreamScriptContext createContext(String serviceName) {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Flowable<NavajoStreamEvent> inStream = Observable.just(input).lift(StreamDocument.domStream()).toFlowable(BackpressureStrategy.BUFFER);
		StreamScriptContext context = new StreamScriptContext("tenant", serviceName, Optional.empty(), Optional.empty(), Collections.emptyMap(),Optional.of(inStream),Optional.empty());
		return context;
	}

}
