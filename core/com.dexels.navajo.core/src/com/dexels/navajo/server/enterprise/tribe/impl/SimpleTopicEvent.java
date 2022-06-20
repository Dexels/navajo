/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server.enterprise.tribe.impl;

import java.io.Serializable;

import com.dexels.navajo.server.enterprise.tribe.TopicEvent;

public class SimpleTopicEvent implements TopicEvent {

	private static final long serialVersionUID = -1683345185876275324L;
	private final Serializable myMessage;
	
	public SimpleTopicEvent(Serializable msg) {
		myMessage = msg;
	}
	
	@Override
	public Object getMessage() {
		return myMessage;
	}

	@Override
	public Object getSource() {
		return null;
	}

}
