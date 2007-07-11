/*
 * Created on Aug 12, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.studio.*;

public interface TipiStandaloneToplevelContainer {
	// public void setResourceBaseDirectory(File f);

	public void loadDefinition(String tipiPath, String definitionName, String resourceBaseDirectory) throws IOException, TipiException;

	public void loadDefinition(String tipiPath[], String[] definitionName, String resourceBaseDirectory) throws IOException, TipiException;

	public void loadDefinition(String name, InputStream contents, ActivityController al, String resourceBaseDirectory) throws IOException,
			TipiException;

	public void loadDefinition(String name, InputStream contents, ActivityController al, String resourceBaseDirectory, ClassLoader cl)
			throws IOException, TipiException;

	public ArrayList getListeningServices();

	public void loadClassPathLib(String location);

	public void loadNavajo(Navajo nav, String method) throws TipiException, TipiBreakException;

	public TipiContext getContext();

	public void shutDownTipi();

}
