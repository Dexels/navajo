/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.internal;

import java.io.File;

import com.dexels.navajo.tipi.internal.cache.CacheManager;
import com.dexels.navajo.tipi.internal.cache.impl.ClassLoaderStorageImpl;
import com.dexels.navajo.tipi.internal.cache.impl.FileLocalStorage;
import com.dexels.navajo.tipi.internal.cache.impl.FileRemoteStorage;
import com.dexels.navajo.tipi.internal.cache.impl.GeneralCacheManager;
import com.dexels.navajo.tipi.internal.cache.impl.LocalDigestCacheValidator;

public class CachedFileResourceLoader extends CachedResourceLoader {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5226489330806491180L;
	protected final CacheManager cache;

	public CachedFileResourceLoader(File baseDir, File baseRemoteFile, String id) {
		cache = new GeneralCacheManager(new ClassLoaderStorageImpl(id), new FileLocalStorage(baseDir),
				new FileRemoteStorage(baseRemoteFile), new LocalDigestCacheValidator(),id);
	}

	@Override
	public CacheManager getCacheManager() {
		return cache;
	}

	
}
