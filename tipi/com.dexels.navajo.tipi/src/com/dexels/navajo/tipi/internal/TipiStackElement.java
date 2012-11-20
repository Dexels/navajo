package com.dexels.navajo.tipi.internal;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.tipixml.XMLElement;

public class TipiStackElement implements Serializable {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiStackElement.class);
	private static final long serialVersionUID = 5599683663955830486L;
	private String title;
	private int lineNr;
	private TipiStackElement parent;
	private TipiStackElement rootCause;

	public TipiStackElement getRootCause() {
		return rootCause;
	}

	public void setRootCause(TipiStackElement rootCause) {
		if (rootCause != this) {
			this.rootCause = rootCause;
		}
	}

	private String name;

	public TipiStackElement(TipiStackElement parent) {
		// def
		setParent(parent);
	}

	public TipiStackElement(String name, XMLElement me, TipiStackElement parent) {
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
		return "       at " + getName() + " ("
				+ (getTitle() == null ? "init" : getTitle()) + ":"
				+ (getLineNr() + 1) + ")";
	}

	public void dumpStack(String message) {
		logger.error("Exception: " + message);
		dumpStack();
	}

	public void dumpStack() {
		logger.error(createLine());
		if (getParent() != null) {
			getParent().dumpStack();
		} else {
			if (rootCause != null && rootCause != this) {
				logger.error("Caused by:");
				rootCause.dumpStack();
			}
		}

	}
}
