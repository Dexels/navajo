package com.dexels.navajo.reactive.transformer.mergesingle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function;

public class MergeSingleTransformer implements ReactiveTransformer {

	private final ReactiveSource source;
	private final Function<StreamScriptContext,Function<DataItem,DataItem>> joiner;

	public MergeSingleTransformer(ReactiveSource source, Function<StreamScriptContext,Function<DataItem,DataItem>> joiner, XMLElement xml) {
		this.source = source;
		this.joiner = joiner;
		if(!source.finalType().equals(DataItem.Type.SINGLEMESSAGE)) {
			throw new IllegalArgumentException("Wrong type of sub source: "+source.finalType()+ ", reduce maybe? It should be: "+Type.SINGLEMESSAGE+" at line: "+xml.getStartLineNr()+" xml: \n"+xml);
		}
		
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		return flow->flow.flatMap(item->{
			Flowable<DataItem> sourceStream = source.execute(context,  Optional.of(item.message()));
			return sourceStream
				.map(reducedItem->joiner.apply(context).apply(item));
		},false,10);
				
				
	}

	@Override
	public Set<Type> inType() {
		return new HashSet<>(Arrays.asList(new Type[] {Type.MESSAGE,Type.SINGLEMESSAGE})) ;
	}


	@Override
	public Type outType() {
		return Type.MESSAGE;
	}

}
