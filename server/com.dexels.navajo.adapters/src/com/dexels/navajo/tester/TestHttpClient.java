package com.dexels.navajo.tester;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.AsynchronousCloseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.client.NavajoResponseHandler;
import com.dexels.navajo.client.async.AsyncClientFactory;
import com.dexels.navajo.client.async.ManualAsyncClient;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;

//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

public class TestHttpClient implements NavajoResponseHandler {

	private static Random rand = new Random(System.currentTimeMillis());
	
	private Object semaphore = new Object();
	private Navajo response = null;
	private boolean fail = false;
	private Integer timeout;
	
	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	private final static Logger logger = LoggerFactory
			.getLogger(TestHttpClient.class);
	
	public static String printHeader(Map<String, List<String>> header) {
		
		StringBuffer sb = new StringBuffer();
		Iterator<String> keys = header.keySet().iterator();
		while ( keys.hasNext() ) {
			String key = keys.next();
			List<String> value = header.get(key);
			sb.append(key+"=");
			for ( int i = 0; i < value.size(); i++ ) {
				sb.append(value.get(i)+",");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public void call(String host, Navajo request) throws Exception {

		response = null;
		fail = false;
		ManualAsyncClient ac = AsyncClientFactory.getManualInstance();
		ac.callService(host.startsWith("http") ? host : "http://" + host, request.getHeader().getRPCUser(), 
				request.getHeader().getRPCPassword(), request, request.getHeader().getRPCName(), this);

	}
	
	public static void main(String [] args) throws Exception {
		
		
		if ( args.length < 5 ) {
			System.err.println("Usage: TestHttpClient -postman [Postman URL] -iterations [Aantal iteraties] -rate [Max rate] -sleep [Sleep time] -request [Request File] -assertString [String]");
			System.exit(1);
		}
		
		String url = null;
		String loopCount = null;
		Integer maxRate = 1;
		Integer sleep = null;
		Integer timeout = null;
		String request = null;
		String assertString = null;
		
		// Set parameters.
		for ( int i = 0; i < args.length; i++ ) {
			if ( args[i].equals("-postman") ) {
				url = args[++i];
			} else if ( args[i].equals("-iterations") ) {
				loopCount = args[++i];
			} else if ( args[i].equals("-rate") ) {
				maxRate = Integer.parseInt(args[++i]);
			} else if ( args[i].equals("-sleep") ) {
				sleep = Integer.parseInt(args[++i]);
			} else if ( args[i].equals("-request") ){
				request = args[++i];
			} else if ( args[i].equals("-assertString") )  {
				assertString = args[++i];
			} else if ( args[i].equals("-timeout") ) {
				timeout = Integer.parseInt(args[++i]);
			}
		}
		
		double minTime = (1.0/maxRate)*1000.0;
		
		System.err.println("url  = " + url);
		System.err.println("loopCount = " + loopCount);
		System.err.println("maxRate = " + maxRate);
		System.err.println("sleep = " + sleep);
		System.err.println("assertString = " + assertString);
		System.err.println("timeout = " + timeout);
		if ( request == null ) {
			throw new Exception("No request specified.");
		}
		FileInputStream fis = new FileInputStream(request);
		Navajo n = NavajoFactory.getInstance().createNavajo(fis);
		fis.close();
		
		TestHttpClient c = new TestHttpClient();
		c.setTimeout(timeout);
		
		double total = 0;
		int failedCount = 0;
		for (int i = 0; i < Integer.parseInt(loopCount); i++) {
			try {
			 long start = System.currentTimeMillis();
			 c.call(url, n);
			 String result = c.getResult();
			 long end = System.currentTimeMillis();
			 
			 String serverTime = ( c.response != null ? c.response.getHeader().getHeaderAttribute("serverTime") : null );
			 if ( result == null || result.indexOf(assertString) == -1 ) {
				 if ( result != null && result.indexOf(assertString) == -1 ) {
					 System.err.println(result);
					
				 }
				 System.err.println(i + ", Failed, " + ( end - start ) + ", " + serverTime + ", "+ ( ++failedCount) );
			 } else {
				 System.err.println(i + ", Success, " +  ( end - start ) + ", " + serverTime + ", " + failedCount );
			 }
			 double sleepTime = ( sleep != null ? sleep : (minTime - ( end - start )) );
			 //System.err.println("sleepTime: " + sleepTime);
			 if ( sleepTime > 0 ) {
				 Thread.sleep((long) sleepTime);
				 end = System.currentTimeMillis();
			 }
			 total += ( end - start )/1000.0;

			} catch (Exception e) {
				logger.error("Error: ", e);
			}
//			 if ( i % 100 == 0) {
//				 System.err.println("i: " + i + ", rate: " + 1.0/(total/100));
//				 total = 0;
//			 }
		}
		
	}

	@Override
	public void onResponse(Navajo n) {
		response = n;
		synchronized (semaphore) {
			semaphore.notifyAll();
		}
	}

	public String getResult() {
		if ( response == null ) {
			synchronized(semaphore) {
				try {
					semaphore.wait( ( timeout != null ? timeout : 5000 ));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if ( fail || response == null ) {
			if ( response == null ) {
				System.err.println("null response");
			}
			return null;
		}
		StringWriter sw = new StringWriter();
		response.write(sw);
		return sw.toString();
	}
	
	@Override
	public void onFail(Throwable t) throws IOException {
		t.printStackTrace(System.err);
		fail = true;
	}
}
