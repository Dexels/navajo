/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.transformer.reduce;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableTransformer;

public class ReduceToListTransformer implements ReactiveTransformer {

//	private Function<StreamScriptContext, Function<DataItem, DataItem>> reducers;

	private final TransformerMetadata metadata;

	private final ReactiveParameters parameters;

	private final static Logger logger = LoggerFactory.getLogger(ReduceToListTransformer.class);

	
	public ReduceToListTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.metadata = metadata;
//		this.reducers = reducers;
		this.parameters = parameters;
	}
	
	
//	return DataItem.of(item.stateMessage().withSubMessages(resolved.paramString("name"), item.messageList()), item.stateMessage());
	
	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters parms = parameters.resolve(context, current,param, metadata);
		// MUTABLE EDITION!
			return flow->{
			try {
				flow = flow.reduce(DataItem.of(new ArrayList<>()), (state,message)->{
					state.messageList().add(message.message());
					return state;
				})
				.map(e->{
					String field = parms.paramString("name");
					return DataItem.of(ImmutableFactory.empty().withSubMessages(field, e.messageList()));

				})
				.toFlowable();
				return flow;
			} catch (Exception e) {
				logger.error("Error: ", context);
			}
			return flow;
		};
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
