/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.http;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.UserException;

public interface HTTPMapInterface {

	public void setTextContent(String s);

	public void setContent(Binary b);

	public void setUrl(String s);

	public void setContentType(String s);

	public void setConnectTimeOut(int i);

	public void setReadTimeOut(int i);

	/**
	 * @param b  
	 */
	public void setDoSend(boolean b) throws UserException;

	public String getTextResult();

	public void setRequest(Binary b);

	public void setMaxInstances();

	public void setMethod(String method);

	public void setCatchConnectionTimeOut(
			boolean catchConnectionTimeOut);

}