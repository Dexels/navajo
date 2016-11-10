package com.dexels.navajo.resource.binarystore;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.BinaryDigest;

public interface BinaryDigestStore {
		public Binary resolve(BinaryDigest digest);
		public void store(Binary b);
		public void delete(BinaryDigest b);

}
