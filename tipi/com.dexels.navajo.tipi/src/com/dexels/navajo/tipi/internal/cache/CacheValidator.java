package com.dexels.navajo.tipi.internal.cache;

import java.io.IOException;

public interface CacheValidator {
	public boolean isLocalValid(String location) throws IOException;
}
