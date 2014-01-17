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
		context.activate();
		
	}

}
