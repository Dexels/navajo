package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.mongo.stream.MongoSupplier;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.reactive.transformer.csv.CSVTransformer;
import com.dexels.navajo.reactive.transformer.filestore.FileStoreTransformer;
import com.dexels.navajo.reactive.transformer.filestore.FileStoreTransformerFactory;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;

public class TestScript {

	private ReactiveScriptParser reactiveScriptParser;
	private ReplicationMessage person1;
	private ReplicationMessage person2;

	@Before
	public void setup() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		Expression.compileExpressions = true;
		reactiveScriptParser = new ReactiveScriptParser();

		MongoSupplier ms = new MongoSupplier();
		ms.activate();
		
		person1 = ReplicationFactory.getInstance().parseStream(this.getClass().getResourceAsStream("person.json"));
		person2 = ReplicationFactory.getInstance().parseStream(this.getClass().getResourceAsStream("person2.json"));

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
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("reactivemongowithoutreduce.xml")) {
			StreamScriptContext myContext = createContext("SimpleReactiveMongoDb");
			reactiveScriptParser.parse(myContext.service, in).execute(myContext)
				.lift(StreamDocument.serialize())
				.blockingForEach(e->System.err.print(new String(e)));
		}
	}
	
	@Test
	public void testCSV() {
		String[] columns = new String[]{"personid","firstname","dateofbirth"};
		
		Flowable.just(person1, person2)
		.map(e->DataItem.of(e))
			.lift(CSVTransformer.flowableCSV(Arrays.asList(columns), Arrays.asList(new String[]{}), false, ";"))
			.blockingForEach(e->{
				System.err.println("Line: "+new String(e.data()));
			});
	}

	@Test
	public void testFileStore() {
		String[] columns = new String[]{"personid","firstname","dateofbirth"};
		
		Flowable.just(person1, person2)
			.map(e->DataItem.of(e))
			.lift(CSVTransformer.flowableCSV(Arrays.asList(columns), Arrays.asList(new String[]{}), false, ";"))
			.lift(FileStoreTransformer.flowableFile("output.csv"))
//			.doOnNext(e->System.err.println("BBBB"))
			.blockingForEach(e->{
				System.err.println("Line: "+ReplicationFactory.getInstance().describe(e.message()));
			});
	}

	
	@Test @Ignore
	public void testMongoScriptAggregate() throws IOException {
		AtomicLong l = new AtomicLong();
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("mongoaggregate.xml")) {
			StreamScriptContext myContext = createContext("SimpleReactiveMongoDb");
			reactiveScriptParser.parse(myContext.service, in).execute(myContext)
				.lift(StreamDocument.serialize())
				.blockingForEach(e->{
					System.err.print(new String(e));
					l.addAndGet(e.length);
				});
		}
		System.err.println("Result: "+l.get());
	}
	
	@Test @Ignore
	public void testMongoScriptReactive() throws IOException {
		AtomicLong l = new AtomicLong();
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream("reactivemongowithoutreduce.xml")) {
			StreamScriptContext myContext = createContext("SimpleReactiveMongoDb");
			reactiveScriptParser.parse(myContext.service, in).execute(myContext)
				.lift(StreamDocument.serialize())
				.blockingForEach(e->{
					System.err.print(new String(e));
					l.addAndGet(e.length);
				});
		}
		System.err.println("Result: "+l.get());
	}
}


