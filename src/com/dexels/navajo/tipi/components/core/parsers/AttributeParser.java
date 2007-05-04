package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class AttributeParser
    extends BaseTipiParser {
  public Object parse(TipiComponent source, String expression, TipiEvent event) {
    return getAttributeByPath(source, expression);
  }

  private Object getAttributeByPath(TipiComponent source, String path) {
    String componentPath = path.substring(0, path.indexOf(":"));
    String attr = path.substring(path.indexOf(":") + 1);
    System.err.println("AttributeParse: Looking for component: "+componentPath);
    TipiComponent tc = getTipiComponent(source, componentPath);
    if (tc!=null) {
		System.err.println("Component found: "+tc.getId()+" class: "+tc.getClass());
	} else {
		System.err.println("No component found");

	}
    System.err.println("Looking for attribute: "+attr);
    Object o =  tc.getValue(attr);
		if(o!=null) {
			System.err.println("Component returned: "+o); 
		}
	    return o;
    }
}
