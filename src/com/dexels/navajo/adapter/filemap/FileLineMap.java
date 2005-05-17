/*
 * Created on May 17, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dexels.navajo.adapter.filemap;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

/**
 * @author arjen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileLineMap implements Mappable {

	public String line;
	public FileRecordMap [] records;
	public String separator;
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#load(com.dexels.navajo.server.Parameters, com.dexels.navajo.document.Navajo, com.dexels.navajo.server.Access, com.dexels.navajo.server.NavajoConfig)
	 */
	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#store()
	 */
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#kill()
	 */
	public void kill() {
	}

	public String getLine() {
		return this.line;
	}
	
	public void setLine(String l) {
		this.line = l + "\n";
	}
	
	public void setRecords(FileRecordMap [] r) {
		this.records = r;
		StringBuffer bf = new StringBuffer();
		for (int i = 0; i < r.length; i++) {
			bf.append(records[i].record);
			if (i < r.length - 1) {
				bf.append(this.separator);
			}
		}
		bf.append('\n');
		line = bf.toString();
	}
	
	public void setSeparator(String s) {
		this.separator = s;
	}
}
