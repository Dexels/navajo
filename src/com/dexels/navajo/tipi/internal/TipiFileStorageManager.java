/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.internal;

import java.io.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;

public class TipiFileStorageManager implements TipiStorageManager {

	// can be null

	private final boolean debugMode = true;
	private final File savingFolder;
	private String instanceId = "default";

	public TipiFileStorageManager(File savingFolder) {
		this.savingFolder = savingFolder;
		if (savingFolder == null) {
			File f = new File(System.getProperty("user.home"));
			savingFolder = new File(f, "sportlink-club");
		}
		if (!savingFolder.exists()) {
			savingFolder.mkdirs();
		}
	}

	public Navajo getStorageDocument(String id) throws TipiException {
		id = id.replace('/', '#');
		id = id.replace('\\', '$');
		id = id.replace(':', '_');

		File base;
		if (instanceId != null) {
			base = new File(savingFolder, instanceId);
		} else {
			base = savingFolder;
		}
		File in = new File(base, id);
		if (!in.exists()) {
			// throw new TipiException("File setting not found:
			// "+in.getAbsolutePath());
			return null;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(in);
			Navajo n = NavajoFactory.getInstance().createNavajo(fis);
			return n;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new TipiException("File setting not found: " + in.getAbsolutePath());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setStorageDocument(String id, Navajo n) throws TipiException {
		File base;

		id = id.replace('/', '#');
		id = id.replace('\\', '$');
		id = id.replace(':', '_');

		if (instanceId != null) {
			base = new File(savingFolder, instanceId);
		} else {
			base = savingFolder;
		}
		File out = new File(base, id);
		if (!base.exists()) {
			base.mkdirs();
		}
		if (debugMode) {
			// Thread.dumpStack();
			System.err.println("Saving navajo to file: " + out);
			try {
				n.write(System.err);
			} catch (NavajoException e) {
				e.printStackTrace();
			}
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(out);
			n.write(fos);
			fos.flush();
		} catch (NavajoException e) {
			e.printStackTrace();
			throw new TipiException("Error constructing file setting not found: " + id, e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new TipiException("Error writing file setting: " + id, e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void setInstanceId(String id) {
		instanceId = id;
	}

}
