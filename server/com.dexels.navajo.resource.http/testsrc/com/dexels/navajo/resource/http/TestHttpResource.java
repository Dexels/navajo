package com.dexels.navajo.resource.http;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.client.stream.ReactiveReply;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.repository.api.AppStoreOperation;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.resource.http.adapter.BinaryStoreAdapter;
import com.dexels.navajo.resource.http.impl.ResourceComponent;
import com.dexels.navajo.runtime.config.TestConfig;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TestHttpResource {

	private  HttpResourceFactory factory;
	private Access access;
	private ResourceComponent component;

	@Before
	public  void setUp() throws Exception {
		
		this.access = new Access();
		access.setTenant("MYTENANT");
		
		factory = new HttpResourceFactory();
		component = new ResourceComponent();
		RepositoryInstance instance = createStubInstance("test");
		component.setRepositoryInstance(instance);
		Map<String,Object> settings = new HashMap<String, Object>();
		String url = TestConfig.HTTP_TEST_URL.getValue();
		Assert.assertNotNull(url);
		settings.put("url", url);
		settings.put("name", "binstore");
		settings.put("authorization", TestConfig.HTTP_TEST_TOKEN.getValue());
		settings.put("secret", TestConfig.HTTP_TEST_SECRET.getValue());
		settings.put("expire", "120");
		component.activate(settings);
		factory.addHttpResource(component, settings);
		factory.activate();

		
	}

	@After
	public void tearDown() throws Exception {
//		HttpResourceFactory.getInstance().deactivate();	
		factory.deactivate();
//		HttpResourceFactory.setInstance(null);
	}

	@Test @Ignore
	public void testHead() throws IOException, MappableException, UserException {
		BinaryStoreAdapter bsa = new BinaryStoreAdapter();
		bsa.load(access);
		Binary b = createBinary();
		b.setMimeType("text/plain");
		boolean exists = bsa.headBinary(b.getHexDigest(), "binstore", "junit");
		Assert.assertFalse(exists);
	}
	
	@Test @Ignore
	public void testDelete() throws IOException, MappableException, UserException {
		BinaryStoreAdapter bsa = new BinaryStoreAdapter();
		bsa.load(access);
		Binary b = createBinary();

		b.setMimeType("text/plain");
		ReactiveReply reply = bsa.deleteBinary(b.getHexDigest(), "binstore", "junit", false);
		Assert.assertNotSame(200, reply.status());
	}

	@Test @Ignore
	public void testPut() throws IOException, MappableException, UserException {
		BinaryStoreAdapter bsa = new BinaryStoreAdapter();
		bsa.load(access);
		Binary b = createBinary();

		bsa.storeBinary(b, "binstore", "junit", false);
	}

	
	@Test @Ignore
	public void testStoreAdapterBasics() throws IOException, MappableException, UserException, InterruptedException {
		BinaryStoreAdapter bsa = new BinaryStoreAdapter();
		bsa.load(access);
		Binary b = createBinary();

		String bb = bsa.storeBinary(b, "binstore", "junit", false);
		System.err.println("Result of put: "+bb);
		
		boolean existsNow = bsa.headBinary(b.getHexDigest(), "binstore", "junit");
		Assert.assertTrue(existsNow);
		System.err.println("exists after insert: "+existsNow);

		ReactiveReply result = bsa.deleteBinary(b.getHexDigest(), "binstore", "junit", false);
		System.err.println("Delete: "+result.status());


		boolean exists = bsa.headBinary(b.getHexDigest(), "binstore", "junit");
		Assert.assertFalse(exists);
		System.err.println("exists after delete: "+exists);

	}

	@Test @Ignore
	public void testStoreAdapterAdvanced() throws IOException, MappableException, UserException, InterruptedException {
		Binary b = createBinary();

		BinaryStoreAdapter bsa = new BinaryStoreAdapter();
		bsa.load(access);
		bsa.setBinaryHash(b.getHexDigest());
		bsa.setResource("binstore");
		bsa.setBucket("junit");
		int delres = bsa.getDeleteResult();
		bsa.store();
		Assert.assertTrue(delres<400);

		//--------------------------------------

		bsa = new BinaryStoreAdapter();
		bsa.load(access);
		bsa.setBinaryHash(b.getHexDigest());
		bsa.setResource("binstore");
		bsa.setBucket("junit");
		boolean existsAfterDelete  = bsa.getHeadResult();
		bsa.store();
		Assert.assertFalse(existsAfterDelete);

		//--------------------------------------

		bsa = new BinaryStoreAdapter();
		bsa.load(access);
		bsa.setBinary(b);
		String hexDigest = b.getHexDigest();
		bsa.setResource("binstore");
		bsa.setBucket("junit");
		String put  = bsa.getPutResult();
		bsa.store();
		Assert.assertTrue(hexDigest.equals(put));

		//--------------------------------------
		bsa = new BinaryStoreAdapter();
		bsa.load(access);
		bsa.setBinaryHash(b.getHexDigest());
		bsa.setResource("binstore");
		bsa.setBucket("junit");
		boolean existsAfterPut  = bsa.getHeadResult();
		bsa.store();
		Assert.assertTrue(existsAfterPut);
		//--------------------------------------


	}

	private Binary createBinary() {
		byte[] content = ("The time is:"+System.currentTimeMillis()+" :Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.").getBytes();
		Binary b = new Binary(content);
		b.setMimeType("text/plain");
		return b;
	}

	
	private RepositoryInstance createStubInstance(String deployment) {
		return new RepositoryInstance() {
			
			@Override
			public int compareTo(RepositoryInstance o) {
				return 0;
			}
			
			@Override
			public boolean requiredForServerStatus() {
				return false;
			}
			
			@Override
			public String repositoryType() {
				return null;
			}
			
			@Override
			public void removeOperation(AppStoreOperation op, Map<String, Object> settings) {
				
			}
			
			@Override
			public void refreshApplicationLocking() throws IOException {
				
			}
			
			@Override
			public void refreshApplication() throws IOException {
				
			}
			
			@Override
			public File getTempFolder() {
				return null;
			}
			
			@Override
			public Map<String, Object> getSettings() {
				return null;
			}
			
			@Override
			public String getRepositoryName() {
				return null;
			}
			
			@Override
			public File getRepositoryFolder() {
				return null;
			}
			
			@Override
			public File getOutputFolder() {
				return null;
			}
			
			@Override
			public List<String> getOperations() {
				return null;
			}
			
			@Override
			public Map<String, Object> getDeploymentSettings(Map<String, Object> source) {
				return null;
			}
			
			@Override
			public String getDeployment() {
				return deployment;
			}
			
			@Override
			public Set<String> getAllowedProfiles() {
				return null;
			}
			
			@Override
			public String applicationType() {
				return null;
			}
			
			@Override
			public void addOperation(AppStoreOperation op, Map<String, Object> settings) {
				
			}
		};
		
	}
	
}
