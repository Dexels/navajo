package com.dexels.navajo.reactive.api;

import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class ReactivePipe implements ReactiveSource {
	public final ReactiveSource source;
	public final List<Object> transformers;

	public ReactivePipe(ReactiveSource source, List<Object> transformers) {
		this.source = source;
		this.transformers = transformers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current,
			ImmutableMessage paramMessage) {
		Flowable<DataItem> currentFlow = source.execute(context, current, paramMessage);
		for (Object reactiveTransformer : transformers) {
			if(reactiveTransformer instanceof ReactiveTransformer) {
				currentFlow = currentFlow.compose(((ReactiveTransformer)reactiveTransformer).execute(context, current, paramMessage));
			} else if(reactiveTransformer instanceof Function) {
				Function<StreamScriptContext,Function<DataItem,DataItem>> mapper = (Function<StreamScriptContext,Function<DataItem,DataItem>>) reactiveTransformer;
				try {
					currentFlow = currentFlow.map(mapper.apply(context));
				} catch (Exception e) {
					return Flowable.error(e);
				}
			}
		}
		return currentFlow;
	}

	@Override
	public boolean streamInput() {
		return source.streamInput();
	}

	@Override
	public Type sourceType() {
		return source.sourceType();
	}
}
