package com.dexels.navajo.adapter.soapmap;

import com.dexels.navajo.document.types.Binary;

public class SoapAttachment {

	public final Binary content;
	public final String mimeType;
	
	public SoapAttachment(Binary content, String mimeType) {
		this.content = content;
		this.mimeType = mimeType;
	}

	public Binary getContent() {
		return content;
	}

	public String getMimeType() {
		return mimeType;
	}
	
}
