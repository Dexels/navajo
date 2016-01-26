import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;

import rx.Observable;

public class TestNavajoNonBlockingStream {

	private final static Logger logger = LoggerFactory.getLogger(TestNavajoNonBlockingStream.class);

	@Before
	public void setup() {
	}

	@Test
	public void testDomStreamerAndCollector() throws Exception {

		Navajo baseTml = NavajoFactory.getInstance()
				.createNavajo(getClass().getClassLoader().getResourceAsStream("tiny_tml.xml"));

		NavajoStreamCollector nsc = new NavajoStreamCollector();
		NavajoDomStreamer domStreamer = new NavajoDomStreamer();
		Navajo result = Observable.<Navajo>just(baseTml)		
		.flatMap(navajo -> domStreamer.feed(navajo))
		  .flatMap(navajoEvent -> nsc.feed(navajoEvent))
		  .toBlocking()
		  .first();

		StringWriter sw1 = new StringWriter();
		baseTml.write(sw1);
		StringWriter sw2 = new StringWriter();
		result.write(sw2);
		FileWriter fw = new FileWriter("out1.xml");
		fw.write(sw1.toString());
		fw.close();
		FileWriter fw2 = new FileWriter("out2.xml");
		fw2.write(sw2.toString());
		fw2.close();
		Assert.assertEquals(sw1.toString(), sw2.toString());
	}

	@Test
	public void testDomStream() throws Exception {
		final Navajo baseTml = NavajoFactory.getInstance()
				.createNavajo(getClass().getClassLoader().getResourceAsStream("tml.xml"));
		Observable.<Navajo>create(subscribe-> {
			subscribe.onNext(baseTml);
			subscribe.onCompleted();
		}
		).flatMap(NavajoDomStreamer::feed)
		 .toBlocking()
		 .forEach(e->System.err.println(e.type()+" with path: "+ e.path()));
	}
	
	@Test
	public void testStreamParser() throws Exception {
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser(Collections.emptyMap());
		int count = Observable.<byte[]> create(subscriber -> {
			try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tml_without_binary.xml")) {
				streamBytes(inputStream, 1, new ObservableOutputStream(subscriber, 1));
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
		}).flatMap(s -> oxf.feed(s))
//				.doOnNext(x->System.err.println(x.toString()))
				.flatMap(xml -> onp.feed(xml))
				.count().toBlocking().first();
		Assert.assertEquals(22, count);
	}

	
	@Test 
	public void testStreamParserAndSerializer() throws Exception {
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser(Collections.emptyMap());
		NavajoStreamSerializer serializer = new NavajoStreamSerializer();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Observable.<byte[]> create(subscriber -> {
			try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tml_without_binary.xml")) {
				streamBytes(inputStream, 1, new ObservableOutputStream(subscriber, 1));
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
		}).flatMap(s -> oxf.feed(s))
				.flatMap(xml -> onp.feed(xml))
				.doOnNext(e->System.err.println("Doing: "+e))
				.flatMap(nsevent -> serializer.feed(nsevent))
				.toBlocking().forEach(b -> {
					try {
						baos.write(b);
					} catch (Exception e) {
					}
				});
		// .flatMap(navajoEvent-> nsc.feed(navajoEvent)).toBlocking().first();
		byte[] original = getNavajoData("tml_without_binary.xml");
//		System.err.println(new String(baos.toByteArray()));
		FileOutputStream fw = new FileOutputStream("original.xml");
		fw.write(original);
		fw.close();
		FileOutputStream fw2 = new FileOutputStream("parsed.xml");
		fw2.write(baos.toByteArray());
		fw2.close();
		// TODO Seems ok, but make test to compare Navajo o
				Assert.assertArrayEquals(original, baos.toByteArray());
		// Assert.assertEquals(sw1.toString(), sw2.toString());
	}


	static void streamBytes(InputStream resourceAsStream, int bufferSize, ObservableOutputStream oos) {
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
			e.printStackTrace();
		}
	}
	

	public byte[] getNavajoData(String name) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; // you can configure the buffer size
		int length;

		try (InputStream in = getClass().getClassLoader().getResourceAsStream(name);) {
			while ((length = in.read(buffer)) != -1)
				out.write(buffer, 0, length);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}

		byte[] result = out.toByteArray();
		return result;
		
		
	}
}
