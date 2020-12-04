/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.stream;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jetty.http.HttpMethod;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.stream.jetty.JettyClient;
import com.dexels.navajo.client.stream.jetty.NavajoReactiveJettyClient;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.runtime.config.TestConfig;

import io.reactivex.Flowable;

public class ITJettyClient {

    private static final Logger logger = LoggerFactory.getLogger(ITJettyClient.class);

    private String uri = TestConfig.NAVAJO_TEST_SERVER.getValue();
    private String service = "vla/authorization/InitLoginSystemUser";
    private String username = TestConfig.NAVAJO_TEST_USER.getValue();
    private String password = TestConfig.NAVAJO_TEST_PASS.getValue();

    @Test
    public void testJettyCLient() throws Exception {

        JettyClient jc = new JettyClient();
        Flowable<byte[]> in = Flowable.<NavajoStreamEvent>empty()
                .compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)))
                .lift(StreamDocument.serialize());

        byte[] result = jc.callWithBodyToStream(uri,
                req -> req
                    .header("X-Navajo-Reactive", "true")
                    .header("X-Navajo-Service", service)
                    .header("X-Navajo-Username", username)
                    .header("X-Navajo-Password", password)
                    .header("Accept-Encoding", null)
                    .method(HttpMethod.POST),
                in, "text/xml;charset=utf-8")
                .reduce(new ByteArrayOutputStream(),(stream,b)->{stream.write(b); return stream;})
                .map(stream->stream.toByteArray())
                .blockingGet();

        logger.info("result: {}", new String(result));
        Assert.assertTrue(result.length > 5000);
        jc.close();
    }

    @Test
    public void testNavajoClient() throws Exception {

        JettyClient client = new JettyClient();
        Flowable<NavajoStreamEvent> in = Flowable.<NavajoStreamEvent>empty().compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)));
        Flowable<byte[]> inStream = in.lift(StreamDocument.serialize()).doOnNext(e->logger.debug("Sending: {}",new String(e,StandardCharsets.UTF_8)));
        Navajo n = client.callWithBodyToStream(uri,
                req -> req
                    .header("X-Navajo-Reactive", "true")
                    .header("X-Navajo-Service", service)
                    .header("X-Navajo-Instance", "")
                    .header("X-Navajo-Username", username)
                    .header("X-Navajo-Password", password)
                    .method(HttpMethod.POST), inStream,"text/xml;charset=utf-8")
                    .lift(XML.parseFlowable(5))
                    .concatMap(e->e)
                    .lift(StreamDocument.parse())
                    .concatMap(e->e)
                    .compose(StreamDocument.inNavajo(service, Optional.of(username), Optional.of(password)))
                    .toObservable()
                    .compose(StreamDocument.domStreamCollector())
                    .blockingFirst();

        StringWriter sw = new StringWriter();
        n.write(sw);
        logger.info("{}", sw);
        client.close();
    }

    @Test
    public void testNavajoClientForReal() throws Exception {

        NavajoReactiveJettyClient client = new NavajoReactiveJettyClient(uri, username, password, false);
        int size = client.call("vla/authorization/InitLoginSystemUser", "", Flowable.empty())
                .lift(StreamDocument.serialize())
                .reduce(new AtomicInteger(), (acc,i) -> { acc.addAndGet(i.length); return acc; })
                .blockingGet()
                .get();

        logger.info("size: {}", size);
        Assert.assertTrue(size > 5000);
        client.close();
    }

}
