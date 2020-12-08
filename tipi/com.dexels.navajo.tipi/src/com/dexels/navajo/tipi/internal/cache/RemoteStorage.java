/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal.cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public interface RemoteStorage {
	public long getRemoteModificationDate(String location) throws IOException;

	/**
	 * Get data from remote.
	 * @param location
	 * @param metaData Metadata to get from a connection. It will contain some HTTP headers.
	 * @return
	 * @throws IOException
	 */
	public InputStream getContents(String location, Map<String, Object> metaData)
			throws IOException;

	public URL getURL(String location) throws IOException;

}
