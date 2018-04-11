package com.dexels.navajo.client.stream;

import java.io.ByteArrayOutputStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jetty.http.HttpMethod;
import org.junit.Test;

import com.dexels.config.runtime.TestConfig;
import com.dexels.navajo.client.stream.jetty.JettyClient;
import com.dexels.navajo.client.stream.jetty.NavajoReactiveJettyClient;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.XML;

import io.reactivex.Flowable;
import junit.framework.Assert;

public class TestJettyClient {

	String uri = TestConfig.NAVAJO_TEST_SERVER.getValue(); 
	String service = "vla/authorization/InitLoginSystemUser";
	String username = TestConfig.NAVAJO_TEST_USER.getValue();
	String password = TestConfig.NAVAJO_TEST_PASS.getValue();

	
	@Test
	public void testJettyCLient() throws Exception {
		JettyClient jc = new JettyClient();
//		String tenant = "KNVB";

		Flowable<byte[]> in = Flowable.<NavajoStreamEvent>empty()
				.compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)))
				.lift(StreamDocument.serialize());

		byte[] result = jc.callWithBodyToStream(uri,req->req
				.header("X-Navajo-Reactive", "true")
				.header("X-Navajo-Service", service)
//				.header("X-Navajo-Instance", tenant)
				.header("X-Navajo-Username", username)
				.header("X-Navajo-Password", password)
				.header("Accept-Encoding", null)
				.method(HttpMethod.POST)
				, in, "text/xml;charset=utf-8")
		.reduce(new ByteArrayOutputStream(),(stream,b)->{stream.write(b); return stream;})
		.map(stream->stream.toByteArray())
		.blockingGet();
		
		System.err.println("\n$$$=>"+new String(result));
		Assert.assertTrue(result.length>5000);
		jc.close();
	}
	
	@Test
	public void testNavajoClient() throws Exception {
		JettyClient client = new JettyClient();

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
	public void testNavajoClientForReal() throws Exception {
		NavajoReactiveJettyClient client = new NavajoReactiveJettyClient(this.uri,this.username,this.password,Optional.empty(),false);
		
		int size = client.call("vla/authorization/InitLoginSystemUser", "", Flowable.empty())
			.lift(StreamDocument.serialize())
			.doOnNext(e->System.err.println("$$: "+new String(e)))
			.reduce(new AtomicInteger(),(acc,i)->{acc.addAndGet(i.length); return acc;})
			.blockingGet().get();
		
		System.err.println("size: "+size);
		Assert.assertTrue(size>5000);
		client.close();
	}

}
