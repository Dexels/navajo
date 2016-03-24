package com.dexels.navajo.resource.swift.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.storage.ObjectStorageService;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.model.storage.object.SwiftObject;
import org.openstack4j.openstack.OSFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.resource.swift.OpenstackStore;

public class OpenstackStoreImpl implements OpenstackStore {
	
	private ObjectStorageService storage;
	private String container = null;

	public void activate(Map<String,Object> settings) {
		String endpoint = (String) settings.get("endpoint");
		String username = (String) settings.get("username");
		String apiKey = (String) settings.get("apiKey");
		String tenantId = (String) settings.get("tenantId");
		container  = (String) settings.get("container");
		
		OSClient os = OSFactory.builder()
                .endpoint(endpoint)
                .raxApiKey(true)
                .credentials(username,apiKey)
                .tenantId(tenantId)
                .authenticate();
		
		this.storage = os.objectStorage();
		
		List<? extends SwiftContainer> containers = os.objectStorage().containers().list();
		for (SwiftContainer swiftContainer : containers) {
			System.err.println("Container: "+swiftContainer);
		}

		List<? extends SwiftObject> objects = os.objectStorage().objects().list("test");
		for (SwiftObject swiftObject : objects) {
			System.err.println("Object: "+swiftObject);
		}
		
		SwiftObject g = os.objectStorage().objects().get("test","sharknado1.jpg");
		System.err.println(">>> "+g);
	}

	public void deactivate() {
	}
	@Override
	public void set(String name, Binary contents) {
		storage.objects().put(this.container, name,Payloads.create(contents.getDataAsStream()));
	}

	@Override
	public Binary get(String name) {
		SwiftObject object = storage.objects().get(this.container, name);
		Binary result = new Binary(object.download().getInputStream());
		return result;

	}

	@Override
	public Map<String,Object> metadata(String name) {
		SwiftObject object = storage.objects().get(this.container, name);
		if(object==null) {
			return null;
		}
		Map<String,Object> result = new HashMap<>(object.getMetadata());
		result.put("etag", object.getETag());
		result.put("mime", object.getMimeType());
		result.put("size", object.getSizeInBytes());
		result.put("lastModified", object.getLastModified());

		return result;

	}

	@Override
	public void delete(String name) {
		storage.objects().delete(this.container, name);
	}
}
