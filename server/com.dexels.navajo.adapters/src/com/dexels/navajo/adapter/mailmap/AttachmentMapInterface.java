package com.dexels.navajo.adapter.mailmap;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.UserException;

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
