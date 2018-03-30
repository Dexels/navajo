package com.dexels.navajo.reactive.transformer.call;

import java.io.IOException;
import java.util.Optional;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
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
	private Optional<XMLElement> sourceElement;
	private String sourcePath;
	private final TransformerMetadata metadata;
	
	public CallTransformer(TransformerMetadata metadata, ReactiveParameters parameters,Optional<XMLElement> sourceElement, String sourcePath) {
		this.parameters = parameters;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(),metadata, sourceElement, sourcePath);

//		final int parallel =  resolved.paramInteger("parallel", ()->0);
		final String service =  resolved.paramString("service");
//		final String messageName =  resolved.paramString("messageName");
		final boolean debug = resolved.paramBoolean("debug", ()->false);
//		final boolean isArray =  resolved.paramBoolean("isArray");
		return flow->
			{
//			Flowable<Flowable<NavajoStreamEvent>> stream = flow.map(di->di.message())
//					.map(msg->StreamDocument.replicationMessageToStreamEvents(messageName, msg, isArray));
			if(debug) {
				flow = flow.doOnNext(e->System.err.println("calltransformerEvent: "+ e));
			}
			Flowable<NavajoStreamEvent> ff = flow.map(e->e.event());
//			Navajo n = flow.map(e->e.event()).compose(StreamDocument.inNavajo(service,context.username, context.password)).lift(StreamDocument.collectFlowable()).blockingFirst();
//			System.err.println("calltransformerEvent...:");
//			n.write(System.err);
			StreamScriptContext ctx = context.withInput(ff).withService(service).withUsername(context.username).withPassword(context.password);
			try {
				return context.runner().build(service, debug).execute(ctx);
			} catch (IOException e1) {
				e1.printStackTrace();
				return Flowable.error(e1);
			}
//			return flow.map(str->context.runner()
//					.build(service,debug)
//					.execute(context.withoutInputNavajo().withService(service).withInput(str))
//					.map(e->e.event())
//				).map(e->DataItem.ofEventStream(e));
		};
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}


}
