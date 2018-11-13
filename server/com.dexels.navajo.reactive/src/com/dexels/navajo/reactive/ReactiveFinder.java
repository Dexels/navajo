package com.dexels.navajo.reactive;

import java.util.Set;

import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;

public interface ReactiveFinder {

	public Set<String> sourceFactories();

	public Set<String> transformerFactories();

	public Set<String> reactiveMappers();

	public ReactiveSourceFactory getSourceFactory(String name);

	public ReactiveTransformerFactory getTransformerFactory(String name);

	public ReactiveMerger getMergerFactory(String name);

//	public void addReactiveSourceFactory(ReactiveSourceFactory factory);
//	
//	public void addReactiveTransformerFactory(ReactiveTransformerFactory transformer);

	public void addReactiveSourceFactory(ReactiveSourceFactory factory, String name);

	public void addReactiveTransformerFactory(ReactiveTransformerFactory factory, String name);
}
