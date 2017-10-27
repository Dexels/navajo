package com.dexels.navajo.reactive.mappers;

import java.util.Map;
import java.util.Map.Entry;
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

public class SetSingle implements ReactiveMapper {

	public SetSingle() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> execute(XMLElement xml) {
		ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(xml);
		return context->(item,second)->{
			// will use the second message as input, if not present, will use the source message
			ReplicationMessage s = second.orElse(item).message();
			
			Map<String,Operand> named = r.resolveNamed(context, Optional.of(s));
			// hmmm. Not beautiful
			s = s.without("index");

			for (Entry<String,Operand> elt : named.entrySet()) {
				s = s.with(elt.getKey(), elt.getValue().value, elt.getValue().type);

//				s = s.with(elt.getKey(), elt.getValue().value, elt.getValue().type);
			}
			
//			Operand resolvedValue = named.get("value");
			return DataItem.of(s);
		};
	
	}

}
