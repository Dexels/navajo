/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;
import java.util.function.Function;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.RunningReactiveScripts;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveParseException;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AsyncTransformer implements ReactiveTransformer {

	private final TransformerMetadata metadata;
	private final ReactiveParameters parameters;
//	private final TopicPublisher publisher;
//	private final Function<StreamScriptContext, Function<DataItem, DataItem>> joinermapper;
	
	public AsyncTransformer(AsyncTransformerFactory metadata, ReactiveParameters parameters,Function<StreamScriptContext, Function<DataItem, DataItem>> joinermapper) {
		this.metadata = metadata;
		this.parameters = parameters;
//		this.joinermapper = joinermapper;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		StreamScriptContext cp = context.copyWithNewUUID();
		RunningReactiveScripts rrs = context.runningScripts().get();
		ReactiveResolvedParameters resolved = parameters.resolve(context, current, param, metadata);
		ReactiveSource subSource = (ReactiveSource) resolved.unnamedParameters().stream().findFirst().orElseThrow(()->new ReactiveParseException("Need source"));
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
			return subSource.execute(context, current,param);
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
	public ReactiveParameters parameters() {
		return parameters;
	}




}
