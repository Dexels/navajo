package com.dexels.navajo.reactive.transformer.call;

import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class CallTransformer implements ReactiveTransformer {


	private final ReactiveParameters parameters;

	public CallTransformer(ReactiveParameters parameters) {
		this.parameters = parameters;
		
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {

		Map<String,Operand> params = parameters.resolveNamed(context, Optional.empty());
		String messageName = (String) params.get("messageName").value;
		boolean isArray = (boolean) params.get("isArray").value;
		String service = (String) params.get("service").value;
		return flow->
			{
			flow = flow.doOnNext(item->
				System.err.println("Item1: "+item)
			);
			Flowable<NavajoStreamEvent> stream = flow.map(di->di.message()).concatMap(msg->StreamDocument.replicationMessageToStreamEvents(messageName, msg, isArray));
			
			return context.runner().run(context,service,stream).map(event->DataItem.of(event));
		};
	}

	@Override
	public Type inType() {
		return Type.MESSAGE;
	}

	@Override
	public Type outType() {
		return Type.EVENT;
	}
}
