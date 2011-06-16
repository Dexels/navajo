package com.dexels.navajo.tipi.tipixml;

import java.util.Hashtable;

public class CaseSensitiveXMLElement extends XMLElement {
	/**
	 * Serialization serial version ID.
	 */
	static final long serialVersionUID = 6685035139346394777L;

	public CaseSensitiveXMLElement() {
		super(new Hashtable<String, char[]>(), true, false);
	}
}