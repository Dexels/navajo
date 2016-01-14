import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.NavajoStreamSerializer;
import com.dexels.navajo.document.stream.ObservableOutputStream;
import com.dexels.navajo.document.stream.impl.TestAsync2;
import com.dexels.navajo.document.stream.impl.TestAsync3;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;

import rx.Observable;
import rx.Subscriber;

public class TestNavajoNonBlockingStream {

	private final static Logger logger = LoggerFactory.getLogger(TestNavajoNonBlockingStream.class);
	private Subscriber<? super Navajo> sub;
	
	@Before
	public void setup() {
		
	}
	

	@Test
	public void testDomStreamerAndCollector() throws Exception {

		Navajo baseTml = NavajoFactory.getInstance().createNavajo(TestAsync3.class.getClassLoader().getResourceAsStream("tml.xml"));
		
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser(Collections.emptyMap());
		NavajoStreamCollector nsc = new NavajoStreamCollector();
		NavajoDomStreamer domStreamer = new NavajoDomStreamer();
		NavajoStreamSerializer serializer = new NavajoStreamSerializer();
		Navajo result = Observable.<Navajo>create(subscribe->{
			sub = subscribe;
			sub.onNext(baseTml);	

			})
			.flatMap(navajo -> domStreamer.feed(navajo))
			.flatMap(navajoEvent-> nsc.feed(navajoEvent)).toBlocking().first();
		
		StringWriter sw1 = new StringWriter();
		baseTml.write(sw1);
		StringWriter sw2 = new StringWriter();
		baseTml.write(sw2);
		Assert.assertEquals(sw1.toString(), sw2.toString());

	}

	
	private static InputStream getTestInput() {
		return TestAsync2.class.getClassLoader().getResourceAsStream("tml.xml");
	}
	
	private static void streamBytes(InputStream resourceAsStream, int bufferSize, boolean sync, int sleep, OutputStream oos) {
		byte[] buffer = new byte[bufferSize];
		Runnable run = new Runnable() {

			@Override
			public void run() {
				int read = 0;
				do {
					try {
						read = resourceAsStream.read(buffer, 0, buffer.length);
						if (sleep > 0) {
							Thread.sleep(sleep);
						}
						if (read != -1) {
							// System.err.println("Input: "+new
							// String(Arrays.copyOfRange(buffer, 0, read)));
							oos.write(buffer, 0, read);
						} else {
							oos.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} while (read != -1);
				try {
					oos.close();
					System.err.println("stream closed");
				} catch (IOException e) {
					logger.error("Error: ", e);
				}
			}
		};
		
		if(sync) {
			run.run();
		} else {
			new Thread(run).start();
		}
	}
	
}
