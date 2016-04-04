package com.dexels.navajo.adapters.swift;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.resource.swift.OpenstackStorageFactory;
import com.dexels.navajo.resource.swift.OpenstackStore;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class SwiftAdapter implements Mappable {

	private String name = null;
	private String tenant = null;
	private Binary value = null;
	private String resource = null;
	
	
	private final static Logger logger = LoggerFactory.getLogger(SwiftAdapter.class);

	
	@Override
	public void load(Access access) throws MappableException, UserException {
		tenant = access.getTenant();
	}

	@Override
	public void store() throws MappableException, UserException {
		if(value!=null) {
			OpenstackStore os = OpenstackStorageFactory.getInstance().getOpenstackStore(resource, tenant);
			if(os==null) {
				logger.warn("Can not find swift resource: {} for tenant: {}",resource,tenant);
				throw new UserException(-1, "Can not find swift resource");
			}
			os.set(name, this.value, new HashMap<String,String>());
		}

	}

	public void setResource(String resource) {
		this.resource  = resource;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(Binary binary) {
		this.value = binary;
	}
	
	public Binary getGet(String name) {
		logger.info("Getting: {} from resource: {} with tenant: {}",name,resource,tenant);
		OpenstackStore os = OpenstackStorageFactory.getInstance().getOpenstackStore(resource, tenant);
		return os.get(name);
	}
	
	@Override
	public void kill() {
		// TODO Auto-generated method stub

	}

}
