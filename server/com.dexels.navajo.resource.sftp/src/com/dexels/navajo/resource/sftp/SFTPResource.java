package com.dexels.navajo.resource.sftp;

import java.io.IOException;

import com.dexels.navajo.document.types.Binary;

public interface SFTPResource {
	public void send(String path, String filename, Binary content) throws IOException;
}
