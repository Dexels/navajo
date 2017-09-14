package com.dexels.navajo.resource.binarystore;

import java.util.Map;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.BinaryDigest;

public interface BinaryStore {
	public Binary resolve(BinaryDigest digest);
	public void store(Binary b);
	public void delete(BinaryDigest b);
	public Map<String,String> metadata(BinaryDigest b);
	
}

