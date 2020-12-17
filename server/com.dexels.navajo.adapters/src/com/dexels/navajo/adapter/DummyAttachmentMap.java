/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.mailmap.AttachementMap;
import com.dexels.navajo.adapter.mailmap.AttachmentMapInterface;
import com.dexels.navajo.document.types.Binary;

public class DummyAttachmentMap extends AttachementMap implements AttachmentMapInterface {

	private static final long serialVersionUID = 3502052945214585999L;

	
	private static final Logger logger = LoggerFactory
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
		logger.debug("in setAttachContentHeader({})",s);
	}

	@Override
	public void setAttachFile(String attachFile) {
		logger.debug("in setAttachFile({})",attachFile);
		this.attachFile = attachFile;
	}

	@Override
	public void setAttachFileContent(Binary attachFileContent) {
		logger.debug("in setAttachFileContent({})",attachFileContent);
	}

	@Override
	public void setAttachFileName(String attachFileName) {
		logger.debug("in setAttachFileName({})",attachFileName);
	}

	@Override
	public void setEncoding(String s) {
		logger.debug("in setEncoding({})",s);
	}

	@Override
	public String getAttachContentDisposition() {
		return null;
	}

	@Override
	public void setAttachContentDisposition(String s) {
		logger.debug("in setAttachContentDisposition({})",s);
	}

	@Override
	public String getAttachContentType() {
		return null;
	}

	@Override
	public void setAttachContentType(String s) {
		logger.debug("in setAttachContentType({})",s);
	}
}
