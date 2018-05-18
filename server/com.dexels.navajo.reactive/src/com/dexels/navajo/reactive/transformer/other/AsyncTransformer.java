package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.RunningReactiveScripts;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AsyncTransformer implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;
	private final ReactiveSource subSource;
//	private final TopicPublisher publisher;

	public AsyncTransformer(AsyncTransformerFactory metadata, ReactiveParameters parameters,
			ReactiveSource sub, Function<StreamScriptContext, Function<DataItem, DataItem>> joinermapper) {
		this.parameters = parameters;
		this.metadata = metadata;
		this.subSource = sub;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		StreamScriptContext cp = context.copyWithNewUUID();
		RunningReactiveScripts rrs = context.runningScripts().get();
		
		return e->{
			Disposable sub = e.map(d->d.message())
				.subscribeOn(Schedulers.io())
//				.observeOn(Schedulers.io())
				.doOnComplete(()->rrs.complete(cp.uuid()))
				.subscribe(c->{
					storeMessage(c);
				}
			);
//			StreamScriptContext withd = cp.withDispose(()->sub.dispose());
			rrs.submit(cp.withDispose(()->sub.dispose()));
			
			return subSource.execute(context, Optional.empty());
//			return Flowable.just(createOutputMessage(context))
//					.map(DataItem::of);
		};
		
//		return e->e.filter(item->{
//			ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.of(item.message()), item.stateMessage(), metadata, Optional.empty(), "");
//			return parms.paramBoolean("filter");
//		});
	}

	private void storeMessage(ImmutableMessage c) {
		//
	}

	private ImmutableMessage createOutputMessage(StreamScriptContext context) {
		return ImmutableFactory.empty()
		.with("service", context.service, Property.STRING_PROPERTY)
		.with("uuid", context.uuid(), Property.STRING_PROPERTY)
		.with("username", context.username.orElse(""), Property.STRING_PROPERTY);
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}


}
