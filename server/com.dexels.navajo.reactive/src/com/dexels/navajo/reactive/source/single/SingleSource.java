package com.dexels.navajo.reactive.source.single;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.ReactiveScriptParser;
import com.dexels.navajo.reactive.api.ParameterValidator;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.Flowable;

public class SingleSource implements ReactiveSource, ParameterValidator {

	private final ReactiveParameters params;
	private final List<ReactiveTransformer> transformers;
	private Type finalType;
//	private final Optional<Function<StreamScriptContext,BiFunction<DataItem,Optional<DataItem>,DataItem>>> mapMapper;
	private final Optional<XMLElement> sourceElement;
	private final String sourcePath;
	
	public SingleSource(ReactiveParameters params, List<ReactiveTransformer> transformers, DataItem.Type finalType, Optional<XMLElement> sourceElement, String sourcePath) {
		this.params = params;
		this.transformers = transformers;
		this.finalType = finalType;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters parameters = this.params.resolveNamed(context, current, ImmutableFactory.empty(), this, sourceElement, sourcePath);
		boolean debug = parameters.paramBoolean("debug", ()->false);
		int count =  parameters.paramInteger("count", ()->1);
		try {
			Flowable<DataItem> flow =  count > 1 ?
					Flowable.range(0, count)
						.map(i->DataItem.of(ReactiveScriptParser.empty().with("index", i, "integer")))
					: Flowable.just(DataItem.of(ReactiveScriptParser.empty()));
			if(debug) {
				flow = flow.doOnNext(di->System.err.println("Item: "+ImmutableFactory.getInstance().describe(di.message())));
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
	
	
	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"count","debug"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Collections.emptyList());
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("count", Property.INTEGER_PROPERTY);
		r.put("debug", Property.BOOLEAN_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}

}
