package com.dexels.navajo.reactive.transformer.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.FlowableTransformer;

public class StreamMessageTransformer implements ReactiveTransformer, ParameterValidator {

	private ReactiveParameters parameters;
	private XMLElement sourceElement;
	private String sourcePath;

	public StreamMessageTransformer(ReactiveParameters parameters, XMLElement sourceElement, String sourcePath) {
		this.parameters = parameters;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.empty(), Optional.empty(), this,sourceElement,sourcePath);
		String messageName = resolved.paramString("messageName");
		boolean isArray = resolved.paramBoolean("isArray");
		// TODO remove duplication
		return flow->flow.map(di->di.message()).compose(StreamDocument.toMessageEvent(messageName,isArray)).map(DataItem::of);
//		if (isArray) {
//			return flow->
//				flow.map(di->di.message()).concatMap(msg->StreamDocument.replicationMessageToStreamEvents(messageName, msg, isArray)).compose(StreamDocument.inArray(messageName)).map(DataItem::of);
//		} else {
//			return flow->flow.take(1).
//					map(di->di.message()).concatMap(msg->StreamDocument.replicationMessageToStreamEvents(messageName, msg, isArray)).map(DataItem::of);
//		}
	}

	@Override
	public Set<Type> inType() {
		return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new Type[]{Type.SINGLEMESSAGE,Type.MESSAGE}))); // Type.SINGLEMESSAGE;
	}

	@Override
	public Type outType() {
		return Type.EVENT;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"messageName","isArray"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"messageName"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<String, String>();
		r.put("messageName", Property.STRING_PROPERTY);
		r.put("isArray", Property.BOOLEAN_PROPERTY);
		return Optional.of(r);
	}

}
