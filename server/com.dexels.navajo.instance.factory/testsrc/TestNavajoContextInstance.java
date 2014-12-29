import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.context.NavajoContextInstanceFactory;


public class TestNavajoContextInstance {

	private NavajoContextInstanceFactory context; 

	
	@Before
	public void setUp() throws Exception {
		context = new NavajoContextInstanceFactory();
	}

	@Test
	@Ignore // fails due to non-existing Repository
	public void test() throws FileNotFoundException {
		context.activate(null);
		
	}

}
