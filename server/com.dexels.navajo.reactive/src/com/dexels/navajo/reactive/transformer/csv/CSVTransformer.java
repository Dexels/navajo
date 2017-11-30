package com.dexels.navajo.reactive.transformer.csv;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

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
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.FlowableOperator;
import io.reactivex.FlowableTransformer;

public class CSVTransformer implements ReactiveTransformer, ParameterValidator {

	private ReactiveParameters parameters;
	private XMLElement sourceElement;
	private String sourcePath;
	
	private FlowableTransformer<DataItem, DataItem> createTransformer(StreamScriptContext context) {
		
		return flow -> flow.lift(flowableCSV(context));
	}
	
	public CSVTransformer(ReactiveParameters parameters, XMLElement sourceElement, String sourcePath) {
		this.parameters = parameters;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
		
	}

	public FlowableOperator<DataItem, DataItem> flowableCSV(StreamScriptContext context) {
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.<ReplicationMessage>empty(), Optional.empty(), this, sourceElement, sourcePath);

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
	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"columns","labels","delimiter"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"columns","delimiter"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<String, String>();
		r.put("columns", Property.STRING_PROPERTY);
		r.put("labels", Property.STRING_PROPERTY);
		r.put("delimiter", Property.STRING_PROPERTY);
		return Optional.of(r);
	}
	

}
