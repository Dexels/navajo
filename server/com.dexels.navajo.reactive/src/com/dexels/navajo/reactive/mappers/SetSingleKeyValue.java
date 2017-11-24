package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class SetSingleKeyValue implements ReactiveMerger, ParameterValidator {

	public SetSingleKeyValue() {
	}

	@Override
	public Function<StreamScriptContext, BiFunction<DataItem, DataItem, DataItem>> execute(String relativePath, XMLElement xml) {
		ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(relativePath, xml);
		return context->(acc,item)->{
			// will use the second message as input, if not present, will use the source message
//			System.err.println(">item>> "+new String(item.message().toBytes(new JSONReplicationMessageParserImpl())));
//			System.err.println(">>acc>> "+new String(acc.message().toBytes(new JSONReplicationMessageParserImpl())));
			ReplicationMessage s = item.message();
			ReactiveResolvedParameters parms = r.resolveNamed(context, Optional.of(s), Optional.of(item.message()), this, xml, relativePath);
			Operand resolvedValue = parms.resolveAllParams().get("value");
			String toValue = parms.paramString("to");
//			String json = new String(item.message().toBytes(new JSONReplicationMessageParserImpl()));
//			System.err.println("JSON DOC: \n"+json);
//			System.err.println("To Value: "+toValue+" -> "+resolvedValue.type+" val: "+resolvedValue.value);
			return DataItem.of(acc.message().with(toValue, resolvedValue.value, resolvedValue.type));
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
