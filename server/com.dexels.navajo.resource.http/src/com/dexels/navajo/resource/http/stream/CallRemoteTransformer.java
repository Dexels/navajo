package com.dexels.navajo.resource.http.stream;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.client.stream.jetty.JettyClient;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.TransformerMetadata;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class CallRemoteTransformer implements ReactiveTransformer {


	private final ReactiveParameters parameters;
	private final Optional<XMLElement> sourceElement;
	private final String sourcePath;
	private final TransformerMetadata metadata;
	private final JettyClient client;
	
	private final static Logger logger = LoggerFactory.getLogger(CallRemoteTransformer.class);
	
	public CallRemoteTransformer(TransformerMetadata metadata, JettyClient client, ReactiveParameters parameters,Optional<XMLElement> sourceElement, String sourcePath) {
		this.parameters = parameters;
		this.sourceElement = sourceElement;
		this.sourcePath = sourcePath;
		this.metadata = metadata;
		this.client = client;
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters resolved = parameters.resolveNamed(context, Optional.empty(), ImmutableFactory.empty(),metadata, sourceElement, sourcePath);
		String server = resolved.paramString("server");
		String username = resolved.paramString("username");
		String password = resolved.paramString("password");
		final String service =  resolved.paramString("service");
		final boolean debug = resolved.paramBoolean("debug", ()->false);
		return flow->
			{
				
			Flowable<NavajoStreamEvent> result = client.callWithBodyToStream(server, e->
				e.header("X-Navajo-Username", username)
				 .header("X-Navajo-Password", password)
				 .header("X-Navajo-Service", service)
				, flow.map(di->di.eventStream())
					.concatMap(e->e)
					.lift(StreamDocument.serialize())
			, "text/xml")
				.lift(XML.parseFlowable(10))
//				.doOnNext(e->System.err.println("Element encountered"))
				.concatMap(e->e)
				.lift(StreamDocument.parse())
				.concatMap(e->e)
				.filter(e->e.type()!=NavajoStreamEvent.NavajoEventTypes.NAVAJO_STARTED && e.type()!=NavajoStreamEvent.NavajoEventTypes.NAVAJO_DONE)
				.doOnNext(e->System.err.println("ITEMMMM: "+e));

			
			return Flowable.just(DataItem.ofEventStream(result));
	
		};
	}

	@Override
	public TransformerMetadata metadata() {
		return metadata;
	}

	@Override
	public Optional<XMLElement> sourceElement() {
		return sourceElement;
	}


}
