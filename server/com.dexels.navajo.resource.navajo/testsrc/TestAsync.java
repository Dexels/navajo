import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.dexels.navajo.client.async.AsyncClient;
import com.dexels.navajo.client.async.NavajoClientResourceManager;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.resource.navajo.impl.NavajoComponentImpl;

public class TestAsync {
	
	@Test
	public void testAsyncToTestServer() throws IOException {
		NavajoComponentImpl nci = new NavajoComponentImpl();
		Map<String,String> settings = new HashMap<String, String>();
		settings.put("name", "testresource");
		settings.put("server", "http://penelope1.dexels.com:90/sportlink/test/knvb/Postman");
		settings.put("username", "user");
		settings.put("password", "<add password>");
		nci.activate(settings);
		
		NavajoClientResourceManager ncrm = new NavajoClientResourceManager();
		ncrm.activate();
		ncrm.addAsyncClient(nci);
		
		AsyncClient ac = NavajoClientResourceManager.getInstance().getAsyncClient("testresource");
		Navajo input = NavajoFactory.getInstance().createNavajo();
		Navajo result = ac.callService(input, "club/InitUpdateClub");
		result.write(System.err);
	}
}
