package com.dexels.navajo.resource.http;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.config.runtime.TestConfig;
import com.dexels.navajo.client.stream.ReactiveReply;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.repository.api.AppStoreOperation;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.resource.http.adapter.BinaryStoreAdapter;
import com.dexels.navajo.resource.http.impl.ResourceComponent;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

import io.reactivex.Flowable;

public class TestHttpResource {

	private HttpResourceFactory factory;
	private Access access;

	@Before
	public void setUp() throws Exception {
		
		this.access = new Access();
		access.setTenant("MYTENANT");
		
		factory = new HttpResourceFactory();
		ResourceComponent component = new ResourceComponent();
		
		RepositoryInstance instance = createStubInstance("test");
		component.setRepositoryInstance(instance);
		Map<String,Object> settings = new HashMap<String, Object>();
		settings.put("url", TestConfig.HTTP_TEST_URL.getValue());
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
		HttpResourceFactory.getInstance().deactivate();	
	}

	@Test @Ignore
	public void testSimple() throws MappableException, UserException {
		long sz = Flowable.fromPublisher(factory.getHttpResource("binstore").get("tenant",TestConfig.HTTP_TEST_BUCKET.getValue(), "test1.png"))
			.reduce(new AtomicLong(),(size,data)->{size.addAndGet(data.length); return size;})
			.blockingGet().get();
		
		System.err.println("Size: "+sz);
		Assert.assertTrue(sz>13000);
	}
	
	@Test @Ignore
	public void testSHA() {
		System.err.println(factory.getHttpResource("binstore").expiringURL("tenant","example","test1.png"));
		
	}
	
	@Test
	public void testStoreAdapter() throws IOException, MappableException, UserException {
		BinaryStoreAdapter bsa = new BinaryStoreAdapter();
		
		
		
		bsa.load(access);
		byte[] content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.".getBytes();
		Binary b = new Binary(content);
		System.err.println(">>>> HASH: "+b.getHexDigest());
		b.setMimeType("text/plain");
		ReactiveReply result = bsa.deleteBinary(b, "binstore", "junit", false);
		System.err.println("Delete: "+result.status());
		boolean exists = bsa.headBinary(b, "binstore", "junit", false);
		Assert.assertFalse(exists);
		System.err.println("exists after delete: "+exists);
			
		ReactiveReply bb = bsa.storeBinary(b, "binstore", "junit", false);
		System.err.println("Result of put: "+bb);
		boolean existsNow = bsa.headBinary(b, "binstore", "junit", false);
		Assert.assertTrue(existsNow);
		System.err.println("exists after insert: "+existsNow);

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
