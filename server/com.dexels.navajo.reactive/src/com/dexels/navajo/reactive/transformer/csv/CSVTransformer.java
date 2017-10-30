package com.dexels.navajo.reactive.transformer.csv;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.io.BaseFlowableOperator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.FlowableOperator;
import io.reactivex.FlowableTransformer;

public class CSVTransformer implements ReactiveTransformer {

	private ReactiveParameters parameters;
	
	private FlowableTransformer<DataItem, DataItem> createTransformer(StreamScriptContext context) {
		
		return flow -> flow.lift(flowableCSV(context));
	}
	
	public CSVTransformer(ReactiveParameters parameters) {
		this.parameters = parameters;
		
	}

	public FlowableOperator<DataItem, DataItem> flowableCSV(StreamScriptContext context) {

		return new BaseFlowableOperator<DataItem, DataItem>(10) {

			@Override
			public Subscriber<? super DataItem> apply(Subscriber<? super DataItem> downstream)
					throws Exception {
				

				Map<String,Operand> resolved = parameters.resolveNamed(context, Optional.empty(), Optional.empty());
				String columnString = (String) resolved.get("columns") .value;
				List<String> columns = Arrays.asList(columnString.split(","));
				String labelString = (String) resolved.get("labels").value;
				List<String> labels = Arrays.asList(labelString.split(","));
				boolean writeHeaders = (boolean) resolved.get("writeHeaders").value;
				String delimiter = (String) resolved.get("delimiter").value;						
				
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
							ReplicationMessage dd = m.message();
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
//		Map<String,Operand> resolved = parameters.resolveNamed(context, current);
//		String columnString = (String) resolved.get("columns") .value;
//		List<String> columns = Arrays.asList(columnString.split(","));
//		String labelString = (String) resolved.get("labels").value;
//		List<String> labels = Arrays.asList(labelString.split(","));
//		boolean writeHeaders = (boolean) resolved.get("writeHeaders").value;
//		String delimiter = (String) resolved.get("delimiter").value;
		return createTransformer(context);
	}

	@Override
	public Type inType() {
		return Type.MESSAGE;
	}

	@Override
	public Type outType() {
		return Type.DATA;
	}

}
