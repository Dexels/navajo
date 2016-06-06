package com.dexels.navajo.resource.swift.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.openstack4j.api.OSClient;
import org.openstack4j.api.storage.ObjectStorageService;
import org.openstack4j.core.transport.Config;
import org.openstack4j.core.transport.ProxyHost;
import org.openstack4j.model.common.Payload;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.identity.Access;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.model.storage.object.SwiftObject;
import org.openstack4j.model.storage.object.options.ObjectLocation;
import org.openstack4j.model.storage.object.options.ObjectPutOptions;
import org.openstack4j.openstack.OSFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.resource.binarystore.BinaryStore;

public class OpenstackStoreImpl implements BinaryStore {
	
	
	private final static Logger logger = LoggerFactory.getLogger(OpenstackStoreImpl.class);

	
	private ObjectStorageService storage;
	private String containerName = null;
	private SwiftContainer container = null;
	private Access osAccess = null;
	
	public void activate(Map<String,Object> settings) {
		String name = (String) settings.get("name");
		String tenant = (String) settings.get("instance");
		logger.info("Creating swift store for tenant: {} with name: {}",tenant,name);
		String endpoint = (String) settings.get("endpoint");
		String username = (String) settings.get("username");
		if(username==null) {
			// workaround for tenant bug
			username = (String) settings.get("user");
		}
		String apiKey = (String) settings.get("apiKey");
		String tenantId = (String) settings.get("tenantId");
		containerName  = (String) settings.get("container");

		OSClient os = OSFactory.builder()
				.withConfig(configureProxy())
                .endpoint(endpoint)
                .raxApiKey(true)
                .credentials(username,apiKey)
                .tenantId(tenantId)
                .authenticate();
		this.osAccess = os.getAccess();
		this.storage = os.objectStorage();
		container = findContainer(containerName);
		if(container==null) {
			logger.info("Container missing, creating container: {}",containerName);
			os.objectStorage().containers().create(containerName);
			container = findContainer(containerName);
		}
		
//		List<? extends SwiftObject> objects = this.storage.objects().list(containerName);
//		for (SwiftObject swiftObject : objects) {
//			System.err.println("Object: "+swiftObject);
//		}
		
	}

	private Config configureProxy() {
		String host = System.getenv("httpProxyHost");
		String port = System.getenv("httpProxyPort");
		if(host==null || port == null) {
			return Config.newConfig();
		}
		String hostURL = host.startsWith("http://")?host:"http://"+host;
		return Config.newConfig().withProxy(ProxyHost.of(hostURL, Integer.parseInt(port)));
	}
	private SwiftContainer findContainer(String name) {
		for (SwiftContainer swiftContainer : storage.containers().list()) {
			System.err.println("Container: "+swiftContainer);
			if(swiftContainer.getName().equals(containerName)) {
				return swiftContainer;
			}
		}
		return null;
	}

	public void deactivate() {
	}
	
	public SwiftContainer getContainer() {
		return container;
	}

	@Override
	public void set(String name, Binary contents,Map<String,String> metadata) {
		if(name==null) {
			logger.warn("Ignoring put without name");
			return;
		}
		if(contents==null) {
			logger.warn("Ignoring put without value. Name: {}",name);
			return;
		}
		Map<String,String> meta = new HashMap<>(metadata);
		meta.put("digest", new String(contents.getDigest()));
		Payload<InputStream> payload = Payloads.create(contents.getDataAsStream());
		
		ObjectPutOptions options = ObjectPutOptions
				.create()
				.contentType(contents.guessContentType())
				.metadata(metadata);

		OSFactory.clientFromAccess(osAccess).objectStorage().objects().put(this.containerName, name,payload,options);
	}

	@Override
	public Binary get(String name) {
		ObjectLocation location = ObjectLocation.create(this.containerName, name);
		
		SwiftObject object = OSFactory.clientFromAccess(osAccess).objectStorage().objects().get(location);
		if(object==null) {
			return new Binary();
		}
		Binary result = new Binary(object.download().getInputStream());
		return result;

	}

	
	@Override
	public Map<String,Object> metadata(String name) {
		SwiftObject object = storage.objects().get(this.containerName, name);
		if(object==null) {
			return null;
		}
		Map<String,Object> result = new HashMap<>();
		result.putAll(object.getMetadata());
		result.put("etag", object.getETag());
		result.put("mime", object.getMimeType());
		result.put("size", object.getSizeInBytes());
		result.put("lastModified", object.getLastModified());

		return result;

	}

	@Override
	public void delete(String name) {
		storage.objects().delete(this.containerName, name);
	}
}
