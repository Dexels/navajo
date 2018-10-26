package com.dexels.navajo.reactive.transformer.call;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class CallLocalSource implements ReactiveSource {

	private final ReactiveParameters params;
	private final SourceMetadata metadata;
	private final String relativePath;
	private final Optional<XMLElement> sourceElement;
	private final Type finalType;
	private List<ReactiveTransformer> transformers;

	public CallLocalSource(SourceMetadata metadata, String relativePath, String type, List<ReactiveParseProblem> problems,
			Optional<XMLElement> sourceElement, ReactiveParameters params, List<ReactiveTransformer> transformers, Type finalType,
			Function<String, ReactiveMerger> reducerSupplier) {
		this.metadata = metadata;
		this.params = params;
		this.relativePath = relativePath;
		this.sourceElement = sourceElement;
		this.transformers = transformers;
		this.finalType = finalType;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters resolved = params.resolveNamed(context, current, ImmutableFactory.empty(), metadata, sourceElement, relativePath);
		final String service =  resolved.paramString("service");
		final Optional<String> tenant =  resolved.optionalString("service");
		final boolean debug = resolved.paramBoolean("debug", ()->false);
		Flowable<NavajoStreamEvent> emptyInput = Flowable.<NavajoStreamEvent>empty().compose(StreamDocument.inNavajo(service, Optional.of(context.getUsername()), Optional.empty()));
		StreamScriptContext ctx = context.withService(service)
				.withInput(emptyInput)
						
//				.withInputNavajo(NavajoFactory.getInstance().createNavajo())
				.withInput(Flowable.empty());
		
		if(tenant.isPresent()) {
			ctx = ctx.withTenant(tenant.get());
		}
				
		
		try {
			Flowable<DataItem> flow = ctx.runner().build(service, debug).execute(ctx)
					.map(e->e.eventStream())
					.concatMap(e->e)
					.filter(e->e.type()!=NavajoStreamEvent.NavajoEventTypes.NAVAJO_STARTED && e.type()!=NavajoStreamEvent.NavajoEventTypes.NAVAJO_DONE)
					.map(DataItem::of);
					
//			Flowable<DataItem> item  =Flowable.just(DataItem.ofEventStream(flow));;
			
			for (ReactiveTransformer reactiveTransformer : transformers) {
				flow = flow.compose(reactiveTransformer.execute(context,current));
			}

			return flow;
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
	public Type finalType() {
		return finalType;
	}

	@Override
	public boolean streamInput() {
		return false;
	}

}
