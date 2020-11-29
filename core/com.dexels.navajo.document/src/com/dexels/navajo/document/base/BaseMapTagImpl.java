package com.dexels.navajo.document.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.FieldTag;
import com.dexels.navajo.document.MapTag;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.ParamTag;
import com.dexels.navajo.document.Property;

public class BaseMapTagImpl extends BaseNode implements MapTag {

	List<Serializable> children = new ArrayList<>();
	Map<String,String> attributes = new HashMap<>();
	MapTag parent;
	String name;
	String ref;
	String condition;
	String filter;
	
	public BaseMapTagImpl(Navajo n, String name, String condition) {
		super(n);
		this.name = name;
		this.condition = condition;
	}
	
	public BaseMapTagImpl(Navajo n, String ref, String filter, MapTag parent) {
		super(n);
		this.ref = ref;
		this.filter = filter;
		this.parent = parent;
	}
	
	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> attr = new HashMap<>();
		if ( condition != null && !"".equals(condition))  {
			attr.put("condition", condition);
		}
		if ( filter != null && !"".equals(filter))  {
			attr.put("filter", filter);
		}
		attr.putAll(attributes);
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
	
	public void setParent(MapTag m) {
		this.parent = m;
	}

	@Override
	public String getTagName() {
		if ( ref != null ) {
			return parent.getObject() + "." + ref;
		} else {
			return "map."+name;
		}
	}

	@Override
	public void addField(FieldTag f) {
		children.add(f);
		f.setParent(this);
	}

	@Override
	public void addParam(ParamTag p) {
		children.add(p);
	}

	@Override
	public void addMessage(Message m) {
		children.add(m);
	}

	@Override
	public String getObject() {
		return name;
	}

	@Override
	public void setObject(String s) {
		this.name = name;
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

	@Override
	public void addAttributeNameValue(String name, String expression) {
		attributes.put(name, expression);
	}

}
