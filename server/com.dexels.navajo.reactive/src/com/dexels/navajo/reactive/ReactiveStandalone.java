package com.dexels.navajo.reactive;

import java.io.ByteArrayInputStream;
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
import com.dexels.navajo.parser.compiled.ASTReactiveScriptNode;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.Node;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.parser.compiled.api.ReactivePipeNode;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactivePipe;

import io.reactivex.Flowable;

public class ReactiveStandalone {

	public static Navajo runBlockingEmptyFromClassPath(String classPathPath) throws ParseException, IOException {
		try(InputStream in = ReactiveStandalone.class.getClassLoader().getResourceAsStream(classPathPath)) {
			Navajo n = ReactiveStandalone.runBlockingEmpty(in);
			return n;
		}
	}
	public static Navajo runBlockingEmpty(String inExpression) throws ParseException, IOException {
		return runBlockingEmpty(new ByteArrayInputStream(inExpression.getBytes()));
	}
	public static Navajo runBlockingEmpty(InputStream inExpression) throws ParseException, IOException {
		try(Reader in = new InputStreamReader(inExpression)) {
			CompiledParser cp = new CompiledParser(in);
			cp.ReactiveScript();
			List<String> problems = new ArrayList<>();
			ASTReactiveScriptNode rootNode = (ASTReactiveScriptNode) cp.getJJTree().rootNode();
//			List<ReactivePipeNode> pipes
			List<ReactivePipeNode> src = (List<ReactivePipeNode>) rootNode.interpretToLambda(problems,"",Reactive.finderInstance().functionClassifier()).apply().value;
			System.err.println("Class: "+rootNode.getClass()+" -> "+rootNode.methods());
			System.err.println("Sourcetype: "+src);
			ReactivePipe pipe = (ReactivePipe) src.get(0).apply().value;
//			return null;
			StreamScriptContext context = new StreamScriptContext("tenant","service","deployment").withInputNavajo(NavajoFactory.getInstance().createNavajo());
			return Flowable.fromIterable(src)
				.map(e->((ReactivePipe)e.apply().value))
				.concatMap(e->e.execute(context, Optional.empty(), ImmutableFactory.empty()))
				.map(e->e.event())
				.compose(StreamDocument.inNavajo("service", Optional.empty(), Optional.empty(),rootNode.methods()))
				.toObservable()
				.compose(StreamDocument.domStreamCollector())
				.blockingFirst();
	}
}

}
