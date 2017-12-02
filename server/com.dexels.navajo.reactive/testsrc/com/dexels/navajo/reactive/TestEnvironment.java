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
import com.dexels.navajo.reactive.source.sql.SQLReactiveSourceFactory;
import com.dexels.navajo.reactive.stored.InputStreamSourceFactory;
import com.dexels.navajo.reactive.transformer.call.CallTransformerFactory;
import com.dexels.navajo.reactive.transformer.csv.CSVTransformerFactory;
import com.dexels.navajo.reactive.transformer.filestore.FileStoreTransformerFactory;
import com.dexels.navajo.reactive.transformer.mergesingle.MergeSingleTransformerFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceTransformerFactory;
import com.dexels.navajo.reactive.transformer.single.SingleMessageTransformerFactory;
import com.dexels.navajo.reactive.transformer.stream.StreamMessageTransformerFactory;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;
import com.dexels.replication.impl.json.ReplicationJSON;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		File root = new File("testscripts");
		env = new ReactiveScriptEnvironment(root);
		ReactiveScriptParser reactiveScriptParser = new ReactiveScriptParser();
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
		Expression.compileExpressions = true;
		reactiveScriptParser = new ReactiveScriptParser();
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
		env.setReactiveScriptParser(reactiveScriptParser);
//		rsp.addReactiveSourceFactory("", settings);
	}

	@Test 
	public void testEnv() throws IOException {
		runScript("singlesimple")
			.lift(StreamDocument.serialize())
			.blockingForEach(e->System.err.print(new String(e)));


	}

	@Test 
	public void testSingleMerge() throws IOException {
		runScript("reactive")
			.lift(StreamDocument.serialize())
			.blockingForEach(e->System.err.print(new String(e)));
	}
	
	@Test 
	public void testSingle() throws IOException {
		runScript("single")
			.lift(StreamDocument.serialize())
			.blockingForEach(e->System.err.print(new String(e)));
	}
	
	@Test 
	public void testSqlDump() throws IOException {
		runScript("sql")
			.lift(StreamDocument.serialize())
			.blockingForEach(e->System.err.print(new String(e)));
	}


	
	@Test 
	public void testInputStream() throws IOException {
		runScript("inputstream")
		.lift(StreamDocument.serialize())
		.blockingForEach(e->System.err.print(new String(e)));
	}
	
	@Test 
	public void testCSVStream() throws IOException {
		runScriptBinary("csv")
			.blockingForEach(e->System.err.print(new String(e)));
	}

	@Test 
	public void testParseJSON() throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream("person.json");
		ReplicationMessage rm = ReplicationJSON.parseReplicationMessage(is, new ObjectMapper());
		System.err.println(ReplicationFactory.getInstance().describe(rm));
		
	}

	private Flowable<NavajoStreamEvent> runScript(String name) {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream(name+".xml")) {
				env.installScript(name, in,"serviceName");
				return env.run(name,true).execute(createContext(name))
						.map(di->di.event());
		} catch (IOException e1) {
			return Flowable.error(e1);
		}
	}
	
	private Flowable<byte[]> runScriptBinary(String name) {
		try( InputStream in = TestScript.class.getClassLoader().getResourceAsStream(name+".xml")) {
				env.installScript(name, in,"serviceName");
				return env.run(name,true).execute(createContext(name))
						.map(di->di.data());
		} catch (IOException e1) {
			return Flowable.error(e1);
		}
	}
	
	public StreamScriptContext createContext(String serviceName) {
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Flowable<NavajoStreamEvent> inStream = Observable.just(input).lift(StreamDocument.domStream()).toFlowable(BackpressureStrategy.BUFFER);
		StreamScriptContext context = new StreamScriptContext("tenant", serviceName, Optional.empty(), Optional.empty(), Collections.emptyMap(),Optional.of(inStream),Optional.empty());
		return context;
	}

}
