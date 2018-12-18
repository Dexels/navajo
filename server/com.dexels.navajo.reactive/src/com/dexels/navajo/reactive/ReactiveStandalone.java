package com.dexels.navajo.reactive;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.Node;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.parser.compiled.api.ReactivePipeNode;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactivePipe;

import io.reactivex.Flowable;

public class ReactiveStandalone {

	public static Navajo runBlockingEmpty(InputStream inExpression) throws ParseException, IOException {
		return runExpression(inExpression, "tenant","service","deployment",NavajoFactory.getInstance().createNavajo())
				.map(e->e.event())
				.compose(StreamDocument.inNavajo("service", Optional.empty(), Optional.empty()))
				.toObservable()
				.compose(StreamDocument.domStreamCollector())
				.blockingFirst();
	}

	public static Flowable<DataItem> runExpression(InputStream inExpression, String tenant, String service, String deployment, Navajo input) throws ParseException, IOException {
		StreamScriptContext context = new StreamScriptContext(tenant, service, deployment).withInputNavajo(input);
		try(Reader in = new InputStreamReader(inExpression)) {
 
			CompiledParser cp = new CompiledParser(in);
			cp.ReactiveScript();
			List<String> problems = new ArrayList<>();
			Node rootNode = cp.getJJTree().rootNode();
			System.err.println("Class: "+rootNode.getClass());
//			List<ReactivePipeNode> pipes
			List<ReactivePipeNode> src = (List<ReactivePipeNode>) rootNode.interpretToLambda(problems,"",Reactive.finderInstance().functionClassifier()).apply().value;
			System.err.println("Sourcetype: "+src);
			ReactivePipe pipe = (ReactivePipe) src.get(0).apply().value;
//			return null;
			return Flowable.fromIterable(src)
				.map(e->((ReactivePipe)e.apply().value))
				.concatMap(e->e.execute(context, Optional.empty(), ImmutableFactory.empty()));
//			Flowable<DataItem> flow = pipe.execute(context, Optional.empty(), ImmutableFactory.empty());
//			return flow;
		}

	}
}
