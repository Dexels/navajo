/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.source.topology;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

public class SinkTransformerFactory implements ReactiveTransformerFactory {

	@Override
	public Set<Type> inType() {
		Set<Type> types = new HashSet<>();
		types.add( Type.MESSAGE);
		return Collections.unmodifiableSet(types);
	}

	// TODO perhaps introduce type 'NONE'? or 'TERMINAL'?
	@Override
	public Type outType() {
		return Type.MESSAGE;
	}

	@Override
	public String name() {
		return "sink";
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList("name"));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList("name"));
}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> types = new HashMap<String, String>();
		types.put("name", "string");
		return Optional.of(Collections.unmodifiableMap(types));
	}

	@Override
	public ReactiveTransformer build(List<ReactiveParseProblem> problems, ReactiveParameters parameters) {
		return new SinkTransformer(this, parameters);
	}

}
