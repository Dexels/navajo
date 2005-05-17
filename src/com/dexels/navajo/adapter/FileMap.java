/*
 * Created on May 17, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dexels.navajo.adapter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.adapter.filemap.*;
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
public class FileMap implements Mappable {

	public String fileName;
	public String separator;
	public FileLineMap [] lines;
	public boolean persist = true;
	public Binary content;
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#load(com.dexels.navajo.server.Parameters, com.dexels.navajo.document.Navajo, com.dexels.navajo.server.Access, com.dexels.navajo.server.NavajoConfig)
	 */
	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
		// TODO Auto-generated method stub
	}

	private byte [] getBytes() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].getLine() != null) {
				baos.write(lines[i].getLine().getBytes());
			} 
		}
		baos.close();
		return baos.toByteArray();
	}
	
	public Binary getContent() throws UserException {
		try {
			Binary b = new Binary(getBytes());
			b.setMimeType("application/text");
			return b;
		}
		catch (Exception e) {
			throw new UserException(-1, e.getMessage());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#store()
	 */
	public void store() throws MappableException, UserException {
		if (persist && fileName != null) {
			File f = new File(fileName);
			try {
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
				bos.write(getBytes());
				bos.close();
			} catch (Exception e) {
				throw new UserException(-1, e.getMessage());
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#kill()
	 */
	public void kill() {
		// TODO Auto-generated method stub

	}

	public void setLines(FileLineMap [] l) {
		this.lines = l;
	}
	
	public void setFileName(String f) {
		this.fileName = f;
	}
	
	public void setSeparator(String s) {
		this.separator = s;
	}
	
	public static void main(String[] args) throws Exception {
		FileMap fm = new FileMap();
		FileLineMap [] flm = new FileLineMap[2];
		flm[0] = new FileLineMap();
		flm[0].setLine("apenoot");
		flm[1] = new FileLineMap();
		flm[1].setLine("kibbeling");
		fm.setLines(flm);
		fm.setFileName("/home/arjen/aap.txt");
		fm.store();
	}
}
