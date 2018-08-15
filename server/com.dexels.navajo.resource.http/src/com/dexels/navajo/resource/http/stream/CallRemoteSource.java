package com.dexels.navajo.resource.http.stream;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.eclipse.jetty.http.HttpMethod;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.client.stream.jetty.JettyClient;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class CallRemoteSource implements ReactiveSource {

	private final ReactiveParameters params;
	private final SourceMetadata metadata;
	private final String relativePath;
	private final Optional<XMLElement> sourceElement;
	private final JettyClient client;
	private final Type finalType;
	private List<ReactiveTransformer> transformers;

	public CallRemoteSource(SourceMetadata metadata, JettyClient client, String relativePath, String type, List<ReactiveParseProblem> problems,
			Optional<XMLElement> sourceElement, ReactiveParameters params, List<ReactiveTransformer> transformers, Type finalType,
			Function<String, ReactiveMerger> reducerSupplier) {
		this.metadata = metadata;
		this.client = client;
		this.params = params;
		this.relativePath = relativePath;
		this.sourceElement = sourceElement;
		this.transformers = transformers;
		this.finalType = finalType;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters resolved = params.resolveNamed(context, current, ImmutableFactory.empty(), metadata, sourceElement, relativePath);
		String server = resolved.paramString("server");
		String username = resolved.paramString("username");
		String password = resolved.paramString("password");
		final String service =  resolved.paramString("service");
		final boolean debug = resolved.paramBoolean("debug", ()->false);
		Flowable<DataItem> flow = client.callWithBodyToStream(server, e->
		e.header("X-Navajo-Username", username)
		 .header("X-Navajo-Password", password)
		 .header("X-Navajo-Service", service)
		 .method(HttpMethod.POST)
		, Flowable.<NavajoStreamEvent>empty()
				.compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)))
				.lift(StreamDocument.serialize())
		, "text/xml")
		.doOnNext(e->System.err.println(new String(e)))
		.lift(XML.parseFlowable(10))
		.concatMap(e->e)
		.lift(StreamDocument.parse())
		.map(DataItem::ofEventStream);
		
		for (ReactiveTransformer reactiveTransformer : transformers) {
			flow = flow.compose(reactiveTransformer.execute(context));
		}
		return flow;
	}

	@Override
	public Type finalType() {
		return finalType;
	}

	@Override
	public boolean streamInput() {
		return false;
	}

}
