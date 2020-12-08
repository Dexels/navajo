/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.transformer.other;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

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
import io.reactivex.functions.Consumer;

public class LogTransformer implements ReactiveTransformer {

	private final TransformerMetadata metadata;
	private final ReactiveParameters parameters;
	private final AtomicLong counter = new AtomicLong();
	
	private final static Logger logger = LoggerFactory.getLogger(LogTransformer.class);

	public LogTransformer(TransformerMetadata metadata,ReactiveParameters parameters) {
		this.metadata = metadata;
		this.parameters = parameters;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters parms = this.parameters.resolve(context, current,param, metadata);
		Optional<Integer> every = parms.optionalInteger("every");
		return flow->flow.doOnSubscribe(e->{
			logger.info("Subscribed stream. ");
		}
		)
		.doOnNext(processElement(every))
		.doOnComplete(()->{
			logger.info("Completed stream.");
		});
	}

	private Consumer<DataItem> processElement(Optional<Integer> every) {
		if(!every.isPresent()) {
			return e->logger.info("#: "+counter.get() +"Element content: {}",ImmutableFactory.getInstance().describe(e.message()));
		} else {
			long ev = every.get();
			return e->{
				long current = counter.incrementAndGet();
				if(current % ev == 0) {
					logger.info("#: "+counter.get() +"Element content: {}",ImmutableFactory.getInstance().describe(e.message()));
				}
			};
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
