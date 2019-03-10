package com.dexels.navajo.adapters.stream;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.stream.jetty.JettyClient;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;

import io.reactivex.Flowable;

public class TestHttp {

	
	private static final Logger logger = LoggerFactory.getLogger(TestHttp.class);

	@Test 
	public void testHttpGet() throws Exception {
		JettyClient jc = new JettyClient();
//		jc.c
		String weather = jc.callWithoutBodyToStream("http://api.openweathermap.org/data/2.5/weather?q=Amsterdam&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml", e->e)
//		String weather = HTTP.get("http://api.openweathermap.org/data/2.5/weather?q=Amsterdam&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
		.lift(XML.parseFlowable(10))
		.flatMap(x->x)
		.filter(e->e.getType()==XmlEventTypes.START_ELEMENT)
		.filter(e->e.getText().equals("weather"))
		.firstElement()
		.map(xml->xml.getAttributes().get("value")).blockingGet();
	
		logger.info("Weather: {}",weather);
		// Not really a good unit test (... or is it?)
	}


	@Test
	public void testBiggerDownload() throws Exception {
		String url = "https://www.ad.nl/home/rss.xml";

		JettyClient jc = new JettyClient();
		long l = jc.callWithoutBodyToStream(url,e->e)
			.lift(XML.parseFlowable(10))
			.flatMap(x->x)
			.count().blockingGet();
		logger.info("> {}",l);
		Assert.assertTrue(l>1000);
	}
	
	@Test
	public void testBackPressure() throws InterruptedException {
		System.setProperty("rx2.buffer-size", "10");
		Flowable<Long> timer = Flowable.interval(100, TimeUnit.MILLISECONDS);
		String[] xmls = new String[]{"<tag a=\"b\"><ble b=\"c\"> ","beeb</ble>","<blub/></tag>"};
		Flowable.fromArray(xmls)
				.doOnNext(i->logger.info("Source emitting: {}",i))
				.map(i->i.getBytes())
				.lift(XML.parseFlowable(10))
				.flatMap(x->x)
				.doOnError(t->t.printStackTrace())
				.zipWith(timer, (a,b)->a)
				.doOnComplete(()->logger.info("done"))
				.blockingForEach(e->logger.info(">> {}",e));
	}
}
