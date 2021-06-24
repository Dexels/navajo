/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.sharedstore.map;

import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.Request;

public class GetRemoteObjectRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 467290545887579503L;
	
	private String guid = null;
	
	GetRemoteObjectRequest(RemoteReference remote) {
		guid = remote.getRef();
	}
	
	@Override
	public Answer getAnswer() {
		Object o = RemoteReference.getObject(guid);
		return new GetRemoteObjectAnswer(this, o);
	}

}
