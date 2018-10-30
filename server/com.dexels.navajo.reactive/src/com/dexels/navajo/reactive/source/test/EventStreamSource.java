package com.dexels.navajo.reactive.source.test;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.SourceMetadata;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class EventStreamSource implements ReactiveSource {

	private final SourceMetadata metadata;
	private final ReactiveParameters parameters;
	private final Optional<XMLElement> sourceElement;
	private final String sourcePath;
	private final Type finalType;
	private final List<ReactiveTransformer> transformers;
	private List<ReactiveParseProblem> problems;


	public EventStreamSource(SourceMetadata metadata, String sourcePath, String type, List<ReactiveParseProblem> problems,
			Optional<XMLElement> sourceElement, ReactiveParameters parameters, List<ReactiveTransformer> transformers, Type finalType,
			Function<String, ReactiveMerger> reducerSupplier) {
		this.metadata = metadata;
		this.parameters = parameters;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
		this.finalType = finalType;
		this.transformers = transformers;
		this.problems = problems;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters parameters = this.parameters.resolveNamed(context, current, ImmutableFactory.empty(), metadata, sourceElement, sourcePath);
		Optional<String> classpath = parameters.optionalString("classpath");
		if(classpath.isPresent()) { // TODO other sources
			InputStream is = getClass().getClassLoader().getResourceAsStream(classpath.get());
			Flowable<DataItem> flow = StreamDocument.dataFromInputStream(is)
					.lift(XML.parseFlowable(10))
					.concatMap(e->e)
					.lift(StreamDocument.parse())
//					.concatMap(e->e)
					.map(DataItem::ofEventStream);
			for (ReactiveTransformer reactiveTransformer : transformers) {
				flow = flow.compose(reactiveTransformer.execute(context,current));
			}
			return flow;
					//			Bytes/ b;
		}
		problems.add(ReactiveParseProblem.of("event source is missing a source ('classpath' only supported now)"));
		return null;
	}

	@Override
	public Type finalType() {
		return finalType;
	}

	@Override
	public boolean streamInput() {
		// TODO Auto-generated method stub
		return false;
	}

}
