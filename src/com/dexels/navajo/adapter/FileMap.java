/*
 * Created on May 17, 2005
 *
 */
package com.dexels.navajo.adapter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.adapter.filemap.*;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

/**
 * @author arjen
 *
 */
public class FileMap implements Mappable {

	public String fileName;
	public String charsetName = null;
	public String separator;

	public FileLineMap line;
	public FileLineMap[] lines;

	public boolean persist = true;
	public boolean exists = false;

	public Binary content;

	private ArrayList lineArray = null;

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#load(com.dexels.navajo.server.Parameters, com.dexels.navajo.document.Navajo, com.dexels.navajo.server.Access, com.dexels.navajo.server.NavajoConfig)
	 */
	public void load(Access access) throws MappableException, UserException {
	}

	private byte[] getBytes() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for (int i = 0; i < lineArray.size(); i++) {
			FileLineMap flm = (FileLineMap) lineArray.get(i);
			if (flm.getLine() != null) {
				baos.write( ( charsetName == null ) ? flm.getLine().getBytes() : flm.getLine().getBytes( charsetName ) );
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
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#store()
	 */
	public void store() throws MappableException, UserException {
		if (persist && fileName != null) {
			File f = new File(fileName);
			if (f.exists()) {
				System.err.println("Deleting existing file");
				f.delete();
			}
			BufferedOutputStream bos = null;
			try {
				bos = new BufferedOutputStream(new FileOutputStream(f));
				bos.write(getBytes());
			} catch (Exception e) {
				throw new UserException(-1, e.getMessage());
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.mapping.Mappable#kill()
	 */
	public void kill() {
	}

	public void setLines(FileLineMap[] l) {
		if (lineArray == null) {
			lineArray = new ArrayList();
		}
		for (int i = 0; i < l.length; i++) {
			lineArray.add(l[i]);
		}
	}

	public void setLine(FileLineMap l) {
		if (lineArray == null) {
			lineArray = new ArrayList();
		}
		lineArray.add(l);
	}

	public void setFileName(String f) {
		this.fileName = f;
	}

	public void setCharsetName(String charset) {
		this.charsetName = charset;
	}

	public void setContent(Binary b) throws UserException {

		if (fileName == null) {
			throw new UserException(-1, "Set filename before setting content");
		}
		if (b == null) {
			throw new UserException(-1, "No or empty content specified");
		}

		this.content = b;
		try {
			FileOutputStream fo = new FileOutputStream(this.fileName);

			b.write(fo);
			fo.flush();
			fo.close();

			this.fileName = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSeparator(String s) {
		this.separator = s;
	}

	public boolean getExists() {
		return new File(fileName).exists();
	}

	public static void main(String[] args) throws Exception {
		FileMap fm = new FileMap();
		FileLineMap[] flm = new FileLineMap[2];
		flm[0] = new FileLineMap();
		flm[0].setLine("apenoot");
		flm[1] = new FileLineMap();
		flm[1].setLine("kibbeling");
		fm.setLines(flm);
		fm.setFileName("/home/arjen/aap.txt");
		fm.store();
	}
}
