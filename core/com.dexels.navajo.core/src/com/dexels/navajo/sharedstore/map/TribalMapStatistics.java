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
