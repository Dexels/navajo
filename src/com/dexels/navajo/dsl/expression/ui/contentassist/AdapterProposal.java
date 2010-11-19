package com.dexels.navajo.dsl.expression.ui.contentassist;

import java.util.ArrayList;
import java.util.List;

public class AdapterProposal implements Comparable<AdapterProposal>{
	private String tagName;
	private final List<AdapterValueEntry> values = new ArrayList<AdapterValueEntry>();
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		System.err.println("tag: "+tagName);
		this.tagName = tagName;
	}

	public void addValueEntry(AdapterValueEntry e) {
		values.add(e);
	}

	@Override
	public int compareTo(AdapterProposal ap) {
		return getTagName().compareTo(ap.getTagName());
	}
	
	public String getRequiredSetters() {
		StringBuffer sb = new StringBuffer();
		for (AdapterValueEntry w : values) {
			if("in".equals(w.getDirection()) && w.isRequired()) {
				sb.append(w.getName()+"=\"["+w.getType()+"]\" ");
			}
		}
		return sb.toString();

	}

	public String getFullProposal() {
		return getTagName()+" "+getRequiredSetters()+">\n</map."+getTagName()+">";
	}
}
