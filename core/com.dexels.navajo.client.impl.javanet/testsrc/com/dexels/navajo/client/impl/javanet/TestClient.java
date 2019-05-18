package com.dexels.navajo.client.impl.javanet;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.config.runtime.TestConfig;
import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class TestClient {

	
	private static final Logger logger = LoggerFactory.getLogger(TestClient.class);

	public TestClient() {
	}

	@Test (timeout=10000)
	public void testClient() throws ClientException {
		NavajoClient cl = new JavaNetNavajoClientImpl();
		cl.setAllowCompression(true);
		cl.setForceGzip(true);
		cl.setServerUrls(new String[] {TestConfig.NAVAJO_TEST_SERVER.getValue()});
		cl.setUsername(TestConfig.NAVAJO_TEST_USER.getValue());
		cl.setPassword(TestConfig.NAVAJO_TEST_PASS.getValue());
		cl.useBasicAuthentication(true);
		Navajo nc = NavajoFactory.getInstance().createNavajo();
		Navajo result = cl.doSimpleSend(nc, "single");
		Assert.assertTrue(result.getErrorDescription()==null);
	}
	
	@Test (timeout=20000)
	public void testClientBig() throws ClientException {
		NavajoClient cl = new JavaNetNavajoClientImpl();
		cl.setAllowCompression(true);
		cl.setForceGzip(true);
		cl.setServerUrls(new String[] {TestConfig.NAVAJO_TEST_SERVER.getValue()});
		cl.setUsername(TestConfig.NAVAJO_TEST_USER.getValue());
		cl.setPassword(TestConfig.NAVAJO_TEST_PASS.getValue());
		cl.useBasicAuthentication(true);
		Navajo nc = NavajoFactory.getInstance().createNavajo();
		Navajo result = cl.doSimpleSend(nc, "club/InitUpdateClub");
		result.getMessage("Club").getProperty("ClubIdentifier").setAnyValue("BBFX31R");
		cl.doSimpleSend(result, "club/ProcessQueryClub");
	}
	
	@Test (timeout=20000)
	public void testDirect() throws IOException {
		Map<String,String> headers = new HashMap<>();
		headers.put("X-Navajo-Username", TestConfig.NAVAJO_TEST_USER.getValue());
		headers.put("X-Navajo-Password", TestConfig.NAVAJO_TEST_PASS.getValue());
		headers.put("X-Navajo-Service", "single");
		headers.put("Accept-Encoding", "jzlib");

		String url = TestConfig.NAVAJO_TEST_SERVER.getValue();
		Navajo nc = NavajoFactory.getInstance().createNavajo();
		StringWriter sw = new StringWriter();
		nc.write(sw);
		byte[] res = sendPOST(url, sw.toString().getBytes(), headers);
		logger.info("RESULT: {}",res);
	}
	private static byte[] sendPOST(String url, byte[] data,Map<String,String> headers) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");

		for (Entry<String, String> e : headers.entrySet()) {
			con.setRequestProperty(e.getKey(), e.getValue());
		}
		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(data);
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		logger.info("POST Response Code :: {}", responseCode);
		String encoding = con.getHeaderField("Content-Encoding");
		if(encoding==null) {
			encoding = "";
		}
		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			switch(encoding) {
			case "deflate":
			case "jzlib":
				copyResource(baos,new InflaterInputStream(con.getInputStream()));
				break;
			case "gzip":
				copyResource(baos,new GZIPInputStream(con.getInputStream()));
				break;
			default:
				copyResource(baos,con.getInputStream());
			}
			
			return baos.toByteArray();
		} else {
			logger.info("POST request not worked");
			return null;
		} 
	}

	private static final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read = -1;
		boolean ready = false;
		while (!ready) {
			read = bin.read(buffer);
			if (read > -1) {
				bout.write(buffer, 0, read);
			}
			if (read <= -1) {
				ready = true;
			}
		}
		try {
			bin.close();
			bout.flush();
			bout.close();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}	
	
	@Test
	public void testEnv() {
	}

}
