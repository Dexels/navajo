/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.enterprise.scheduler.tribe;

import com.dexels.navajo.server.enterprise.scheduler.WebserviceListenerFactory;
import com.dexels.navajo.server.enterprise.tribe.Answer;

public class AfterWebServiceAnswer extends Answer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3939068318168197624L;

	
	public AfterWebServiceAnswer(AfterWebServiceRequest q) {
		super(q);
		WebserviceListenerFactory.getInstance().afterWebservice(q.getWebservice(), q.getMyAccess(), q.getIgnoreTaskIds(), true);
	}
	
	@Override
	public boolean acknowledged() {
		return true;
	}

}
