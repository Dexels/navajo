/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapters.stream;

import com.dexels.immutable.api.ImmutableMessage;

public interface Row {
	public Object get(String columnName);
	public Object get(int columnIndex);
	public Row withValue(String name, Object value);
	public ImmutableMessage toElement();

}
