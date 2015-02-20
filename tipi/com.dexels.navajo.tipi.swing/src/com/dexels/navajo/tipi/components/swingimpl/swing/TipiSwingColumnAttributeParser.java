package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.tipi.swingclient.components.ColumnAttribute;
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
public class TipiSwingColumnAttributeParser {
	private String name, type, column;
	private String myAttributeType;
	private Map<String, String> paramMap;

	public TipiSwingColumnAttributeParser() {
	}

	public TipiSwingColumnAttributeParser(XMLElement elm) {
	}

	public ColumnAttribute parseAttribute(XMLElement elm) {
	    paramMap = new HashMap<String, String>();
	    myAttributeType = ColumnAttribute.TYPE_UNKNOWN;
	    name = null;
	    type = null;
	    
		ColumnAttribute ca = new ColumnAttribute();
		name = (String) elm.getAttribute("name");
		type = (String) elm.getAttribute("type");
		column = (String) elm.getAttribute("column");
		List<XMLElement> kids = elm.getChildren();
		for (int i = 0; i < kids.size(); i++) {
			XMLElement child = kids.get(i);
			if (child.getName().equals("param")) {
				String param_name = (String) child.getAttribute("name");
				String param_value = (String) child.getAttribute("value");
				paramMap.put(param_name, param_value);
			}
		}
		if (type.equals(ColumnAttribute.TYPE_ROWCOLOR)) {
			myAttributeType = ColumnAttribute.TYPE_ROWCOLOR;
		} else if (type.equals(ColumnAttribute.TYPE_ROWTEXTCOLOR)) {
		    myAttributeType = ColumnAttribute.TYPE_ROWTEXTCOLOR;
		}
		
		ca.setName(name);
		ca.setType(myAttributeType);
		ca.setParams(paramMap);
		ca.setColumn(column);
		return ca;
	}

	public XMLElement storeAttribute(ColumnAttribute ca) {
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.setName("column-attribute");
		xe.setAttribute("type", ca.getType());
		xe.setAttribute("name", ca.getName());
		for (Iterator<String> iter = ca.getParamKeys().iterator(); iter
				.hasNext();) {
			String name = iter.next();
			String value = ca.getParam(name);
			XMLElement cc = new CaseSensitiveXMLElement();
			cc.setName("param");
			cc.setAttribute("name", name);
			cc.setAttribute("value", value);
			xe.addChild(cc);
		}
		return xe;
	}

	public String getType() {
		return myAttributeType;
	}

	public String getName() {
		return name;
	}

	public String getParam(String name) {
		return paramMap.get(name);
	}
}
