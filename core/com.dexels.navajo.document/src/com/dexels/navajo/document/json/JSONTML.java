/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.json;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;

import com.dexels.navajo.document.Navajo;

public interface JSONTML {

	public void setEntityTemplate(Navajo m);
	
	public void setDateFormat(DateFormat format);
	
	public abstract Navajo parse(InputStream is) throws Exception;

	public abstract Navajo parse(InputStream is, String topLevelMessageName) throws Exception;
	
	public abstract Navajo parse(Reader r) throws Exception;
	
	public abstract Navajo parse(Reader r, String topLevelMessageName) throws Exception;

	public abstract void format(Navajo n, OutputStream os) throws Exception;
	
	public abstract void format(Navajo n, OutputStream os, boolean skipTopLevelMessage) throws Exception;

	public abstract void format(Navajo n, Writer w) throws Exception;
	
	public abstract void format(Navajo n, Writer w, boolean skipTopLevelMessage) throws Exception;
	
	
	/** 
	 * Format navajo definition style: the message type is used as its value
	 */
	public abstract void formatDefinition(Navajo n, Writer w, boolean skipTopLevelMessage) throws Exception;


}