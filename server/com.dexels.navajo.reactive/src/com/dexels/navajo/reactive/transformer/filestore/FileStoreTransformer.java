package com.dexels.navajo.reactive.transformer.filestore;

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

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.io.BaseFlowableOperator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;

import io.reactivex.FlowableOperator;
import io.reactivex.FlowableTransformer;

public class FileStoreTransformer implements ReactiveTransformer {

	
	private final static Logger logger = LoggerFactory.getLogger(FileStoreTransformer.class);

	private final ReactiveParameters parameters;
	
	public FileStoreTransformer(ReactiveParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context,
			Optional<ReplicationMessage> current) {
		Map<String,Operand> resolved = parameters.resolveNamed(context, current);
		String path = (String) resolved.get("path").value;
		return flow->flow.lift(flowableFile(path));
	}

	public static FlowableOperator<DataItem, DataItem> flowableFile(String path) {

		
		return new BaseFlowableOperator<DataItem, DataItem>(1) {

			private final AtomicLong sizeCounter = new AtomicLong();

			@Override
			public Subscriber<DataItem> apply(Subscriber<? super DataItem> downstream) throws Exception {
				try {
					FileOutputStream output = new FileOutputStream(path);
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
							offer(DataItem.of(ReplicationFactory.fromMap(null, values, types)));
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
}
