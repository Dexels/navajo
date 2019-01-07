package com.dexels.navajo.reactive.api;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.Flowable;

public class ReactivePipe implements ReactiveSource {
	public final ReactiveSource source;
	public final List<Object> transformers;

	public ReactivePipe(ReactiveSource source, List<Object> transformers) {
		this.source = source;
		this.transformers = transformers;
		typeCheck();
	}
	
	private void typeCheck() {
		Type current = source.sourceType();
		for (Object reactiveTransformer : transformers) {
			if(reactiveTransformer instanceof ReactiveTransformer) {
				TransformerMetadata transformer = ((ReactiveTransformer)reactiveTransformer).metadata();
				// maybe return implicit transformer
				current = matchType(current,transformer);
				
			} else {
				current = matchType(current, new ImplicitTransformerMetadata());
			}
		}
		System.err.println("Final type: "+current);
	}

	private Type matchType(Type initial, TransformerMetadata metadata) {
		System.err.println("Matching data for transformer: "+metadata.name());
		System.err.println("Moving from input: "+initial+" to in-type: "+metadata.inType()+" contains? "+metadata.inType().contains(initial));
		System.err.println("Next type: "+metadata.outType());
		return metadata.outType();
		
	}

	public DataItem.Type finalType() {
		Type type = source.sourceType();
		for (Object reactiveTransformer : transformers) {
			if(reactiveTransformer instanceof ReactiveTransformer) {
				type = ((ReactiveTransformer)reactiveTransformer).metadata().outType();
			} else {
				type = Type.MESSAGE;
			}
		}
		return type;
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
				// create implicit transformer around mapper
				Function<StreamScriptContext,Function<DataItem,DataItem>> mapper = (Function<StreamScriptContext,Function<DataItem,DataItem>>) reactiveTransformer;
					currentFlow = currentFlow.map(a->mapper.apply(context).apply(a));
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
