/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.transformer.other;

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
import io.reactivex.schedulers.Schedulers;

public class BufferMessage implements ReactiveTransformer {

	private final ReactiveParameters parameters;
	private final TransformerMetadata metadata;
//	private final Function<StreamScriptContext, Function<DataItem, DataItem>> joiner;
	
	
	private final static Logger logger = LoggerFactory.getLogger(BufferMessage.class);

	public BufferMessage(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
//		this.joiner = joinermapper;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current,ImmutableMessage param) {
		ReactiveResolvedParameters parms = parameters.resolve(context, current,param, metadata);
		int count = parms.paramInteger("count");
		try {
//			Function<DataItem,DataItem> fi = joiner.apply(context);
			return flow->flow
					.map(e->e.message())
					.buffer(count)
					.doOnNext(l->logger.info("Grouped to list of size: {}",l.size()))
					.map(e->Flowable.fromIterable(e))
					.map(DataItem::of)
					.observeOn(Schedulers.io());
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
		// TODO Auto-generated method stub
		return null;
	}

}
