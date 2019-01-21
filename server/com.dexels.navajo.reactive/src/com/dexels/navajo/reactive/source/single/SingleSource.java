package com.dexels.navajo.reactive.source.single;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;

public class SingleSource implements ReactiveSource {

	private final ReactiveParameters params;
//	private final List<ReactiveTransformer> transformers;
//	private Type finalType;
//	private final Optional<XMLElement> sourceElement;
//	private final String sourcePath;
	private final SourceMetadata metadata;
	
	
	private final static Logger logger = LoggerFactory.getLogger(SingleSource.class);

	
	public SingleSource(SourceMetadata metadata, ReactiveParameters params) {
		this.metadata = metadata;
		this.params = params;
//		this.transformers = transformers;
//		this.finalType = finalType;
//		this.sourceElement = sourceElement;
//		this.sourcePath = sourcePath;
	}


	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current,
			ImmutableMessage paramMessage) {
		ReactiveResolvedParameters parameters = this.params.resolve(context, current, paramMessage,metadata);

		boolean debug = parameters.paramBoolean("debug", ()->false);
		int count =  parameters.paramInteger("count", ()->1);
		int delay =  parameters.paramInteger("delay", ()->0);
		try {

			Flowable<Long> f = delay > 0 ? Flowable.interval(delay, TimeUnit.MILLISECONDS).take(count) : Flowable.rangeLong(0, count);
			Flowable<DataItem> flow = f.map(i->DataItem.of(ImmutableFactory.empty(),ImmutableFactory.empty().with("index", i.intValue(), "integer")));
			
			if(debug) {
				flow = flow.doOnNext(di->logger.info("Item: "+ImmutableFactory.getInstance().describe(di.message())));
				flow = flow.doOnNext(di->logger.info("State: "+ImmutableFactory.getInstance().describe(di.stateMessage())));
			}
			return flow;
		} catch (Exception e) {
			return Flowable.error(e);
		}
	}

	@Override
	public boolean streamInput() {
		return false;
	}


	@Override
	public Type sourceType() {
		return metadata.sourceType();
	}	
}
