package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.parser.Expression;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

public class TestScript {

	private StreamScriptContext context;
	private Navajo input;
	@Before
	public void setup() {
		input = NavajoFactory.getInstance().createNavajo();
//		RxJavaAssemblyTracking.enable();
		context = new StreamScriptContext("tenant", "service", "username", "password", Collections.emptyMap());
		context.setNavajo(input);
		Expression.compileExpressions = true;
	}
	@Test
	public void testSQL() {
		SQL.query("dummy", "KNVB", "select * from organization where rownum < 500")
			.flatMap(msg->StreamDocument.replicationMessageToStreamEvents("Organization", msg, true))
			.compose(StreamDocument.inArray("Organization"))
			.compose(StreamDocument.inNavajo("ProcessGetOrg", "", ""))
			.lift(StreamDocument.serialize())
		
		.blockingForEach(e->System.err.print(new String(e)));
	}
	@Test
	public void testSimpleScript() throws IOException {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("simplereactive.xml")) {
			ReactiveScriptParser rsp = new ReactiveScriptParser();
			rsp.parse(in).execute(context, input, null)
				.lift(StreamDocument.serialize())
				.blockingForEach(e->System.err.print(new String(e)));
		}
	}
	
	@Test
	public void testScript() throws IOException {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("reactive.xml")) {
			ReactiveScriptParser rsp = new ReactiveScriptParser();
			rsp.parse(in).execute(context, input, null)
				.lift(StreamDocument.serialize())
				.blockingForEach(e->System.err.print(new String(e)));
		}
	}
}
