/*
 * Created on May 17, 2005
 *
 */
package com.dexels.navajo.adapter.filemap;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

/**
 * @author arjen
 */
public class FileRecordMap implements Mappable {

	public String record;
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#load(com.dexels.navajo.server.Parameters, com.dexels.navajo.document.Navajo, com.dexels.navajo.server.Access, com.dexels.navajo.server.NavajoConfig)
	 */
	public void load(Access access) throws MappableException, UserException {
	

	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#store()
	 */
	public void store() throws MappableException, UserException {


	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#kill()
	 */
	public void kill() {
	

	}

	public void setRecord(String r) {
		this.record = r;
	}
}
