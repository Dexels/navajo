package com.dexels.navajo.reactive.mappers;

import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class SetSingleKeyValue implements ReactiveMapper {

	public SetSingleKeyValue() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> execute(XMLElement xml) {
		ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(xml);
		return context->(item,second)->{
			// will use the second message as input, if not present, will use the source message
			ReplicationMessage s = second.orElse(item).message();
			Map<String,Operand> named = r.resolveNamed(context, Optional.of(s));
			Operand resolvedValue = named.get("value");
			return DataItem.of(item.message().with((String)named.get("to").value, resolvedValue.value, resolvedValue.type));
		};
	
	}

}
