package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class SetSingle implements ReactiveMerger, ParameterValidator, ReactiveTransformer {

	private ReactiveParameters parameters;

	public SetSingle(ReactiveParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params, String relativePath, Optional<XMLElement> xml) {
		return context->(item)->{
			// will use the second message as input, if not present, will use the source message
			ImmutableMessage s = item.message();
			ReactiveResolvedParameters parms = params.resolveNamed(context, Optional.of(s), item.stateMessage(), this, xml, relativePath);
			
			for (Entry<String,Operand> elt : parms.resolveAllParams().entrySet()) {
				s = s.with(elt.getKey(), elt.getValue().value, elt.getValue().type);
			}
			return DataItem.of(s);
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

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return flow->{
			try {
				return flow.map(this.execute(parameters).apply(context));
			} catch (Exception e) {
				return Flowable.error(e);
			}
		};
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE}));
	}

	@Override
	public Type outType() {
		return Type.MESSAGE;
	}
}
