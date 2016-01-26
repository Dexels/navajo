import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.NavajoStreamSerializer;
import com.dexels.navajo.document.stream.ObservableOutputStream;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;

import rx.Observable;

public class TestScriptingExample {
	
	
	private final static Logger logger = LoggerFactory.getLogger(TestScriptingExample.class);

	
	@Test
	public void testScriptRun() throws Exception {
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser(Collections.emptyMap());
		TestScriptRunner runner = new TestScriptRunner();
		NavajoStreamSerializer serializer = new NavajoStreamSerializer();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Observable.<byte[]> create(
			subscriber -> {
				try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tml_without_binary.xml")) {
					TestScriptingExample.streamBytes(inputStream, 1, new ObservableOutputStream(subscriber, 1));
				} catch (Exception e) {
					logger.error("Error: ", e);
				}
			})
			.flatMap(s -> oxf.feed(s))
			.flatMap(xml -> onp.feed(xml))
			.flatMap(n->runner.call(n))
			.flatMap(n->serializer.feed(n))
			.doOnCompleted(()->System.err.println("Completed"))
			.toBlocking().forEach(b -> {
			try {
				baos.write(b);
			} catch (Exception e) {
			}
		});
		System.err.println("OUTPUT::\n"+new String(baos.toByteArray()));
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
	
}
