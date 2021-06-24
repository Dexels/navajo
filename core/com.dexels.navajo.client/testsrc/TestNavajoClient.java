/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.dexels.navajo.client.BasicNavajoServerTests;
import com.dexels.navajo.client.NavajoScriptingTests;



@RunWith(Suite.class)
@Suite.SuiteClasses({
	NavajoScriptingTests.class, 
	BasicNavajoServerTests.class})  
public class TestNavajoClient {



}
