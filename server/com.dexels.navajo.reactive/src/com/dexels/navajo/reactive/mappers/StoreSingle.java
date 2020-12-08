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

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;

public class StoreSingle implements ReactiveMerger {

	public StoreSingle() {
	}

	@Override
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params) {
		return context->item->{
			// will use the second message as input, if not present, will use the source message
			ImmutableMessage s = item.message();
			ImmutableMessage state = item.stateMessage();
			ReactiveResolvedParameters parms = params.resolve(context, Optional.of(s),state , this);
			boolean condition = parms.optionalBoolean("condition").orElse(true);
			if(!condition) {
				return item;
			}			
			Operand resolvedValue = parms.namedParameters().get("value");
			String toValue = parms.paramString("to");
			ImmutableMessage di = item.stateMessage().with(toValue, resolvedValue.value,resolvedValue.type);

			return DataItem.of(s,di);
		};
	
	}
	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList("to","value","condition"));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList("to","value"));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("to", Property.STRING_PROPERTY);
		r.put("condition", Property.BOOLEAN_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}
}
