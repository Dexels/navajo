package com.dexels.navajo.reactive.source.single;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;

public class SingleSource implements ReactiveSource {

	private final ReactiveParameters params;
	private final List<ReactiveTransformer> transformers;
	private Type finalType;
	private final Optional<XMLElement> sourceElement;
	private final String sourcePath;
	private final SourceMetadata metadata;
	
	public SingleSource(SourceMetadata metadata, ReactiveParameters params, List<ReactiveTransformer> transformers, DataItem.Type finalType, Optional<XMLElement> sourceElement, String sourcePath) {
		this.metadata = metadata;
		this.params = params;
		this.transformers = transformers;
		this.finalType = finalType;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters parameters = this.params.resolveNamed(context, current, ImmutableFactory.empty(), metadata, sourceElement, sourcePath);
		boolean debug = parameters.paramBoolean("debug", ()->false);
		int count =  parameters.paramInteger("count", ()->1);
		int delay =  parameters.paramInteger("delay", ()->0);
		System.err.println("DELAY: "+delay+" count: "+count);
		try {

//			if(delay>0) {
//				Flowable<Long> f = Flowable.interval(delay, TimeUnit.MILLISECONDS).take(count);
//			} else {
//				Flowable.range(0, count);
//			}
			
			Flowable<Long> f = delay > 0 ? Flowable.interval(delay, TimeUnit.MILLISECONDS).take(count) : Flowable.rangeLong(0, count);
			Flowable<DataItem> flow = f.map(i->DataItem.of(ReactiveScriptParser.empty().with("index", i.intValue(), "integer")));
			
			if(debug) {
				flow = flow.doOnNext(di->System.err.println("Item: "+ImmutableFactory.getInstance().describe(di.message())));
			}
			for (ReactiveTransformer reactiveTransformer : transformers) {
				flow = flow.compose(reactiveTransformer.execute(context));
			}
			return flow;
		} catch (Exception e) {
			return Flowable.error(e);
		}
	}

	@Override
	public Type finalType() {
		return finalType;
	}

	@Override
	public boolean streamInput() {
		return false;
	}
	
}
