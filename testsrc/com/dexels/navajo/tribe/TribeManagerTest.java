package com.dexels.navajo.tribe;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.test.TestDispatcher;
import com.dexels.navajo.server.test.TestNavajoConfig;

import junit.framework.TestCase;

public class TribeManagerTest extends TestCase {

	private final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
		bout.close();
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(new TestNavajoConfig("instance1","mygroup")));
		df.getInstance().setUseAuthorisation(false);	
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public static void main(String [] args) throws Exception {
		TribeManagerTest tmt = new TribeManagerTest();
		tmt.setUp();
		InputStream is = tmt.getClass().getClassLoader().getResourceAsStream("jgroups_1.xml");
		FileOutputStream os = new FileOutputStream("/tmp/jgroups_1.xml");
		tmt.copyResource(os,is);
		TribeManager tm = new TribeManager("jgroups_1.xml");
		Thread.sleep(2000);
		System.err.println("I am chief: " + tm.getIsChief());
	}
	
	public void testGetChief() {
		fail("Not yet implemented");
	}

	public void testBroadcastSmokeSignal() {
		fail("Not yet implemented");
	}

	public void testMulticast() {
		fail("Not yet implemented");
	}

	public void testGetStatistics() {
		fail("Not yet implemented");
	}

	public void testAskSomebody() {
		fail("Not yet implemented");
	}

	public void testAskChief() {
		fail("Not yet implemented");
	}

	public void testGetChiefName() {
		fail("Not yet implemented");
	}

	public void testGetIsChief() {
		fail("Not yet implemented");
	}

	public void testTerminate() {
		fail("Not yet implemented");
	}

	public void testGetClusterState() {
		fail("Not yet implemented");
	}

	public void testGetAllMembers() {
		fail("Not yet implemented");
	}

	public void testBroadcastNavajo() {
		fail("Not yet implemented");
	}

	public void testForwardNavajo() {
		fail("Not yet implemented");
	}

	public void testForwardNavajoObject() {
		fail("Not yet implemented");
	}

	public void testGetMyName() {
		fail("Not yet implemented");
	}

	public void testGetMyMembership() {
		fail("Not yet implemented");
	}

	public void testAskAnybody() {
		fail("Not yet implemented");
	}

}
