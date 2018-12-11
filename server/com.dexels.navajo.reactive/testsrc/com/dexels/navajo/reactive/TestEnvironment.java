package com.dexels.navajo.reactive;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.reactive.api.ReactiveFinder;
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
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.impl.json.ReplicationJSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class TestEnvironment {

	private ReactiveScriptEnvironment env;

	public TestEnvironment() {
	}

	@Before
	public void setup() {
//		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		File root = new File("testscripts");
		env = new ReactiveScriptEnvironment(root);
		ReactiveScriptParser reactiveScriptParser = new ReactiveScriptParser();
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

	@Test 
	public void testEnv() throws IOException {
		runScriptTML("singlesimple")
			.lift(StreamDocument.serialize())
			.blockingForEach(e->System.err.print(new String(e)));


	}

	@Test 
	public void testSingleMerge() throws IOException {
		runScriptTML("reactive")
			.lift(StreamDocument.serialize())
			.blockingForEach(e->System.err.print(new String(e)));
	}
	
	@Test 
	public void testSingle() throws IOException {
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		runScriptTML("single")
			.lift(StreamDocument.serialize())
			.blockingForEach(e->System.err.print(new String(e)));
	}
	
	@Test 
	public void testSqlDump() throws IOException {
		runScriptTML("sql")
			.lift(StreamDocument.serialize())
			.blockingForEach(e->System.err.print(new String(e)));
	}


	
	@Test 
	public void testInputStream() throws IOException {
		runScriptTML("inputstream")
		.lift(StreamDocument.serialize())
		.blockingForEach(e->System.err.print(new String(e)));
	}
	
	@Test 
	public void testCSVStream() throws IOException {
		runScriptBinary("csv")
			.blockingForEach(e->System.err.print(new String(e)));
	}
	
	@Test 
	public void testCreateSubmessage() throws IOException {
		ImmutableMessage imm = runScript("singlesubmessage")
			.map(di->di.message())
			.blockingFirst();
		ImmutableFactory.getInstance().describe(imm);
		Assert.assertTrue(imm.subMessage("aap").isPresent());
		Assert.assertEquals("mies", imm.columnValue("other"));
//		Assert.assertTrue(imm.subMessages("mies").isPresent());
		
	}


	@Test 
	public void testParseJSON() throws IOException {
		InputStream is = getClass().getResourceAsStream("person.json");
		ImmutableFactory.setInstance(ImmutableFactory.createParser());
		ReplicationMessage rm = ReplicationJSON.parseReplicationMessage(is, Optional.empty(), new ObjectMapper());
		System.err.println(ImmutableFactory.getInstance().describe(rm.message()));
		
	}

	private Flowable<NavajoStreamEvent> runScriptTML(String name) {
		return runScript(name).map(d->d.event());
	}
	private Flowable<DataItem> runScript(String name) {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream(name+".xml")) {
				env.installScript(name, in,"serviceName");
				return env.build(name,true).execute(createContext(name));
						
		} catch (IOException e1) {
			return Flowable.error(e1);
		}
	}
	
	private Flowable<byte[]> runScriptBinary(String name) {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream(name+".xml")) {
				env.installScript(name, in,"serviceName");
				return env.build(name,true).execute(createContext(name))
						.map(di->di.data());
		} catch (IOException e1) {
			return Flowable.error(e1);
		}
	}
	
	public StreamScriptContext createContext(String serviceName) {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Flowable<NavajoStreamEvent> inStream = Single.just(input)
				.compose(StreamDocument.domStreamTransformer())
				.toObservable()
				.flatMap(e->e)
				.toFlowable(BackpressureStrategy.BUFFER);
		StreamScriptContext context = new StreamScriptContext("tenant", serviceName, Optional.empty(), Optional.empty(),
				NavajoFactory.getInstance().createNavajo(),
				Collections.emptyMap(),
				Optional.of(inStream),Optional.empty(),
				Collections.emptyList(),Optional.empty(),Optional.empty());
		return context;
	}

}
