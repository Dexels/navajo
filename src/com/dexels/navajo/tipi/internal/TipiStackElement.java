package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.tipixml.*;

public class TipiStackElement {
	private String title;
	private int lineNr;
	private TipiStackElement parent;
	private String name;
	


	public TipiStackElement(TipiStackElement parent) {
		// def
		setParent(parent);
	}
	
	public TipiStackElement(String name,XMLElement me, TipiStackElement parent) {
		setTitle(me.findTitle());
		setLineNr(me.getStartLineNr());
		setName(name);
		setParent(parent);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getLineNr() {
		return lineNr;
	}
	public void setLineNr(int lineNr) {
		this.lineNr = lineNr;
	}
	public TipiStackElement getParent() {
		return parent;
	}
	public void setParent(TipiStackElement parent) {
		this.parent = parent;
	}

	public String createLine() {
		return "       at "+getName()+" ("+getTitle()+":"+(getLineNr()+1)+")";
	}

	public void dumpStack(String message) {
		System.err.println("Exception: "+message);
		dumpStack();
	}
	public void dumpStack() {
		System.err.println(createLine());
		if(getParent()!=null) {
			getParent().dumpStack();
		} else {
			System.err.println("No more parents");
		}
		
	}
}
