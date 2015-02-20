package com.dexels.navajo.tipi.swingclient.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Title: Seperate project for Navajo Swing client
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class RowAttribute {
	private Map<Integer, Object> myAttributes;

	public final static int ROW_BACKGROUND_COLOR = 1;
	public final static int ROW_FOREGROUND_COLOR = 2;


	public RowAttribute() {
	    myAttributes = new HashMap<Integer, Object>();
	}

	public Object getAttribute(int i){
	    return myAttributes.get(i);
	}
	
	public void setAttribute(int type, Object value) {
	    myAttributes.put(type,  value);
	}


}
