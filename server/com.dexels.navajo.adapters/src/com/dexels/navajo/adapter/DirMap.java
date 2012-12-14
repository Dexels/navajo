/*
 * Created on May 17, 2005
 *
 */
package com.dexels.navajo.adapter;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.dirmap.FileEntryMap;
import com.dexels.navajo.adapter.dirmap.FileFilter;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

/**
 * @author arjen
 *
 */
public class DirMap implements Mappable {

    public final TreeMap<String, FileEntryMap> fileEntry = new TreeMap<String, FileEntryMap>();
	public String path;
	public String extension;
	private File currentPath = null;
    private FileFilter fileFilter = null;
	
	private final static Logger logger = LoggerFactory.getLogger(DirMap.class);
	
	public synchronized String getPath() {
		return path;
	}

	public FileEntryMap[] getFileEntries() {
        Object[] res = fileEntry.values().toArray();
        FileEntryMap[] result = new FileEntryMap[res.length];
        for (int i = 0; i < res.length; i++) {
            result[i] = (FileEntryMap) res[i];
        }
		
		return result;
	}

    public void setMask(String mask) {
        if (mask != null && ! mask.isEmpty()) {
            fileFilter = new FileFilter(mask);
        }
    }

    public void setInsensitive(Boolean insensitive) {
        if (fileFilter != null) {
            fileFilter.setInsensitive(insensitive);
        }
    }


	public synchronized void setPath(String path) {
		this.path = path;
		currentPath = new File(path);
        File[] aaa = currentPath.listFiles(fileFilter);
		for (File file : aaa) {
			if(file.isFile()) {
					FileEntryMap f = new FileEntryMap();
					try {
						f.setFile(file);
	                    fileEntry.put(f.getName(), f);
					
					} catch (IOException e) {
						e.printStackTrace();
					}
	
			}
		}
	}



	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#load(com.dexels.navajo.server.Parameters, com.dexels.navajo.document.Navajo, com.dexels.navajo.server.Access, com.dexels.navajo.server.NavajoConfig)
	 */
	public void load(Access access) throws MappableException, UserException {
	}



	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#store()
	 */
	public void store() throws MappableException, UserException {
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#kill()
	 */
	public void kill() {
	}


	

	public static void main(String[] args) throws Exception {
		DirMap fm = new DirMap();
        fm.setMask("*.*");
		fm.setPath("D:/tmp");
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
