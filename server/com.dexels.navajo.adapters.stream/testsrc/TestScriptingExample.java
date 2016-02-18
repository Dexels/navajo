import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.NavajoDomStreamer;
import com.dexels.navajo.document.stream.NavajoStreamCollector;
import com.dexels.navajo.document.stream.ObservableOutputStream;

import rx.Observable;

public class TestScriptingExample {
	
	

	@Test @Ignore
	public void testInit() {
		TestScriptRunner runner = new TestScriptRunner();
		TestScriptRunner runner2 = new TestScriptRunner();
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
