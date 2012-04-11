import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.dexels.navajo.client.BasicNavajoServerTests;
import com.dexels.navajo.client.NavajoScriptingTests;



@RunWith(Suite.class)
@Suite.SuiteClasses({
	NavajoScriptingTests.class, 
	BasicNavajoServerTests.class})  
public class NavajoClient {



}
