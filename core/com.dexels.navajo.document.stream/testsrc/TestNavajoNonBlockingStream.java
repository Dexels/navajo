import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.impl.ObservableNavajoParser;
import com.dexels.navajo.document.stream.impl.ObservableOutputStream;
import com.dexels.navajo.document.stream.impl.ObservableXmlFeeder;
import com.dexels.navajo.document.stream.impl.TestAsync2;

import rx.Observable;

public class TestNavajoNonBlockingStream {

	private final static Logger logger = LoggerFactory.getLogger(TestNavajoNonBlockingStream.class);
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testBytes() throws Exception {

		ObservableOutputStream oos = new ObservableOutputStream();
		Observable<byte[]> bytecount = oos.getObservable()
				 .doOnNext(b->System.err.println("Bytes: "+new String(b)));
//				 .doOnTerminate(()->System.err.println("Terminate!"))
//			.count();
		oos.getObservable().connect();

		streamBytes(getTestInput(), 1,false, 0, oos);
		
//		Assert.assertEquals(1595+2,(int)bytecount.toBlocking().first());
		
	}

	@Test
	public void testXml() throws Exception {

		ObservableOutputStream oos = new ObservableOutputStream();
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser();
		NavajoStreamCollector nsc = new NavajoStreamCollector();
		oos.getObservable()
				// .doOnNext(b->System.err.println("Bytes: "+new String(b)))
				// .doOnTerminate(()->System.err.println("Terminate!"))
				.flatMap(bytes -> oxf.feed(bytes))
				// .doOnNext(b->System.err.println("XMLElement: "+b))
				.flatMap(xmlEvents -> onp.feed(xmlEvents)).doOnNext(b -> System.err.println("Navajo events: " + b))
				.flatMap(navajoEvents -> nsc.feed(navajoEvents))
				// .doOnNext(navajo->navajo.write(System.err))
				.subscribe(ar -> System.err.println("Print: " + ar));
		oos.getObservable().connect();
		streamBytes(getTestInput(), 100,false, 0, oos);
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
