package com.dexels.navajo.reactive.transformer.csv;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.io.BaseFlowableOperator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.FlowableOperator;
import io.reactivex.FlowableTransformer;

public class CSVTransformer implements ReactiveTransformer {

	private ReactiveParameters parameters;
	private Optional<XMLElement> sourceElement;
	private String sourcePath;
	private final TransformerMetadata metadata;
	
	private FlowableTransformer<DataItem, DataItem> createTransformer(StreamScriptContext context) {
		
		return flow -> flow.lift(flowableCSV(context));
	}
	
	public CSVTransformer(TransformerMetadata metadata, ReactiveParameters parameters, Optional<XMLElement> sourceElement, String sourcePath) {
		this.parameters = parameters;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
		this.metadata = metadata;
		
	}

	public FlowableOperator<DataItem, DataItem> flowableCSV(StreamScriptContext context) {
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.<ImmutableMessage>empty(), ImmutableFactory.empty(), metadata, sourceElement, sourcePath);

		return new BaseFlowableOperator<DataItem, DataItem>(10) {

			@Override
			public Subscriber<? super DataItem> apply(Subscriber<? super DataItem> downstream)
					throws Exception {
				
				// TODO use labels and writeHeaders
				String columnString = resolved.paramString("columns");
				List<String> columns = Arrays.asList(columnString.split(","));
//				String labelString = resolved.paramString("labels", "");
//				List<String> labels = Arrays.asList(labelString.split(","));
//				boolean writeHeaders = !"".equals(labelString);
				String delimiter = resolved.paramString("delimiter"); // (String) resolved.get("delimiter").value;						
				
				return new Subscriber<DataItem>() {

					@Override
					public void onComplete() {
						downstream.onComplete();
						operatorComplete(downstream);
}

					@Override
					public void onError(Throwable e) {
						operatorError(e, downstream);

					}

					@Override
					public void onNext(DataItem msg) {
						// TODO use labels

						
						operatorNext(msg, m->{
							ImmutableMessage dd = m.message();
							String line = columns.stream().map(column -> "" + dd.columnValue(column))
									.collect(Collectors.joining(delimiter, "", "\n"));
								return DataItem.of(line.getBytes(Charset.forName("UTF-8")));
						}, downstream);
					}

					@Override
					public void onSubscribe(Subscription subscription) {
						operatorSubscribe(subscription, downstream);
					}
				};
			}
		};
	}
	

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return createTransformer(context);
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}
}
