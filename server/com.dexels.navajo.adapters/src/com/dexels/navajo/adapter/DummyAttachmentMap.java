package com.dexels.navajo.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.mailmap.AttachmentMapInterface;
import com.dexels.navajo.document.types.Binary;

public class DummyAttachmentMap implements AttachmentMapInterface {

	private String attachFile;
	
	private final static Logger logger = LoggerFactory
			.getLogger(DummyAttachmentMap.class);
	
	@Override
	public String getAttachFile() {
		return this.attachFile;
	}

	@Override
	public Binary getAttachFileContent() {
		return null;
	}

	@Override
	public String getAttachFileName() {
		return null;
	}

	@Override
	public String getEncoding() {
		return null;
	}

	@Override
	public void setAttachContentHeader(String s) {
		logger.debug("in setAttachContentHeader(" + s + ")");
	}

	@Override
	public void setAttachFile(String attachFile) {
		logger.debug("in setAttachFile(" + attachFile + ")");
		this.attachFile = attachFile;
	}

	@Override
	public void setAttachFileContent(Binary attachFileContent) {
		logger.debug("in setAttachFileContent(" + attachFileContent + ")");
	}

	@Override
	public void setAttachFileName(String attachFileName) {
		logger.debug("in setAttachFileName(" + attachFileName + ")");
	}

	@Override
	public void setEncoding(String s) {
		logger.debug("in setEncoding(" + s + ")");
	}

	@Override
	public String getAttachContentDisposition() {
		return null;
	}

	@Override
	public void setAttachContentDisposition(String s) {
		logger.debug("in setAttachContentDisposition(" + s + ")");
	}

	@Override
	public String getAttachContentType() {
		return null;
	}

	@Override
	public void setAttachContentType(String s) {
		logger.debug("in setAttachContentType(" + s + ")");
	}
}
