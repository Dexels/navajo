package com.dexels.navajo.resource.http.stream;

import java.util.List;
import java.util.Optional;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.navajo.resource.http.HttpResourceFactory;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class HttpPushStreamTransformer implements ReactiveTransformer {


	private final TransformerMetadata metadata;
	private final ReactiveParameters parameters;
	private Optional<XMLElement> sourceElement;

	public HttpPushStreamTransformer(TransformerMetadata metadata, String relativePath,
			List<ReactiveParseProblem> problems, ReactiveParameters parameters, Optional<XMLElement> xml,
			boolean useGlobalInput) {
		this.parameters = parameters;
		this.metadata = metadata;
		this.sourceElement = xml;
	}


	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context) {
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(), metadata, sourceElement, "");
		String name = resolved.paramString("name");
		String id = resolved.paramString("id");
		String bucket = resolved.paramString("bucket");
		String type = resolved.optionalString("type").orElse("application/octetstream");
		return flow->{
			Flowable<byte[]> in = flow.map(f->f.data());
			return HttpResourceFactory.getInstance()
					.getHttpResource(name)
					.put(bucket, id,type, in)
					.map(status->ImmutableFactory.empty().with("code", status, Property.INTEGER_PROPERTY)).map(DataItem::of)
					.toFlowable();
		};
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
