package com.dexels.navajo.resource.http.stream;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.navajo.resource.http.HttpResource;
import com.dexels.navajo.resource.http.HttpResourceFactory;

import io.reactivex.FlowableTransformer;
import io.reactivex.schedulers.Schedulers;

public class HttpPushTransformer implements ReactiveTransformer {


	private final TransformerMetadata metadata;
	private final ReactiveParameters parameters;

	public HttpPushTransformer(TransformerMetadata metadata,
			ReactiveParameters parameters) {
		this.parameters = parameters;
		this.metadata = metadata;
	}


	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters resolved = parameters.resolve(context, current,param, metadata);
		String name = resolved.paramString("name");
		int parallel = resolved.optionalInteger("parallel").orElse(1);
		HttpResource res = HttpResourceFactory.getInstance().getHttpResource(name);
		
		return flow->{
			if(res==null) {
				throw new NullPointerException("Missing http resource: "+name);
			}
			return flow.map(f->f.message())
					.observeOn(Schedulers.io())
					.map(msg->{
						ReactiveResolvedParameters resInMsg = parameters.resolve(context, Optional.of(msg), ImmutableFactory.empty(), metadata);
						String id = resInMsg.paramString("id");
						String bucket = resInMsg.paramString("bucket");
						String property = resInMsg.paramString("property");
						
						Binary bin = (Binary)msg.columnValue(property);
						
						return res.put(context.getTenant(), bucket, id,bin)
							.map(e->e.toMessage())
							.toFlowable();
						
					})
					.flatMap(f->f,parallel)
					.map(DataItem::of);
		};

	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}



}
