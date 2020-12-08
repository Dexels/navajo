/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.transformer.filestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.io.BaseFlowableOperator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableOperator;
import io.reactivex.FlowableTransformer;

public class FileStoreTransformer implements ReactiveTransformer {

	
	private final static Logger logger = LoggerFactory.getLogger(FileStoreTransformer.class);

	private final ReactiveParameters parameters;

	private final TransformerMetadata metadata;
	
	public FileStoreTransformer(TransformerMetadata metadata, ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters parms = parameters.resolve(context, current,param, metadata);
		String path = parms.paramString("path");
		return flow->flow.lift(flowableFile(path));
	}

	public static FlowableOperator<DataItem, DataItem> flowableFile(String path) {

		
		return new BaseFlowableOperator<DataItem, DataItem>(1) {

			private final AtomicLong sizeCounter = new AtomicLong();

			@Override
			public Subscriber<DataItem> apply(Subscriber<? super DataItem> downstream) throws Exception {
				try {
					File filePath = new File(path);
					FileOutputStream output = new FileOutputStream(filePath);
					logger.info("Writing file to: "+filePath.getAbsolutePath());
					return new  Subscriber<DataItem>() {

						@Override
						public void onComplete() {
							Map<String,Object> values = new HashMap<>();
							Map<String,String> types = new HashMap<>();
							values.put("Ok", true);
							types.put("Ok", "boolean");
							// TODO create 'withTyped'?
							values.put("Size", sizeCounter.get());
							types.put("Size", "long");
							values.put("Path", path);
							types.put("Path", "string");
							try {
								output.close();
							} catch (IOException e) {
								logger.error("Error closing output file: "+path, e);
								downstream.onError(e);
							}
							offer(DataItem.of(ImmutableFactory.create(values, types)));
							operatorComplete(downstream);
//							downstream.onNext(ReplicationFactory.fromMap(null, values, types));
							downstream.onComplete();
						}

						@Override
						public void onError(Throwable e) {
							operatorError(e, downstream);
						}

						@Override
						public void onNext(DataItem di) {
							byte[] msg = di.data();
							sizeCounter.addAndGet(msg.length);
							try {
								output.write(msg);
							} catch (IOException e) {
								logger.error("Error writing to output file: "+path, e);
								operatorError(e, downstream);
							}
							operatorNext(null, m->null, downstream);
						}

						@Override
						public void onSubscribe(Subscription subscription) {
							operatorSubscribe(subscription, downstream);
						}
					};
					} catch (FileNotFoundException e1) {
					logger.error("Error: ", e1);
					return null;
				}
			}

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
