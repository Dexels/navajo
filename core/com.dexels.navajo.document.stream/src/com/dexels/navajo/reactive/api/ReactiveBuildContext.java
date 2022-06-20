/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.api;

import java.util.Set;
import java.util.function.Function;

import com.dexels.navajo.document.stream.DataItem.Type;

import io.reactivex.functions.BiFunction;

public class ReactiveBuildContext {

	public final Function<String, ReactiveSourceFactory> sourceSupplier;
	public final BiFunction<String,Type, ReactiveTransformerFactory> factorySupplier;
	public final Function<String, ReactiveMerger> reducerSupplier;
	public final Set<String> transformers;
	public final Set<String> reducers;
	public final boolean useGlobalInput;
	
	private ReactiveBuildContext(Function<String, ReactiveSourceFactory> sourceSupplier,
			BiFunction<String, Type,ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier,
			Set<String> transformers,
			Set<String> reducers,
			boolean useGlobalInput) {
		this.sourceSupplier = sourceSupplier;
		this.factorySupplier = factorySupplier;
		this.reducerSupplier = reducerSupplier;
		this.transformers = transformers;
		this.reducers = reducers;
		this.useGlobalInput = useGlobalInput;
	}

	public static ReactiveBuildContext of(Function<String, ReactiveSourceFactory> sourceSupplier,
			BiFunction<String, Type,ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier,
			Set<String> transformers,
			Set<String> reducers,
			boolean useGlobalInput) {
		return new ReactiveBuildContext(sourceSupplier, factorySupplier, reducerSupplier, transformers, reducers, useGlobalInput);
	}
	
}
	