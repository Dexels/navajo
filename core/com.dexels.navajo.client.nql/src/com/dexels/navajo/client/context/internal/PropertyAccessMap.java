/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.context.internal;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.document.Property;

public class PropertyAccessMap implements Map<String, Property> {

	
	private final NavajoContext context;

	public PropertyAccessMap(NavajoContext n) {
		this.context = n;
	}
	@Override
	public void clear() {
		
	}

	@Override
	public boolean containsKey(Object key) {
		Property o = get(key);
		return o==null;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, Property>> entrySet() {
		return null;
	}

	@Override
	public Property get(Object key) {
		String path = (String)key;
		return context.parsePropertyPath(path);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public Property put(String key, Property value) {
		return null;
	}



	@Override
	public int size() {
		return 1;
	}

	@Override
	public Collection<Property> values() {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Property> m) {
		
	}

	@Override
	public Property remove(Object key) {
		return null;
	}

}
