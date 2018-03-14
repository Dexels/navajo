package com.dexels.navajo.reactive.transformer.call;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class CallTransformer implements ReactiveTransformer, ParameterValidator {


	private final ReactiveParameters parameters;
	private Optional<XMLElement> sourceElement;
	private String sourcePath;
	
	public CallTransformer(ReactiveParameters parameters,Optional<XMLElement> sourceElement, String sourcePath) {
		this.parameters = parameters;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(),this, sourceElement, sourcePath);

//		final int parallel =  resolved.paramInteger("parallel", ()->0);
		final String service =  resolved.paramString("service");
		final String messageName =  resolved.paramString("messageName");
		final boolean debug = resolved.paramBoolean("debug", ()->false);
		final boolean isArray =  resolved.paramBoolean("isArray");
		return flow->
			{
			Flowable<Flowable<NavajoStreamEvent>> stream = flow.map(di->di.message())
					.map(msg->StreamDocument.replicationMessageToStreamEvents(messageName, msg, isArray));
			if(debug) {
				stream = stream.doOnNext(e->System.err.println(e));
			}
			return stream.map(str->context.runner().build(service,debug).execute(context.withService(service).withInput(str)).map(e->e.event())).map(e->DataItem.ofEvent(e));
		};
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE,Type.SINGLEMESSAGE})) ;
	}

	@Override
	public Type outType() {
		return Type.EVENTSTREAM;
	}
	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"messageName","isArray","service","debug"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"messageName","isArray","service"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<String, String>();
		r.put("messageName", Property.STRING_PROPERTY);
		r.put("service", Property.STRING_PROPERTY);
//		r.put("parallel", Property.INTEGER_PROPERTY);
		r.put("isArray", Property.BOOLEAN_PROPERTY);
		r.put("debug", Property.BOOLEAN_PROPERTY);
		return Optional.of(r);
	}

}
