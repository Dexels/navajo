package com.dexels.navajo.reactive;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactiveFinder;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformerFactory;
import com.dexels.navajo.reactive.mappers.Delete;
import com.dexels.navajo.reactive.mappers.DeleteSubMessage;
import com.dexels.navajo.reactive.mappers.JsonFileAppender;
import com.dexels.navajo.reactive.mappers.Log;
import com.dexels.navajo.reactive.mappers.LogState;
import com.dexels.navajo.reactive.mappers.Rename;
import com.dexels.navajo.reactive.mappers.SetSingle;
import com.dexels.navajo.reactive.mappers.SetSingleKeyValue;
import com.dexels.navajo.reactive.mappers.Store;
import com.dexels.navajo.reactive.mappers.StoreAsSubMessage;
import com.dexels.navajo.reactive.mappers.StoreAsSubMessageList;
import com.dexels.navajo.reactive.mappers.StoreSingle;
import com.dexels.navajo.reactive.mappers.ToSubMessage;
import com.dexels.navajo.reactive.mappers.ToSubMessageList;
import com.dexels.navajo.reactive.source.single.SingleSourceFactory;
import com.dexels.navajo.reactive.source.sql.SQLReactiveSourceFactory;
import com.dexels.navajo.reactive.source.test.EventStreamSourceFactory;
import com.dexels.navajo.reactive.transformer.eventstream.EventStreamMessageTransformerFactory;
import com.dexels.navajo.reactive.transformer.mergesingle.MergeSingleTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.FilterTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.FirstTransformerFactory;
import com.dexels.navajo.reactive.transformer.other.FlattenEventStreamFactory;
import com.dexels.navajo.reactive.transformer.other.TakeTransformerFactory;
import com.dexels.navajo.reactive.transformer.parseevents.ParseEventStreamFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceToListTransformerFactory;
import com.dexels.navajo.reactive.transformer.reduce.ReduceTransformerFactory;
import com.dexels.navajo.reactive.transformer.stream.StreamMessageTransformerFactory;

public class CoreReactiveFinder implements ReactiveFinder {

	private final Map<String,ReactiveSourceFactory> factories = new HashMap<>();
	private final Map<String, ReactiveTransformerFactory> reactiveOperatorFactory = new HashMap<>();
	private final Map<String,ReactiveMerger> reactiveReducer = new HashMap<>();

	public CoreReactiveFinder() {
		reactiveReducer.put("set", new SetSingle());
		reactiveReducer.put("setkv", new SetSingleKeyValue());
		reactiveReducer.put("toSubMessage", new ToSubMessage());
		reactiveReducer.put("delete", new Delete());
		reactiveReducer.put("deleteAll", new DeleteSubMessage());
		reactiveReducer.put("rename", new Rename());
		reactiveReducer.put("dump", new JsonFileAppender());
		reactiveReducer.put("log", new Log());
		reactiveReducer.put("logState", new LogState());
		reactiveReducer.put("saveall", new Store());
		reactiveReducer.put("save", new StoreSingle());
		reactiveReducer.put("store", new StoreAsSubMessage());
		reactiveReducer.put("storeList", new StoreAsSubMessageList());

		reactiveReducer.put("toSubList", new ToSubMessageList());
		
		addReactiveSourceFactory(new SingleSourceFactory(), "single");
		addReactiveSourceFactory(new SQLReactiveSourceFactory(), "sql");
		addReactiveSourceFactory(new EventStreamSourceFactory(), "eventsource");
		addReactiveTransformerFactory(new StreamMessageTransformerFactory(), "stream");
		addReactiveTransformerFactory(new ReduceTransformerFactory(), "reduce");
		addReactiveTransformerFactory(new ReduceToListTransformerFactory(), "reduceToSubList");
		
		addReactiveTransformerFactory(new ParseEventStreamFactory(), "streamtoimmutable");
		addReactiveTransformerFactory(new FilterTransformerFactory(), "filter");
		addReactiveTransformerFactory(new TakeTransformerFactory(), "take");
		addReactiveTransformerFactory(new MergeSingleTransformerFactory(),"join");
		addReactiveTransformerFactory(new FirstTransformerFactory(),"first");
		addReactiveTransformerFactory(new FlattenEventStreamFactory(),"flatten");

	}


	@Override
	public final Set<String> sourceFactories() {
		return factories.keySet();
	}

	@Override
	public final Set<String> transformerFactories() {
		return reactiveOperatorFactory.keySet();
	}

	@Override
	public final Set<String> reactiveMappers() {
		return reactiveReducer.keySet();
	}

	@Override
	public final ReactiveSourceFactory getSourceFactory(String name) {
		return factories.get(name);
	}

	@Override
	public final ReactiveTransformerFactory getTransformerFactory(String name) {
		return reactiveOperatorFactory.get(name);
	}

	@Override
	public final ReactiveMerger getMergerFactory(String name) {
		return reactiveReducer.get(name);
	}

	@Override
	public void addReactiveSourceFactory(ReactiveSourceFactory factory, String name) {
		factories.put(name, factory);
	}

	public void addReactiveSourceFactory(ReactiveSourceFactory factory, Map<String,Object> settings) {
		factories.put((String) settings.get("name"), factory);
	}

	public void removeReactiveSourceFactory(ReactiveSourceFactory factory, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		factories.remove(name);
	}

	@Override
	public void addReactiveTransformerFactory(ReactiveTransformerFactory factory, String name) {
		reactiveOperatorFactory.put(name, factory);
	}
	
	public void addReactiveTransformerFactory(ReactiveTransformerFactory factory, Map<String,Object> settings) {
		reactiveOperatorFactory.put((String) settings.get("name"), factory);
	}

	public void removeReactiveTransformerFactory(ReactiveTransformerFactory factory, Map<String,Object> settings) {
		reactiveOperatorFactory.remove((String) settings.get("name"));
	}

	@Override
	public Function<String, FunctionClassification> functionClassifier() {
		return name->{
			if(factories.containsKey(name)) {
				return FunctionClassification.REACTIVE_SOURCE;
			} else if(reactiveOperatorFactory.containsKey(name)) {
				return FunctionClassification.REACTIVE_TRANSFORMER;
			} else if(reactiveReducer.containsKey(name)) {
				return FunctionClassification.REACTIVE_REDUCER;
			}
			return FunctionClassification.DEFAULT;
		};
	}


}
