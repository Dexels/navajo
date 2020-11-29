package com.dexels.navajo.document.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.document.MapTag;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Navascript;
import com.dexels.navajo.document.ParamTag;

public class BaseNavascriptImpl extends BaseNavajoImpl implements Navascript {

	private final List<Serializable> myChildren = new ArrayList<>();

	public BaseNavascriptImpl(NavajoFactory nf) {
		super(nf);
	}

	@Override
	public ParamTag addParam(ParamTag p) {
		myChildren.add(p);
		return p;
	}

	@Override
	public MapTag addMap(MapTag map) {
		myChildren.add(map);
		return map;
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

}
