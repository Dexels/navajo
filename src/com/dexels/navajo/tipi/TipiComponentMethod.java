package com.dexels.navajo.tipi;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */
public class TipiComponentMethod {
	private Map<String, TipiValue> myArgs = new HashMap<String, TipiValue>();
	// private Map myInstanceArgs = new HashMap();
	private TipiAction myTipiAction = null;
	private final TipiComponent myComponent;

	public TipiComponentMethod(TipiComponent c) {
		myComponent = c;
	}

	public void load(XMLElement x) {
		if (!x.getName().equals("method")) {
			throw new IllegalArgumentException("Method components are supposed to be called 'method'");
		}
		List<XMLElement> v = x.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement child = v.get(i);
			if(child.getName().equals("description")) {
				continue;
			}
			String argName = child.getStringAttribute("name");
			TipiValue tv = new TipiValue(myComponent, child);
			// tv.load(child);
			myArgs.put(argName, tv);
		}
	}

	public void loadInstance(TipiAction instance) {
		myTipiAction = instance;
	}

	public void performMethod(TipiComponent source, TipiComponent current, XMLElement invocation) {
		System.err.println("Unimplemented method!");
	}

	public TipiValue getParameter(String name) {
		return myTipiAction.getParameter(name);
	}

	public Operand getEvaluatedParameter(String name, TipiEvent event) {
		TipiValue tv = getParameter(name);
		if (tv != null) {
			return myTipiAction.evaluate(tv.getValue(), event);
		} else {
			return null;
		}
	}

	public TipiAction getAction() {
		return myTipiAction;
	}

	public boolean checkFormat(String name, XMLElement invocation) {
		return true;
	}
}
