package com.dexels.navajo.tipi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
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
 * @author not attributable
 * @version 1.0
 */
public class TipiValue implements Serializable {

	private static final long serialVersionUID = 3803476438347607550L;
//	private static final Logger logger = LoggerFactory.getLogger(TipiValue.class);
	private String name = null;

	private String type = null;

	private String direction = null;

	private Object value = null;

	private String defaultValue = null;
	private boolean required = false;
	private HashMap<String, String> selectionMap;
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiValue.class);

	private final TipiComponent myComponent;

	// / private Object rawValue = null;

	// private XMLElement myXml = null;
	public Object clone() {
		TipiValue tv = new TipiValue(this.myComponent);
		tv.name = this.name;
		tv.type = this.type;
		tv.direction = this.direction;
		tv.value = this.value;
		tv.defaultValue = this.defaultValue;
		tv.required = this.required;
		if (this.selectionMap != null) {
			tv.selectionMap = new HashMap<String, String>(this.selectionMap);
		}

		// com.dexels.navajo.tipi.TipiLink a;
		return tv;
	}

	public TipiValue(TipiComponent tc) {
		myComponent = tc;
	}

	public TipiValue(TipiComponent tc, XMLElement xe) {
		myComponent = tc;
		load(xe);
	}

	// public Object clone() {
	// TipiValue tv = new TipiValue();
	// tv.setName(name);
	// tv.setType(type);
	// tv.setRequired(required);
	// tv.setValue(value);
	// if (selectionMap != null) {
	// tv.setSelectionMap((HashMap) selectionMap.clone());
	// }
	// return tv;
	// }

	public void load(XMLElement xe) {
		// myXml = xe;
		assert xe.getName().equals("value") || xe.getName().equals("param")
				|| xe.getName().equals("description") : "Illegal tag detected: "
				+ xe.getName();
		if (!xe.getName().equals("value") && !xe.getName().equals("param")
				&& !xe.getName().equals("description")) {
			System.err
					.println("A tipi value element is supposed to be called: 'value' or even 'param', but definitely not '"
							+ xe.getName() + "' you wobbling chincilla");
		}
		this.name = xe.getStringAttribute("name");
		this.type = xe.getStringAttribute("type", "!no type");
		this.direction = xe.getStringAttribute("direction", "in");
		this.value = xe.getStringAttribute("value", "");
		this.defaultValue = xe.getStringAttribute("default", "");
		required = xe.getBooleanAttribute("required", "true", "false", false);
		if ("selection".equals(this.type)) {
			List<XMLElement> options = xe.getChildren();
			if (options.size() > 0) {
				selectionMap = new HashMap<String, String>();
				for (int i = 0; i < options.size(); i++) {
					XMLElement option = options.get(i);
					String selectionValue = option.getStringAttribute("value");
					String description = option.getStringAttribute(
							"description", selectionValue);
					selectionMap.put(selectionValue, description);
				}
			} else {
				throw new RuntimeException(
						"One or more options expected for selection value ["
								+ this.name + "]");
			}
		}
	}

	public Property createProperty() {
		try {
			Property p = NavajoFactory.getInstance().createProperty(
					myComponent.getContext().getStateNavajo(), getName(),
					getType(), "", 0, "", getDirection());
			p.setAnyValue(null);
			p.setSubType("tipitype=" + type);
			return p;
		} catch (NavajoException e) {
			logger.error("Error: ",e);
		}
		return null;
	}

	public String toString() {
		// if (myXml!=null) {
		// return myXml.toString();
		// }
		// return "Aap: "+super.toString();
		return toXml().toString();
	}

	private XMLElement toXml() {
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.setName("value");
		if(name!=null) {
			xe.setAttribute("name", name);
//		} else {
//			logger.warn("Converting TipiValue without name to xml! value: "+value+" type: "+type);
		}
		if (value != null) {
			xe.setAttribute("value", value);
		}
		if (defaultValue != null) {
			xe.setAttribute("default", defaultValue);
		}
		if (direction != null) {
			xe.setAttribute("direction", direction);
		}
		if (type != null) {
			xe.setAttribute("type", type);
		}
		return xe;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public void setType(String t) {
		type = t;
	}

	public void setDirection(String d) {
		direction = d;
	}

	public void setRequired(boolean r) {
		required = r;
	}

	public void setSelectionMap(HashMap<String, String> m) {
		selectionMap = m;
	}

	public String getType() {
		return type;
	}

	public String getDirection() {
		return direction;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValue(Object o) {
		if (o != null) {
			this.value = o;
		} else {
			this.value = null;
		}
	}

	public boolean isRequired() {
		return required;
	}

//	public boolean isValidSelectionValue(String selectionValue) {
//		if (selectionMap != null) {
//			if (selectionMap.get(selectionValue) != null) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public String getSelectionDescription(String selectionValue) {
//		return selectionMap.get(selectionValue);
//	}

	public String getValidSelectionValues() {
		String values = "";
		if (selectionMap != null) {
			Set<String> keySet = selectionMap.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				values = values + ", " + it.next();
			}
			return values.substring(2);
		} else {
			return null;
		}
	}

	public String getValue() {
		return value == null ? null : value.toString();
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String v) {
		defaultValue = v;
	}

	public Object getRawValue() {
		return value;
	}

	/**
	 * @param typeValue  
	 */
	public void typeCheck(Object typeValue) {
		/** @todo Implement this */
	}
}