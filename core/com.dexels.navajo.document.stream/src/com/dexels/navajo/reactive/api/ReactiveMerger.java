package com.dexels.navajo.reactive.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;


public interface ReactiveMerger extends ParameterValidator {
	public Function<StreamScriptContext,Function<DataItem,DataItem>> execute(ReactiveParameters params);
	default public ReactiveTransformer toReactiveTransformer(ReactiveParameters params) {
		final Function<StreamScriptContext, Function<DataItem, DataItem>> merger = ReactiveMerger.this.execute(params);
		return new ReactiveTransformer() {
			
			@Override
			public TransformerMetadata metadata() {
				return new TransformerMetadata() {
					
					@Override
					public Optional<List<String>> requiredParameters() {
						return ReactiveMerger.this.requiredParameters();
					}
					
					@Override
					public Optional<Map<String, String>> parameterTypes() {
						return ReactiveMerger.this.parameterTypes();
					}
					
					@Override
					public Optional<List<String>> allowedParameters() {
						return ReactiveMerger.this.allowedParameters();
					}
					
					@Override
					public Type outType() {
						return Type.MESSAGE;
					}
					
					@Override
					public String name() {
						return "implicit_transformer_class: "+ReactiveMerger.this.getClass().getName();
					}
					
					@Override
					public Set<Type> inType() {
						HashSet<Type> hashSet = new HashSet<>();
						hashSet.add(Type.MESSAGE);
						return Collections.unmodifiableSet(hashSet);
					}
				};
			}
			
			@Override
			public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context,
					Optional<ImmutableMessage> current, ImmutableMessage param) {
				Function<DataItem, DataItem> mapper;
				try {
					mapper = ReactiveMerger.this.execute(params).apply(context);
				} catch (Exception e) {
					e.printStackTrace();
					return item->Flowable.error(e);
				}
				return item->{
					return item.map(mapper);
				};
			}
		};
	}
}
