/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Aug 12, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi;

import java.io.IOException;

import com.dexels.navajo.document.Navajo;

import navajo.ExtensionDefinition;

public interface TipiStandaloneToplevelContainer {
	// public void setResourceBaseDirectory(File f);

	public void loadDefinition(String tipiPath, String definitionName,
			String resourceBaseDirectory, ExtensionDefinition ed)
			throws IOException, TipiException;

	public void loadClassPathLib(String location);

	public void loadNavajo(Navajo nav, String method) throws TipiException,
			TipiBreakException;

	public TipiContext getContext();

	public void shutDownTipi();

}
