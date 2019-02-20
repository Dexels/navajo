import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.client.stream.jetty.JettyClient;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;

import io.reactivex.Flowable;

public class TestHttp {

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
	
		System.err.println("Weather: "+weather);
		// Not really a good unit test (... or is it?)
//		Assert.assertEquals("few clouds", weather);
	}


	@Test
	public void testBiggerDownload() throws Exception {
//		String url = "https://repo.dexels.com/nexus/service/local/repositories/central/content/org/apache/tika/tika-bundle/1.6/tika-bundle-1.6.jar";
		String url = "https://www.ad.nl/home/rss.xml";
//		String url = "http://localhost:8080/clubs.xml";

		JettyClient jc = new JettyClient();
		long l = jc.callWithoutBodyToStream(url,e->e)
			.lift(XML.parseFlowable(10))
			.flatMap(x->x)
			.count().blockingGet();
		System.err.println("> "+l);
		Assert.assertTrue(l>1000);
	}
	
	@Test
	public void testBackPressure() throws InterruptedException {
		System.setProperty("rx2.buffer-size", "10");
		Flowable<Long> timer = Flowable.interval(100, TimeUnit.MILLISECONDS);
		String[] xmls = new String[]{"<tag a=\"b\"><ble b=\"c\"> ","beeb</ble>","<blub/></tag>"};
		Flowable.fromArray(xmls)
				.doOnNext(i->System.err.println("Source emitting: "+i))
				.map(i->i.getBytes())
				.lift(XML.parseFlowable(10))
				.flatMap(x->x)
				.doOnError(t->t.printStackTrace())
				.zipWith(timer, (a,b)->a)
				.doOnComplete(()->System.err.println("done"))
				.blockingForEach(e->System.err.println(e));
	}
}
