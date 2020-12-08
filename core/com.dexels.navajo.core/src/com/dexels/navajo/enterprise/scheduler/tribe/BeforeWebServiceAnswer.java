/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.enterprise.scheduler.tribe;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.enterprise.scheduler.WebserviceListenerFactory;
import com.dexels.navajo.server.enterprise.tribe.Answer;

public class BeforeWebServiceAnswer extends Answer {

	private Navajo myNavajo;
	
	BeforeWebServiceAnswer(BeforeWebServiceRequest q) {
		super(q);
		// Emit 'beforeWebservice'.
		myNavajo = WebserviceListenerFactory.getInstance().beforeWebservice(q.getWebservice(), q.getMyAccess(), q.getIgnoreTaskIds(), true);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2110911541531797766L;

	@Override
	public boolean acknowledged() {
		return true;
	}

	public Navajo getMyNavajo() {
		return myNavajo;
	}

}
