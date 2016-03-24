package com.dexels.navajo.resource.swift;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.resource.swift.impl.OpenstackStoreImpl;


public class TestSwift {

	@Test
	public void test() throws IOException {
		OpenstackStoreImpl osi = new OpenstackStoreImpl();
		Map<String,Object> settings = new HashMap<>();
		settings.put("endpoint", "https://identity.api.rackspacecloud.com/v2.0");
		settings.put("username", System.getenv("username"));
		settings.put("apiKey", System.getenv("apiKey"));
		settings.put("tenantId", System.getenv("tenantId"));
		settings.put("container", "test");

		osi.activate(settings);
		
		Binary result = osi.get("sharknado1.jpg");
		System.err.println("Result: "+result.getLength());

		URL u = new URL("https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png");
		Binary b = new Binary(u,true,true);		
		osi.set("dexlogo.png", b);

		for (int i = 0; i < 10; i++) {
			osi.set("dexlogo_"+i+".png", b);
			
		}
		//		OSClient os = OSFactory.builder()
//                .endpoint("https://identity.api.rackspacecloud.com/v2.0")
//                .raxApiKey(true)
//                .credentials("sportlinkinfrauk","a34a148f90594ce4946306bf1b821373")
//                .tenantId("10044839")
//                .authenticate();

	}

}
