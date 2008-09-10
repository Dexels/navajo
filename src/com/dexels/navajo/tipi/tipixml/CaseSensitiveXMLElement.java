package com.dexels.navajo.tipi.tipixml;

import java.util.*;

public class CaseSensitiveXMLElement extends XMLElement {
	public CaseSensitiveXMLElement() {
		super(new Hashtable<String, char[]>(), true, false);
	}
}