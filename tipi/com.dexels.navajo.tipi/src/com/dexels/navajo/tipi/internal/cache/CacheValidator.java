/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal.cache;

import java.io.IOException;

public interface CacheValidator {
	public boolean isLocalValid(String location) throws IOException;
	public void update(String location) throws IOException;

	public void setLocalStorage(LocalStorage localStorage);

	public void setRemoteStorage(RemoteStorage remoteStorage);
	public void invalidate();
	boolean isClassLoaderValid(String location) throws IOException;
}
