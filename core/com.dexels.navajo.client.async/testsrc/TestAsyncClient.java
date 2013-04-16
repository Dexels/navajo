import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.client.async.ManualAsyncClient;
import com.dexels.navajo.client.async.impl.AsyncClientImpl;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;

public class TestAsyncClient {

	private final static Logger logger = LoggerFactory
			.getLogger(TestAsyncClient.class);

	@Test
	public void test() throws IOException, InterruptedException {

		final ManualAsyncClient ac = new AsyncClientImpl();
		// final AsyncClient ac = new
		// AsyncClient("http://10.0.0.100:8080/JsSportlink/Comet", "ROOT",
		// "ROOT");
		// final AsyncClient ac = new
		// AsyncClient("http://localhost:9080/JsSportlink/Comet", "ROOT",
		// "ROOT");
		// ac.setServer("http://penelope1.dexels.com:90/sportlink/test/knvb/Postman");
		ac.setServer("https://source.dexels.com/test/PostmanLegacy");
		ac.setUsername("ROOT");
		ac.setPassword("R20T");
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
