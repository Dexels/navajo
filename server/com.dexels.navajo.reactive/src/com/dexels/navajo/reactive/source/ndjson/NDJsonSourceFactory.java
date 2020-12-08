/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.source.ndjson;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;

public class NDJsonSourceFactory implements ReactiveSourceFactory {

	@Override
	public Type sourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReactiveSource build(ReactiveParameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

}
