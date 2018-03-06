package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
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

public class Store implements ReactiveMerger, ParameterValidator, ReactiveTransformer {

	private final ReactiveParameters parameters;
	
	public Store(ReactiveParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params, String relativePath, Optional<XMLElement> xml) {
		return context->(item)->{
			// will use the second message as input, if not present, will use the source message
			ImmutableMessage s = item.message();
			ReactiveResolvedParameters parms = params.resolveNamed(context, Optional.of(s), item.stateMessage(), this, xml, relativePath);
			String name = parms.paramString("name");
			Operand value = parms.paramObject("value",()->new Operand(null,null));
			DataItem result = DataItem.of(s,
					Optional.of( item.stateMessage().orElse(ImmutableFactory.empty()).with(name, value.value, value.type))
					);
			System.err.println("name>>>>>  "+name);
			System.err.println("msg>>>>>>  "+result.message().toDataMap());
			System.err.println("state>>>>  "+result.stateMessage().get().toDataMap());
			
			return result;
		};
	
	}

	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"name","value"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"name","value"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> result = new HashMap<String, String>();
		result.put("name", Property.STRING_PROPERTY);
		result.put("value", Property.INTEGER_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(result));
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
