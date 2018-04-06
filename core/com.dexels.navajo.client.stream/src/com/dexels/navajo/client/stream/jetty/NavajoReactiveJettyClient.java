package com.dexels.navajo.client.stream.jetty;

import java.util.Map;
import java.util.Optional;

import org.eclipse.jetty.http.HttpMethod;

import com.dexels.navajo.client.stream.ReactiveReply;
import com.dexels.navajo.document.stream.StreamCompress;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.XML;

import io.reactivex.Flowable;

public class NavajoReactiveJettyClient {
	private final JettyClient client;
	private final String uri;
	private final String username;
	private final String password;
	private final boolean useReactive;
	private final Optional<String> tenant;
	
	public NavajoReactiveJettyClient(String uri, String username, String password, Optional<String> tenant, boolean useReactive) throws Exception {
		this.client = new JettyClient();
		this.uri = uri;
		this.username = username;
		this.password = password;
		this.tenant = tenant;
		this.useReactive = useReactive;
	}

	public Flowable<NavajoStreamEvent> call(String service, String tenant, Flowable<NavajoStreamEvent> in) {
		Flowable<byte[]> inStream = in
				.compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)))
				.lift(StreamDocument.serialize())
				.compose(StreamCompress.compress(Optional.of("deflate")))
				.doOnNext(e->System.err.println("Sending: "+new String(e)));
		return this.client.callWithBodyToStream(uri, req->req
				.header("X-Navajo-Reactive", useReactive?"true":"false")
				.header("X-Navajo-Service", service)
				.header("X-Navajo-Instance", tenant)
				.header("X-Navajo-Username", username)
				.header("X-Navajo-Password", password)
				.header("Content-Encoding", "jzlib")
				.header("Accept-Encoding", "jzlib")
				.method(HttpMethod.POST), inStream,"text/xml;charset=utf-8")
					.lift(XML.parseFlowable(5))
			.concatMap(e->e)
			.lift(StreamDocument.parse())
			.concatMap(e->e)
			.compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)));
	}
	public void activate(Map<String,Object> settings) {
		
	}
}
