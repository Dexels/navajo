package com.dexels.navajo.resource.swift;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.resource.binarystore.BinaryStoreFactory;
import com.dexels.navajo.resource.swift.impl.SwiftReactiveImpl;


public class TestSwift {

	private static SwiftReactiveImpl osi;

//	@BeforeClass
	public static void setup() throws Exception {
		osi = new SwiftReactiveImpl();
		Map<String,Object> settings = new HashMap<>();
		settings.put("endpoint", "https://identity.api.rackspacecloud.com/v2.0");
		settings.put("username", System.getenv("username"));
		settings.put("apiKey", System.getenv("apiKey"));
//		settings.put("tenantId", System.getenv("tenantId"));
		settings.put("container", "test3");
//		settings.put("name", "testresource");
//		settings.put("tenant", "Generic");
		osi.activate(settings);

		BinaryStoreFactory factory = new BinaryStoreFactory();
		factory.activate();
		BinaryStoreFactory instance = BinaryStoreFactory.getInstance();
		instance.addBinaryStore(osi, settings);
	}

	@Test @Ignore
	public void testCreateContainer() throws IOException {
		osi.createContainer();
	}


	@Test @Ignore
	public void test() throws IOException {

		Binary b1 = new Binary(getClass().getResourceAsStream("logo.gif"));
		osi.store(b1);
		System.err.println("digest: "+b1.getDigest().hex());
//		Map<String,String> metadata = osi.metadata(b1.getDigest());
//		System.err.println("Meta: "+metadata);
//		Map<String,String> met = new HashMap<>();
//		met.put("aap", "noot");
//		met.put("mies", "wim");


		//		osi.store(b1);
//		osi.set("kip/some.png","image/png", b, met);
//		metadata = osi.metadata("kip/some.png");
//		System.err.println("Meta2: "+metadata);

//		osi.set(name, contents);
//
//		for (int i = 0; i < 10; i++) {
//			osi.set("dexlogo_"+i+".png", b);
//
//		}
	}

	@Test @Ignore
	public void testStoreURLBasedBinary() throws IOException {
		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b = new Binary(u,true);
		osi.store(b);
	}

	@Test @Ignore
	public void testFactory() throws IOException {
//		BinaryStore os = BinaryStoreFactory.getInstance().getBinaryStore("testresource", "Generic");
		Map<String,String> meta = new HashMap<String,String>();
		meta.put("aap", "noot");
		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b = new Binary(u,true);
		osi.store(b);
		Binary b2 = osi.resolve(b.getDigest());
		Assert.assertTrue(b2.getLength() > 0);
		System.err.println("B size: "+b.getLength());
		System.err.println("B2s size: "+b2.getLength());
		Assert.assertEquals(b, b2);
//		os.set("test/factory","image/png", b, meta);
//		os.set("test/factory","image/png", b, meta);
//		os.get("test/factory");
	}

	@Test @Ignore
	public void testNullMime() throws IOException {
		Map<String,String> meta = new HashMap<String,String>();
		meta.put("aap", "noot");
		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b = new Binary(u,true);
		osi.store(b);
//		osi.set("test/factory",null, b, meta);
	}

	@Test @Ignore
	public void testBrokenMime() throws IOException {
		Map<String,String> meta = new HashMap<String,String>();
		meta.put("aap", "noot");
		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b = new Binary(u,true);
		osi.store(b);
//		osi.set("test/factory","blablalba", b, meta);
	}


}
