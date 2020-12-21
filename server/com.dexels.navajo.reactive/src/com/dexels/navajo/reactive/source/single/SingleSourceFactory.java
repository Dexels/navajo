/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.source.single;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.SourceMetadata;

public class SingleSourceFactory implements ReactiveSourceFactory, SourceMetadata {

	public SingleSourceFactory() {
	}

//	@Override
//	public ReactiveSource build(String relativePath, String type, List<ReactiveParseProblem> problems, Optional<XMLElement> x, ReactiveParameters params,
//			List<ReactiveTransformer> transformers, Type finalType, Function<String, ReactiveMerger> reducerSupplier
//			) {
//		return new SingleSource(this,params,transformers,finalType,x, relativePath);
//	}


	@Override
	public ReactiveSource build(ReactiveParameters parameters) {
		return new SingleSource(this,parameters);
	}

	@Override
	public Type sourceType() {
		return DataItem.Type.MESSAGE;
	}

	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"count","debug","delay"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Collections.emptyList());
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("count", Property.INTEGER_PROPERTY);
		r.put("delay", Property.INTEGER_PROPERTY);
		r.put("debug", Property.BOOLEAN_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}

}
