/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.source.sql;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;

public class SQLReactiveSource implements ReactiveSource {
	
	
	private final static Logger logger = LoggerFactory.getLogger(SQLReactiveSource.class);

	private final ReactiveParameters parameters;
	private final SourceMetadata metadata;
	
	public SQLReactiveSource(SourceMetadata metadata, ReactiveParameters params) {
		this.metadata = metadata;
		this.parameters = params;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context,  Optional<ImmutableMessage> current,
			ImmutableMessage paramMessage) {
		ReactiveResolvedParameters params = this.parameters.resolve(context, current, paramMessage,metadata);
		Operand[] unnamedParams = params.unnamedParametersArray();
//		Object[] unnamedParams = evaluateParams(context, current);
		String datasource = params.paramString("resource");
		String query = params.paramString("query");
		Optional<String> queryTenant = params.optionalString("tenant");
		boolean debug = params.optionalBoolean("debug").orElse(false);
		if(debug) {
			logger.info("Starting SQL query to resource: {} and query:\n{}",datasource,query);
			for (Operand object : unnamedParams) {
				logger.info(" -> param : {} / {}",object.type,object.value);
			}
		}
		Flowable<DataItem> flow = SQL.query(datasource, queryTenant.orElseGet(()->context.getTenant()), query,unnamedParams)
				.map(d->DataItem.of(d).withStateMessage(current.orElse(ImmutableFactory.empty())));
		if(debug) {
			flow = flow.doOnNext(dataitem->{
				logger.info("Result record: {}",ImmutableFactory.getInstance().describe(dataitem.message()));
				
			});
		}

		if(debug) {
			flow = flow.doOnNext(dataitem->{
				logger.info("After record: {}",ImmutableFactory.getInstance().describe(dataitem.message()));
				
			});
		}
		return flow;
	}

	@Override
	public boolean streamInput() {
		return false;
	}

	@Override
	public Type sourceType() {
		return Type.MESSAGE;
	}
	
	public ReactiveParameters parameters() {
		return parameters;
	}
}
