import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.client.async.ManualAsyncClient;
import com.dexels.navajo.client.async.apache.impl.AsyncClientImpl;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;

public class TestAsyncClient {

	private final static Logger logger = LoggerFactory
			.getLogger(TestAsyncClient.class);

	@Test @Ignore
	public void test() throws IOException, InterruptedException {

		final ManualAsyncClient ac = new AsyncClientImpl();

//		myClient.setClientCertificate("SunX509","JKS", getClass().getClassLoader().getResourceAsStream("client.jks"), "password".toCharArray());
		ac.setClientCertificate("SunX509", "JKS", getClass().getClassLoader().getResourceAsStream("client.jks"), "password".toCharArray());
		final NavajoResponseHandler showOutput = new NavajoResponseHandler() {
			@Override
			public void onResponse(Navajo n) {
				logger.debug("Navajo finished!");
				try {
					logger.debug("Response2 ..");
					n.write(System.err);
				} catch (NavajoException e) {
					logger.error("Error: ", e);
				}
			}

			@Override
			public void onFail(Throwable t) {
				logger.error("whoops: ", t);
			}

			@Override
			public Throwable getCaughtException() {
				return null;
			}
		};

		String service = "club/InitUpdateClub";

		Navajo input = NavajoFactory.getInstance().createNavajo();
		for (int i = 0; i < 10; i++) {
			ac.callService(input, service, showOutput);
			System.out.println("Exchange sent");
		}
		Thread.sleep(10000);
	}

}
