/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.sharedstore;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;


public class SerializationUtilTest {

	static SharedStoreInterface ssi = null;
    @Rule
    public TemporaryFolder folder= new TemporaryFolder();
    
	@Before
	public void setUp() throws Exception {
		final File newFolder = folder.newFolder("simpleSharedStore");
		ssi = new SimpleSharedStore(newFolder.getAbsolutePath());
		SharedStoreFactory.setInstance(ssi);
	}
	
	@After
	public void cleanup() {
	    // RemoveAll not supported in SimpleSharedStore
		//SerializationUtil.removeAllNavajos();
	}
	
	@Test
	public void testNavajo() {
		
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "TestMessage");
		n.addMessage(m);
		
		String name = SerializationUtil.serializeNavajo(n, "TestNavajo");
		Assert.assertEquals("TestNavajo", name);
		Assert.assertEquals(true, SerializationUtil.existsNavajo("TestNavajo"));
		
		Navajo n2 = SerializationUtil.deserializeNavajo("TestNavajo");
		Assert.assertNotNull(n2);
		Assert.assertNotNull(n2.getMessage("TestMessage"));
		
		// Should be cleaned up after deserialization (not true anymore).
		// Assert.assertEquals(false, SerializationUtil.existsNavajo("TestNavajo"));
		
	}
	
	@Test
	public void testRemove1() {
		
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "TestMessage");
		n.addMessage(m);
		
		String name = SerializationUtil.serializeNavajo(n, "TestNavajo");
		
		Assert.assertEquals(true, SerializationUtil.existsNavajo("TestNavajo"));
		
		SerializationUtil.removeNavajo(name);
		
		Assert.assertEquals(false, SerializationUtil.existsNavajo("TestNavajo"));
	}
	
	@Test
	public void testRemove2() {
		
		SerializationUtil.removeNavajo(null);
		
	}
	
	@Test
	public void testRemove3() {
		
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message m = NavajoFactory.getInstance().createMessage(n, "TestMessage");
		n.addMessage(m);
		
		String name = SerializationUtil.serializeNavajo(n, "TestNavajo");
		Assert.assertEquals(true, SerializationUtil.existsNavajo("TestNavajo"));
		
		SerializationUtil.removeNavajo("Apenoot");
		
		Assert.assertEquals(false, SerializationUtil.existsNavajo("Apenoot"));
		
		
		SerializationUtil.removeNavajo(name);
		
		Assert.assertEquals(false, SerializationUtil.existsNavajo("TestNavajo"));
		
	}
	
}
