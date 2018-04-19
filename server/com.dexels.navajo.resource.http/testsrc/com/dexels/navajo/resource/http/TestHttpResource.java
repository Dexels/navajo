package com.dexels.navajo.resource.http;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.dexels.config.runtime.TestConfig;
import com.dexels.navajo.resource.http.impl.ResourceComponent;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

import io.reactivex.Flowable;

public class TestHttpResource {

	private HttpResourceFactory factory;

	@Before
	public void setUp() throws Exception {
		factory = new HttpResourceFactory();
		ResourceComponent component = new ResourceComponent();
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

	@Test
	public void testSimple() throws MappableException, UserException {
		long sz = Flowable.fromPublisher(factory.getHttpResource("binstore").get(TestConfig.HTTP_TEST_BUCKET.getValue(), "test1.png"))
				.doOnNext(b->System.err.println(">>"+new String(b)))
			.reduce(new AtomicLong(),(size,data)->{size.addAndGet(data.length); return size;})
			.blockingGet().get();
		
		System.err.println("Size: "+sz);
		Assert.assertTrue(sz>13000);
	}
	
	@Test
	public void testSHA() {
		System.err.println(factory.getHttpResource("binstore").expiringURL("example","test1.png"));
		
	}
}
