import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.context.NavajoContextInstanceFactory;


public class TestNavajoContextInstance {

	private NavajoContextInstanceFactory context; 

	
	@Before
	public void setUp() throws Exception {
		context = new NavajoContextInstanceFactory();
	}

	@Test
	public void test() {
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("installationPath", "/Users/frank/git/sportlink.restructure");
		context.activate(properties);
		
	}

}
