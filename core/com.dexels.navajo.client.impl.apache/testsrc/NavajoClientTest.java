import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.dexels.navajo.client.impl.apache.test.BasicNavajoServerTests;
import com.dexels.navajo.client.impl.apache.test.NavajoScriptingTests;



@RunWith(Suite.class)
@Suite.SuiteClasses({
	NavajoScriptingTests.class, 
	BasicNavajoServerTests.class})  
public class NavajoClientTest {



}
