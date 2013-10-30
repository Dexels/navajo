package com.dexels.navajo.sharedstore;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MySerializableObject implements Serializable {

	
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
		// System.err.println("IN WRITEOBJECT(): " + this.hashCode() +
		// ", field " + field);
	}

}
public class SharedMemoryStoreTest {

	
	private final static Logger logger = LoggerFactory
			.getLogger(SharedMemoryStoreTest.class);
	
	private SharedStoreInterface si;
	boolean locked = false;
	boolean threadAssertFailed = false;
	SharedStoreLock myssl;
	int locks = 0;

	@Before
	public void setUp() throws Exception {
		si = new SharedMemoryStore(new ConcurrentHashMap<String, SharedStoreEntry>(), new DefaultSharedStoreEntryFactoryImplementation());
	}


	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testStoreWithoutLock() throws Exception {
		si.store("myparent", "mystoredobject", new MySerializableObject(), false, false);
		Assert.assertNotNull(si.get("myparent", "mystoredobject"));
	}

	
	@Test
	public void testRemove() throws Exception {
		si.store("myparent", "mystoredobject", new MySerializableObject(), false,
				false);
		Assert.assertNotNull(si.get("myparent", "mystoredobject"));
		si.remove("myparent", "mystoredobject");
		//Assert.assertNull(si.get("myparent", "mystoredobject"));
	}

	@Test
	public void testRemoveAll() throws Exception {
		si.store("myparent", "mystoredobject1", new MySerializableObject(),
				false, false);
		si.store("myparent", "mystoredobject2", new MySerializableObject(),
				false, false);
		si.store("myparent", "mystoredobject3", new MySerializableObject(),
				false, false);
		Assert.assertNotNull(si.get("myparent", "mystoredobject1"));
		Assert.assertNotNull(si.get("myparent", "mystoredobject2"));
		Assert.assertNotNull(si.get("myparent", "mystoredobject3"));
		si.removeAll("myparent");
		Assert.assertEquals(0, si.getObjects("myparent").length);
	}

	@Test
	public void testRemoveAllRecursive() throws Exception {
		si.store("myparent/child1", "mystoredobject1",
				new MySerializableObject(), false, false);
		si.store("myparent/child2", "mystoredobject2",
				new MySerializableObject(), false, false);
		si.store("myparent/child3", "mystoredobject3",
				new MySerializableObject(), false, false);
		Assert.assertNotNull(si.get("myparent/child1", "mystoredobject1"));
		Assert.assertNotNull(si.get("myparent/child2", "mystoredobject2"));
		Assert.assertNotNull(si.get("myparent/child3", "mystoredobject3"));
		Assert.assertEquals(1, si.getObjects("myparent/child1").length);
		Assert.assertEquals(1, si.getObjects("myparent/child2").length);
		Assert.assertEquals(1, si.getObjects("myparent/child3").length);
		si.removeAll("myparent");
		Assert.assertEquals(0, si.getObjects("myparent/child1").length);
		Assert.assertEquals(0, si.getObjects("myparent/child2").length);
		Assert.assertEquals(0, si.getObjects("myparent/child3").length);
	}


	@Test
	public void testCreateParent() throws Exception {
		si.createParent("myparent");
		Assert.assertNotNull(si.getObjects("myparent"));
	}
	

	@Test
	public void testLastModified() throws Exception {
		si.store("myparent", "myobject", new MySerializableObject(), false, false);
		long l1 = si.lastModified("myparent", "myobject");
		Thread.sleep(1000);
		si.store("myparent", "myobject", new MySerializableObject(), false, false);
		long l2 = si.lastModified("myparent", "myobject");
		Assert.assertTrue(l2 > l1);

	}

	@Test
	public void testSetLastModified() throws Exception {
		si.store("myparent", "myobject", new MySerializableObject(), false, false);
		si.setLastModified("myparent", "myobject",
				Long.parseLong("1222180873000"));
		long l1 = si.lastModified("myparent", "myobject");
		Assert.assertEquals(Long.parseLong("1222180873000"), l1);
	}

	@Test
	public void testExists() throws Exception {
		si.store("myparent", "myobject", new MySerializableObject(), false, false);
		Assert.assertTrue(si.exists("myparent", "myobject"));
		Assert.assertFalse(si.exists("myparet", "myobject"));
		Assert.assertFalse(si.exists("myparent", "myoect"));
	}

	@Test
	public void testGet() throws Exception {
		si.store("myparent", "myobject", new MySerializableObject(), false, false);
		Object o = si.get("myparent", "myobject");
		Assert.assertNotNull(o);
		Assert.assertTrue(o instanceof MySerializableObject);

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

	}

	@Test
	public void testGetParentObjects() throws Exception {

		MySerializableObject s1 = new MySerializableObject();
		s1.setField("ONE");
		MySerializableObject s2 = new MySerializableObject();
		s2.setField("TWO");
		MySerializableObject s3 = new MySerializableObject();
		s3.setField("THREE");
		si.store("myparent/child1", "myobject1", s1, false, false);
		si.store("myparent/child2", "myobject2", s2, false, false);
		si.store("myparent/child3", "myobject3", s3, false, false);
		String[] obs = si.getObjects("myparent");
		Assert.assertEquals(0, obs.length);

		obs = si.getObjects("myparent/child1");
		Assert.assertEquals(1, obs.length);

		obs = si.getObjects("myparent/child2");
		Assert.assertEquals(1, obs.length);

		obs = si.getObjects("myparent/child3");
		Assert.assertEquals(1, obs.length);

	}

	@Test
	public void testGetObjects() throws Exception {
		MySerializableObject s1 = new MySerializableObject();
		s1.setField("ONE");
		MySerializableObject s2 = new MySerializableObject();
		s2.setField("TWO");
		MySerializableObject s3 = new MySerializableObject();
		s3.setField("THREE");
		si.store("myparent", "myobject1", s1, false, false);
		si.store("myparent", "myobject2", s2, false, false);
		si.store("myparent", "myobject3", s3, false, false);
		String[] obs = si.getObjects("myparent");
		Assert.assertNotNull(obs);
		Assert.assertEquals(3, obs.length);
		int count = 0;
		System.err.println("count: " + obs.length);
		for (int i = 0; i < obs.length; i++) {
			System.err.println("obs[" + i + "]=" + obs[i]);
			MySerializableObject so = (MySerializableObject) si.get("myparent",
					obs[i]);
			Assert.assertNotNull(so);
			if (so.getField().equals("ONE")) {
				count++;
			}
			if (so.getField().equals("TWO")) {
				count++;
			}
			if (so.getField().equals("THREE")) {
				count++;
			}
		}
		Assert.assertEquals(3, count);
		// No objects..
		obs = si.getObjects("myparet");
		Assert.assertNotNull(obs);
		Assert.assertEquals(0, obs.length);
	}

	@Test
	public void testStoreWithThreads() throws Exception {

		si.removeAll("myparent");
		
		int MAXTHREADS = 5000;
		final MySerializableObject[] objects = new MySerializableObject[MAXTHREADS];
		for (int i = 0; i < MAXTHREADS; i++) {
			objects[i] = new MySerializableObject();
			objects[i].setField("field-" + i);
		}

		// Define threads.
		Thread[] threads = new Thread[MAXTHREADS];
		for (int i = 0; i < MAXTHREADS; i++) {
			final int index = i;
			threads[i] = new Thread() {
				@Override
				public void run() {
					try {
						si.store("myparent", "mystoredobject" + index, objects[index], false, false);
					} catch (SharedStoreException e) {
						logger.error("Error: ", e);
					}
				}
			};
		}

		// Start threads.
		for (int i = 0; i < MAXTHREADS; i++) {
			threads[i].start();
		}

		// Join threads.
		for (int i = 0; i < MAXTHREADS; i++) {
			threads[i].join();
		}

		System.err.println("size: " + ((SharedMemoryStore) si).getSize());
		// Asserts
		Assert.assertEquals(MAXTHREADS, si.getObjects("myparent").length);
		
		for (int i = 0; i < MAXTHREADS; i++) {
			MySerializableObject o = (MySerializableObject) si.get("myparent", "mystoredobject" + i);
			Assert.assertNotNull(o.getField());
			Assert.assertEquals("field-" + i, o.getField());
		}
		
		for (int i = 0; i < MAXTHREADS; i++) {
			final int index = i;
			threads[i] = new Thread() {
				@Override
				public void run() {
					si.remove("myparent", "mystoredobject" + index);
				}
			};
		}

		// Start threads.
		for (int i = 0; i < MAXTHREADS; i++) {
			threads[i].start();
		}

		// Join threads.
		for (int i = 0; i < MAXTHREADS; i++) {
			threads[i].join();
		}
		
		Assert.assertEquals(0, si.getObjects("myparent").length);

	}
	
	@Test
	public void testPerformance() throws Exception {
		int count = 10000;
		long start = System.currentTimeMillis();
		for ( int i = 0; i < count; i++ ) {
			si.store("PARENT", "Key"+i, Integer.valueOf(i), false, false);
		}
		long end = System.currentTimeMillis();
		//Assert.assertEquals(count, si.getSize());
		System.err.println("STORED " + count + " values in " +  + (double) count / ( (end - start) ) + " millis");
		
		start = System.currentTimeMillis();
		for ( int i = 0; i < count; i++ ) {
			Serializable s = si.get("PARENT", "Key"+i);
		}
		end = System.currentTimeMillis();
		System.err.println("GOT " + count + " values in " +  + (double) count / ( (end - start) ) + " millis");
		//Assert.assertEquals(true, ( end - start ) < 200 );
	}
	
	public static void main(String [] args) throws Exception {
		
		SharedMemoryStoreTest t = new SharedMemoryStoreTest();
		t.setUp();
		t.testStoreWithThreads();
	}
}
