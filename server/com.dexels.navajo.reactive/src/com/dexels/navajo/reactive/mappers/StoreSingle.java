package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;

import io.reactivex.functions.Function;

public class StoreSingle implements ReactiveMerger {

	public StoreSingle() {
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params, String relativePath, Optional<XMLElement> xml) {
		return context->(item)->{
			// will use the second message as input, if not present, will use the source message
			ImmutableMessage s = item.message();
			ImmutableMessage state = item.stateMessage();
			ReactiveResolvedParameters parms = params.resolveNamed(context, Optional.of(s),state , this, xml, relativePath);
			Operand resolvedValue = parms.resolveAllParams().get("value");
			String toValue = parms.paramString("to");
			ImmutableMessage di = item.stateMessage().with(toValue, resolvedValue.value,resolvedValue.type);
			return DataItem.of(s,di);
		};
	
	}
	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"to","value"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"to","value"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("to", Property.STRING_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}
}
