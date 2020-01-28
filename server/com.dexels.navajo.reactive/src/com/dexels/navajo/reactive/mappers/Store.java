package com.dexels.navajo.reactive.mappers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;


public class Store implements ReactiveMerger {

	public Store() {
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params) {
		return context->item->{
			// will use the second message as input, if not present, will use the source message
			ImmutableMessage s = item.message();
			ImmutableMessage state = item.stateMessage();
			
			ReactiveResolvedParameters parms = params.resolve(context, Optional.of(s),state , this);
			boolean condition = parms.optionalBoolean("condition").orElse(true);
			if(!condition) {
				return item;
			}
			ImmutableMessage di = item.stateMessage();
			Set<Entry<String, Operand>> entrySet = parms.namedParameters().entrySet();
			for (Entry<String, Operand> e : entrySet) {
				di = di.with(e.getKey(), e.getValue().value,e.getValue().type);
			}
			return DataItem.of(s,di);
		};
	
	}
	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.empty();
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.empty();
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		return Optional.empty();
	}
}
