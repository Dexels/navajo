package com.dexels.navajo.tipi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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
public class TipiComponentMethod implements Serializable {

	private static final long serialVersionUID = -1549010350317406362L;
	private Map<String, TipiValue> myArgs = new HashMap<String, TipiValue>();
	// private Map myInstanceArgs = new HashMap();
	private TipiAction myTipiAction = null;
	private final TipiComponent myComponent;

	private Map<String, Object> parameters;

	public TipiComponentMethod(TipiComponent c) {
		myComponent = c;
	}

	public void load(XMLElement x) {
		if (!x.getName().equals("method")) {
			throw new IllegalArgumentException(
					"Method components are supposed to be called 'method'");
		}
		List<XMLElement> v = x.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement child = v.get(i);
			if (child.getName().equals("description")) {
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



	public TipiValue getParameter(String name) {
		return myTipiAction.getParameter(name);
	}

	public Operand getEvaluatedParameter(String name, TipiEvent event) {
		if (parameters != null) {
			Object o = parameters.get(name);
			// need to set the type?
			Operand op = new Operand(o, null, null);
			return op;
		}
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

	public Object getEvaluatedParameterValue(String string, TipiEvent event) {
		Operand o = getEvaluatedParameter(string, event);
		if (o == null) {
			return null;
		}
		return o.value;
	}

	public void loadParams(Map<String, Object> params) {
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		parameters.putAll(params);
		for (Map.Entry<String, Object> element : parameters.entrySet()) {
			TipiValue parameter = getParameter(element.getKey());
			if (parameter != null) {
				parameter.setValue(element.getValue());
			}
		}
	}
}
