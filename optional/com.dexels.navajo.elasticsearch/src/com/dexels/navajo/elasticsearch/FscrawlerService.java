package com.dexels.navajo.elasticsearch;

import java.io.IOException;

import com.dexels.navajo.document.types.Binary;

public interface FscrawlerService {
	public void upload(Binary data, String id, String name) throws IOException;
}
