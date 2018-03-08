package com.dexels.navajo.reactive.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class CollapsedTransformer implements ReactiveTransformer {

	private final List<ReactiveTransformer> parents;

	public CollapsedTransformer(List<ReactiveTransformer> base) {
		this.parents = base;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return flow->{
			Flowable<DataItem> f = flow;
			for (ReactiveTransformer reactiveTransformer : parents) {
				f = f.compose(reactiveTransformer.execute(context));
			}
			return f;
		};
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {DataItem.Type.MESSAGE,DataItem.Type.SINGLEMESSAGE}));
	}

	@Override
	public Type outType() {
		return DataItem.Type.MESSAGE;
	}

}
