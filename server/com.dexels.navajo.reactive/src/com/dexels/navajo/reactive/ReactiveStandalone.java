package com.dexels.navajo.reactive;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.ReactiveScript;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.parser.compiled.ASTReactiveScriptNode;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.parser.compiled.api.ReactivePipeNode;
import com.dexels.navajo.reactive.api.CompiledReactiveScript;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactivePipe;

import io.reactivex.Flowable;

public class ReactiveStandalone {

	public static Navajo runBlockingEmptyFromClassPath(String classPathPath) throws ParseException, IOException {
		try(InputStream in = ReactiveStandalone.class.getClassLoader().getResourceAsStream(classPathPath)) {
			Navajo n = ReactiveStandalone.runBlockingEmpty(in, Optional.empty());
			return n;
		}
	}
	
	public static Navajo runBlockingEmpty(ReactiveScriptEnvironment resolver, String service, Optional<String> binaryMime, List<String> methods) throws ParseException, IOException {
		StreamScriptContext context = new StreamScriptContext("tenant","service","deployment")
				.withRunner(resolver)
				.withInputNavajo(NavajoFactory.getInstance().createNavajo());
		ReactiveScript compiledScript = resolver.compiledScript(service);
		System.err.println("Data type: "+compiledScript.dataType());
		Flowable<Flowable<DataItem>> execute = compiledScript.execute(context);

		switch(compiledScript.dataType()) {
		case ANY:
			break;
		case DATA:
			break;
		case EMPTY:
			break;
		case EVENT:
			return execute
					.flatMap(e->e)
					.map(e->e.event())
					.compose(StreamDocument.inNavajo("service", Optional.empty(), Optional.empty(),methods))
					.toObservable()
					.compose(StreamDocument.domStreamCollector())
					.blockingFirst();
		case EVENTSTREAM:
			return execute
					.flatMap(e->e)
					.concatMap(e->e.eventStream())
					.compose(StreamDocument.inNavajo("service", Optional.empty(), Optional.empty(),methods))
					.toObservable()
					.compose(StreamDocument.domStreamCollector())
					.blockingFirst();
		case MESSAGE:
			return execute
					.concatMap(e->e)
					.map(e->e.message())
					.compose(StreamDocument.toMessageEvent("Item",true))
					.compose(StreamDocument.inNavajo("service", Optional.empty(), Optional.empty(),methods))
					.toObservable()
					.compose(StreamDocument.domStreamCollector())
					.blockingFirst();
					
		case MSGLIST:
			break;
		case MSGSTREAM:
			break;
		case SINGLEMESSAGE:
			break;
		default:
			break;
		
		}
		return execute
			.flatMap(e->e)
			.map(e->e.event())
			.compose(StreamDocument.inNavajo("service", Optional.empty(), Optional.empty(),methods))
			.toObservable()
			.compose(StreamDocument.domStreamCollector())
			.blockingFirst();
//		return runBlockingEmpty(new ByteArrayInputStream(inExpression.getBytes()),binaryMime);
	}
	public static Navajo runBlockingEmpty(String inExpression, Optional<String> binaryMime) throws ParseException, IOException {
		return runBlockingEmpty(new ByteArrayInputStream(inExpression.getBytes()),binaryMime);
	}
	public static Navajo runBlockingEmpty(InputStream inExpression, Optional<String> binaryMime) throws ParseException, IOException {
		return runBlockingInput(inExpression,binaryMime, NavajoFactory.getInstance().createNavajo());
	}

	public static Navajo runBlockingStream(InputStream inExpression, Optional<String> binaryMime, Flowable<DataItem> input) throws ParseException, IOException {
		StreamScriptContext context = new StreamScriptContext("tenant","service","deployment").withInput(input);
		return runContext(inExpression, binaryMime, context);
	}

	public static Navajo runBlockingInput(InputStream inExpression, Optional<String> binaryMime, Navajo input) throws ParseException, IOException {
		StreamScriptContext context = new StreamScriptContext("tenant","service","deployment").withInputNavajo(input);
		return runContext(inExpression, binaryMime, context);
	}

	private static Navajo runContext(InputStream inExpression, Optional<String> binaryMime, StreamScriptContext context)
			throws ParseException, IOException {
		CompiledReactiveScript crs = compileReactiveScript(inExpression,binaryMime);
		crs.typecheck();
		return Flowable.fromIterable(crs.pipes)
				.concatMap(e->e.execute(context, Optional.empty(), ImmutableFactory.empty()))
				.map(e->e.event())
				.compose(StreamDocument.inNavajo("service", Optional.empty(), Optional.empty(),crs.methods))
				.toObservable()
				.compose(StreamDocument.domStreamCollector())
				.blockingFirst();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static CompiledReactiveScript compileReactiveScript(InputStream inExpression, Optional<String> binaryMime) throws ParseException, IOException {
		try(Reader in = new InputStreamReader(inExpression)) {
			CompiledParser cp = new CompiledParser(in);
			cp.ReactiveScript();
			List<String> problems = new ArrayList<>();
			ASTReactiveScriptNode rootNode = (ASTReactiveScriptNode) cp.getJJTree().rootNode();
//			List<ReactivePipeNode> pipes
			List<ReactivePipeNode> src = (List<ReactivePipeNode>) rootNode.interpretToLambda(problems,"",Reactive.finderInstance().functionClassifier()).apply().value;
			System.err.println("Class: "+rootNode.getClass()+" -> "+rootNode.methods());
			System.err.println("Sourcetype: "+src);
			List<ReactivePipe> pp = src.stream().map(e->((ReactivePipe)e.apply().value)).collect(Collectors.toList());
			CompiledReactiveScript compiledReactiveScript = new CompiledReactiveScript(pp, rootNode.methods(),binaryMime);
			compiledReactiveScript.typecheck();
			return compiledReactiveScript;
		}
	}

}
