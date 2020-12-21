/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on May 17, 2005
 *
 */
package com.dexels.navajo.adapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.dirmap.FileEntryMap;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

/**
 * @author arjen
 *
 */
public class DirMap implements Mappable {

	public final List<FileEntryMap> fileEntry = new ArrayList<>();
	public String path;
	public String extension;
	
	private static final Logger logger = LoggerFactory.getLogger(DirMap.class);
	
	public synchronized String getPath() {
		return path;
	}

	public FileEntryMap[] getFileEntries() {
		Object[] res = fileEntry.toArray();
		FileEntryMap[] result = new FileEntryMap[res.length];
		for (int i = 0; i<res.length;i++) {
			result[i] = (FileEntryMap) res[i];
		}
		
		return result;
	}


	public synchronized void setPath(String path) {
		this.path = path;
		File currentPath = new File(path);
		File[] aaa = currentPath.listFiles();
		for (File file : aaa) {
			if(file.isFile()) {
					FileEntryMap f = new FileEntryMap();
					try {
						f.setFile(file);
						fileEntry.add(f);
					
					} catch (IOException e) {
						logger.error("Error: ", e);
					}
	
			}
		}
	}



	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.Mappable#load(com.dexels.navajo.server.Parameters, com.dexels.navajo.document.Navajo, com.dexels.navajo.api.Access, com.dexels.navajo.server.NavajoConfig)
	 */
	@Override
	public void load(Access access) throws MappableException, UserException {
	}



	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.Mappable#store()
	 */
	@Override
	public void store() throws MappableException, UserException {
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.Mappable#kill()
	 */
	@Override
	public void kill() {
	}
}
