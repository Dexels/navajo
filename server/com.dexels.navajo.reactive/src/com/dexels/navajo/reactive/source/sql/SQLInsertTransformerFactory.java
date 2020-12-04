/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.source.sql;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

public class SQLInsertTransformerFactory implements ReactiveTransformerFactory {

	public SQLInsertTransformerFactory() {
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE,Type.SINGLEMESSAGE})) ;
	}

	@Override
	public Type outType() {
		return Type.MSGSTREAM;
	}
	

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"resource","query","debug"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"resource","query"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("resource", Property.STRING_PROPERTY);
		r.put("query", Property.STRING_PROPERTY);
		r.put("debug", Property.BOOLEAN_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}
	@Override
	public ReactiveTransformer build(List<ReactiveParseProblem> problems,
			ReactiveParameters parameters) {

		return new SQLInsertTransformer(this,parameters);

	}

	@Override
	public String name() {
		return "sqlinsert";
	}

}
