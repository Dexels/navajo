package com.dexels.navajo.tipi.internal.cache;

import java.io.*;
import java.util.*;

public interface LocalStorage {
	public boolean hasLocal(String location);
	public Date getLocalModificationDate(String location);
	public InputStream getLocalData(String location);
	public void storeData(String location, InputStream data);
	public void flush(String location);
	public void flushAll(String location);
}
