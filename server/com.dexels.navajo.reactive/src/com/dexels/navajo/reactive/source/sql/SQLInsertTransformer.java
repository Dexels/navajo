package com.dexels.navajo.reactive.source.sql;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class SQLInsertTransformer implements ReactiveTransformer {


	private ReactiveParameters parameters;
	private TransformerMetadata metadata;
	
	private final static Logger logger = LoggerFactory.getLogger(SQLInsertTransformer.class);


	public SQLInsertTransformer(SQLInsertTransformerFactory mongoInsertTransformerFactory,
			ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = mongoInsertTransformerFactory;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context,
			Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters resolved = parameters.resolve(context, current, ImmutableFactory.empty(), metadata);
		boolean debug = resolved.optionalBoolean("debug").orElse(false);
		try {
			String resource = resolved.paramString("resource");
			String query =  resolved.paramString("query");
			if(debug) {
				logger.info("Transforming to SQL. resource: {} query: {}",resource,query);
			}
			FlowableTransformer<DataItem, DataItem> result = flow->flow.map(m->{
				List<Object> operand = parameters.resolveUnnamed(context,Optional.of(m.message()), ImmutableFactory.empty());
				Object[] params = operand.stream()
						.toArray();
				if(debug) {
					logger.info("Transforming inputmessage {}",ImmutableFactory.createParser().describe(m.message()));
					logger.info("Unnamed params: {}",params);
					logger.info("Current thread: "+Thread.currentThread().getName());
				}
				
				return DataItem.of(SQL.update(resource, context.getTenant(), query, params)
						.subscribeOn(Schedulers.io(), true));
//						.map(DataItem::of);
			});

			return result;
		} catch (Exception e) {
			return flow->Flowable.error(e);
		}
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
