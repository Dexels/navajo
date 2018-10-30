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
	private Optional<XMLElement> sourceElement;
	private String sourcePath;
	private TransformerMetadata metadata;
	
	private final static Logger logger = LoggerFactory.getLogger(SQLInsertTransformer.class);


	public SQLInsertTransformer(SQLInsertTransformerFactory mongoInsertTransformerFactory,
			ReactiveParameters parameters, Optional<XMLElement> sourceElement, String sourcePath) {
		this.parameters = parameters;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
		this.metadata = mongoInsertTransformerFactory;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata, sourceElement, sourcePath);
//		int parallel = resolved.optionalInteger("parallel").orElse(1);
		boolean debug = resolved.optionalBoolean("debug").orElse(false);
		Map<String, Function3<StreamScriptContext, Optional<ImmutableMessage>, ImmutableMessage, Operand>> named = resolved.named;
		try {
			String resource = (String) named.get("resource").apply(context, Optional.empty(), ImmutableFactory.empty()).value;
			String query = (String) named.get("query").apply(context, Optional.empty(), ImmutableFactory.empty()).value;
			if(debug) {
				logger.info("Transforming to SQL. resource: {} query: {}",resource,query);
			}
			FlowableTransformer<DataItem, DataItem> result = flow->flow.map(m->{
				List<Operand> operand = parameters.resolveUnnamed(context, DataItem.of(m.message()), DataItem.empty());
				Object[] params = operand.stream()
						.map(e->e.value)
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

	@Override
	public Optional<XMLElement> sourceElement() {
		return this.sourceElement;
	}

}
