package com.dexels.navajo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.asyncservice.ServerAsyncListener;
import com.dexels.navajo.client.asyncservice.impl.AsyncRegistryImpl;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;

public class TestAsyncService {

	private final static Logger logger = LoggerFactory
			.getLogger(TestAsyncService.class);

	private AsyncRegistryImpl registry;

	public TestAsyncService() {
		this.registry = new AsyncRegistryImpl();
		this.registry.setClientInterface(NavajoClientFactory.getClient());
	}

	public void test(Navajo n) {
		try {
			logger.info("Checking asynchronous progress..");
			Property p = n.getProperty("/Input/iter");
			p.setValue(10000000);
			Property q = n.getProperty("/Input/d");
			q.setValue(1.5);
			registry.doServerAsyncSend(n, "ProcessAsyncTest", new ServerAsyncListener() {
				
				@Override
				public void setProgress(String id, int d) {
					logger.info("Progress on id: "+id+" : "+d);
				}
				
				@Override
				public void serviceStarted(String id) {
					logger.info("Service: "+id);
				}
				
				@Override
				public void receiveServerAsync(Navajo n, String method, String serverId,
						String clientId) {
					System.err.println("Receive server async: " + method + ", serverId = " + serverId + ", clientId = " + clientId);
					try {
						n.write(System.err);
					} catch (NavajoException e) {
						logger.error("Error: ", e);
					}
//					received = n;
					synchronized (NavajoClientFactory.getClient()) {
//						finished = true;
//						myClient.notifyAll();
					}
			}
				
				@Override
				public void handleException(Exception e) {
					logger.error("Exception in service call: ", e);
				}
			}, "aap", 5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void receiveServerAsync(Navajo n, String method, String serverId,
			String clientId) {

	}
	public static void main(String[] args) throws ClientException {
		TestAsyncService tas = new TestAsyncService();
		NavajoClientFactory.getClient().setUsername("");
		NavajoClientFactory.getClient().setPassword("");
		NavajoClientFactory.getClient().setServerUrl("http://localhost:8080/navajo/KNZB");
		final Navajo n = NavajoClientFactory.getClient().doSimpleSend("InitAsync");
		tas.test(n);
	}

}
