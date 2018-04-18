package com.dexels.navajo.resource.http.stream;

import java.util.List;
import java.util.Optional;
import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;
import com.dexels.navajo.resource.http.HttpResource;
import com.dexels.navajo.resource.http.HttpResourceFactory;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Single;

public class HttpPushTransformer implements ReactiveTransformer {


	private final TransformerMetadata metadata;
	private final ReactiveParameters parameters;
	private Optional<XMLElement> sourceElement;

	public HttpPushTransformer(TransformerMetadata metadata, String relativePath,
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
		HttpResource res = HttpResourceFactory.getInstance().getHttpResource(name);
		
		return flow->{
			if(res==null) {
				throw new NullPointerException("Missing http resource: "+name);
			}
			return flow.map(f->f.message())
					.map(msg->{
						ReactiveResolvedParameters resInMsg = parameters.resolveNamed(context, Optional.of(msg), ImmutableFactory.empty(), metadata, sourceElement, "");
						String id = resInMsg.paramString("id");
						String bucket = resInMsg.paramString("bucket");
						String type = resInMsg.optionalString("type").orElse("application/octetstream");
						String method = resInMsg.paramString("method");
						String property = resInMsg.paramString("property");
						
						Binary bin = (Binary)msg.columnValue(property);
						
						return res.put(bucket, id,bin)
							.map(e->e.toMessage())
							.toFlowable();
						
					})
					.flatMap(f->f,10)
					.map(DataItem::of);
					
			
//			Binary b = in.map(msg->
//					.put(bucket, id,type, in)
//					.map(status->ImmutableFactory.empty().with("code", status, Property.INTEGER_PROPERTY)).map(DataItem::of)
//					.toFlowable());
		};
		
//		return d->d.map(item->item.message())
//				
//				map(di->di.data())
//				
//		
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

}
