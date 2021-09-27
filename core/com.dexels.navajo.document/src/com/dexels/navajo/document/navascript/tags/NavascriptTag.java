/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.navascript.tags;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.base.BaseNavascriptImpl;
import com.dexels.navajo.document.base.BaseNode;

public class NavascriptTag extends BaseNavascriptImpl implements NS3Compatible {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1648795731441833541L;
	String author;
	String debug;
	String id;
	String notes;

	MapDefinitionInterrogator mapChecker;
	NS3Compatible parent;

	public NS3Compatible getParentTag() {
		return parent;
	}

	public void addParent(NS3Compatible p) {
		parent = p;
	}

	public NavascriptTag() {
		super(NavajoFactory.getInstance());
	}

	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setMapChecker(MapDefinitionInterrogator m) {
		mapChecker = m;
	}

	public MapDefinitionInterrogator getMapChecker() {
		return mapChecker;
	}

	public DefinesTag addDefines(DefinesTag defines) {
		super.addDefines(defines);
		defines.addParent(this);
		return defines;
	}

	public BreakTag addBreak(String condition, String id, String description) {
		BreakTag bt = new BreakTag(this, condition, id, description);
		super.addBreak(bt);
		bt.addParent(this);
		return bt;
	}

	public MapTag addMap(String condition, String object) {
		MapTag m = new MapTag(this, object, condition);
		super.addMap(m);
		m.addParent(this);
		return m;
	}

	public MessageTag addMessage(String name, String type) {
		MessageTag m = new MessageTag(this, name, type);
		super.addMessage(m);
		m.addParent(this);
		return m;
	}

	public ParamTag addParam(String condition, String value) {
		ParamTag pt = new ParamTag(this, condition, value);
		super.addParam(pt);
		pt.addParent(this);
		return pt;
	}

	// add <block/>
	public BlockTag addBlockTag(BlockTag bt) {
		super.addBlock(bt);
		bt.addParent(this);
		return bt;
	}

	public SynchronizedTag addSynchronizedTag(SynchronizedTag st) {
		super.addSynchronized(st);
		st.addParent(this);
		return st;
	}

	public FinallyTag addFinallyTag(FinallyTag ft) {
		super.addFinally(ft);
		ft.addParent(this);
		return ft;
	}

	public MethodsTag addMethodsTag(MethodsTag mt) {
		super.addMethods(mt);
		mt.addParent(this);
		return mt;
	}

	public IncludeTag addInclude(String script) {
		IncludeTag it = new IncludeTag(this, script);
		super.addInclude(it);
		it.addParent(this);
		return it;
	}

	public ValidationsTag addValidations() {
		ValidationsTag vt = new ValidationsTag(this);
		super.addValidations(vt);
		vt.addParent(this);
		return vt;
	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String, String> m = new HashMap<>();
		if ( author != null ) {
			m.put("author", getAuthor());
		}
		if ( id != null ) {
			m.put("id", getId());
		}
		if ( debug != null ) {
			m.put("debug", getDebug());
		}
		return m;
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("/*\n");
		if ( id != null) sb.append("   id      : " + id + "\n");
		if ( author != null) sb.append("   author  : " + author + "\n");
		if ( notes != null) sb.append("   notes   : " + notes + "\n");
		sb.append("   Copyright(c) 2021 Dexels BV\n");
		sb.append("*/\n\n");
		w.write(sb.toString().getBytes());
		for ( BaseNode c : getChildren() ) {
			if ( c instanceof NS3Compatible ) {
				((NS3Compatible) c).formatNS3(indent, w);
			}
		}

	}

	@Override
	public void addComment(CommentBlock cb) {
		super.addComment(cb);
		cb.addParent(this);
	}

	public void addDebug(DebugTag dt) {
		super.addDebug(dt);
		dt.addParent(this);
	}

}
