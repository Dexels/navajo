package com.dexels.navajo.reactive.mappers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveReducer;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class SetSingle implements ReactiveReducer, ParameterValidator {

	public SetSingle() {
	}

	@Override
	public Function<StreamScriptContext,BiFunction<DataItem,DataItem,DataItem>> execute(String relativePath, XMLElement xml) {
		ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(relativePath, xml);
		return context->(item,params)->{
			// will use the second message as input, if not present, will use the source message
			ReplicationMessage s = item.message();
			ReactiveResolvedParameters parms = r.resolveNamed(context, Optional.of(s), Optional.of(params.message()), this, xml, relativePath);
			
			for (Entry<String,Operand> elt : parms.resolveAllParams().entrySet()) {
				System.err.println("<<<><<< "+elt.getKey());
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
}
