package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveMapper;
import com.dexels.navajo.reactive.api.ReactiveParameters;

import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class Rename implements ReactiveMapper {

	public Rename() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>> execute(XMLElement xml) {
		ReactiveParameters r = ReactiveScriptParser.parseParamsFromChildren(xml);
		return context->(item,second)->{
			Map<String,Operand> named = r.resolveNamed(context, Optional.of(item.message()));
			String name = (String)named.get("name").value;	
			return DataItem.of(item.message().without(Arrays.asList(name.split(","))));
		};
	
	}}
