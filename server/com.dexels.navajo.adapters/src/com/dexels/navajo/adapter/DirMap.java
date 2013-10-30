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
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

/**
 * @author arjen
 *
 */
public class DirMap implements Mappable {

	public final List<FileEntryMap> fileEntry = new ArrayList<FileEntryMap>();
	public String path;
	public String extension;
	private File currentPath = null;
	
	private final static Logger logger = LoggerFactory.getLogger(DirMap.class);
	
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
		currentPath = new File(path);
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
	 * @see com.dexels.navajo.mapping.Mappable#load(com.dexels.navajo.server.Parameters, com.dexels.navajo.document.Navajo, com.dexels.navajo.server.Access, com.dexels.navajo.server.NavajoConfig)
	 */
	@Override
	public void load(Access access) throws MappableException, UserException {
	}



	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#store()
	 */
	@Override
	public void store() throws MappableException, UserException {
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#kill()
	 */
	@Override
	public void kill() {
	}


	

	public static void main(String[] args) throws Exception {
		DirMap fm = new DirMap();
		fm.setPath(".");
		FileEntryMap[] m = fm.getFileEntries();
		for (FileEntryMap f : m) {
			logger.info("file name: "+f.getName()+" age: "+f.getMimeType()+" age: "+f.getFileAge()+" size: "+f.getSize());
			if(f.getName().equals("aap.txt")) {
				logger.info("Aap detected!");
				f.setDelete(true);
			}
		}

	}
}
