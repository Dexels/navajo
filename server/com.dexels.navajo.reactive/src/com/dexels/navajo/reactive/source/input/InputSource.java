package com.dexels.navajo.reactive.source.input;

import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;

public class InputSource implements ReactiveSource {
	
	private Type finalType;
	private final List<ReactiveTransformer> transformers;
//	private final ReactiveParameters params;
//	private final Optional<XMLElement> sourceElement;
//	private final String sourcePath;
//	private final SourceMetadata metadata;
	
	public InputSource(SourceMetadata metadata, ReactiveParameters params, List<ReactiveTransformer> transformers, DataItem.Type finalType, Optional<XMLElement> sourceElement, String sourcePath) {
		this.transformers = transformers;
		this.finalType = finalType;
//		this.metadata = metadata;
//		this.params = params;
//		this.sourceElement = sourceElement;
//		this.sourcePath = sourcePath;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
//		ReactiveResolvedParameters parameters = this.params.resolveNamed(context, current, ImmutableFactory.empty(), metadata, sourceElement, sourcePath);

		Flowable<DataItem> flow = context.inputFlowable()
				.lift(StreamDocument.collectEventsToImmutable())
				.map(DataItem::of);
		for (ReactiveTransformer reactiveTransformer : transformers) {
			flow = flow.compose(reactiveTransformer.execute(context));
		}
		return flow;
	}

	@Override
	public Type finalType() {
		return finalType;
	}

	@Override
	public boolean streamInput() {
		return true;
	}

}
