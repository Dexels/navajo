/*
 * Created on May 17, 2005
 *
 */
package com.dexels.navajo.adapter.filemap;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

/**
 * @author arjen
 */
public class FileRecordMap implements Mappable {

	public String record;
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.Mappable#load(com.dexels.navajo.server.Parameters, com.dexels.navajo.document.Navajo, com.dexels.navajo.api.Access, com.dexels.navajo.server.NavajoConfig)
	 */
	public void load(Access access) throws MappableException, UserException {
	

	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.Mappable#store()
	 */
	public void store() throws MappableException, UserException {


	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.Mappable#kill()
	 */
	public void kill() {
	

	}

	public void setRecord(String r) {
		this.record = r;
	}
}
