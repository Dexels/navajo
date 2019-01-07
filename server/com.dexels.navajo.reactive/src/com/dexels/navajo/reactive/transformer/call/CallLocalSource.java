package com.dexels.navajo.reactive.transformer.call;

import java.io.IOException;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;

public class CallLocalSource implements ReactiveSource {

	private final ReactiveParameters params;
	private final SourceMetadata metadata;

	public CallLocalSource(SourceMetadata metadata, ReactiveParameters params) {
		this.metadata = metadata;
		this.params = params;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters resolved = params.resolve(context, current, ImmutableFactory.empty(), metadata);
		final String service =  resolved.paramString("service");
		final Optional<String> tenant =  resolved.optionalString("service");
		final boolean debug = resolved.paramBoolean("debug", ()->false);
		System.err.println("Calling local: "+service);
		Flowable<NavajoStreamEvent> emptyInput = Flowable.<NavajoStreamEvent>empty().compose(StreamDocument.inNavajo(service, Optional.of(context.getUsername()), Optional.empty()));
		StreamScriptContext ctx = context.withService(service)
				.withInput(emptyInput)
				.withInput(Flowable.empty());
		
		if(tenant.isPresent()) {
			ctx = ctx.withTenant(tenant.get());
		}
				
		
		try {
			return ctx.runner()
					.build(service, debug)
					.execute(ctx)
					.concatMap(e->e)
					.map(e->e.eventStream())
					.concatMap(e->e)
					.filter(e->e.type()!=NavajoStreamEvent.NavajoEventTypes.NAVAJO_STARTED && e.type()!=NavajoStreamEvent.NavajoEventTypes.NAVAJO_DONE)
					.map(DataItem::of);

		} catch (IOException e) {
			return Flowable.error(e);
		}

		
//		Flowable<DataItem> flow = client.callWithBodyToStream(server, e->
//		e.header("X-Navajo-Username", username)
//		 .header("X-Navajo-Password", password)
//		 .header("X-Navajo-Service", service)
//		 .method(HttpMethod.POST)
//		, Flowable.<NavajoStreamEvent>empty()
//				.compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)))
//				.lift(StreamDocument.serialize())
//		, "text/xml")
//		.doOnNext(e->System.err.println(new String(e)))
//		.lift(XML.parseFlowable(10))
//		.concatMap(e->e)
//		.lift(StreamDocument.parse())
//		.map(DataItem::ofEventStream);
//		
//		for (ReactiveTransformer reactiveTransformer : transformers) {
//			flow = flow.compose(reactiveTransformer.execute(context));
//		}
//		return flow;
	}


	@Override
	public boolean streamInput() {
		return false;
	}

	@Override
	public Type sourceType() {
		return Type.EVENT;
	}

}
