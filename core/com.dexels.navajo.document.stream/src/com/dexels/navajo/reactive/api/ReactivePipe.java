package com.dexels.navajo.reactive.api;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.Flowable;

public class ReactivePipe implements ReactiveSource {
	public final ReactiveSource source;
	public final List<Object> transformers;
	Optional<String> binaryMime = Optional.empty();

	private final static Logger logger = LoggerFactory.getLogger(ReactivePipe.class);

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
		logger.info("# of transformers: "+transformers.size());
		for (Object reactiveTransformer : transformers) {
			logger.info("index: "+index++);
			if(reactiveTransformer instanceof ReactiveTransformer) {
				TransformerMetadata transformer = ((ReactiveTransformer)reactiveTransformer).metadata();
				// maybe return implicit transformer
				current = matchType(last,lastTransformer,current,transformer);
				lastTransformer = Optional.of(transformer.name());
				if(transformer.outType()==Type.DATA) {
					binaryMime = ((ReactiveTransformer)reactiveTransformer).mimeType();
				}
				
			} else {
				current = matchType(last,lastTransformer,current, new ImplicitTransformerMetadata());
				logger.info("Type checking implicit");
				lastTransformer = Optional.of("implicit");
			}
			last = Optional.of(current);
		}
		logger.info("Final type: "+current);
	}

	private Type matchType(Optional<Type> last, Optional<String> lastTransformer, Type initial, TransformerMetadata metadata) {
		logger.info("Matching data for transformer: "+metadata.name()+" from previous transformer: "+lastTransformer.orElse("source"));
		logger.info("Moving from input: "+initial+" to in-type: "+metadata.inType()+" contains? "+metadata.inType().contains(initial));
		logger.info("Next type: "+metadata.outType());
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
	
	public Optional<String> mimeType() {
		return binaryMime;
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
