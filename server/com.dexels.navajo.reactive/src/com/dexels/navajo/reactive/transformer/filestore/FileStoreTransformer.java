package com.dexels.navajo.reactive.transformer.filestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.io.BaseFlowableOperator;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.factory.ReplicationFactory;

import io.reactivex.FlowableOperator;
import io.reactivex.FlowableTransformer;

public class FileStoreTransformer implements ReactiveTransformer, ParameterValidator {

	
	private final static Logger logger = LoggerFactory.getLogger(FileStoreTransformer.class);

	private final ReactiveParameters parameters;

	private XMLElement sourceElement;

	private String sourcePath;
	
	public FileStoreTransformer(ReactiveParameters parameters, XMLElement sourceElement, String sourcePath) {
		this.parameters = parameters;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.empty(), Optional.empty(), this, sourceElement, sourcePath);
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

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.DATA})) ;
	}

	@Override
	public Type outType() {
		return Type.MESSAGE;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"path"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"path"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<String, String>();
		r.put("path", Property.STRING_PROPERTY);
		return Optional.of(r);
	}
	
}
