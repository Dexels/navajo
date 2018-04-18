package com.dexels.navajo.resource.http;

public class HttpElement {

	public final String name;
	public final String type;
	public final long size;
	public final String hash;
	
	public HttpElement(String name, String type, long size, String hash) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.hash = hash;
	}

}
