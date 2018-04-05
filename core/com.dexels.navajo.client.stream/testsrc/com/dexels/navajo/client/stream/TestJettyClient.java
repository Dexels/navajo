package com.dexels.navajo.client.stream;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

import org.eclipse.jetty.http.HttpMethod;
import org.junit.Test;

import com.dexels.navajo.client.stream.jetty.JettyClient;
import com.dexels.navajo.client.stream.jetty.NavajoReactiveJettyClient;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.XML;

import io.reactivex.Flowable;
import junit.framework.Assert;

public class TestJettyClient {

	@Test
	public void testJettyCLient() throws Exception {
		JettyClient jc = new JettyClient();

		String uri = "http://localhost:9090/navajo";
		
		Flowable<byte[]> flw = jc.callWithoutBodyToStream(uri,req->req
				.header("X-Navajo-Reactive", "true")
				.header("X-Navajo-Service", "single")
				.header("X-Navajo-Instance", "")
				.header("X-Navajo-Username", "")
				.header("X-Navajo-Password", "")
				.header("Accept-Encoding", null)
				)
				.doOnNext(e->System.err.println("INPUT: "+new String(e)));
		
//		flw.blockingForEach(b->System.err.println("=>"+new String(b)));

		byte[] result = jc.callWithBodyToStream(uri,req->req
				.header("X-Navajo-Reactive", "true")
				.header("X-Navajo-Service", "input")
				.header("X-Navajo-Instance", "")
				.header("X-Navajo-Username", "")
				.header("X-Navajo-Password", "")
				.header("Accept-Encoding", null)
				.method(HttpMethod.POST)
				, flw, "text/xml;charset=utf-8")
		.reduce(new ByteArrayOutputStream(),(stream,b)->{stream.write(b); return stream;})
		.map(stream->stream.toByteArray())
		.blockingGet();
		
		System.err.println("\n$$$=>"+new String(result));
		Assert.assertTrue(result.length>10000);
		jc.close();
	}
	
	@Test
	public void testNavajoClient() throws Exception {
		JettyClient client = new JettyClient();
		String uri="http://localhost:9090/navajo";
//		.compose(StreamCompress.compress(Optional.of("deflate")));
		String service = "single";
		String username = "";
		String password = "";
		Flowable<NavajoStreamEvent> in = Flowable.<NavajoStreamEvent>empty().compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)));
		Flowable<byte[]> inStream = in.lift(StreamDocument.serialize()).doOnNext(e->System.err.println("Sending: "+new String(e)));
		Navajo n = client.callWithBodyToStream(uri, req->req
			.header("X-Navajo-Reactive", "true")
			.header("X-Navajo-Service", service)
			.header("X-Navajo-Instance", "")
			.header("X-Navajo-Username", username)
			.header("X-Navajo-Password", password)
//			.header("Content-Encoding", "deflate")
//			.header("Accept-Encoding", "deflate")

			.method(HttpMethod.POST), inStream,"text/xml;charset=utf-8")
//	.compose(StreamCompress.decompress(Optional.of("deflate")))
	.lift(XML.parseFlowable(5))
	.concatMap(e->e)
	.lift(StreamDocument.parse())
	.concatMap(e->e)
	.compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)))
	.toObservable()
	.compose(StreamDocument.domStreamCollector())
	.blockingFirst();
	System.err.println(">>>> ");
	n.write(System.err);
//	.blockingForEach(e->System.err.println("elt: "+e));
	}

	private static Flowable<NavajoStreamEvent> call(JettyClient client,String uri, String username, String password, String service, String tenant, Flowable<NavajoStreamEvent> in) {
		Flowable<byte[]> inStream = in
				.compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)))
				.lift(StreamDocument.serialize())
				.doOnNext(e->System.err.println("Sending: "+new String(e)));
//			.compose(StreamCompress.compress(Optional.of("deflate")));
		return client.callWithBodyToStream(uri, req->req
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
	
	@Test
	public void testNavajoClientSeriously() throws Exception {
		JettyClient client = new JettyClient();
		String uri="http://localhost:9090/navajo";
//		.compose(StreamCompress.compress(Optional.of("deflate")));
		String service = "single";
		String username = "";
		String password = "";
		call(client,uri,username,password,service,"KNVB",Flowable.empty())
			.blockingForEach(e->System.err.println("Item: "+e));
	}
	
	@Test
	public void testNavajoClientForReal() throws Exception {
		NavajoReactiveJettyClient client = new NavajoReactiveJettyClient();
		client.call("club/InitUpdateClub", "", Flowable.empty())
			.blockingForEach(c->System.err.println("Elt: "+c));
	}

}
