

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
import java.util.zip.InflaterInputStream;

import org.junit.Test;
import com.dexels.config.runtime.TestConfig;
import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.NavajoClient;
import com.dexels.navajo.client.impl.apache.ApacheNavajoClientImpl;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class TestClient {

	public TestClient() {
	}

	@Test 
	public void testClient() throws ClientException {
		NavajoClient cl = new ApacheNavajoClientImpl();
		cl.setAllowCompression(true);
		cl.setForceGzip(true);
		cl.setServerUrls(new String[] {TestConfig.NAVAJO_TEST_SERVER.getValue()});
		cl.setUsername(TestConfig.NAVAJO_TEST_USER.getValue());
		cl.setPassword(TestConfig.NAVAJO_TEST_PASS.getValue());
		Navajo nc = NavajoFactory.getInstance().createNavajo();
		Navajo result = cl.doSimpleSend(nc, "single");
		result.write(System.err);;
	}
	
	@Test 
	public void testClientBig() throws ClientException {
		NavajoClient cl = new ApacheNavajoClientImpl();
		cl.setAllowCompression(true);
		cl.setForceGzip(true);
		cl.setServerUrls(new String[] {TestConfig.NAVAJO_TEST_SERVER.getValue()});
		cl.setUsername(TestConfig.NAVAJO_TEST_USER.getValue());
		cl.setPassword(TestConfig.NAVAJO_TEST_PASS.getValue());
		Navajo nc = NavajoFactory.getInstance().createNavajo();
		Navajo result = cl.doSimpleSend(nc, "club/InitUpdateClub");
		result.getMessage("Club").getProperty("ClubIdentifier").setAnyValue("BBFX31R");
		Navajo result2 = cl.doSimpleSend(result, "club/ProcessQueryClub");
		result2.write(System.err);;
	}
	
	@Test 
	public void testClientBigDirect() throws IOException {
		NavajoClient cl = new ApacheNavajoClientImpl();
		cl.setAllowCompression(true);
//		cl.setForceGzip(true);
		cl.setServerUrls(new String[] {TestConfig.NAVAJO_TEST_SERVER.getValue()});
		cl.setUsername(TestConfig.NAVAJO_TEST_USER.getValue());
		cl.setPassword(TestConfig.NAVAJO_TEST_PASS.getValue());
		Navajo nc = NavajoFactory.getInstance().createNavajo();
		Navajo result = cl.doSimpleSend(nc, "club/InitUpdateClub");
		result.write(System.err);
		result.getMessage("Club").getProperty("ClubIdentifier").setAnyValue("BBFX31R");

		Map<String,String> headers = new HashMap<String, String>();
		headers.put("X-Navajo-Username", TestConfig.NAVAJO_TEST_USER.getValue());
		headers.put("X-Navajo-Password", TestConfig.NAVAJO_TEST_PASS.getValue());
		headers.put("X-Navajo-Service", "single");
		headers.put("Accept-Encoding", "jzlib");

		String url = "http://localhost:9090/stream/KNVB";
//		Navajo nc = NavajoFactory.getInstance().createNavajo();
		StringWriter sw = new StringWriter();
		result.write(sw);
		byte[] res = sendPOST(url, sw.toString().getBytes(), headers);
		System.err.println("RESULT: "+new String(res));

	}

	@Test 
	public void testDirect() throws IOException {
		Map<String,String> headers = new HashMap<String, String>();
		headers.put("X-Navajo-Username", TestConfig.NAVAJO_TEST_USER.getValue());
		headers.put("X-Navajo-Password", TestConfig.NAVAJO_TEST_PASS.getValue());
		headers.put("X-Navajo-Service", "single");
		headers.put("Accept-Encoding", "jzlib");

		String url = TestConfig.NAVAJO_TEST_SERVER.getValue();
		Navajo nc = NavajoFactory.getInstance().createNavajo();
		StringWriter sw = new StringWriter();
		nc.write(sw);
		byte[] res = sendPOST(url, sw.toString().getBytes(), headers);
		System.err.println("RESULT: "+new String(res));
	}
	private static byte[] sendPOST(String url, byte[] data,Map<String,String> headers) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
//		con.setRequestProperty("User-Agent", USER_AGENT);

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
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			copyResource(baos,con.getInputStream());
			copyResource(baos,new InflaterInputStream(con.getInputStream()));
			
			return baos.toByteArray();
//			return con.getInputStream();
		} else {
			System.out.println("POST request not worked");
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

		}
	}	
	
	@Test
	public void testEnv() {
		System.err.println("Environments: "+System.getenv());
	}

}
