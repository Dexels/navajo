package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.nanoimpl.XMLElement;
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

	private final TransformerMetadata metadata;
	private final ReactiveSource subSource;
	private final Optional<XMLElement> sourceElement;
//	private final TopicPublisher publisher;

	public AsyncTransformer(AsyncTransformerFactory metadata, ReactiveParameters parameters,
			ReactiveSource sub, Function<StreamScriptContext, Function<DataItem, DataItem>> joinermapper, Optional<XMLElement> sourceElement) {
		this.metadata = metadata;
		this.subSource = sub;
		this.sourceElement = sourceElement;
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

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

	@Override
	public Optional<XMLElement> sourceElement() {
		return sourceElement;
	}


}
