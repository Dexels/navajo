package com.dexels.navajo.tipi.dev.server.appmanager;

public class ExtensionEntry {

	public ExtensionEntry() {
	}
	public ExtensionEntry(String element) {
		String[] elts = element.split("/");
		setName(elts[0]);
		setVersion(elts.length>1?elts[1]:"Any");
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	String name;
	String version;
}
