package com.dexels.navajo.adapter.mailmap;

import com.dexels.navajo.document.types.Binary;

public interface AttachmentMapInterface {

	public void setEncoding(String s);
	public String getEncoding();
	public Binary getAttachFileContent();
	public void setAttachFileContent(Binary attachFileContent);
	public void setAttachFileName(String attachFileName);
	public String getAttachFileName();
	public String getAttachFile();
	public void setAttachFile(String attachFile);
	public void setAttachContentHeader(String s);
}
