import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.ObservableOutputStream;
import com.dexels.navajo.document.stream.xml.ObservableNavajoParser;
import com.dexels.navajo.document.stream.xml.ObservableXmlFeeder;

import rx.Observable;

public class TestScriptingExample {
	
	
	private final static Logger logger = LoggerFactory.getLogger(TestScriptingExample.class);

	
	@Test @Ignore
	public void testScriptRun() throws Exception {
		ObservableXmlFeeder oxf = new ObservableXmlFeeder();
		ObservableNavajoParser onp = new ObservableNavajoParser(Collections.emptyMap());
		TestScriptRunner runner = new TestScriptRunner();
		NavajoStreamCollector nsc = new NavajoStreamCollector();

		Navajo result = Observable.<byte[]>create(
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
			.flatMap(navajo->nsc.feed(navajo))
			.toBlocking().first();
		
		int size = result.getMessage("Company").getArraySize();
		Assert.assertTrue(size>1400);
		System.err.println("size: "+size);
	}


	@Test @Ignore
	public void testInit() {
		TestScriptRunner runner = new TestScriptRunner();
		TestScriptRunner runner2 = new TestScriptRunner();
		NavajoDomStreamer nds = new NavajoDomStreamer();
		NavajoStreamCollector nsc = new NavajoStreamCollector();
		Navajo nn = runner.runInitScript("example.CompanyList", "Pam", "Pet")
			.flatMap(NavajoDomStreamer::feed)
			.flatMap(n->runner2.call(n))
			.doOnNext(n->System.err.println(":: "+n))
			.flatMap(n->nsc.feed(n)).toBlocking().first();
		nn.write(System.err);
	}


	@Test @Ignore
	public void testListCompany() {
		TestScriptRunner runner = new TestScriptRunner();
		NavajoStreamCollector nsc = new NavajoStreamCollector();
		Navajo nn = Observable.just(NavajoFactory.getInstance().createNavajo())
			.flatMap(NavajoDomStreamer::feed)
			.flatMap(n->runner.to(n,"example.CompanyList","User","Pw"))
			.doOnNext(a->System.err.println("Found event: "+a))
			.flatMap(n->runner.call(n))
			.doOnNext(e->System.err.println("|||>> "+e))
			.flatMap(n->nsc.feed(n))
			.toBlocking().first();
		
		nn.write(System.err);
		Assert.assertEquals(10,nn.getMessage("Company").getArraySize());
		nn.write(System.err);
	}

	@Test 
	public void testEachCompany() throws InterruptedException {
		long time =  System.currentTimeMillis();
		TestScriptRunner runner = new TestScriptRunner();
		NavajoStreamCollector nsc = new NavajoStreamCollector();
		Navajo nn = Observable.just(NavajoFactory.getInstance().createNavajo())
			.flatMap(NavajoDomStreamer::feed)
			.flatMap(n->runner.to(n,"example.EachCompany","User","Pw"))
			.flatMap(n->runner.call(n))
			.flatMap(n->nsc.feed(n)).toBlocking().first();
		System.err.println("Elapsed: "+(System.currentTimeMillis()-time));
		nn.write(System.err);
		Thread.sleep(10000);
		nn.write(System.err);
		Assert.assertEquals(20,nn.getMessage("Company").getArraySize());
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
