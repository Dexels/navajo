package com.dexels.navajo.reactive.transformer.stream;

import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.FlowableTransformer;

public class StreamMessageTransformer implements ReactiveTransformer {

	private ReactiveParameters parameters;

	public StreamMessageTransformer(ReactiveParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		Map<String,Operand> params = parameters.resolveNamed(context, Optional.empty(), Optional.empty());
		String messageName = (String) params.get("messageName").value;
		boolean isArray = (boolean) params.get("isArray").value;
		return flow->flow.map(di->di.message()).concatMap(msg->StreamDocument.replicationMessageToStreamEvents(messageName, msg, isArray)).compose(StreamDocument.inArray(messageName)).map(DataItem::of);
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
