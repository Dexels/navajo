package com.dexels.navajo.document.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.MapAdapter;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Param;
import com.dexels.navajo.document.Property;

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
	
}
