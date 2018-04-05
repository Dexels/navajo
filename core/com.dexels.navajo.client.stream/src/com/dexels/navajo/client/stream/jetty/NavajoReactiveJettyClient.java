package com.dexels.navajo.client.stream.jetty;

import java.util.Map;
import java.util.Optional;

import org.eclipse.jetty.http.HttpMethod;

import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.XML;

import io.reactivex.Flowable;

public class NavajoReactiveJettyClient {
	private final JettyClient client;
	private final String uri = "http://localhost:9090/stream";
	private final String username = "";
	private final String password = "";
	public NavajoReactiveJettyClient() throws Exception {
		this.client = new JettyClient();
	}

	public Flowable<NavajoStreamEvent> call(String service, String tenant, Flowable<NavajoStreamEvent> in) {
		Flowable<byte[]> inStream = in
				.compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)))
				.lift(StreamDocument.serialize())
				.doOnNext(e->System.err.println("Sending: "+new String(e)));
//			.compose(StreamCompress.compress(Optional.of("deflate")));
		return this.client.callWithBodyToStream(uri, req->req
				.header("X-Navajo-Reactive", "true")
				.header("X-Navajo-Service", service)
				.header("X-Navajo-Instance", tenant)
				.header("X-Navajo-Username", username)
				.header("X-Navajo-Password", password)
//				.header("Content-Encoding", "deflate")
//				.header("Accept-Encoding", "deflate")

				.method(HttpMethod.POST), inStream,"text/xml;charset=utf-8")
//		.compose(StreamCompress.decompress(Optional.of("deflate")))
		.lift(XML.parseFlowable(5))
		.concatMap(e->e)
		.lift(StreamDocument.parse())
		.concatMap(e->e)
		.compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)));
	}
	public void activate(Map<String,Object> settings) {
		
	}
}
