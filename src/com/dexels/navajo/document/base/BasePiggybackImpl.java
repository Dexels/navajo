package com.dexels.navajo.document.base;

import java.util.*;

public class BasePiggybackImpl extends BaseNode {

	private final Map<String,String> piggyData;
	public BasePiggybackImpl(Map<String,String> init) {
		piggyData = init;
	}
	public Map<String,String> getAttributes() {
		return piggyData;
	}

	public List<BaseNode> getChildren() {
		return null;
	}

	public String getTagName() {
		return "piggyback";
	}

}
