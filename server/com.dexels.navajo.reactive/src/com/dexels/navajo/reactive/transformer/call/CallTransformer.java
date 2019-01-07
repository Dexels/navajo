package com.dexels.navajo.reactive.transformer.call;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

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
		return flow->
			{
				//TODO add messages? We have an event stream input, unsure how to deal with this.
			ReactiveResolvedParameters resolved = parameters.resolve(context, current,param,metadata);

			final String service =  resolved.paramString("service");
			final boolean debug = resolved.paramBoolean("debug", ()->false);
	
			if(debug) {
				flow = flow.doOnNext(e->logger.info("calltransformerEvent: "+ e));
			}
			Flowable<Flowable<NavajoStreamEvent>> ff = flow.map(e->e.eventStream());
			Flowable<Flowable<DataItem>> concatMap = ff.concatMap(ee->callService(context,service,debug).apply(ee));
			return concatMap.concatMap(e->e);
			
//			return ff.doOnNext(e->System.err.println("Calling service: "+service))
//					.map(ee->callService(context,service,debug).apply(ee))
//					.concatMap(e->e);
			
//			return ff.map(fx->{
//				StreamScriptContext ctx = context.withInput(fx)
//						.withService(service)
//						.withUsername(context.username)
//						.withPassword(context.password);
//				try {
//					Flowable<DataItem> x = context.runner().build(service, debug).execute(ctx);
//					return x;
//				} catch (IOException e1) {
//					e1.printStackTrace();
//					return Flowable.error(e1);
//				}
//			});
//					
//					; //.concatMap(e->e);
		};
	}

		
	private Function<Flowable<NavajoStreamEvent>,Flowable<Flowable<DataItem>>> callService(StreamScriptContext context, String service, boolean debug) {
		return fx->{
			
			StreamScriptContext ctx = context
			        .withInput(fx)
					.withService(service);
			try {
				Flowable<Flowable<DataItem>> x = Flowable.just(
							context.runner()
								.build(service, debug)
								.execute(ctx)
								.concatMap(e->e)
								
							);
				return x;
			} catch (IOException e1) {
				e1.printStackTrace();
				return Flowable.error(e1);
			}			
		};
	}
	
	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}
}
