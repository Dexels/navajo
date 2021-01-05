/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.document.Break;
import com.dexels.navajo.document.Include;
import com.dexels.navajo.document.MapAdapter;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Navascript;
import com.dexels.navajo.document.Param;
import com.dexels.navajo.document.Validations;
import com.dexels.navajo.document.navascript.tags.BlockTag;
import com.dexels.navajo.document.navascript.tags.CommentBlock;
import com.dexels.navajo.document.navascript.tags.FinallyTag;

public class BaseNavascriptImpl extends BaseNavajoImpl implements Navascript {

	private final List<Serializable> myChildren = new ArrayList<>();

	public BaseNavascriptImpl(NavajoFactory nf) {
		super(nf);
	}

	public void addDefines(BaseNode defines) {
		myChildren.add(defines);
	}
	
	@Override
	public Param addParam(Param p) {
		myChildren.add(p);
		return p;
	}

	@Override
	public MapAdapter addMap(MapAdapter map) {
		myChildren.add(map);
		return map;
	}
	
	@Override
	public void addBreak(Break b) {
		myChildren.add(b);
	}
	
	@Override
	public Message addMessage(Message map) {
		myChildren.add(map);
		return map;
	}

	@Override
	public String getTagName() {
		return "navascript";
	}

	@Override
	public final List<BaseNode> getChildren() {
		
		List<BaseNode> children = new ArrayList<>();
		for ( Serializable s : myChildren ) {
			if ( s instanceof BaseNode ) {
				children.add((BaseNode) s);
			}
		}
		return children;
	}

	@Override
	public void addInclude(Include inc) {
		myChildren.add(inc);
	}
	
	@Override
	public void addValidations(Validations val) {
		myChildren.add(val);
	}

	public void addBlock(BlockTag bt) {
		myChildren.add(bt);
	}

	public void addFinally(FinallyTag ft) {
		myChildren.add(ft);
	}

	public void addComment(CommentBlock cb) {
		myChildren.add(cb);
	}

}
