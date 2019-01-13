package com.dexels.navajo.reactive.transformer.call;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class CallTransformer implements ReactiveTransformer {


	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;
	
	private final static Logger logger = LoggerFactory.getLogger(CallTransformer.class);
	
	public CallTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
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
				//TODO add messages? We have an event stream input, unsure how to deal with this.
	
			if(debug) {
				flow = flow.doOnNext(
						e->logger.info("calltransformerEvent: "+ e)
				);
			}
			Flowable<NavajoStreamEvent> ff = flow.map(e->e.eventStream())
					.concatMap(e->e)
					.doOnNext(e->System.err.println("><> event: "+e));
//			Flowable<DataItem> ddd = ff.concatMap(e->e)
//					.doOnNext(
//							e->System.err.println("Navajo Stream Event: "+e)
//							)
//					.map(DataItem::of);
			
			StreamScriptContext ctx = context
			        .withInput( ff.map(DataItem::of))
					.withService(service);

//			return ctx.inputFlowable().get();
			return callService(ctx, service, debug).concatMap(e->e);
//			return ff.map(DataItem::of);
//			return callService(context,ff,service,debug).concatMap(e->e);

		};
	}

		
	private Flowable<Flowable<DataItem>> callService(StreamScriptContext ctx, String service, boolean debug) {
//			StreamScriptContext ctx = context
//			        .withoutInputNavajo()
//			        .withInput(input.map(DataItem::of))
//					.withService(service);
			
			try {
				return Flowable.just(
							ctx.runner()
								.build(service, debug)
								.execute(ctx)
								.concatMap(e->e)
							);
			} catch (IOException e1) {
				return Flowable.error(e1);
			}			
	}
	
	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}
}
