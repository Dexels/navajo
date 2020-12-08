/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Feb 10, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiStorageManager;

public class TipiFileStorageManager implements TipiStorageManager, Serializable {

	// can be null

	private static final long serialVersionUID = -5542607178184878467L;
	private final boolean debugMode = true;
	private final File savingFolder;
	private String instanceId = "default";
	private final static Logger logger = LoggerFactory
			.getLogger(TipiFileStorageManager.class);

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

	@Override
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
			return null;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(in);
			Navajo n = NavajoFactory.getInstance().createNavajo(fis);
			return n;

		} catch (FileNotFoundException e) {
			logger.error("Error: ",e);
			throw new TipiException("File setting not found: "
					+ in.getAbsolutePath());
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("Error: ",e);
				}
			}
		}
	}

	@Override
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
			logger.info("Saving navajo to file: " + out);
			try {
				n.write(System.err);
			} catch (NavajoException e) {
				logger.error("Error: ",e);
			}
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(out);
			n.write(fos);
			fos.flush();
		} catch (NavajoException e) {
			logger.error("Error: ",e);
			throw new TipiException(
					"Error constructing file setting not found: " + id, e);
		} catch (IOException e) {
			logger.error("Error: ",e);
			throw new TipiException("Error writing file setting: " + id, e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error("Error: ",e);
				}
			}
		}

	}

	@Override
	public void setInstanceId(String id) {
		instanceId = id;
	}

	@Override
	public void setContext(TipiContext tc) {
		// TODO Auto-generated method stub

	}

}
