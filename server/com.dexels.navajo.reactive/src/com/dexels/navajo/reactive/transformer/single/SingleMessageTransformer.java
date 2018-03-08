package com.dexels.navajo.reactive.transformer.single;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class SingleMessageTransformer implements ReactiveTransformer, ParameterValidator {

	private final Function<StreamScriptContext,Function<DataItem,DataItem>> joinerMapper;
	private final ReactiveParameters parameters;
	private final Optional<XMLElement> source;
	private final String path;
	
	public SingleMessageTransformer(ReactiveParameters parameters, Function<StreamScriptContext, Function<DataItem, DataItem>> joinermapper, Optional<XMLElement> xml, String path) {
		this.parameters = parameters;
		this.joinerMapper = joinermapper;
		this.source = xml;
		this.path = path;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters parms = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), this, source, path);
		boolean debug = parms.paramBoolean("debug", ()->false);
		
		FlowableTransformer<DataItem, DataItem> transformer = debug ? 
				   flow->flow.map(item->joinerMapper.apply(context).apply(item)).doOnNext(this::debugMessage)
				:  flow->flow.map(item->joinerMapper.apply(context).apply(item));
		return transformer;
	}

	private void debugMessage(DataItem di) {
		System.err.println("Message:DEBUG: "+di.message().flatValueMap(true, Collections.emptySet(), ""));
	}
	
	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE,Type.SINGLEMESSAGE})) ;
	}


	@Override
	public Type outType() {
		return Type.MESSAGE;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"debug"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.empty();
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> res = new HashMap<>();
		res.put("debug", "boolean");
		return Optional.of(Collections.unmodifiableMap(res));
	}

}
