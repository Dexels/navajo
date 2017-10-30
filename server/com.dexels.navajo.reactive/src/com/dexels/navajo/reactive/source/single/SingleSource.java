package com.dexels.navajo.reactive.source.single;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class SingleSource implements ReactiveSource {

	private final ReactiveParameters params;
	private final List<ReactiveTransformer> transformers;
//	private Function<StreamScriptContext, BiFunction<DataItem, Optional<DataItem>, DataItem>> dataMapper;
	private Type finalType;
//	private final Function<DataItem,DataItem> transformationFunction;
	private final Optional<Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>>> mapMapper;
	
	public SingleSource(ReactiveParameters params, List<ReactiveTransformer> transformers, DataItem.Type finalType,Optional<Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>>> mapMapper) {
		this.params = params;
		this.transformers = transformers;
		this.finalType = finalType;
		this.mapMapper = mapMapper;
//		this.transformationFunction = item->{
//			return item;
//		};
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ReplicationMessage> current) {
		Map<String,Operand> resolvedParams = this.params.resolveNamed(context, current);
		boolean debug = resolvedParams.containsKey("debug");
		Operand countOperand = resolvedParams.get("count");
		int count = countOperand == null ? 1 :(Integer) countOperand.value;
		try {
			if(!mapMapper.isPresent()) {
				throw new RuntimeException("No datamapper, this will end badly!");
			}
			
//			(dataMapper.apply(context).apply(DataItem.of(ReactiveScriptParser.empty().with("index", i, "integer")), Optional.empty())
			Flowable<DataItem> flow =  count > 1 ?
						Flowable.range(0, count)
							.map(i->mapMapper.get().apply(context).apply(DataItem.of(ReactiveScriptParser.empty()), Optional.of(DataItem.of(ReactiveScriptParser.empty().with("index", i, "integer")))))
							
					: Flowable.just(mapMapper.get().apply(context).apply(DataItem.of(ReactiveScriptParser.empty()), Optional.empty()));
			if(debug) {
				flow = flow.doOnNext(di->System.err.println("Item: "+ReplicationFactory.getInstance().describe(di.message())));
			}
			for (ReactiveTransformer reactiveTransformer : transformers) {
				flow = flow.compose(reactiveTransformer.execute(context));
			}
			return flow;
		} catch (Exception e) {
			return Flowable.error(e);
		}
	}

	@Override
	public Type dataType() {
		return Type.MESSAGE;
	}

	@Override
	public Type finalType() {
		return finalType;
	}

}
