package com.dexels.navajo.document.base;

import java.util.List;
import java.util.Map;

public class BasePiggybackImpl extends BaseNode {

	private final Map piggyData;
	public BasePiggybackImpl(Map init) {
		piggyData = init;
	}
	public Map getAttributes() {
		return piggyData;
	}

	public List getChildren() {
		return null;
	}

	public String getTagName() {
		return "piggyback";
	}

}
