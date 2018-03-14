package com.dexels.navajo.reactive.transformer.other;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.FlowableTransformer;

public class InMessageTransformer implements ReactiveTransformer, ParameterValidator {

	private final ReactiveParameters parameters;

	public InMessageTransformer(ReactiveParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), this, Optional.empty(), "");
		boolean isArray = parms.paramBoolean("isArrayElement");
		String name = parms.paramString("name");
//		e.
		//
		if(isArray) {
			return e->e.map(f->f.eventStream()
					.compose(StreamDocument.inArrayElement(name))
					.compose(StreamDocument.inArray(name))
					)
					.map(g->DataItem.ofEvent(g));
		} else {
			return e->e.map(f->f.eventStream()
					.compose(StreamDocument.inMessage(name))
					)
					.map(g->DataItem.ofEvent(g));
			
		}
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {DataItem.Type.EVENTSTREAM}));
	}

	@Override
	public Type outType() {
		return DataItem.Type.EVENTSTREAM;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[] {"isArrayElement","name"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[] {"isArrayElement","name"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String, String> r = new HashMap<>();
		r.put("isArrayElement", Property.BOOLEAN_PROPERTY);
		r.put("name", Property.STRING_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}

}
