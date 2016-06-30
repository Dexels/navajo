package com.dexels.navajo.resource.swift;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.resource.binarystore.BinaryStore;
import com.dexels.navajo.resource.binarystore.BinaryStoreFactory;
import com.dexels.navajo.resource.swift.impl.OpenstackStoreImpl;


public class TestSwift {

	
	private OpenstackStoreImpl osi;

	@Before
	public void setup() {
		osi = new OpenstackStoreImpl();
		Map<String,Object> settings = new HashMap<>();
		settings.put("endpoint", "https://identity.api.rackspacecloud.com/v2.0");
		settings.put("username", System.getenv("username"));
		settings.put("apiKey", System.getenv("apiKey"));
		settings.put("tenantId", System.getenv("tenantId"));
		settings.put("container", "test");
		settings.put("name", "testresource");
		settings.put("tenant", "Generic");
		osi.activate(settings);	
		
		BinaryStoreFactory factory = new BinaryStoreFactory();
		factory.activate();
		BinaryStoreFactory instance = BinaryStoreFactory.getInstance();
		instance.addBinaryStore(osi, settings);
	}
	
	@Test
	public void test() throws IOException {


//		Binary result = osi.get("sharknado1.jpg");
//		System.err.println("Result: "+result.getLength());
		Map<String,Object> metadata = osi.metadata("sharknado1.jpg");
		System.err.println("Meta: "+metadata);
		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b = new Binary(u,true,true);
		Map<String,String> met = new HashMap<>();
		met.put("aap", "noot");
		met.put("mies", "wim");
		osi.set("kip/some.png", b, met);
		
		metadata = osi.metadata("kip/some.png");
		System.err.println("Meta2: "+metadata);

//		osi.set(name, contents);
//		
//		for (int i = 0; i < 10; i++) {
//			osi.set("dexlogo_"+i+".png", b);
//			
//		}
	}
	
	@Test
	public void testCreateContainer() throws IOException {
		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b = new Binary(u,true,true);		
		osi.set("tic/tac/toe/dexlogo.png", b,new HashMap<String,String>());
		Assert.assertNotNull(osi.getContainer());
	}
	
	@Test
	public void testFactory() throws IOException {
		BinaryStore os = BinaryStoreFactory.getInstance().getBinaryStore("testresource", "Generic");
		Map<String,String> meta = new HashMap<String,String>();
		meta.put("aap", "noot");
		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b = new Binary(u,true,true);		
		os.set("test/factory", b, meta);
		os.set("test/factory", b, meta);
	}
	
	
	
	

}
