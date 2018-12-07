package com.dexels.navajo.reactive.source.sql;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;

public class SQLReactiveSource implements ReactiveSource {
	
	
	private final static Logger logger = LoggerFactory.getLogger(SQLReactiveSource.class);

	private final ReactiveParameters parameters;
	private final List<ReactiveTransformer> transformers;
	private final Type finalType;
	private final Optional<XMLElement> sourceElement;
	private final String sourcePath;
	private final SourceMetadata metadata;
	
	public SQLReactiveSource(SourceMetadata metadata, ReactiveParameters params, List<ReactiveTransformer> transformers, DataItem.Type finalType, Optional<XMLElement> sourceElement, String sourcePath) {
		this.metadata = metadata;
		this.parameters = params;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context,  Optional<ImmutableMessage> current,
			ImmutableMessage paramMessage) {
		ReactiveResolvedParameters params = this.parameters.resolve(input, current, paramMessage);
		List<Object> unnamedParams = params.unnamedParameters();
//		Object[] unnamedParams = evaluateParams(context, current);
		String datasource = params.paramString("resource");
		String query = params.paramString("query");
		Optional<String> queryTenant = params.optionalString("tenant");
		boolean debug = params.optionalBoolean("debug").orElse(false);
		if(debug) {
			logger.info("Starting SQL query to resource: {} and query:\n{}",datasource,query);
			for (Object object : unnamedParams) {
				logger.info(" -> param : {}",object);
			}
		}
		Flowable<DataItem> flow = SQL.query(datasource, queryTenant.orElseGet(()->context.getTenant()), query, unnamedParams)
				.map(d->DataItem.of(d).withStateMessage(current.orElse(ImmutableFactory.empty())));
		if(debug) {
			flow = flow.doOnNext(dataitem->{
				logger.info("Result record: {}",ImmutableFactory.getInstance().describe(dataitem.message()));
				
			});
		}
		for (ReactiveTransformer trans : transformers) {
			flow = flow.compose(trans.execute(context,current));
		}
		if(debug) {
			flow = flow.doOnNext(dataitem->{
				logger.info("After record: {}",ImmutableFactory.getInstance().describe(dataitem.message()));
				
			});
		}
//		flow = flow.doOnNext(e->System.err.println("TYPE: "+e.type+" msg: "+e));
		
		return flow;
	}


	private Object[] evaluateParams(StreamScriptContext context, Optional<ImmutableMessage> immutable) {
		return parameters.resolveUnnamed(context, immutable, ImmutableFactory.empty()).stream().map(e->e.value).toArray();
	}

	@Override
	public boolean streamInput() {
		return false;
	}



}
