/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.sharedstore.map;

public class TribalMapStatistics {

	public String id;
	public int size;
	public long insertCount;
	public long deleteCount;
	public long getCount;
	public String keyType;
	public String valueType;
	
	public String getId() {
		return id;
	}
	public int getSize() {
		return size;
	}
	
	public String getKeyType() {
		return keyType;
	}
	public String getValueType() {
		return valueType;
	}
	
	public long getInsertCount() {
		return insertCount;
	}
	
	public long getDeleteCount() {
		return deleteCount;
	}
	
	public long getGetCount() {
		return getCount;
	}
	
}
