package com.dexels.navajo.reactive.transformer.call;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class PipeTransformer implements ReactiveTransformer {


	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;
	
	private final static Logger logger = LoggerFactory.getLogger(PipeTransformer.class);
	
	public PipeTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current,ImmutableMessage param) {
		ReactiveResolvedParameters resolved = parameters.resolve(context, current,param,metadata);
		final String service =  resolved.paramString("service");
		final boolean debug = resolved.paramBoolean("debug", ()->false);

		return flow->
			
			{
			if(debug) {
				flow = flow.doOnNext(e->logger.info("pipe: "+ e));
			}

			Flowable<Flowable<DataItem>> cc;
			try {
				cc = context.runner()
						.build(service, debug)
						.execute(context.withService(service).withInput(flow));
				Flowable<DataItem> ccatMap = cc.concatMap(e->e);
				return ccatMap;
			} catch (IOException e1) {
				return Flowable.error(e1);
			}
		};
	}
	
	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}
}
