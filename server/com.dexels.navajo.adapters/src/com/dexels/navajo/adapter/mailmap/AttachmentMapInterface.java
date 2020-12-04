/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter.mailmap;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.UserException;

public interface AttachmentMapInterface {

	public void setEncoding(String s);
	public String getEncoding();
	public Binary getAttachFileContent();
	public String getAttachContentDisposition();
	public String getAttachContentType();
	public void setAttachFileContent(Binary attachFileContent);
	public void setAttachFileName(String attachFileName);
	public String getAttachFileName();
	public String getAttachFile() throws UserException;
	public void setAttachFile(String attachFile) throws UserException;
	public void setAttachContentHeader(String s);
	public void setAttachContentDisposition(String s);
	public void setAttachContentType(String s);
}
