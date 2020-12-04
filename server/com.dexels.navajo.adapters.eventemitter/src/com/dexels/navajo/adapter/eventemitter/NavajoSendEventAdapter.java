/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter.eventemitter;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import navajoadaptersevent.Version;

import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.UserException;

public class NavajoSendEventAdapter implements Mappable {

	private final Map<String,Object> parameters = new HashMap<>();
	private String propertyName = null;
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		
	}

	@Override
	public void store() throws MappableException, UserException {
		
	}

	@Override
	public void kill() {
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
	public void setValue(Object value) {
		this.parameters.put(propertyName, value);
	}
	
	public void setSend(String topic) {
		ServiceReference<EventAdmin> sr = Version.getDefaultBundleContext().getServiceReference(EventAdmin.class);
		EventAdmin ea = Version.getDefaultBundleContext().getService(sr);
		ea.postEvent(new Event(topic,parameters));
		Version.getDefaultBundleContext().ungetService(sr);
	}
	
}
