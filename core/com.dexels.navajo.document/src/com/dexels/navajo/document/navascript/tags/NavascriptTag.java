/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackReader;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.base.BaseNavascriptImpl;
import com.dexels.navajo.document.base.BaseNode;

public class NavascriptTag extends BaseNavascriptImpl implements NS3Compatible {

	MapDefinitionInterrogator mapChecker;

	public NavascriptTag() {
		super(NavajoFactory.getInstance());
	}

	public void setMapChecker(MapDefinitionInterrogator m) {
		mapChecker = m;
	}

	public MapDefinitionInterrogator getMapChecker() {
		return mapChecker;
	}

	public DefinesTag addDefines(DefinesTag defines) {
		super.addDefines(defines);
		return defines;
	}

	public BreakTag addBreak(String condition, String id, String description) {
		BreakTag bt = new BreakTag(this, condition, id, description);
		super.addBreak(bt);
		return bt;
	}

	public MapTag addMap(String condition, String object) {
		MapTag m = new MapTag(this, object, condition);
		super.addMap(m);
		return m;
	}

	public MessageTag addMessage(String name, String type) {
		MessageTag m = new MessageTag(this, name, type);
		super.addMessage(m);
		return m;
	}

	public ParamTag addParam(String condition, String value) {
		ParamTag pt = new ParamTag(this, condition, value);
		super.addParam(pt);
		return pt;
	}

	// add <block/>
	public BlockTag addBlockTag(BlockTag bt) {
		super.addBlock(bt);
		return bt;
	}

	public FinallyTag addFinallyTag(FinallyTag ft) {
		super.addFinally(ft);
		return ft;
	}

	public IncludeTag addInclude(String script) {
		IncludeTag it = new IncludeTag(this, script);
		super.addInclude(it);
		return it;
	}

	public ValidationsTag addValidations() {
		ValidationsTag vt = new ValidationsTag(this);
		super.addValidations(vt);
		return vt;
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		for ( BaseNode c : getChildren() ) {
			if ( c instanceof NS3Compatible ) {
				((NS3Compatible) c).formatNS3(indent, w);
			}
		}

	}

	@Override
	public void addComment(CommentBlock cb) {
		super.addComment(cb);
	}

}
