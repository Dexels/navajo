package com.dexels.navajo.server;

import com.dexels.navajo.document.Navajo;

import junit.framework.Assert;
import junit.framework.TestCase;

public class NavajoScriptingTests extends BasicTest {

	public void testPropertyTypes() throws Exception {
		
		Navajo n = myClient.doSimpleSend("tests/InitProperties");
		Assert.assertNotNull(n.getMessage("TestProperties"));
		
	}

}
