package com.dexels.navajo.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.mailmap.AttachmentMapInterface;
import com.dexels.navajo.document.types.Binary;

public class DummyAttachmentMap implements AttachmentMapInterface {

	private String attachFile;
	
	private final static Logger logger = LoggerFactory
			.getLogger(DummyAttachmentMap.class);
	
	public String getAttachFile() {
		return this.attachFile;
	}

	public Binary getAttachFileContent() {
		return null;
	}

	public String getAttachFileName() {
		return null;
	}

	public String getEncoding() {
		return null;
	}

	public void setAttachContentHeader(String s) {
		logger.debug("in setAttachContentHeader(" + s + ")");
	}

	public void setAttachFile(String attachFile) {
		logger.debug("in setAttachFile(" + attachFile + ")");
		this.attachFile = attachFile;
	}

	public void setAttachFileContent(Binary attachFileContent) {
		logger.debug("in setAttachFileContent(" + attachFileContent + ")");
	}

	public void setAttachFileName(String attachFileName) {
		logger.debug("in setAttachFileName(" + attachFileName + ")");
	}

	public void setEncoding(String s) {
		logger.debug("in setEncoding(" + s + ")");
	}

	public String getAttachContentDisposition() {
		return null;
	}

	public void setAttachContentDisposition(String s) {
		logger.debug("in setAttachContentDisposition(" + s + ")");
	}

	public String getAttachContentType() {
		return null;
	}

	public void setAttachContentType(String s) {
		logger.debug("in setAttachContentType(" + s + ")");
	}
}
