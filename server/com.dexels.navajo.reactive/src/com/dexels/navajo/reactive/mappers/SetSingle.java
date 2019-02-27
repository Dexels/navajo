package com.dexels.navajo.reactive.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;


public class SetSingle implements ReactiveMerger {

	public SetSingle() {
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params) {
		return context->(item)->{
			ImmutableMessage s = item.message();
			ReactiveResolvedParameters parms = params.resolve(context, Optional.of(s), item.stateMessage(), this);
			boolean condition = parms.optionalBoolean("condition").orElse(true);
			if(!condition) {
				return item;
			}
			// will use the second message as input, if not present, will use the source message
			for (Entry<String,Operand> elt : parms.namedParameters().entrySet()) {
				if(!elt.getKey().equals("condition")) {
					String type = parms.namedParamType(elt.getKey());
					s = addColumn(s, elt.getKey(), elt.getValue(), type);
				}
			}
			return DataItem.of(s);
		};
	
	}
	
	private ImmutableMessage addColumn(ImmutableMessage input, String name, Operand value, String type) {
		return addColumn(input, Arrays.asList(name.split("/")), value, type);
	}

	private ImmutableMessage addColumn(ImmutableMessage input, List<String> path, Operand value, String type) {
//		logger.info("Setting path: {} value: {} type: {}",path,value.value,type);
		if(path.size()>1) {
			String submessage = path.get(0);
			Optional<ImmutableMessage> im = input.subMessage(submessage);
			List<String> popped = new ArrayList<>(path);
			popped.remove(0);
			if(im.isPresent()) {
				return input.withSubMessage(submessage, addColumn(im.get(),popped,value,type));
			} else {
				ImmutableMessage nw = addColumn(ImmutableFactory.empty(), popped, value, type);
				return input.withSubMessage(submessage, nw);
			}
		} else {
			return input.with(path.get(0), value.value, value.type);
		}
		
	}

	// parameter checking off 
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
