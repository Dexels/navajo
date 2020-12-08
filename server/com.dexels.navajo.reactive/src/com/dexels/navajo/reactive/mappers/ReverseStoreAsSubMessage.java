/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;

public class ReverseStoreAsSubMessage implements ReactiveMerger {

	
	private final static Logger logger = LoggerFactory.getLogger(ReverseStoreAsSubMessage.class);

	public ReverseStoreAsSubMessage() {
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params) {
		return context->(item)->{
			ImmutableMessage message = item.message();
			ImmutableMessage stateMessage = item.stateMessage();
			ReactiveResolvedParameters parms = params.resolve(context, Optional.of(message),stateMessage, this);
			boolean debug = parms.optionalBoolean("debug").orElse(false);
			boolean condition = parms.optionalBoolean("condition").orElse(true);
			if(!condition) {
				return item;
			}
			if(debug) {
				logger.info("Store as Submessage.State:\n{}Input:\n{}",ImmutableFactory.getInstance().describe(stateMessage),ImmutableFactory.getInstance().describe(message));
			}
			Optional<String> nameOpt = parms.optionalString("name");
			if(nameOpt.isPresent()) {
				String name = nameOpt.get();
				ImmutableMessage assembled = message.withSubMessage(name, stateMessage);
				return DataItem.of(assembled, item.stateMessage());
			} else {
				return DataItem.of(message.merge(item.stateMessage(), Optional.empty()));
			}
		};
	}

	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"name","condition","debug"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> result = new HashMap<String, String>();
		result.put("name", Property.STRING_PROPERTY);
		result.put("condition", Property.BOOLEAN_PROPERTY);
		result.put("debug", Property.BOOLEAN_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(result));
	}
}
