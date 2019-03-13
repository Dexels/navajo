package com.dexels.navajo.adapter;

import java.io.File;
import java.io.FileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class NavajoLoadAdapter implements Mappable {

	private static final Logger logger = LoggerFactory.getLogger(NavajoLoadAdapter.class);
	public String pathPrefix;

	public String fileName;

	private Access access = null;

	public NavajoLoadAdapter() {
	}

	public void setPathPrefix(String p) {
		pathPrefix = p;
	}

	public void setFileName(String p) {
		fileName = p;
	}

	public String getPathPrefix() {
		return pathPrefix;
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public void kill() {
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		this.access = access;
	}

	@Override
	public void store() throws MappableException, UserException {
		String totalPath = fileName;
		logger.info("Total: {}", totalPath);
		File f = new File(totalPath);
		if (!f.exists()) {
			throw new MappableException("Could not open file!");
		}
		Navajo n = null;

		try (FileInputStream fr = new FileInputStream(f)) {
			n = NavajoFactory.getInstance().createNavajo(fr);
			access.setOutputDoc(n);
		}

		catch (Exception ex) {
			throw new MappableException("Could not parse file!", ex);
		}
	}

}
