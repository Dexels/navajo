/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.dexels.grus;

import java.util.Map;

import javax.sql.DataSource;

import com.dexels.navajo.script.api.UserException;


public interface GrusProvider {

	public GrusConnection requestConnection(String instance, String name) throws UserException;
	public GrusConnection requestReuseThreadConnection(String instance, String name) throws UserException;
	public boolean threadContainsConnection(String instance, String name);
	
	public String getDatabaseIdentifier(long id) throws UserException;
	public String getDatabaseIdentifier(String instance, String name) throws UserException;

	public GrusConnection requestConnection(long id);
	public void release(GrusConnection grusDataSource);
	public DataSource getInstanceDataSource(String instance, String name);
	public Map<String, Object> getInstanceDataSourceSettings(String instance, String name) throws UserException;
}
