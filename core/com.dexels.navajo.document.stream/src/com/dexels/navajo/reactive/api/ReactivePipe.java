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
//		typecheck();
	}
	
	public void typecheck() {
		Type current = source.sourceType();
		Optional<Type> last = Optional.empty();
		Optional<String> lastTransformer = Optional.empty();
		int index = 0;
		System.err.println("# of transformers: "+transformers.size());
		for (Object reactiveTransformer : transformers) {
			System.err.println("index: "+index++);
			if(reactiveTransformer instanceof ReactiveTransformer) {
				TransformerMetadata transformer = ((ReactiveTransformer)reactiveTransformer).metadata();
				// maybe return implicit transformer
				current = matchType(last,lastTransformer,current,transformer);
				lastTransformer = Optional.of(transformer.name());
				
			} else {
				current = matchType(last,lastTransformer,current, new ImplicitTransformerMetadata());
				System.err.println("Type checking implicit");
				lastTransformer = Optional.of("implicit");
			}
			last = Optional.of(current);
		}
		System.err.println("Final type: "+current);
	}

	private Type matchType(Optional<Type> last, Optional<String> lastTransformer, Type initial, TransformerMetadata metadata) {
		System.err.println("Matching data for transformer: "+metadata.name()+" from previous transformer: "+lastTransformer.orElse("source"));
		System.err.println("Moving from input: "+initial+" to in-type: "+metadata.inType()+" contains? "+metadata.inType().contains(initial));
		System.err.println("Next type: "+metadata.outType());
		if(!metadata.inType().contains(initial)) {
			throw new RuntimeException("Type mismatch. Outgoing type: "+initial+" from previous transformer: "+lastTransformer.orElse("source")+" does not match possible incoming types: "+metadata.inType()+" name: "+metadata.name()+" type: "+metadata.getClass().getName());
		}
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
