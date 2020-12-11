/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.base;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dexels.navajo.document.Break;
import com.dexels.navajo.document.ExpressionTag;
import com.dexels.navajo.document.Field;
import com.dexels.navajo.document.MapAdapter;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Param;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.navascript.tags.FieldTag;
import com.dexels.navajo.document.navascript.tags.IncludeTag;

public class BaseMessageTagImpl extends BaseMessageImpl {

	List<Serializable> children = new ArrayList<>();
	Map<String,String> attributes = new HashMap<>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 5373087399563951223L;

	public BaseMessageTagImpl(Navajo n, String name) {
		super(n, name);
	}

	@Override
	public List<BaseNode> getChildren() {
		List<BaseNode> c = new ArrayList<>();
		for ( Serializable s : children ) {
			if ( s instanceof BaseNode ) {
				c.add((BaseNode) s);
			}
		}
		return c;
	}

	@Override
	public void addMap(MapAdapter m) {
		children.add(m);
	}

	public void removeMap(MapAdapter m) {
		children.remove(m);
	}

	@Override
	public Message addMessage(Message m) {
		children.add(m);
		return m;
	}

	@Override
	public void addParam(Param p) {
		children.add(p);
	}

	@Override
	public void addProperty(Property p) {
		children.add(p);
	}

	public void addField(FieldTag p) {
		children.add(p);
	}
	
	@Override
	public void addBreak(Break b) {
		children.add(b);
	}

	public void addInclude(IncludeTag it) {
		children.add(it);
	}

}
