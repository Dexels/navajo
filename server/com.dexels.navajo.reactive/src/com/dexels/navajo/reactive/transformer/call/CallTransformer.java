package com.dexels.navajo.reactive.transformer.call;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

public class CallTransformer implements ReactiveTransformer {


	private final ReactiveParameters parameters;
	private final ParameterValidator validator;
	private XMLElement sourceElement;
	private String sourcePath;
	
	public CallTransformer(ReactiveParameters parameters, ParameterValidator validator, XMLElement sourceElement, String sourcePath) {
		this.parameters = parameters;
		this.validator = validator;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.empty(), Optional.empty(),validator, sourceElement, sourcePath);

		final int parallel =  resolved.paramInteger("parallel", 0);
		final String service =  resolved.paramString("service");
		final String messageName =  resolved.paramString("messageName");
		final boolean debug = resolved.paramBoolean("debug", false);
		final boolean isArray =  resolved.paramBoolean("isArray");
		return flow->
			{
			Flowable<Flowable<NavajoStreamEvent>> stream = flow.map(di->di.message())
					.map(msg->StreamDocument.replicationMessageToStreamEvents(messageName, msg, isArray));
			if(debug) {
				stream = stream.doOnNext(e->System.err.println(e));
			}
			if (parallel==0) {
				return stream.concatMap(str->context.runner().run(service,debug).execute(context.withService(service).withInput(str)));
			} else {
				return stream.flatMap(str->context.runner().run(service,debug).execute(context.withService(service).withInput(str)),parallel);
			}
//			return context.runner().run(context,service,stream);
		};
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE,Type.SINGLEMESSAGE})) ;
	}

	@Override
	public Type outType() {
		return Type.EVENT;
	}
}
