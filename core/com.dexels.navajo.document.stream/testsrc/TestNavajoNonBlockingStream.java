import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.NavajoStreamSerializer;
import com.dexels.navajo.document.stream.ObservableOutputStream;
import com.dexels.navajo.document.stream.impl.TestAsync3;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;

import rx.Observable;

public class TestNavajoNonBlockingStream {

	private final static Logger logger = LoggerFactory.getLogger(TestNavajoNonBlockingStream.class);
	// private Subscriber<? super Navajo> sub;

	@Before
	public void setup() {
	}

	@Test @Ignore
	public void testDomStreamerAndCollector() throws Exception {

		Navajo baseTml = NavajoFactory.getInstance()
				.createNavajo(TestAsync3.class.getClassLoader().getResourceAsStream("tml.xml"));

		NavajoStreamCollector nsc = new NavajoStreamCollector();
		NavajoDomStreamer domStreamer = new NavajoDomStreamer();
		Navajo result = Observable.<Navajo> create(subscribe -> {
			subscribe.onNext(baseTml);

		}).flatMap(navajo -> domStreamer.feed(navajo)).flatMap(navajoEvent -> nsc.feed(navajoEvent)).toBlocking()
				.first();

		StringWriter sw1 = new StringWriter();
		baseTml.write(sw1);
		StringWriter sw2 = new StringWriter();
		result.write(sw2);
		System.err.println("orig: " + sw1.toString().length());
		System.err.println("proc: " + sw2.toString().length());
		FileWriter fw = new FileWriter("out1.xml");
		fw.write(sw1.toString());
		fw.close();
		FileWriter fw2 = new FileWriter("out2.xml");
		fw2.write(sw2.toString());
		fw2.close();
		Assert.assertEquals(sw1.toString(), sw2.toString());
	}

	public void testSomething() throws Exception {

		Navajo baseTml = NavajoFactory.getInstance()
				.createNavajo(getClass().getClassLoader().getResourceAsStream("tml.xml"));

		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser(Collections.emptyMap());
		NavajoStreamCollector nsc = new NavajoStreamCollector();
		NavajoDomStreamer domStreamer = new NavajoDomStreamer();
		NavajoStreamSerializer serializer = new NavajoStreamSerializer();
		Navajo result = Observable.<Navajo> create(subscribe -> {
			subscribe.onNext(baseTml);

		}).flatMap(navajo -> domStreamer.feed(navajo)).flatMap(navajoEvent -> nsc.feed(navajoEvent)).toBlocking()
				.first();

		StringWriter sw1 = new StringWriter();
		baseTml.write(sw1);
		StringWriter sw2 = new StringWriter();
		baseTml.write(sw2);
		Assert.assertEquals(sw1.toString(), sw2.toString());
	}

	@Test 
	public void testStreamParser() throws Exception {


		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser(Collections.emptyMap());
		NavajoStreamSerializer serializer = new NavajoStreamSerializer();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Observable.<byte[]> create(subscriber -> {
			try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tml.xml")) {
				streamBytes(inputStream, 1, new ObservableOutputStream(subscriber, 1));
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
		}).flatMap(s -> oxf.feed(s))
				.flatMap(xml -> onp.feed(xml))
				.doOnEach(ee->System.err.println("xEvent: "+ee))
				.flatMap(nsevent -> serializer.feed(nsevent))
				.doOnEach(ee->System.err.println("Event: "+ee))
				.toBlocking().forEach(b -> {
					try {
						baos.write(b);
					} catch (Exception e) {
					}
				});
		// .flatMap(navajoEvent-> nsc.feed(navajoEvent)).toBlocking().first();
		byte[] original = getNavajoData();
		System.err.println("output: "+new String(baos.toByteArray()));
		Assert.assertArrayEquals(original, baos.toByteArray());

		// Assert.assertEquals(sw1.toString(), sw2.toString());
	}

	@Test @Ignore
	public void testXmlParser() throws Exception {


		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		int count = Observable.<byte[]> create(subscriber -> {
//			try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tml.xml")) {
//				streamBytes(inputStream, 1, new ObservableOutputStream(subscriber, 1));
//			} catch (Exception e) {
//				logger.error("Error: ", e);
//			}
//		}).flatMap(s -> oxf.feed(s)).doOnEach(a->System.err.println(a.getKind()+" -> "+a.toString())).count().toBlocking().first();

//	int count =	Observable.just("<tml/>".getBytes(),new byte[]{}).flatMap(s -> oxf.feed(s)).doOnEach(a->System.err.println(a.getKind()+" -> "+a.toString())).count().toBlocking().first();

	int count =	Observable.just(1,2,3)
			.flatMap(s -> Observable.<Integer>create(subscr-> {
				subscr.onNext(s);
				subscr.onCompleted();
			}))
			.count()
			.toBlocking()
			.first();

	// .flatMap(navajoEvent-> nsc.feed(navajoEvent)).toBlocking().first();
//		byte[] original = getNavajoData();
//		Assert.assertArrayEquals(original, baos.toByteArray());
		System.err.println("Count: "+count);
		// Assert.assertEquals(sw1.toString(), sw2.toString());
	}

	private static void streamBytes(InputStream resourceAsStream, int bufferSize, ObservableOutputStream oos) {
		byte[] buffer = new byte[bufferSize];
		int read = -1;
		do {
			try {
				read = resourceAsStream.read(buffer, 0, buffer.length);
				if (read != -1) {
					// System.err.println("Input: "+new
					// String(Arrays.copyOfRange(buffer, 0, read)));
					oos.write(buffer, 0, read);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (read != -1);
		try {
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public byte[] getNavajoData() {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; // you can configure the buffer size
		int length;

		try (InputStream in = getClass().getClassLoader().getResourceAsStream("tml.xml");) {
			while ((length = in.read(buffer)) != -1)
				out.write(buffer, 0, length);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}

		byte[] result = out.toByteArray();
		return result;
	}
}
