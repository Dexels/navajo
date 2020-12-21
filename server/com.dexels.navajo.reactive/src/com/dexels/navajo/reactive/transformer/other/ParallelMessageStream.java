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
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.schedulers.Schedulers;

public class ParallelMessageStream implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;
	private final Function<StreamScriptContext, Function<DataItem, DataItem>> joiner;
	
	public ParallelMessageStream(TransformerMetadata metadata, ReactiveParameters parameters, Function<StreamScriptContext, Function<DataItem, DataItem>> joinermapper) {
		this.parameters = parameters;
		this.metadata = metadata;
		this.joiner = joinermapper;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
//		ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata, Optional.empty(), "");
//		int parallel = parms.optionalInteger("parallel").orElse(1);
		try {
			Function<DataItem,DataItem> fi = joiner.apply(context);
			return flow->flow.parallel().runOn(Schedulers.io()).map(a->fi.apply(a)).sequential();
		} catch (Exception e1) {
			return flow->Flowable.error(e1);
		}
		
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
