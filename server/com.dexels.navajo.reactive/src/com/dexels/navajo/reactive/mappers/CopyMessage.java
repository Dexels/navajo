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

public class CopyMessage implements ReactiveMapper {

	public CopyMessage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> execute(XMLElement xml) {
		ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(xml);
		return context->(item,second)->{
//			ReplicationMessage s = second.get().message();
			Map<String,Operand> named = r.resolveNamed(context, item,second);
			return DataItem.of(second.get().message().withSubMessage((String)named.get("name").value, item.message()));
		};
	
	}

}
