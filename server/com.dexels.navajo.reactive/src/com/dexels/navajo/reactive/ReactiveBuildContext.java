package com.dexels.navajo.reactive;

import java.util.Set;

import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

import io.reactivex.functions.Function;

public class ReactiveBuildContext {

	public final Function<String, ReactiveSourceFactory> sourceSupplier;
	public final Function<String, ReactiveTransformerFactory> factorySupplier;
	public final Function<String, ReactiveMerger> reducerSupplier;
	public final Set<String> transformers;
	public final Set<String> reducers;
	public final boolean useGlobalInput;
	
	private ReactiveBuildContext(Function<String, ReactiveSourceFactory> sourceSupplier,
			Function<String, ReactiveTransformerFactory> factorySupplier,
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
			Function<String, ReactiveTransformerFactory> factorySupplier,
			Function<String, ReactiveMerger> reducerSupplier,
			Set<String> transformers,
			Set<String> reducers,
			boolean useGlobalInput) {
		return new ReactiveBuildContext(sourceSupplier, factorySupplier, reducerSupplier, transformers, reducers, useGlobalInput);
	}
	
}
	