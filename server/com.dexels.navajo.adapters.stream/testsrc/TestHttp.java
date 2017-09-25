import java.io.File;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.adapters.stream.HTTP;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.document.stream.xml.XMLEvent.XmlEventTypes;
import com.github.davidmoten.rx2.Bytes;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class TestHttp {

	@Test @Ignore
	public void testHttpGet() throws MalformedURLException {
		
		String weather = HTTP.get("http://api.openweathermap.org/data/2.5/weather?q=Amsterdam&APPID=c9a22840a45f9da6f235c718475c4f08&mode=xml")
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


	@Test @Ignore
	public void testBiggerDownload() throws MalformedURLException, InterruptedException {
		String url = "https://repo.dexels.com/nexus/service/local/repositories/central/content/org/apache/tika/tika-bundle/1.6/tika-bundle-1.6.jar";
//		String url = "http://spiritus.dexels.com:9090/nexus/content/repositories/obr2/.meta/obr.xml";
//		String url = "http://localhost:8080/clubs.xml";
		long l = HTTP.get(url)
			.lift(XML.parseFlowable(10))
			.flatMap(x->x)
//			.lift(NavajoStreamOperatorsNew.parse())
//			.zipWith(timer, (a,b)->a)
			.count().blockingGet();
		System.err.println(">> "+l);
//			.blockingForEach(e->System.err.println(e));
//		Thread.sleep(40000);
	}

	@Test @Ignore
	public void testBigPost() throws MalformedURLException, InterruptedException {
//		String url = "https://repo.dexels.com/nexus/service/local/repositories/central/content/org/apache/tika/tika-bundle/1.6/tika-bundle-1.6.jar";
//		String url = "http://spiritus.dexels.com:9090/nexus/content/repositories/obr2/.meta/obr.xml";
		String url = "http://localhost:8080/reactive-servlet/reactive";
		AtomicLong readCount = new AtomicLong(0);
		Flowable<byte[]> input = Bytes.from(new File("/Users/frank/git/reactive-servlet/rxjava-extras-0.8.0.8.jar"))
				.doOnNext(c->{
					long r = readCount.addAndGet(c.length);
					System.err.println("Read "+r);
				})
				.doOnComplete(()->{System.err.println("input done");});
		AtomicLong count = new AtomicLong(0);
		long l = HTTP.post(url,input,Collections.emptyMap())
//			.lift(XML.parseFlowable(10))
//			.flatMap(x->x)
				.observeOn(Schedulers.io())
				.doOnNext(c->{
					long r = count.addAndGet(c.length);
					System.err.println("Data: "+r);
				})
//			.lift(NavajoStreamOperatorsNew.parse())
//			.zipWith(timer, (a,b)->a)
			.count().blockingGet();
		System.err.println(">> "+l);
//			.blockingForEach(e->System.err.println(e));
//		Thread.sleep(40000);
	}
	

	
	@Test @Ignore
	public void testBackPressure() throws InterruptedException {
//		String xml1 = "<tag><ble";
//		String xml2 = "bla/></tag>";
		
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
		
//			Thread.sleep(1000);
				
//		HTTP.get(url)
//			.lift(XML2.parse())
////			.lift(NavajoStreamOperatorsNew.parse())
//			.zipWith(timer, (a,b)->a)
//			.subscribe(e->System.err.println(e));
//		Thread.sleep(40000);
	}
}
