package com.dexels.navajo.sharedstore;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringBufferInputStream;
import java.io.StringReader;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.InputStreamReader;
import com.dexels.navajo.server.TestNavajoConfig;

class SerializableObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7806593130574518792L;
	
	private String field;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	private void writeObject(ObjectOutputStream aStream) throws IOException {
    	aStream.defaultWriteObject();
    	System.err.println("IN WRITEOBJECT(): " + this.hashCode() + ", field " + field);
    }
	
}

public class SharedStoreInterfaceTest {

	private SharedStoreInterface si;
	boolean locked = false;
	boolean threadAssertFailed = false;
	SharedStoreLock myssl;
	int locks = 0;
	
	@Before
	public void setUp() throws Exception {
		DispatcherFactory df = new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
		si = SharedStoreFactory.getInstance();
	}

	private void deleteFiles(File f) {
		if ( f.isFile() ) {
			f.delete();
		}
		if ( f.isDirectory() ) {
			File [] children = f.listFiles();
			for (int i = 0; i < children.length; i++) {
				deleteFiles(children[i]);
			}
			f.delete();
		}
	}
	
	@After
	public void tearDown() throws Exception {
		File f = new File("/tmp/sharedstore");
		deleteFiles(f);
		SharedStoreFactory.clear();
	}

	public static void main(String [] args) throws Exception {
		DispatcherFactory df = new DispatcherFactory(new Dispatcher(new TestNavajoConfig()));
		df.getInstance().setUseAuthorisation(false);
		SharedStoreInterfaceTest t = new SharedStoreInterfaceTest();
		t.setUp();
		t.testGetObjects();
		t.tearDown();
	}
	
	@Test
	public void testStoreWithoutLock() throws Exception {
		si.store("myparent", "mystoredobject", new SerializableObject(), false, false);
		Assert.assertTrue(new File("/tmp/sharedstore/myparent/mystoredobject").exists());
	}
	
	@Test
	public void testStoreWithLock() throws Exception {
		final SerializableObject o1 = new SerializableObject();
		o1.setField("FIRST");
		final SerializableObject o2 = new SerializableObject();
		o2.setField("SECOND");
		final SerializableObject o3 = new SerializableObject();
		o3.setField("THIRD");
		locks = 0;
		Thread t1 = new Thread() {
			public void run() {
				try {
					si.store("myparent", "mystoredobject", o1, false, true);
					locks++;
				} catch (SharedStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t1.start();
		Thread t2 = new Thread() {
			public void run() {
				try {
					si.store("myparent", "mystoredobject", o2, false, true);
					locks++;
				} catch (SharedStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t2.start();
		Thread t3 = new Thread() {
			public void run() {
				try {
					si.store("myparent", "mystoredobject", o3, false, true);
					locks++;
				} catch (SharedStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t3.start();
		
		t1.join();
		t2.join();
		t3.join();
		
		SerializableObject o4 = (SerializableObject) si.get("myparent", "mystoredobject");
		Assert.assertEquals(3, locks);
		Assert.assertNotNull(o4.getField());
	}

	/**
	 * TODO: FIGURE OUT HOW TO FORCE OBJECT SERIALIZATION FAILURE!!!!!!
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStoreWithLockFailure() throws Exception {
		final SerializableObject o1 = new SerializableObject();
		o1.setField("FIRST");
		final SerializableObject o2 = new SerializableObject();
		o2.setField("SECOND");
		final SerializableObject o3 = new SerializableObject();
		o3.setField("THIRD");
		locks = 0;
		Thread t1 = new Thread() {
			public void run() {
				try {
					si.store("myparent", "mystoredobject", o1, false, false);
					locks++;
				} catch (SharedStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t1.start();
		Thread t2 = new Thread() {
			public void run() {
				try {
					si.store("myparent", "mystoredobject", o2, false, false);
					locks++;
				} catch (SharedStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t2.start();
		Thread t3 = new Thread() {
			public void run() {
				try {
					si.store("myparent", "mystoredobject", o3, false, false);
					locks++;
				} catch (SharedStoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t3.start();
		
		t1.join();
		t2.join();
		t3.join();
		
		SerializableObject o4 = (SerializableObject) si.get("myparent", "mystoredobject");
		System.err.println("locks = " + locks + ", o4.getField() =" + o4.getField());
		Assert.assertEquals(3, locks);
		Assert.assertNotNull(o4.getField());
	}
	
	@Test
	public void testRemove() throws Exception{
		si.store("myparent", "mystoredobject", new SerializableObject(), false, false);
		Assert.assertTrue(new File("/tmp/sharedstore/myparent/mystoredobject").exists());
		si.remove("myparent", "mystoredobject");
		Assert.assertFalse(new File("/tmp/sharedstore/myparent/mystoredobject").exists());
	}

	@Test
	public void testRemoveAll() throws Exception {
		si.store("myparent", "mystoredobject1", new SerializableObject(), false, false);
		si.store("myparent", "mystoredobject2", new SerializableObject(), false, false);
		si.store("myparent", "mystoredobject3", new SerializableObject(), false, false);
		Assert.assertTrue(new File("/tmp/sharedstore/myparent/mystoredobject1").exists());
		Assert.assertTrue(new File("/tmp/sharedstore/myparent/mystoredobject2").exists());
		Assert.assertTrue(new File("/tmp/sharedstore/myparent/mystoredobject3").exists());
		si.removeAll("myparent");
		Assert.assertFalse(new File("/tmp/sharedstore/myparent/mystoredobject1").exists());
		Assert.assertFalse(new File("/tmp/sharedstore/myparent/mystoredobject2").exists());
		Assert.assertFalse(new File("/tmp/sharedstore/myparent/mystoredobject3").exists());
	}

	
	@Test
	public void testStoreText() throws Exception {
		si.storeText("myparent", "mytextobject", "text", false, false);
		BufferedReader r = new BufferedReader(new FileReader("/tmp/sharedstore/myparent/mytextobject"));
		String l = r.readLine();
		Assert.assertEquals("text", l);
	}

	@Test
	public void testCreateParent() throws Exception {
		si.createParent("myparent");
		Assert.assertTrue(new File("/tmp/sharedstore/myparent").exists());
	}

	@Test
	public void testLockStringStringStringIntBoolean() throws Exception {
		SharedStoreLock ssl = si.lock("myparent", "mylockfile", "owner1", SharedFileStore.READ_WRITE_LOCK, false);
		Assert.assertTrue(new File("/tmp/sharedstore/owner1_myparent_mylockfile.lock").exists());
	}
	
	@Test
	public void testLockStringStringStringIntBoolean2() throws Exception {
		SharedStoreLock ssl = si.lock("myparent", "mylockfile", "owner1", SharedFileStore.READ_WRITE_LOCK, false);
		Assert.assertNotNull(ssl);
		SharedStoreLock ssl2 = si.lock("myparent", "mylockfile", "owner2", SharedFileStore.READ_WRITE_LOCK, false);
		Assert.assertNull(ssl2);
		Assert.assertTrue(new File("/tmp/sharedstore/owner1_myparent_mylockfile.lock").exists());
	}
	
	@Test
	public void testLockStringStringStringIntBooleanWithMultipleThreads() throws Exception {
		
		locked = false;
		locks = 0;
		
		Thread t1 = new Thread() {
			public void run() {
				SharedStoreLock ssl = si.lock("myparent", "mylockfile", "owner1", SharedFileStore.READ_WRITE_LOCK, false);
				if (ssl != null) {
					locked = true;
					myssl = ssl;
					locks++;
				}
			}
		};
		
		Thread t2 = new Thread() {
			public void run() {
				SharedStoreLock ssl = si.lock("myparent", "mylockfile", "owner2", SharedFileStore.READ_WRITE_LOCK, false);
				if (ssl != null) {
					locked = true;
					myssl = ssl;
					locks++;
				}
			}
		};
		
		Thread t3 = new Thread() {
			public void run() {
				SharedStoreLock ssl = si.lock("myparent", "mylockfile", "owner3", SharedFileStore.READ_WRITE_LOCK, false);
				if (ssl != null) {
					locked = true;
					myssl = ssl;
					locks++;
				}
			}
		};
		t1.start();t2.start();t3.start();
		t1.join();t2.join();t3.join();
		
		System.err.println(myssl.toString());
		Assert.assertEquals(1, locks);
		Assert.assertTrue(locked);
	}
	
	@Test
	public void testLockStringStringStringIntBooleanWithMultipleThreadsAndWaits() throws Exception {
		
		locked = false;
		locks = 0;
		
		Thread t1 = new Thread() {
			public void run() {
				SharedStoreLock ssl = si.lock("myparent", "mylockfile", "owner1", SharedFileStore.READ_WRITE_LOCK, true);
				if (ssl != null) {
					locked = true;
					Assert.assertTrue(new File("/tmp/sharedstore/owner1_myparent_mylockfile.lock").exists());
					Assert.assertFalse(new File("/tmp/sharedstore/owner2_myparent_mylockfile.lock").exists());
					Assert.assertFalse(new File("/tmp/sharedstore/owner3_myparent_mylockfile.lock").exists());
					myssl = ssl;
					locks++;
					// wait a bit.
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					si.release(ssl);
					System.err.println("RELEASED: " + ssl);
				}
			}
		};
		
		Thread t2 = new Thread() {
			public void run() {
				SharedStoreLock ssl = si.lock("myparent", "mylockfile", "owner2", SharedFileStore.READ_WRITE_LOCK, true);
				if (ssl != null) {
					locked = true;
					Assert.assertFalse(new File("/tmp/sharedstore/owner1_myparent_mylockfile.lock").exists());
					Assert.assertTrue(new File("/tmp/sharedstore/owner2_myparent_mylockfile.lock").exists());
					Assert.assertFalse(new File("/tmp/sharedstore/owner3_myparent_mylockfile.lock").exists());
					myssl = ssl;
					locks++;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					si.release(ssl);
					System.err.println("RELEASED: " + ssl);
				}
			}
		};
		
		Thread t3 = new Thread() {
			public void run() {
				SharedStoreLock ssl = si.lock("myparent", "mylockfile", "owner3", SharedFileStore.READ_WRITE_LOCK, true);
				if (ssl != null) {
					Assert.assertFalse(new File("/tmp/sharedstore/owner1_myparent_mylockfile.lock").exists());
					Assert.assertFalse(new File("/tmp/sharedstore/owner2_myparent_mylockfile.lock").exists());
					Assert.assertTrue(new File("/tmp/sharedstore/owner3_myparent_mylockfile.lock").exists());
					locked = true;
					myssl = ssl;
					locks++;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					si.release(ssl);
					System.err.println("RELEASED: " + ssl);
				}
			}
		};
		t1.start();t2.start();t3.start();
		t1.join(2000);t2.join(2000);t3.join(2000);
		
		System.err.println(myssl.toString());
		Assert.assertEquals(3, locks);
		Assert.assertTrue(locked);
	}

	@Test
	public void testLockStringStringIntBoolean() {
		String instance = DispatcherFactory.getInstance().getNavajoConfig().getInstanceName();
		System.err.println("instance = " + instance);
		SharedStoreLock ssl = si.lock("myparent", "mylock", SharedFileStore.READ_WRITE_LOCK, false);
		// instance name is used as owner:
		Assert.assertNotNull(ssl);
		Assert.assertTrue(ssl.toString().indexOf(instance) != -1);
	}

	@Test
	public void testRelease() {
		SharedStoreLock ssl = si.lock("myparent", "mylockfile", "owner", SharedFileStore.READ_WRITE_LOCK, false);
		Assert.assertTrue(new File("/tmp/sharedstore/owner_myparent_mylockfile.lock").exists());
		si.release(ssl);
		Assert.assertFalse(new File("/tmp/sharedstore/owner_myparent_mylockfile.lock").exists());
	}

	@Test
	public void testGetLock() throws Exception {
		si.lock("myparent", "mylockfile", "owner", SharedFileStore.READ_WRITE_LOCK, false);
		SharedStoreLock ssl =  si.getLock("myparent", "mylockfile", "owner");
		Assert.assertNotNull(ssl);
		
		SharedStoreLock ssl2 =  si.getLock("myparent", "mylockfile2", "owner");
		Assert.assertNull(ssl2);
		
		SharedStoreLock ssl3 =  si.getLock("myparent", "mylockfile", "owner2");
		Assert.assertNull(ssl3);
	}
	
	@Test
	public void testLastModified() throws Exception {
		si.store("myparent", "myobject", new SerializableObject(), false, false);
		long l1 = si.lastModified("myparent", "myobject");
		Thread.sleep(1000);
		si.store("myparent", "myobject", new SerializableObject(), false, false);
		long l2 = si.lastModified("myparent", "myobject");
		Assert.assertTrue(l2 > l1);
		
	}

	@Test
	public void testSetLastModified() throws Exception {
		si.store("myparent", "myobject", new SerializableObject(), false, false);
		si.setLastModified("myparent", "myobject", Long.parseLong("1222180873000"));
		long l1 = si.lastModified("myparent", "myobject");
		Assert.assertEquals(Long.parseLong("1222180873000"), l1); 
	}
	
	@Test
	public void testSetLastModifiedUnknownObject() throws Exception {
		boolean exception = false;
		try {
			si.setLastModified("myparet", "myobject", Long.parseLong("1222180873000"));
		} catch (Exception e) {
			exception = true;
		}
		Assert.assertTrue(exception);
	}

	@Test
	public void testExists() throws Exception {
		si.store("myparent", "myobject", new SerializableObject(), false, false);
		Assert.assertTrue(si.exists("myparent", "myobject"));
		Assert.assertFalse(si.exists("myparet", "myobject"));
		Assert.assertFalse(si.exists("myparent", "myoect"));
	}

	@Test
	public void testGet() throws Exception {
		si.store("myparent", "myobject", new SerializableObject(), false, false);
		Object o = si.get("myparent", "myobject");
		Assert.assertNotNull(o);
		Assert.assertTrue(o instanceof SerializableObject);
		
		// Unknown object.
		boolean exception = false;
		Object o2 = null;
		try {
			o2 = si.get("myparent", "myobct");
		} catch (Exception e) {
			exception = true;
		}
		Assert.assertTrue(exception);
		Assert.assertNull(o2);
		// Invalid object.
		si.storeText("myparent", "mytext", "some text", false, false);
		exception = false;
		try {
			Object o4 = si.get("myparent", "mytext");
		} catch (Exception e) {
			exception = true;
		}
		Assert.assertTrue(exception);

	}

	@Test
	public void testGetStream() throws Exception {
		si.store("myparent", "myobject", new SerializableObject(), false, false);
		ObjectInputStream ois = new ObjectInputStream(si.getStream("myparent", "myobject"));
		Object o = ois.readObject();
		Assert.assertNotNull(o);
		Assert.assertTrue(o instanceof SerializableObject);
		// Test text stream.
		si.storeText("myparent", "mytext", "some text", false, false);
		java.io.BufferedReader is = new BufferedReader( new java.io.InputStreamReader( si.getStream("myparent", "mytext") ) );
		Assert.assertEquals("some text", is.readLine());
		// Unknown object.
		boolean exception = false;
		InputStream o2 = null;
		try {
			o2 = si.getStream("myparent", "myobct");
		} catch (Exception e) {
			exception = true;
		}
		Assert.assertTrue(exception);
		Assert.assertNull(o2);
		
		
	}

	@Test
	public void testGetOutputStream() throws Exception {
		OutputStream os = si.getOutputStream("myparent", "myobject", false);
		SerializableObject o = new SerializableObject();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(o);
		oos.close();
		// Assert that object was created.
		Object o2 = si.get("myparent", "myobject");
		Assert.assertNotNull(o2);
		Assert.assertTrue(o2 instanceof SerializableObject);
		
		// Write plain text.
		OutputStreamWriter os2 = new OutputStreamWriter( si.getOutputStream("myparent", "mytext", false) );
		String s = "some text";
		os2.write(s);
		os2.close();
		java.io.BufferedReader is = new BufferedReader( new java.io.InputStreamReader( si.getStream("myparent", "mytext") ) );
		Assert.assertNotNull(is);
		Assert.assertEquals(s, is.readLine());
	}

	@Test
	public void testGetObjects() throws Exception {
		SerializableObject s1 = new SerializableObject();s1.setField("ONE");
		SerializableObject s2 = new SerializableObject();s2.setField("TWO");
		SerializableObject s3 = new SerializableObject();s3.setField("THREE");
		si.store("myparent", "myobject1", s1, false, false);
		si.store("myparent", "myobject2", s2, false, false);
		si.store("myparent", "myobject3", s3, false, false);
		String [] obs = si.getObjects("myparent");
		Assert.assertNotNull(obs);
		Assert.assertEquals(3, obs.length);
		int count = 0;
		System.err.println("count: " + obs.length);
		for (int i = 0; i < obs.length; i++) {
			System.err.println("obs[" + i + "]=" + obs[i]);
			SerializableObject so = (SerializableObject) si.get("myparent", obs[i]);
			Assert.assertNotNull(so);
			if ( so.getField().equals("ONE") ) {
				count++;
			}
			if ( so.getField().equals("TWO") ) {
				count++;
			}
			if (so.getField().equals("THREE") ) {
				count++;
			}
		}
		Assert.assertEquals(3, count);
		// No objects..
		obs = si.getObjects("myparet");
		Assert.assertNotNull(obs);
		Assert.assertEquals(0, obs.length);
	}

}
