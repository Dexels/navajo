/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
	@Override
	public void load(Access access) throws MappableException, UserException {
	

	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.Mappable#store()
	 */
	@Override
	public void store() throws MappableException, UserException {


	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.Mappable#kill()
	 */
	@Override
	public void kill() {
	

	}

	public void setRecord(String r) {
		this.record = r;
	}
}
