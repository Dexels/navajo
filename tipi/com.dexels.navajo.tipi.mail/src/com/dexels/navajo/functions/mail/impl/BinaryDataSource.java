/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions.mail.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import com.dexels.navajo.document.types.Binary;

public class BinaryDataSource implements DataSource {

	private final Binary binary;

	public BinaryDataSource(Binary b) {
		this.binary = b;
	}
	
	@Override
	public String getContentType() {
		return binary.getMimeType();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return binary.getDataAsStream();
	}

	@Override
	public String getName() {
		return binary.getHandle();
	}

	/**
	 * This will append to the existing binary. Might not be what you want.
	 */
	@Override
	public OutputStream getOutputStream() throws IOException {
		return binary.getOutputStream();
	}

}
