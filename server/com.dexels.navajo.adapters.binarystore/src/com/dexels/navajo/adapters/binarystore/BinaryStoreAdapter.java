package com.dexels.navajo.adapters.binarystore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.BinaryDigest;
import com.dexels.navajo.resource.binarystore.BinaryStore;
import com.dexels.navajo.resource.binarystore.BinaryStoreFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class BinaryStoreAdapter implements Mappable {

	private String name = null;
	private String tenant = null;
	private Binary value = null;
	private String resource = null;
	
	private static final Logger logger = LoggerFactory.getLogger(BinaryStoreAdapter.class);

	
	@Override
	public void load(Access access) throws MappableException, UserException {
		tenant = access.getTenant();
	}

	@Override
	public void store() throws MappableException, UserException {
		if(value!=null) {
			BinaryStore os = BinaryStoreFactory.getInstance().getBinaryStore(resource, tenant);
			if(os==null) {
				logger.warn("Can not find swift resource: {} for tenant: {}",resource,tenant);
				throw new UserException(-1, "Can not find binary store resource");
			}
			os.store(this.value);
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

	public Binary getResolve(BinaryDigest digest) {
		logger.info("Getting: {} from resource: {} with tenant: {}",name,resource,tenant);
		BinaryStore os = BinaryStoreFactory.getInstance().getBinaryStore(resource, tenant);
		return os.resolve(digest);
	}
	
	@Override
	public void kill() {

	}

}
