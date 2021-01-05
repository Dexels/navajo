/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Break;
import com.dexels.navajo.document.Field;
import com.dexels.navajo.document.MapAdapter;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Param;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.navascript.tags.BlockTag;
import com.dexels.navajo.document.navascript.tags.CommentBlock;
import com.dexels.navajo.document.navascript.tags.IncludeTag;
import com.dexels.navajo.document.navascript.tags.MapTag;

public class BaseMapTagImpl extends BaseNode implements MapAdapter {

	List<Serializable> children = new ArrayList<>();
	Map<String,String> attributes = new HashMap<>();
	MapAdapter parent;
	String name;
	String ref;
	String condition;
	String filter;
	boolean oldStyleMap;
	
	public void setOldStyleMap(boolean oldStyleMap) {
		this.oldStyleMap = oldStyleMap;
	}

	public BaseMapTagImpl(Navajo n) {
		super(n);
	}
	
	public BaseMapTagImpl(Navajo n, String name, String condition) {
		super(n);
		this.name = name;
		this.condition = condition;
	}
	
	public BaseMapTagImpl(Navajo n, String name, String condition, boolean oldStyleMap) {
		super(n);
		this.name = name;
		this.condition = condition;
		this.oldStyleMap = oldStyleMap;
	}
	
	public BaseMapTagImpl(Navajo n, String ref, String filter, MapAdapter parent) {
		super(n);
		this.ref = ref;
		this.filter = filter;
		this.parent = parent;
	}
	
	public BaseMapTagImpl(Navajo n, String ref, String filter, MapAdapter parent, boolean oldStyleMap) {
		super(n);
		this.ref = ref;
		this.filter = filter;
		this.parent = parent;
		this.oldStyleMap = oldStyleMap;
	}
	
	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> attr = new HashMap<>();
		attr.putAll(attributes);
		if ( !attr.containsKey("condition") && condition != null && !"".equals(condition))  {
			attr.put("condition", condition);
		}
		if ( !attr.containsKey("filter") && filter != null && !"".equals(filter))  {
			attr.put("filter", filter);
		}
		if ( !attr.containsKey("object") &&  oldStyleMap && name != null && ref == null)  {
			attr.put("object", name);
		}
		if ( !attr.containsKey("ref") &&  oldStyleMap && ref != null)  {
			attr.put("ref", ref);
		}
		return attr;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		List<BaseNode> c = new ArrayList<>();
		for ( Serializable s : children ) {
			if ( s instanceof BaseNode ) {
				c.add((BaseNode) s);
			}
		}
		return c;
	}
	
	public void setName(String s) {
		this.name = s;
	}
	
	public String getName() {
		return name;
	}
	
	public void setParent(MapAdapter m) {
		this.parent = m;
	}

	@Override
	public String getTagName() {
		if ( oldStyleMap ) {
			return "map";
		} else if ( ref != null ) {
			return parent.getObject() + "." + ref;
		} else {
			return "map."+name;
		} 
	}

	@Override
	public void addField(Field f) {
		children.add(f);
		f.setParent(this);
	}

	@Override
	public void addParam(Param p) {
		children.add(p);
	}

	@Override
	public void addMessage(Message m) {
		children.add(m);
	}
	
	@Override
	public void addMap(MapAdapter m) {
		children.add(m);
	}

	@Override
	public String getObject() {
		return name;
	}

	@Override
	public void setObject(String s) {
		this.name = s;
	}

	@Override
	public String getCondition() {
		return condition;
	}

	@Override
	public void setCondition(String s) {
		this.condition = s;
	}

	@Override
	public String getRefAttribute() {
		return ref;
	}

	@Override
	public void setRefAttribute(String s) {
		this.ref = s;
	}

	@Override
	public String getFilter() {
		return filter;
	}

	@Override
	public void setFilter(String s) {
		this.filter = s;
	}

	@Override
	public Object getRef() {
		return null;
	}

	@Override
	public void addProperty(Property p) {
		children.add(p);
	}
	
	public void addAttributes(Map<String,String> attr) {
		attributes.putAll(attr);
	}

	@Override
	public void addAttributeNameValue(String name, String expression) {
		attributes.put(name, expression);
	}
	
	@Override
	public void addBreak(Break b) {
		children.add(b);
	}

	public void addInclude(IncludeTag it) {
		children.add(it);
	}

	public void addBlock(BlockTag bt) {
		children.add(bt);
	}

	public void addComment(CommentBlock cb) {
		children.add(cb);
	}

}
